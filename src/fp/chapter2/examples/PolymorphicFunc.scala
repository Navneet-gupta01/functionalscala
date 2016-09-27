package fp.chapter2.examples

import scala.annotation.tailrec

// Example page. 27 of Book
object PolymorphicFunc {
  
  def binarySearch(xs : Array[Double]): Int = ???
  
  def binarySearchTailRec(xs: Array[Double],key: Int) : Boolean = {
    @tailrec
    def find(start: Int, end: Int) : Boolean = {
      if(start > end) return false
      else {
        val mid = (start+end)/2
        val d = xs(mid)
        if(d == key) true
        else if(d < key) find(mid, end)
        else find(start,mid)
      }
    }
    find(0,xs.length-1)
  }
  
  def binarySearchTR(xs: Array[Double], key:Int) : Int = {
    @tailrec
    def find(start: Int, mid: Int, end: Int) : Int = {
      if(start > end) -mid-1
      else {
        val mid2 = (start+end)/2
        val d = xs(mid2)
        if(d==key) mid2
        else if(d>key) find(start,mid2,mid2-1)
        else find(mid2+1,mid2,end)
      }
    }
    find(0,0,xs.length-1)
  }
  
  // Now the ploymorphic one
  
  def binarySrchTRPoly[A](xs: Array[A],key : A, compare : (A,A) => Int) : Int = {
    @tailrec
    def find(start: Int, mid: Int, end: Int) : Int = {
      if(start > end) -mid-1
      else {
        val mid2 = (start+end)/2
        val d = xs(mid2)
        if(compare(d,key) == 0) mid2
        else if(compare(d,key)>0) find(start,mid2,mid2-1)
        else find(mid2+1,mid2,end)
      }
    }
    find(0,0,xs.length-1)
  }
  
}