package fp.chapter2.examples

import fp.chapter2.examples.example1.{abs,formatAbs}

object ex1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  abs(-6)                                         //> res0: Int = 6
  
  println(formatAbs(-8))                          //> msg1 == The absolute value of -8 is 8.  msg2 == The absolute value of -8 is 
                                                  //| 8
  
}