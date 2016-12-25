package fp.chapter8.examples

import fp.chapter6.examples.State
import fp.chapter6.examples.RNG
import fp.chapter5.exercises.Stream1
import Gen1._

object Gen1 {
  
  def unit[A](a : => A): Gen1[A] = Gen1(State.unit(a),Stream1(a))
  
  def boolean: Gen1[Boolean] = Gen1(State(RNG.boolean),Stream1(true,false))
  
  def choose(start: Int, stopExclusive: Int): Gen1[Int] = 
    Gen1(State(RNG.nonNegativeInt).map(n => start + n % (stopExclusive -  start)),Stream1.from(start).take(stopExclusive-start))
    
  
//  def listOfN[A](n: Int,g: Gen1[A]):Gen1[List[A]] = ??? //Gen1(State.sequence(List.fill(n)(g.sample)),Stream1.constant(g.exhaustive).take(n))
  
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
    
      
  def listOfN[A](n: Int,g: Gen1[A]):Gen1[List[A]] = 
    Gen1(State.sequence(List.fill(n)(g.sample)),cartesian(Stream1.constant(g.exhaustive).take(n)).map(_.toList))
  
    
  // To randomly sample from these domains is straightforward, but what should we do for exhaustive? Return the empty Stream? No, probably not.
  //we made a choice about the meaning of an empty stream—we interpreted it to mean that we have finished exhaustively 
  //generating values in our domain and there are no more values to generate. We could change its meaning to "the domain is 
  // infinite, use random sampling to generate test cases", but then we lose the ability to determine that we have exhaustively 
  // enumerated our domain, or that the domain is simply empty. How can we distinguish these cases? One simple way to do this is
  // with Option: 
  //          case class Gen[+A](sample: State[RNG,A], exhaustive: Stream[Option[A]])   :: TODO : See example 6

  /** Between 0 and 1, not including 1. */
  def uniform: Gen1[Double] = ???
  
  /** Between `i` and `j`, not including `j`. */
  def choose(i: Double, j: Double): Gen1[Double] = ???
 
  
}

case class Gen1[+A](sample: State[RNG,A], exhaustive: Stream1[A]) {
  
  def map[B](f: A => B): Gen1[B] = 
    Gen1(sample.map(f),exhaustive.map(f))
    
  def map2[B,C](g: Gen1[B])(f: (A,B) => C): Gen1[C] = Gen1(sample.map2(g.sample)(f),map2Stream1(exhaustive,g.exhaustive)(f))
  
  def flatMap[B](f: A => Gen1[B]): Gen1[B] = Gen1(sample.flatMap(a => f(a).sample),exhaustive.flatMap(a => f(a).exhaustive))
  
  def listOfN(size: Int) : Gen1[List[A]] = Gen1.listOfN(size,this)
  
  def listOfN(size: Gen1[Int]): Gen1[List[A]] = size.flatMap(n => this.listOfN(n))  
  
}