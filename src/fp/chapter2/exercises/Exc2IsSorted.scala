package fp.chapter2.exercises

// Excercise 2: isSorted page : 28
object Exc2IsSorted {
  /*def isSorted[A](arr : Array[A],comparator: ((A,A) => Boolean)): Boolean = {
    if()
    def checkSorted(start: Int,issorted:Boolean):Boolean = {
      if(!issorted) false
      else {
        if(start>= arr.length-1) false
        else {
          val greater = comparator(arr(start),arr(start+1))

        }
      }
    }
    false
  }*/
  // this checks only if sorted in ascending order
  def isSorted[A](as: Array[A], gt: (A, A) => Boolean): Boolean = {
    @tailrec
    def go(n: Int): Boolean =
      if (n >= as.length - 1) true
      else if (gt(as(n), as(n + 1))) false
      else go(n + 1)

    go(0)
  }
  //to check both sorted array Ascending or Descending
  def isSortedBoth[A](xs: Array[A], comparator: (A, A) => Boolean): Boolean = {
    @tailrec
    def check(n: Int, isAsc: Boolean): Boolean =
      if (n >= xs.length - 1) true
      else if (comparator(xs(n), xs(n + 1)) && isAsc) false
      else if (comparator(xs(n + 1), xs(n)) && !isAsc) false
      else check(n + 1, isAsc)

    check(0, comparator(xs(1), xs(0)))
  }
}
