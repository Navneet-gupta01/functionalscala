package fp.chapter2.exercises

import scala.annotation.tailrec

//Excersise 1 : Page No. 22 of Book
object FibocaniExercise {
  def nthFibonacciNo(n : Int) : BigInt = {
    @tailrec
    def loop(n : Int, prev : BigInt, next :BigInt) : BigInt = n match {
      case 0 => prev
      case 1 => next
      case _ => loop(n-1,next,next+prev)
    }
    loop(n,0,1)
  }
  // if u remove the comment for below annotaion this method will throw error
  //since it is doing addition as a end of recursion process
  //@tailrec
  def nthFibonacci(n: Int) : BigInt = {
    if(n==0 || n==1 ) n
    else nthFibonacci(n-1)+nthFibonacci(n-2)
  }
}