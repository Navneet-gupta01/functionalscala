package fp.chapter11.examples

import fp.chapter8.examples.Gen2
import fp.chapter7.examples.Par.Par
import fp.chapter7.examples.Par

trait Monad[M[_]] extends Functor[M] {
  
  def unit[A](a: => A): M[A]
  def flatMap[A,B](ma: M[A])(f: A => M[B]) :  M[B]
  def map[A,B](ma: M[A])(f: A => B): M[B] = flatMap(ma)(a => unit(f(a)))
  def map2[A,B,C](ma: M[A],mb: M[B])(f: (A,B) => C): M[C] = flatMap(ma)(a => map(mb)(b => f(a,b)))
  def sequence[A](lma: List[M[A]]): M[List[A]] = lma.foldRight[M[List[A]]](unit(List()))((h,t) => map2(h,t)(_::_))
  def traverse[A,B](la: List[A])(f: A => M[B]): M[List[B]] = sequence(la map f)
  def traverse2[A,B](la: List[A])(f: A => M[B]): M[List[B]] = la.foldRight[M[List[B]]](unit(List()))((h,t) => map2(f(h),t)(_ :: _))
  def factor[A,B](ma: M[A], mb: M[B]): M[(A, B)] = map2(ma, mb)((_, _))

  def replicateM[A](n: Int, ma: M[A]): M[List[A]]  = sequence(List.fill(n)(ma))
  
  //recursive
  def _replicateM[A](n: Int, ma: M[A]): M[List[A]]  = 
    if(n<=0) unit(List[A]()) else map2(ma, _replicateM(n-1, ma))(_ :: _)
  
  def cofactor[A,B](e: Either[M[A], M[B]]): M[Either[A, B]] = e match {
      case Left(ma) => map(ma)(Left(_)) 
      case Right(ma) => map(ma)(Right(_))
  }
  
  def compose[A,B,C](f: A => M[B], g: B => M[C]): A => M[C] = 
    a => flatMap(f(a))(g)
  
  //IMPORTANT  
  def flatMapViaCompose[A,B](ma: M[A])(f: A => M[B]) :  M[B] = compose((_:Unit) => ma, f)()
  
  
  def join[A](mma: M[M[A]]): M[A] = flatMap(mma)(ma => ma)
  
  def flatMapViaJoin[A,B](ma: M[A])(f: A => M[B]) : M[B] = 
    join(map(ma)(f))
  
  def composeViaJoin[A,B,C](f: A => M[B],g: B => M[C]): A =>  M[C]  = 
    a => join(map(f(a))(g))
    
  
}
object Monad {
  val genMonad = new Monad[Gen2] {
    def unit[A](a: => A): Gen2[A] = Gen2.unit(a)
    def flatMap[A,B](gen: Gen2[A])(f: A => Gen2[B]): Gen2[B] = gen flatMap f
  }
  
  val parMonad = new Monad[Par] {
    def unit[A](a: => A): Par[A] = Par.unit(a)
    def flatMap[A,B](pa: Par[A])(f: A => Par[B]): Par[B] =  Par.flatMap(pa)(f)
  }
  
  val optionMonad = new Monad[Option] {
    def unit[A](a: => A): Option[A] = Option.apply(a) //Some(a)
    def flatMap[A,B](oa: Option[A])(f: A => Option[B]): Option[B] = oa flatMap f
  }
  
  val streamMonad = new Monad[Stream] {
    def unit[A](a: => A): Stream[A] = Stream.apply(a) // Stream(a)
    def flatMap[A,B](sa: Stream[A])(f: A => Stream[B]): Stream[B] = sa flatMap f
  }
  
  val listMonad = new Monad[List] {
    def unit[A](a: => A): List[A] = List.apply(a) // List(a)
    def flatMap[A,B](la: List[A])(f: A => List[B]): List[B] = la flatMap f
  }
  
//  TODO to implement Parser combinator Chapter 9

//  val parserMonad = new Monad[Parser] {
//    def unit[A](a: => A): Parser[A] = ???
//    def flatMap[A,B](pa: Parser[A])(f: A => Parser[B]): Parser[B] = ???
//  }
}