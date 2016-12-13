package fp.chapter8.examples

import Prop4._
import fp.chapter6.examples.RNG
import fp.chapter5.exercises.Stream1
import fp.chapter5.exercises.Cons
import fp.chapter8.examples.Gen2._
import fp.chapter8.examples.Prop3._
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import fp.chapter7.examples.Par
import fp.chapter7.examples.Par.Par

object Prop4{
  type FailedCase  = String
  type SuccessCount = Int
  type TestCases = Int
  type Result = Either[FailedCase,(Status,SuccessCount)]
  
  type MaxSize = Int
    
  def forAll[A](a: Gen2[A])(f: A => Boolean): Prop4 = Prop4 {
    (n,rng) => {
      def go(i: Int, j: Int, s: Stream1[Option[A]], onEnd: Int => Result): Result =
        if (i == j) Right((Unfalsified, i))
        else s match {
          case Cons(h,t) => h() match {
            case Some(h) => 
              try { 
                if (f(h)) go(i+1,j,t(),onEnd)
                else Left(h.toString) 
              } catch { 
                case e: Exception => Left(buildMsg(h, e)) 
              }
            case None => Right((Unfalsified,i))
          }
          case _ => onEnd(i)
        }
      go(0, n/3, a.exhaustive, i => Right((Proven, i))) match {
        case Right((Unfalsified,_)) =>
          val rands = randomStream(a)(rng).map(Some(_))
          go(n/3, n, rands, i => Right((Unfalsified, i)))
        case s => s
      }
    }
  }
  
  /* The sized case of `forAll` is as before, though we convert from `Proven` to
   * `Exhausted`. A sized generator can never be proven, since there are always
   * larger-sized tests that were not run which may have failed.
   */
  def forAll[A](g: Int => Gen2[A])(f: A => Boolean): Prop4 = Prop4 {
    (max,n,rng) => {
      val casesPerSize = n / max + 1
      val props:List[Prop4] = 
        Stream1.from(0).take(max+1).map( i => forAll(g(i))(f)).toList
        
     val p =  props.map(p => 
        Prop4(
            (max,n,rng) => 
              p.run(max,casesPerSize,rng))).reduceLeft(_ && _)
      p.run(max,n,rng).right.map {
        case (Proven,n) => (Exhausted,n)
        case x => x
      }
    }
  }
  
  
  /*
   * This implementation highlights a couple minor problems with our representation of Prop. For one, 
   * there are actually now three ways that a property can succeed. It can be proven, if the domain of 
   * the generator has been fully examined (for instance, a SGen[Boolean] can only ever generate two distinct values,
   * regardless of size). It can be exhausted, if the domain of the generator has been fully examined, 
   * but only up through the maximum size. Or it could be merely unfalsified, if we had to resort to random 
   * generation and no counterexamples were found
   */
  
  
  def apply(f: (TestCases,RNG) => Result): Prop4 =
    Prop4 { (_,n,rng) => f(n,rng) }
  
  
  /* We pattern match on the `SGen`, and delegate to our `Gen` version of `forAll`
   * if `g` is unsized; otherwise, we call the sized version of `forAll` (below).
   */
  def forAll[A](g: SGen1[A])(f: A => Boolean): Prop4 = g match {
    case Unsized(g2) => forAll(g2)(f)
    case Sized(gs) => forAll(gs)(f)
  }
  
  
  def run(p:Prop4,
          maxSize: Int = 100,
          testCases: Int = 100,
          rng: RNG = RNG.simple(System.currentTimeMillis)): Unit = {
    p.run(maxSize,testCases,rng) match {
      case Left(msg) => println("! test cases failed: \n " + msg)
      
      case Right((Unfalsified,n)) => 
        println("+ property unfalsified , ran " + n + " test cases")
        
      case Right((Proven,n)) => 
        println(" + property proven, ran " + n + " tests")
        
      case Right((Exhausted,n)) => 
        println("+ property unfalsified up to max size, ran " + n + " test cases.")
        
      case Right(_) => println("Default case")
    }
  }
  
  val ES: ExecutorService = Executors.newCachedThreadPool
  
  ///val ES1 : ExecutorService = Executors.newFixedThreadPool
  
  val p1 = Prop4.forAll(Gen2.unit(Par.unit(1)))(i =>
    Par.map(i)(_ + 1)(ES).get == Par.unit(2)(ES).get)
  
  def check(p: => Boolean): Prop4 =
    forAll(unit(()))(_ => p)
    
  val S = weighted(
    choose(1,4).map(Executors.newFixedThreadPool) -> .75, 
    unit(ES) -> .25)
    
  def forAllPar[A](g: Gen2[A])(f: A => Par[Boolean]): Prop4 = 
    forAll(S.map2(g)((_, _))) { case (s,a) => f(a)(s).get }
  
  def checkPar(p: Par[Boolean]): Prop4 = 
    forAllPar(Gen2.unit(()))(_ => p)
    
  def forAllPar2[A](g: Gen2[A])(f: A => Par[Boolean]): Prop4 = 
    forAll(S ** g) { case (s,a) => f(a)(s).get}
  
  def forAllPAr3[A](g: Gen2[A])(f: A => Par[Boolean]): Prop4 = 
    forAll(S ** g){ case s ** a => f(a)(s).get }
  
  val pint = Gen2.choose(0,10) map (Par.unit(_))
  val p4 =
    forAllPar(pint)(n => Par.equal(Par.map(n)(y => y), n))
    
    
}


case class Prop4(run: (MaxSize,TestCases,RNG) => Result) {
   
  def &&(p:Prop4):Prop4 = Prop4 {
    (max,n,rng) => run(max,n,rng) match {
      case Right((s,n)) => p.run(max,n,rng).right.map {case(s,m) => (s,n+m)}
      case l => l
    }
  }
  
  def ||(p:Prop4):Prop4 = Prop4 {
    (max,n,rng) => run(max,n,rng) match {
      case Left(f) => p.tag(f).run(max,n,rng)
      case r => r
    }
  }
  
  /* This is rather simplistic - in the event of failure, we simply prepend
   * the given message on a newline in front of the existing message.
   */
  def tag(msg: String) = Prop4 {
    (max,n,rng) => run(max,n,rng) match {
      case Left(e) => Left(msg + "\n" + e)
      case r => r
    }
  }
}

sealed trait Status1 {}

object Status1 {
  case object Exhausted extends Status1
  case object Proven extends Status1
  case object Unfalsified extends Status1
}
