package fp.chapter8.examples

import scala.collection.concurrent.Gen

object Library {
  
  type Gen[A] 
  
  def listOf[A](a: Gen[A]): Gen[List[A]] = ???
  
  def listOfN[A](n: Int, a: Gen[A]): Gen[List[A]] = ???
  
  def forAll[A](a: Gen[A])(f: A => Boolean): Prop = ???
}

trait Prop { 
  def check : Boolean
  
  def &&(p: Prop): Prop 
}