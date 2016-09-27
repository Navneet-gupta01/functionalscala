package fp.chapter4.exercises

trait Either[+E, +A] {
  def map[B](f: A => B): Either[E, B] = this match {
    case Right(v) => Right(f(v))
    case Left(e) => Left(e)
  }
  
  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = this match {
    case Right(v) => f(v)
    case Left(e) => Left(e)
  }
  
  def orElse[EE >: E,B >: A](b: => Either[EE, B]): Either[EE, B] = this match {
    case Right(v) => this
    case Left(_) => b
  }
  
  def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] = 
    for {
      a <- this;
      b1 <- b
    } yield f(a,b1) 
    
}

case class Left[+E](value: E) extends Either[E, Nothing]
case class Right[+A](value: A) extends Either[Nothing, A]

object Either {
  def mean(xs: IndexedSeq[Double]): Either[String, Double] =
    if (xs.isEmpty)
      Left("mean of empty list!")
    else
      Right(xs.sum / xs.length)
      
  def safeDiv(x: Double, y: Double): Either[Exception, Double] =
    try {
      Right(x / y)
    } catch {
      case e: Exception => Left(e)
    }

  def sequence2[A,B](a:List[Either[A,B]]): Either[List[A],B] = ???
  
  def traverse2[A, B](a: List[A])(f: A => Right[B]): Either[List[B],Nothing] = ???
  
  def traverse[E,A,B](as : List[A])(f: A => Either[E, B]): Either[E, List[B]] =  {
    as match {
      case Nil => Right(Nil:List[B])
      case x::xs => f(x) flatMap { x => traverse(xs)(f) map { y => (x::y) }}
    }
  }
  
  def traverseHOF[E,A,B](as: List[A])(f: A => Either[E,B]): Either[E, List[B]] = {
    as.foldRight(Right(Nil):Either[E,List[B]])((a,b) => b flatMap { l => f(a) map { x => x::l } })
  }
  
  def sequence[E,A](es: List[Either[E,A]]): Either[E,List[A]] = traverse(es)(x => x)
    

}