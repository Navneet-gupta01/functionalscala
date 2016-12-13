package fp.chapter8.examples

//case class SGen1[+A](forSize: Int => Gen[A])

import Gen2._

trait SGen1[+A] {
  
  def map[B](f: A => B) : SGen1[B] = this match {
    case Sized(g) => Sized(g andThen(_ map f))
    case Unsized(g) =>Unsized( g map f)
  }
  
  def flatMap[B](f: A => Gen2[B]): SGen1[B] = this match {
    case Sized(g) => Sized(g andThen(_ flatMap f))
    case Unsized(g) => Unsized(g flatMap f)
  }
  
  def **[B](s2: SGen1[B]): SGen1[(A,B)] = (this,s2) match {
    case (Sized(g1),Sized(g2)) => Sized(n => g1(n) ** g2(n) )
    case (Sized(g1), Unsized(g2)) => Sized(n => g1(n) ** g2)
    case (Unsized(g1), Sized(g2)) => Sized(n => g1 ** g2(n))
    case (Unsized(g1),Unsized(g2)) => Unsized(g1**g2) 
  }
}

case class Sized[+A](forSize: Int => Gen2[A]) extends SGen1[A]
case class Unsized[+A](get: Gen2[A]) extends SGen1[A]

object SGen1 {
  implicit def unsized[A](g: Gen2[A]): SGen1[A] = Unsized(g)
}