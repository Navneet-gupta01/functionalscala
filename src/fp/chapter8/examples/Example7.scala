package fp.chapter8.examples

import Prop3._
import fp.chapter6.examples.RNG
import fp.chapter5.exercises.Stream1
import fp.chapter5.exercises.Cons
import fp.chapter8.examples.Gen2._


object Prop3 {
  type FailedCase  = String
  type SuccessCount = Int
  type TestCases = Int
  type Result = Either[FailedCase,(Status,SuccessCount)]
  
  def forAll[A](a: Gen2[A])(f: A => Boolean): Prop3 = Prop3 {
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
  
  def folAll2[A](g: SGen1[A])(f: A => Boolean): Prop3 = ???
  
  /*
   * an you see how this function is not possible to implement? SGen is expecting to be told a size, 
   * but Prop does not receive any size information. Much like we did with the source of randomness 
   * and number of test cases, we simply need to propagate this dependency to Prop. But rather than just 
   * propagating this dependency as is to the caller of Prop, we are going to have Prop accept a maximum size. 
   * This puts Prop in charge of invoking the underlying generators with various sizes, up to and including the
   * maximum specified size, which means it can also search for the smallest failing test case.
   */
  
  
  def buildMsg[A](s: A, e: Exception): String =
      "test case: " + s + "\n" +
      "generated an exception: " + e.getMessage + "\n" +
      "stack trace:\n" + e.getStackTrace.mkString("\n")
          
}

trait Status

case object Proven extends Status
case object Unfalsified extends Status
case object Exhausted extends Status

case class Prop3(run : (TestCases,RNG) => Result) {
  
  def &&(p:Prop3):Prop3 = Prop3 {
    (n,rng) => run(n,rng) match {
      case Right((s,n)) => p.run(n,rng).right.map {case(s,m) => (s,n+m)}
      case l => l
    }
  }
  
  def ||(p:Prop3):Prop3 = Prop3 {
    (n,rng) => run(n,rng) match {
      case Left(f) => p.tag(f).run(n,rng)
      case r => r
    }
  }
  
  /* This is rather simplistic - in the event of failure, we simply prepend
   * the given message on a newline in front of the existing message.
   */
  def tag(msg: String) = Prop3 {
    (n,rng) => run(n,rng) match {
      case Left(e) => Left(msg + "\n" + e)
      case r => r
    }
  }
}

//trait Prop3 {
//  def run: Either[FailedCase,(Status,SuccessCount)]
//}

/*
 * A test can succeed by being proven, if the domain has been fully enumerated and no counterexamples found,
 * or it can be merely unfalsified, if the test runner had to resort to random sampling.
 * Prop is now nothing more than a non-strict Either. But Prop is still missing some information—we have not specified
 * how many test cases to examine before we consider the property to be passed.
 */

