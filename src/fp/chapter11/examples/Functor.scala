package fp.chapter11.examples

trait Functor[F[_]] {
  def map[A,B](fa: F[A])(f: A => B): F[B] 
  def distribute[A,B](fab: F[(A,B)]): (F[A],F[B]) = (map(fab)(_._1),map(fab)(_._2))
  def map2[A,B,C](fa: F[A],fb: F[B])(f: (A,B) => C): F[C]
  
  def codistribute[A,B](e: Either[F[A], F[B]]): F[Either[A, B]] = e match {
    case Left(fa) => map(fa)(Left(_))
    case Right(fb) => map(fb)(Right(_))
  }
}

object Functor {
}
object listFunctor extends Functor[List] {
    def map[A,B](xs: List[A])(f: A => B): List[B] = xs map f
    def map2[A,B,C](xs: List[A],ys: List[B])(f: (A,B) => C): List[C] =xs flatMap (x => ys map (y => f(x,y)))
} 
