package fp.chapter10.examples

trait Foldable[F[_]] {
  import fp.chapter10.examples.Monoid._
  
  def foldRight[A, B](as: F[A])(z:B)(f: (A, B) => B): B = 
    foldMap(as)(f.curried)(EndoMonoid2[B])(z)
    
  def foldLeft[A, B](as: F[A])(z:B)(f: (B, A) => B): B = 
    foldMap(as)(a => (b:B) => f(b,a))(dual(EndoMonoid2[B]))(z)
    
  def foldMap[A, B](as: F[A])(f: A => B)(mb: Monoid[B]): B =
    foldRight(as)(mb.zero)((a,b) => mb.op(f(a),b))
    
  def concatenate[A](as: F[A])(m: Monoid[A]): A =
    foldLeft(as)(m.zero)(m.op)
    
  def concatenate2[A](as: F[A])(m: Monoid[A]): A =
    foldRight(as)(m.zero)(m.op)
    
  def toList[A](as: F[A]):List[A] = 
    foldRight(as)(List[A]())(_ :: _)
    
}

object FoldableList extends Foldable[List] {
  override def foldRight[A,B](as: List[A])(z:B)(f: (A,B) => B): B = 
    as.foldRight(z)(f)
  override def foldLeft[A,B](as: List[A])(z:B)(f: (B,A) => B) : B = 
    as.foldLeft(z)(f)
  override def foldMap[A,B](as: List[A])(f: A => B)(mb: Monoid[B]): B = 
    foldLeft(as)(mb.zero)((b,a) => mb.op(b,f(a)))
  override def concatenate[A](as: List[A])(m: Monoid[A]): A =  
    foldLeft(as)(m.zero)(m.op)
  override def toList[A](as:List[A]):List[A] = as
}

object FoldableIndexedSeq extends Foldable[IndexedSeq] {
  override def foldRight[A,B](as: IndexedSeq[A])(z: B)(f: (A,B) => B): B = 
    as.foldRight(z)(f)
    
  override def foldLeft[A,B](as: IndexedSeq[A])(z: B)(f: (B,A) => B): B = 
    as.foldLeft(z)(f)
    
  override def foldMap[A,B](as: IndexedSeq[A])(f: A => B)(mb: Monoid[B]): B =
    foldLeft(as)(mb.zero)((b,a) => mb.op(b, f(a)))
 
}

object FoldableStream extends Foldable[Stream] {
  override def foldRight[A,B](as: Stream[A])(z: B)(f: (A,B) => B): B = 
    as.foldRight(z)(f)
    
  override def foldLeft[A,B](as: Stream[A])(z:B)(f: (B,A) => B) : B = 
    as.foldLeft(z)(f)
  
  override def foldMap[A,B](as: Stream[A])(f: A => B)(mb: Monoid[B]): B = 
    foldLeft(as)(mb.zero)((b,a) => mb.op(b,f(a)))
}

sealed trait BinaryTree[+A]
case object EmptyTree extends BinaryTree[Nothing]
case class Node[A](value : A, left: BinaryTree[A] , right: BinaryTree[A]) extends BinaryTree[A]

object FoldableBinaryTree extends Foldable[BinaryTree] {
  override def foldRight[A,B](as: BinaryTree[A])(z: B)(f: (A,B) => B): B = as match {
    case EmptyTree => z
    case Node(v,l,r) => f(v,foldRight(r)(foldRight(l)(z)(f))(f))
  }
  override def foldLeft[A,B](as: BinaryTree[A])(z:B)(f: (B,A) => B) : B = as match {
    case EmptyTree => z
    case Node(v,l,r) => f(foldRight(r)(foldRight(l)(z)((a,b) => f(b,a)))((a,b) => f(b,a)),v)
  }
  override def foldMap[A,B](as: BinaryTree[A])(f: A => B)(mb: Monoid[B]): B = as match {
    case EmptyTree => mb.zero
    case Node(v,l,r) => mb.op(foldMap(l)(f)(mb),foldMap(r)(f)(mb))
  }
}

object FoldableOption extends Foldable[Option] {
  override def foldRight[A,B](as: Option[A])(z: B)(f: (A,B) => B): B = as match {
    case None => z
    case Some(x) => f(x,z)
  }
  override def foldLeft[A,B](as: Option[A])(z:B)(f: (B,A) => B) : B = as match {
    case None => z
    case Some(x) => f(z,x)
  }
  override def foldMap[A,B](as: Option[A])(f: A => B)(mb: Monoid[B]): B = as match {
    case None => mb.zero
    case Some(x) => f(x)
  }
}

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object TreeFoldable extends Foldable[Tree] {
  override def foldMap[A, B](as: Tree[A])(f: A => B)(mb: Monoid[B]): B = as match {
    case Leaf(a) => f(a)
    case Branch(l, r) => mb.op(foldMap(l)(f)(mb), foldMap(r)(f)(mb))
  }
  override def foldLeft[A, B](as: Tree[A])(z: B)(f: (B, A) => B) = as match {
    case Leaf(a) => f(z, a)
    case Branch(l, r) => foldLeft(r)(foldLeft(l)(z)(f))(f)
  }
  override def foldRight[A, B](as: Tree[A])(z: B)(f: (A, B) => B) = as match {
    case Leaf(a) => f(a, z)
    case Branch(l, r) => foldRight(l)(foldRight(r)(z)(f))(f)
  }
}

// Notice that in `TreeFoldable.foldMap`, we don't actually use the `zero`
// from the `Monoid`. This is because there is no empty tree.
// This suggests that there might be a class of types that are foldable
// with something "smaller" than a monoid, consisting only of an
// associative `op`. That kind of object (a monoid without a `zero`) is
// called a semigroup. `Tree` itself is not a monoid, but it is a semigroup.













