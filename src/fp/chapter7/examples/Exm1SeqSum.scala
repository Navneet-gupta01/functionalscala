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
  
  /*def sum2(as: IndexedSeq[Int]):Int = {
    if(as.size <= 1) as.headOption getOrElse 0
    else {
      val (l,r) = as.splitAt(as.length/2)
      val sumL : Par[Int] = Par.unit(sum2(l))
      val sumR : Par[Int] = Par.unit(sum2(r))
      Par.get(sumL) + Par.get(sumR)
    }
  }*/
  
//  def sum3(as : IndexedSeq[Int]):Par[Int] = {
//    if(as.size <=1) as.headOption getOrElse 0
//    else {
//      val (l,r) = as.splitAt(as.length/2)
//      Par.map2(sum3(l),sum3(r))(_+_)
//    }
//  }
//  
//  def sum4(as : IndexedSeq[Int]):Par[Int] = {
//    if (as.isEmpty) Par.unit(0)
//    else {
//      val (l,r) = as.splitAt(as.length/2)
//      Par.map2(Par.fork(sum(l)), Par.fork(sum(r)))(_ + _)
//    }
//  }
//  def async[A](a: => A):Par[A] = fork(unit(a))
}