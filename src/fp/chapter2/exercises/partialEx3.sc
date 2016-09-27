package fp.chapter2.exercises

import fp.chapter2.exercises.Ex3Partial1.partial1
object partialEx3 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val part = partial1(1,(a:Int,b:Double) => {(a + b).toString})
                                                  //> part  : Double => String = <function1>
  
  part(12.122)                                    //> res0: String = 13.122
}