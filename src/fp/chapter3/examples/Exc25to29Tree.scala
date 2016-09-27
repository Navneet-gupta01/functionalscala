package fp.chapter3.examples

sealed trait Tree[+A]

case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {
  
  def size[A](tree : Tree[A]) : Int = {
    tree match {
      case Leaf(a) => 1
      case Branch(l,r) => 1 + size(l)+size(r)
    }
  }
  
  def maximum(tree : Tree[Int]):Int = {
    tree match {
      case Leaf(a) => a
      case Branch(l,r) => maximum(l) max maximum(r)
    }
  }
  
  def depth(tree: Tree[Int]) : Int = {
    tree match {
      case Leaf(_) => 0
      case Branch(l,r) => 1+(depth(l) max depth(r))
    }
  }
  
  def mapOnTree[A,B](tree : Tree[A])(f: A => B) : Tree[B] = {
    tree match {
      case Leaf(a) => Leaf(f(a))
      case Branch(l,r) => Branch(mapOnTree(l)(f),mapOnTree(r)(f))
    }
  }
  
  def fold[A,B](tree : Tree[A])(f: A => B)(g: (B,B) => B) : B = {
    tree match {
      case Leaf(a) => f(a)
      case Branch(l,r) => g(fold(l)(f)(g),fold(r)(f)(g))
    }
  }
  
  def sizeViaFold(tree:Tree[Int]): Int = fold(tree)(a => 1)((b,c) => 1+b+c)
  
  def maxViaFold(tree:Tree[Int]):Int = fold(tree)(a => a)((b,c) => b max c)
  
  def depthViaFold[A](tree:Tree[A]):Int = fold(tree)(a => 0)((b,c) => 1 + (b max c))
  
  
  def mapViaFold[A,B](tree:Tree[A])(h:A => B) : Tree[B] = fold(tree)(a => Leaf(h(a)):Tree[B])(Branch(_,_)) 
  
  
}