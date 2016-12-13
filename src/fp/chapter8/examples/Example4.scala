package fp.chapter8.examples

import fp.chapter6.examples.State
import fp.chapter6.examples.RNG

object Gen {
  def unit[A](a: => A): Gen[A] = Gen(State.unit(a))
  
  val boolean: Gen[Boolean] = Gen(State(RNG.boolean))
  
  def listOf[A](g: Gen[A]): SGen[List[A]]  = ???
  
  def listOfN[A](n: Int, g: Gen[A]): Gen[List[A]] = ???
  
  def listOf1[A](g: Gen[A]): SGen[List[A]] = ???
}

case class Gen[+A](sample: State[RNG,A]) {
  
  def map[B](f:A => B): Gen[B] = Gen(sample.map(f))
  
  def map2[B,C](g: Gen[B])(f:(A,B) => C): Gen[C] = 
    Gen(sample.map2(g.sample)(f))
    
  def flatMap[B](f: A => Gen[B]): Gen[B] = 
    Gen(sample.flatMap(a => f(a).sample))
    
  def listOfN(size: Int): Gen[List[A]] = 
    Gen.listOfN(size, this)
    
  /* A version of `listOfN` that generates the size to use dynamically. */
  def listOfN(size: Gen[Int]): Gen[List[A]] =
    size flatMap (n => this.listOfN(n))
  
  def listOf: SGen[List[A]] = Gen.listOf(this)
  
  def listOf1: SGen[List[A]] = Gen.listOf1(this)

  def unsized = SGen(_ => this)

  def **[B](g: Gen[B]): Gen[(A,B)] =
    (this map2 g)((_,_))
  
}

case class SGen[+A](g: Int => Gen[A]) {
  def apply(n: Int): Gen[A] = g(n)

  def map[B](f: A => B): SGen[B] =
    SGen { g(_) map f }

  def flatMap[B](f: A => SGen[B]): SGen[B] = {
    val g2: Int => Gen[B] = n => {
      g(n) flatMap { f(_).g(n) }
    }
    SGen(g2)
  }

  def **[B](s2: SGen[B]): SGen[(A,B)] =
    SGen(n => apply(n) ** s2(n))
}