package fp.chapter5.exercises

import scala.annotation.tailrec
import Stream1._
trait Stream1[+A] {
  def toList : List[A] = this match {
    case Cons(h,t) => h() :: t().toList
    case Empty => List()
  }
  
  def toListTR: List[A] = {
    @tailrec
    def go(s: Stream1[A],acc : List[A]):List[A] = {
      s match {
        case Cons(h,t) => go(t(),h()::acc)
        case _ => acc
      }
    }
    go(this,List()).reverse
  }
  
  def toListFast: List[A] = {
    val buff = new collection.mutable.ListBuffer[A]
    @tailrec
    def go(s: Stream1[A]) : List[A] = s match {
      case Cons(h,t) => buff += h();go(t())
      case _ => buff.toList 
    }
    go(this)
  }
  
  def foldRight[B](z: => B)(f: (A, => B) => B): B = this match {
    case Cons(h,t) => f(h(),t().foldRight(z)(f))
    case _ => z
  }
  
  def exists(p: A => Boolean): Boolean = this match {
    case Cons(h,t) => if(p(h())) true else t().exists(p)
    case _ => false
  }
  
  def existsViaFR(p: A =>Boolean):Boolean = foldRight(false)((a,b) => p(a) || b)
  
  def existsTR(p:A => Boolean) : Boolean = {
    @tailrec
    def go(s:Stream1[A],acc: Boolean) : Boolean = s match {
      case Cons(h,t) => if(acc) true else go(t(),acc || p(h()))
      case _ => acc
    }
    go(this,false)
  }
  
  def take(n:Int):Stream1[A] = this match {
    case Cons(h,t) if(n>1) => cons(h(),t().take(n-1))
    case Cons(h,_) if(n==1) => cons(h(),Empty)
    case _ => Empty
  }
  
  def takeViaFR(n:Int): Stream1[A] = foldRight(Empty:Stream1[A])((a,b) => if(n<=0)b else cons(a,b.takeViaFR(n-1)))
  
  def drop(n: Int) : Stream1[A] = this match {
    case Cons(h,t) if(n>1) => t().drop(n-1)
    case Cons(h,t) if(n==1) => t()
    case _ => Empty
  }
  
  /*def dropRec(n: Int): Stream1[A] = {
    def go(as : Stream[A],k: Int) : Stream[A] = {
      as match {
        case Cons
      }
    }
    go(this,n)
  }*/
  /*//def dropViaFR(n:Int): Stream1[A] = foldRight(Empty:Stream1[A])((a,b) => if(n>1) b )
*/  
 /* def dropViaFR(n:Int):Stream1[A] = foldRight(Empty:Stream1[A])((a,b) => if(n>0) b else )*/
  
  def takeWhile(f: A => Boolean): Stream1[A] = this match {
    case Cons(h,t) if(f(h())) => cons(h(),t().takeWhile(f))
    case _ => Empty
  }
  
  def takeWhileViaFR(f:A => Boolean) : Stream1[A] = foldRight(Empty:Stream1[A])((a,b) => if(f(a)) cons(a,b) else b)
  
  def forAll(f: A => Boolean): Boolean = foldRight(true)((a,b) => f(a) && b)
  
  def map[B](f: A => B) : Stream1[B]= this match {
    case Cons(a,b) => cons(f(a()),b().map(f))
    case _ => Empty
  }
  
  def mapViaFR[B](f:A => B) : Stream1[B] = foldRight(Empty:Stream1[B])((a,b) => cons(f(a),b))
  
  def filter(f: A => Boolean): Stream1[A] = this match {
    case Cons(a,b) if(f(a())) => cons(a(),b().filter(f))
    case Cons(a,b) => b().filter(f)
    case _ => Empty
  }
  
  def filterViaFR(f : A => Boolean):Stream1[A] = foldRight(Empty:Stream1[A])((a,b) => if(f(a)) cons(a,b) else b)
  
  def append[B>:A](s: => Stream1[B]): Stream1[B] = this.foldRight(s)(cons(_,_))
  
