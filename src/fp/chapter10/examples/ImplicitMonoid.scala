package fp.chapter10.examples

object ImplicitMonoid {
  
  def monoid[A](implicit A: Monoid[A]): Monoid[A] = A
  
  case class Product(value: Int)
  
  implicit val ProductMonoid: Monoid[Product] = new Monoid[Product] {
    def op(a: Product, b: Product) = Product(a.value * b.value)
    def zero = Product(1)
  }
  
  case class Sum(value: Int)
  
  implicit val SumMonoid: Monoid[Sum] = new Monoid[Sum] {
    def op(a: Sum, b: Sum) = Sum(a.value + b.value)
    def zero = Sum(0)
  }
  
  val ints: List[Int] = List(2,5,3,6,3,46,7,8,5,24,56,76,3)
  
  val sum = FoldableList.foldMap(ints)(Sum(_))(_)
  
  val product = FoldableList.foldMap(ints)(Product(_))(_)
}