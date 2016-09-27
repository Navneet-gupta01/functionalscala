package fp.chapter2.examples

import fp.chapter2.examples.PolymorphicFunc.{binarySearchTailRec,binarySearchTR}
object Polymorphic {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  var a: Array[Double] = Array(1,2,3,4,5,6)       //> a  : Array[Double] = Array(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
  binarySearchTR(a, 5)                            //> res0: Int = 4
  
}