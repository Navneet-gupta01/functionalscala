package fp.chapter5.exercises

trait Stream[+A] {
  import Stream._
  def uncons: Option[(A,Stream[A])]
  def isEmpty: Boolean = uncons.isEmpty
  def #::[A](a: => A) = cons(a, this)
  def toList:List[A] = uncons match {
    case Some((a,t)) => a::t.toList
    case None => Nil
  }
  
 /* def take(n:Int) : Stream[A] = uncons match {
    case Some((a,t)) if(n>0) => a#::t.take(n-1)
    case None => None
  }*/
  
  def takeWhile(f: A => Boolean):List[A] = uncons match {
    case Some((a,t)) if(f(a)) => a::t.takeWhile(f)
    case None => Nil
  }
  
 /* def toList2:List[A] = this match {
    case Cons(h,t) => h() :: t().toList2
    case _ => List()
  }*/
  /*def toList3: List[A] = {
    def listRec[A](s : Stream[A],xs:List[A]):List[A] = {
      this match {
        case Empty => xs
        case Cons(h,t) => listRec(t,h()::xs)
      } 
    }
    listRec(this,List())
  }*/
  
  
}
/*
case object Empty extends Stream[Nothing]
case class Cons[+A](h:() => A, t: () => Stream[A]) extends Stream[A]*/

object Stream {
  def empty[A]: Stream[A] = new Stream[A] { def uncons = None}
  
  def cons[A](head : => A ,tail: => Stream[A]) : Stream[A] = 
    new Stream[A] {lazy val uncons = Some((head,tail))}
  
  def apply[A](as: A*): Stream[A] = 
    if(as.isEmpty) empty
    else cons(as.head,apply(as.tail:_*))

}