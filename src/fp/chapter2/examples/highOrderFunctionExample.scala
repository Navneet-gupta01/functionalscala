package fp.chapter2.examples

import scala.annotation.tailrec

// Page 23 of book
object highOrderFunctionExample {
  def abs(n :Int) : Int = {
    if(n<0) -n else n
  }
  
  def factorial(n : Int) = {
    @tailrec
    def goFact(n: Int, acc: BigInt) : BigInt = {
      if(n<=0) acc
      else goFact(n-1,n*acc)
    }
    goFact(n,1)
  }
  
  def formatAbs(x: Int) = {
    val msg = "The absolute value of %d is %d"
    val msg1 = msg.format(x,abs(x))
    // using String InterPolation
    val msg2 = s"The absolute value of $x is ${abs(x)}"
    "msg1 == " + msg1 + ".  msg2 == " + msg2
  }
  
  def formatFactorial(x: Int) = {
    val msg = "The absolute value of %d is %d"
    val msg1 = msg.format(x,factorial(x))
    // using String InterPolation
    val msg2 = s"The absolute value of $x is ${factorial(x)}"
    "msg1 == " + msg1 + ".  msg2 == " + msg2
  }
  
  // Now since formatAbs and formatFactorial are almost same we can refactor these two in one
  // function using higher order function as below
  
  def formatResult(x: Int, f: Int => Int) = {
    val msg = "The absolute value of %d is %d"
    val msg1 = msg.format(x,f(x))
    // using String InterPolation
    val msg2 = s"The absolute value of $x is ${f(x)}"
    "msg1 == " + msg1 + ".  msg2 == " + msg2
  }
  
  
  
}