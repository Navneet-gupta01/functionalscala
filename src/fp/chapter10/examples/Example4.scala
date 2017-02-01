package fp.chapter10.examples

object MonoidCompose {
  import Monoid._
  
  //if types A and B are monoids, then the tuple type (A, B) is also a monoid (called their product).
  def productMonoid[A,B](a1: Monoid[A],b1: Monoid[B]): Monoid[(A,B)] = new Monoid[(A,B)] {
    def op(x: (A,B),y: (A,B)) = (a1.op(x._1,y._1),b1.op(x._2,y._2))
    def zero = (a1.zero,b1.zero)
  }
  
  
  
  //with Either. This is called a monoid coproduct.
  def coProductMonoid[A,B](a: Monoid[A],b: Monoid[B]): Monoid[Either[A,B]] = new Monoid[Either[A,B]] {
    def op(x: Either[A,B],y: Either[A,B]) = (x,y) match {
      case (Left(x1),Left(y1)) => Left(a.op(x1,y1))
      case (Left(x1),Right(y1)) => ???
      case (Right(x1),Left(y1)) => ???
      case (Right(x1),Right(y1)) => ???
    }
    def zero = ???
  }
  
  def mapMergeMonoid[K,V](V: Monoid[V]): Monoid[Map[K, V]] = {
    new Monoid[Map[K, V]] {
      def zero = Map()
      def op(a: Map[K, V], b: Map[K, V]) =
        a.map {
          case (k, v) => (k, V.op(v, b.get(k) getOrElse V.zero))
        } 
    }
  }
  
  val M: Monoid[Map[String, Map[String, Int]]] = mapMergeMonoid(mapMergeMonoid(intAddition))
  
  def functionMonoid[A,B](b: Monoid[B]): Monoid[A => B] = new Monoid[A => B] {
    def op(a1: (A => B),b1 : (A => B)) = a => b.op(a1(a), b1(a))
    def zero = _ => b.zero
  } 
  
  def frequencyMap[A](strings: IndexedSeq[A]): Map[A, Int] = 
    foldMapV(strings, mapMergeMonoid[A, Int](intAddition))((a: A) => Map(a -> 1))
    
  
}