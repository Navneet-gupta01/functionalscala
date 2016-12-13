package fp.chapter8.examples

import fp.chapter6.examples.State
import fp.chapter6.examples.RNG
import fp.chapter5.exercises.Stream1
import fp.chapter5.exercises.Cons
import fp.chapter7.examples.Par
import fp.chapter7.examples.Par.Par


//fp.chapter5.exercises.Cons
import Gen2._

object Gen2 {
  // here We'll adopt the convention that a None in exhaustive signals to the test runner should 
  // switch to random sampling, because the domain is infinite or otherwise not worth fully enumerating
  // If the domain can be fully enumerated, exhaustive will be a finite stream of Some values.
  type Domain[+A] = Stream1[Option[A]]

  def bounded[A](a: Stream1[A]): Domain[A] = a map ( x => Some(x))
  def unbounded: Domain[Nothing] = Stream1(None)
  
  def unit[A](a : => A): Gen2[A] = Gen2(State.unit(a),bounded(Stream1(a)))
  
  def boolean: Gen2[Boolean] = Gen2(State(RNG.boolean),bounded(Stream1(true,false)))
  
  def choose(start: Int, stopExclusive: Int): Gen2[Int] = 
    Gen2(State(RNG.nonNegativeInt).map(n => start + n % (stopExclusive - start)),bounded(Stream1.from(start).take(stopExclusive-start)))
   
  def map2Option[A,B,C](oa: Option[A], ob: Option[B])(f: (A,B) => C): Option[C] = 
    for {
      a <- oa
      b <- ob
    } yield f(a,b)
  
  
  def map2Stream1[A,B,C](sa: Stream1[A],sb: Stream1[B])(f: (A, B) => C) : Stream1[C] = 
    for {
      a <- sa;
      b <- sb
    } yield f(a,b)
    
    
  /* `cartesian` generates all possible combinations of a `Stream[Stream[A]]`. For instance:
   *
   *    cartesian(Stream(Stream(1,2), Stream(3), Stream(4,5))) ==
   *    Stream(Stream(1,3,4), Stream(1,3,5), Stream(2,3,4), Stream(2,3,5))
  */
  def cartesian[A](s: Stream1[Stream1[A]]): Stream1[Stream1[A]] =
    s.foldRight(Stream1(Stream1[A]()))((hs,ts) => map2Stream1(hs,ts)(Stream1.cons(_,_)))
   
    
  /* This implementation is rather tricky, but almost impossible to get wrong
   * if you follow the types. It relies on several helper functions (see below).
   */
  def listOfN[A](n: Int, g: Gen2[A]): Gen2[List[A]] = 
    Gen2(State.sequence(List.fill(n)(g.sample)),cartesian(Stream1.constant(g.exhaustive).take(n)).map(l => sequenceOption(l.toList)))
    
  def sequenceOption[A](o: List[Option[A]]) : Option[List[A]] = 
    o.foldLeft[Option[List[A]]](Some(List()))((a,b) => map2Option(b,a)(_ :: _)).map(_.reverse)
  
  /** Between 0 and 1, not including 1. */
  def uniform: Gen2[Double] = Gen2(State(RNG.double),unbounded)
  
  /** Between `i` and `j`, not including `j`. */
  def choose(i: Double, j: Double): Gen2[Double] = Gen2(State(RNG.double).map(n => i + n % (j-i)),unbounded)
  
  /* Basic idea is add 1 to the result of `choose` if it is of the wrong
   * parity, but we require some special handling to deal with the maximum
   * integer in the range.
   */
  def even(start: Int, stopExclusive: Int): Gen2[Int] = 
    choose(start, if(stopExclusive % 2 == 0) stopExclusive-1 else stopExclusive)
    .map( x=> if(x%2 !=0) x+1 else x)
    
  def odd(start: Int, stopExclusive: Int): Gen2[Int] =
    choose(start, if(stopExclusive % 2 != 0) stopExclusive-1 else stopExclusive)
    .map(n => if(n%2==0) n+1 else n)
    
  def sameParity(from: Int, to: Int): Gen2[(Int,Int)] = for {
    i <- choose(from, to)
    j <- if(i % 2 == 0) even(from,to) else odd(from,to)
  } yield (i,j)

  /* The simplest possible implementation. This will put all elements of one
   * `Gen` before the other in the exhaustive traversal. It might be nice to
   * interleave the two streams, so we get a more representative sample if we
   * don't get to examine the entire exhaustive stream.
   */
  def union_1[A](g1: Gen2[A], g2: Gen2[A]): Gen2[A] = boolean.flatMap( x => if(x) g1 else g2)
  
  //union, for combining two generators of the same type into one, by pulling values from each generator with equal likelihood.
  def union[A](g1: Gen2[A], g2: Gen2[A]): Gen2[A] = 
    Gen2(
        State(RNG.boolean).flatMap(x => if(x) g1.sample else g2.sample),
        interleave(g1.exhaustive,g2.exhaustive)
        )
  
