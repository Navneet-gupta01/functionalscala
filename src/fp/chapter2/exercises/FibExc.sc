package fp.chapter2.exercises

import fp.chapter2.exercises.FibocaniExercise.{nthFibonacciNo,nthFibonacci}
object FibExc {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  nthFibonacciNo(0)                               //> res0: BigInt = 0
  nthFibonacci(0)                                 //> res1: BigInt = 0
  nthFibonacciNo(1)                               //> res2: BigInt = 1
  nthFibonacci(1)                                 //> res3: BigInt = 1
  nthFibonacciNo(2)                               //> res4: BigInt = 1
  nthFibonacci(2)                                 //> res5: BigInt = 1
  nthFibonacciNo(3)                               //> res6: BigInt = 2
  nthFibonacci(3)                                 //> res7: BigInt = 2
  nthFibonacciNo(4)                               //> res8: BigInt = 3
  nthFibonacci(4)                                 //> res9: BigInt = 3
  nthFibonacciNo(5)                               //> res10: BigInt = 5
  nthFibonacci(5)                                 //> res11: BigInt = 5
  nthFibonacciNo(6)                               //> res12: BigInt = 8
  nthFibonacci(6)                                 //> res13: BigInt = 8
  nthFibonacciNo(7)                               //> res14: BigInt = 13
  nthFibonacci(7)                                 //> res15: BigInt = 13
  nthFibonacciNo(8)                               //> res16: BigInt = 21
  nthFibonacci(8)                                 //> res17: BigInt = 21
  nthFibonacciNo(9)                               //> res18: BigInt = 34
  nthFibonacciNo(10)                              //> res19: BigInt = 55
  nthFibonacciNo(11)                              //> res20: BigInt = 89
  nthFibonacciNo(13)                              //> res21: BigInt = 233
  nthFibonacciNo(15)                              //> res22: BigInt = 610
 	nthFibonacciNo(16)                        //> res23: BigInt = 987
}