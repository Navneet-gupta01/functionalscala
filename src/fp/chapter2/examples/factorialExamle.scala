package fp.chapter2.examples

import scala.annotation.tailrec

object factorialExamle {
  
  def computeFactorial(n : Int) : Int = {
    if(n == 0 || n==1) n
    else n*computeFactorial(n-1)
  }
  
  def computeFactorial2(n : Int) : Int = {
    @tailrec
    def go(n: Int, acc: Int) : Int = {
      if(n<=0) acc
      else go(n-1,acc*n)
    }
    go(n,1)
  }
}