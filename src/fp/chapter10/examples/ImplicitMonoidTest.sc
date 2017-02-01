package fp.chapter10.examples

object ImplicitMonoidTest {
	import ImplicitMonoid._
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  sum                                             //> res0: fp.chapter10.examples.Monoid[fp.chapter10.examples.ImplicitMonoid.Sum]
                                                  //|  => fp.chapter10.examples.ImplicitMonoid.Sum = <function1>
  
  product                                         //> res1: fp.chapter10.examples.Monoid[fp.chapter10.examples.ImplicitMonoid.Prod
                                                  //| uct] => fp.chapter10.examples.ImplicitMonoid.Product = <function1>
}