package fp.chapter7.examples

object Exm1SeqSum {
  
  def sum(as: IndexedSeq[Int]):Int = {
    if(as.size <= 1) as.headOption getOrElse 0
    else {
      val (l,r) = as.splitAt(as.length/2)
      sum(l) + sum(r)
    }
  }
  
  type Par[A] = Int
  
  def unit[A](a: => A):Par[A] = ???
  
  def get[A](a: Par[A]): A = ???
  
  def map2[A,B,C](a:A,b:B)(f:(A,B)=> C) : C = ???
  
  
}