  def interleave[A](s1: Stream1[A],s2: Stream1[A]): Stream1[A] = 
    s1.zipAll(s2).flatMap{ case (a,a1) =>  Stream1((a.toList ++ a1.toList ):_*)}
  
  //weighted, a version of union which accepts a weight for each Gen and generates values from each Gen
  //with probability proportional to its weight.
  def weighted[A](g1: (Gen2[A],Double), g2: (Gen2[A],Double)): Gen2[A] = {
    
    /* The probability we should pull from `g1`. */
    val g1Threshold = g1._2.abs/ (g1._2.abs + g2._2.abs)
    
    /* Some random booleans to use for selecting between g1 and g2 in the exhaustive case.
     * Making up a seed locally is fine here, since we just want a deterministic schedule
     * with the right distribution. */
    def bools: Stream1[Boolean] = 
      randomStream(uniform._map(_ < g1Threshold))(RNG.simple(302837L))
      
    Gen2(State(RNG.double).flatMap(d => if(d < g1Threshold)g1._1.sample else g2._1.sample),interleave(bools,g1._1.exhaustive,g2._1.exhaustive))
  }
  
  def randomStream[A](g: Gen2[A])(rng: RNG): Stream1[A] = 
    Stream1.unfold(rng)(rng => Some(g.sample.run(rng)))
   
  /* Interleave the two streams, using `b` to control which stream to pull from at each step.
   * A value of `true` attempts to pull from `s1`; `false` attempts to pull from `s2`.
   * When either stream is exhausted, insert all remaining elements from the other stream.
   */
  def interleave[A](b: Stream1[Boolean],s1:Stream1[A],s2: Stream1[A]):Stream1[A] = {
    b.headOption.map { hd => 
      if(hd) s1 match {
        case Cons(h,t) =>Stream1.cons(h(),interleave(b drop 1,t(),s2))
        case _ => s2
      }
      else s2 match {
        case Cons(h,t) => Stream1.cons(h(),interleave(b drop 1,s1,t()))
        case _ => s1
      }
    }getOrElse( Stream1.empty)
  }
  
  object ** {
    def unapply[A,B](p: (A,B)) = Some(p)
  }
  
  def listOf[A](g: Gen2[A]): SGen1[List[A]] =
    Sized(n => g.listOfN(n))
    
  def listOf1[A](g: Gen2[A]): SGen1[List[A]] =
    Sized(n => g.listOfN(n max 1))
    
  /* Not the most efficient implementation, but it's simple.
   * This generates ASCII strings.
   */
  def stringN(n: Int): Gen2[String] =
    listOfN(n, choose(0,127)).map(_.map(_.toChar).mkString)
    
  val smallInt = Gen2.choose(-10,10)
  
  /* A `Gen[Par[Int]]` generated from a list summation that spawns a new parallel
   * computation for each element of the input list summed to produce the final
   * result. This is not the most compelling example, but it provides at least some
   * variation in structure to use for testing.
   */
  lazy val pint2: Gen2[Par[Int]] = choose(-100,100).listOfN(choose(0,20)).map(l =>
    l.foldLeft(Par.unit(0))((p,i) =>
      Par.fork { Par.map2(p, Par.unit(i))(_ + _) }))
      
  def genStringIntFn(g: Gen2[Int]): Gen2[String => Int] =
    g map (i => (s => i))
}


case class Gen2[+A](sample: State[RNG,A], exhaustive: Stream1[Option[A]]) {
  
  def map[B](f: A => B): Gen2[B] = Gen2(sample.map(f),exhaustive.map(x => x match {
    case Some(a) => Some(f(a))
    case None => None
  }))
  
  def _map[B](f: A => B): Gen2[B] = Gen2(sample.map(f),exhaustive.map(_.map(f)))
  
  def map2[B,C](g: Gen2[B])(f: (A,B) => C): Gen2[C] = 
    Gen2(sample.map2(g.sample)(f),map2Stream1(exhaustive, g.exhaustive)(map2Option(_,_)(f))) 
  
  def flatMap[B](f: A => Gen2[B]): Gen2[B] = 
    Gen2(sample.flatMap(x => f(x).sample),exhaustive.flatMap{ x => 
      x match {
        case Some(a) => f(a).exhaustive
        case None => unbounded
      }
    })
  
  /* A method alias for the function we wrote earlier. */
  def listOfN(size: Int): Gen2[List[A]] = Gen2.listOfN(size, this)
  
  /* A version of `listOfN` that generates the size to use dynamically. */
  def listOfN(size: Gen2[Int]): Gen2[List[A]] = size.flatMap(n => this.listOfN(n)) 
    
  def listOf: SGen1[List[A]] = Gen2.listOf(this)
  def listOf1: SGen1[List[A]] = Gen2.listOf1(this)

  def unsized = Unsized(this)

  /*
   * S.map2(g)((_,_)) is a rather noisy way of combining two generators to produce a pair of their outputs. 
   * Let's quickly introduce a combinator to clean that up:15
   */
 
  def **[B](g: Gen2[B]): Gen2[(A,B)] =
    (this map2 g)((_,_))
  
}