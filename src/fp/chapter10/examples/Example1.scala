package fp.chapter10.examples

trait Monoid[A] {
  def op(a1: A, a2: A): A
  def zero: A
}
object Monoid {
  
  def stringMonoid = new Monoid[String] {
    def op(a1: String, a2: String) = a1 + a2
    def zero = ""
  }
  
  def listMonoid[A] = new Monoid[List[A]] {
    def op(a1: List[A], a2: List[A]) = a1 ++ a2
    def zero = Nil
  }
  
  def intAddition: Monoid[Int] = new Monoid[Int] {
    def op(a1: Int, a2: Int) = a1 + a2
    def zero = 0
  }
  
  def intMultiplication: Monoid[Int] = new Monoid[Int] {
    def op(a1: Int, a2: Int) = a1 * a2
    def zero = 1
  }
  
  def booleanOr: Monoid[Boolean] = new Monoid[Boolean] {
    def op(a1: Boolean, a2: Boolean) = a1 || a2
    def zero = false
  }
  
  def booleanAnd: Monoid[Boolean] = new Monoid[Boolean] {
    def op(a1: Boolean, a2: Boolean) = a1 && a2
    def zero = true
  }
  
  // Notice that we have a choice in how we implement `op`.
  // We can compose the options in either order. Both of those implementations
  // satisfy the monoid laws, but they are not equivalent.
  // This is true in general--that is, every monoid has a _dual_ where the
  // `op` combines things in the opposite order. Monoids like `booleanOr` and
  // `intAddition` are equivalent to their duals because their `op` is commutative
  // as well as associative.
  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def op(a1: Option[A],a2: Option[A]) = a1 orElse a2
    def zero = None
  }
  
  def EndoMonoid[A] : Monoid[A => A] = new Monoid[A => A] {
    def op(a1: (A => A), a2: (A => A)) = a => a1(a2(a))
    def zero = (a => a)
  }
  
  //A function having the same argument and return type is called an endofunction2. 
  def EndoMonoid2[A] : Monoid[A => A] = new Monoid[A => A] {
    def op(a1: (A => A), a2: (A => A)) = a1 compose a2
    def zero = (a => a)
  }
  
  // We can get the dual of any monoid just by flipping the `op`.
  def dual[A](m: Monoid[A]): Monoid[A] = new Monoid[A] {
    def op(a1: A, a2: A): A = m.op(a1, a2)
    val zero = m.zero
  }
  
  def wordsMonoid(s: String): Monoid[String] = new Monoid[String] {
    def op(a1: String, a2: String) = (a1.trim() + s + a2.trim()).trim()
    def zero = ""
  }
  
  def concanate[A](xs: List[A], m: Monoid[A]) : A = xs.foldLeft(m.zero)(m.op)
  
  def foldMap[A,B](as: List[A], m: Monoid[B])(f: A => B): B = 
    as.foldLeft(m.zero)((a,b) => m.op(a,f(b)))
    
  def foldLeftViaFoldMap[A,B](as: List[A])(z:B)(f: (B,A) => B) : B = ??? //foldMap(as)

  
  // The function type `(A, B) => B`, when curried, is `A => (B => B)`.
  // And of course, `B => B` is a monoid for any `B` (via function composition).
  def foldRight[A, B](as: List[A])(z: B)(f: (A, B) => B): B =
    foldMap(as, EndoMonoid[B])(f.curried)(z)

  // Folding to the left is the same except we flip the arguments to
  // the function `f` to put the `B` on the correct side.
  // Then we have to also "flip" the monoid so that it operates from left to right.
  def foldLeft[A, B](as: List[A])(z: B)(f: (B, A) => B): B =
    foldMap(as, dual(EndoMonoid[B]))(a => b => f(b, a))(z)
  
    
  
  
}