  def flatMap[B](f: A => Stream1[B]):Stream1[B] = this match {
    case Cons(a,b) => f(a()).append(b().flatMap(f))
    case _ => Empty
  }
  
  def flatMapViaMap[B](f: A => Stream1[B]):Stream1[B] = ???
  
  def headOption: Option[A] = ???
  def startsWith[B](s: Stream1[B]): Boolean = ???
  
  def mapViaUnfold[B](f: A => B) : Stream1[B] = { 
    unfold(this) {
      case Cons(h,t) => Some((f(h()),t()))
      case _ => None
    }
  }
  
  def takeViaUnfold(n:Int): Stream1[A] = {
    unfold((this,n)) {
      case (Cons(h,t),1) => Some((h(),(empty,0)))
      case (Cons(h,t),n) if(n>1) => Some((h(),(t(),n-1)))
      case _ => None
    }
  }
  
  def takeWhileViaUnfold(f : A => Boolean): Stream1[A] = {
    unfold((this)) {
      case Cons(h,t) if(f(h())) => Some(h(),t())
      case _ => None
    }
  }
  
  def zipWith[B,C](s2: Stream1[B])(f: (A,B) => C): Stream1[C] =
    unfold((this, s2)) {
      case (Cons(h1,t1), Cons(h2,t2)) =>
        Some((f(h1(), h2()), (t1(), t2())))
      case _ => None
    }

  // special case of `zipWith`
  def zip[B](s2: Stream1[B]): Stream1[(A,B)] =
    zipWith(s2)((_,_))


  def zipAll[B](s2: Stream1[B]): Stream1[(Option[A],Option[B])] =
    zipWithAll(s2)((_,_))

  def zipWithAll[B, C](s2: Stream1[B])(f: (Option[A], Option[B]) => C): Stream1[C] =
    Stream1.unfold((this, s2)) {
      case (Empty, Empty) => None
      case (Cons(h, t), Empty) => Some(f(Some(h()), Option.empty[B]) -> (t(), empty[B]))
      case (Empty, Cons(h, t)) => Some(f(Option.empty[A], Some(h())) -> (empty[A] -> t()))
      case (Cons(h1, t1), Cons(h2, t2)) => Some(f(Some(h1()), Some(h2())) -> (t1() -> t2()))
    }
  
  
}

case object Empty extends Stream1[Nothing]
case class Cons[+A](h: () => A,t: () => Stream1[A]) extends Stream1[A]

object Stream1 {
  def cons[A](head: => A, tail : => Stream1[A]): Stream1[A] = Cons(() => head,() => tail)
  def empty[A] : Stream1[A] = Empty
  def apply[A](as : A*) : Stream1[A] = if(as.isEmpty) Empty else cons(as.head,apply(as.tail:_*))
  
  val ones: Stream1[Int] = Stream1.cons(1,ones)
  
  def constant[A](a: A): Stream1[A] = Stream1.cons(a,constant(a))
  
  def fibs:Stream1[Int] = {
    def series(f0: Int,f1: Int): Stream1[Int] = {
      cons(f0,series(f1,f0 + f1))
    }
    series(0, 1)
  }
  
  def from(n:Int): Stream1[Int] = Stream1.cons(n,from(n+1))
  
  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream1[A] = {
    f(z) match {
      case Some((a,s)) => cons(a,unfold(s)(f))
      case None => empty
    }
  }
  
  def fibsViaUnfold: Stream1[Int] = unfold((0,1)) {case (f0,f1) => Some((f0,(f1,f0+f1)))}
  
  def fromViaUnfold(n:Int): Stream1[Int] = unfold(n){n => Some((n,n+1))}
  
  def constantViaUnfold[A](a:A) : Stream1[A] = unfold(a){_ => Some((a,a))}
  
  def onesViaUnfold:Stream1[Int] = constantViaUnfold(1)
  
  def onesViaUnfold2:Stream1[Int] = unfold(1){_ => Some((1,1))}
  
}