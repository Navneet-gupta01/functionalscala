package fp.chapter3.examples

import scala.annotation.tailrec

// Example Page:34
sealed trait List[+A]

// data constructors of List
case object Nil extends List[Nothing]
case class Cons[+A](head:A, tail : List[A]) extends List[A]

// companion object to List
object List {
  
  def sum(xs:List[Int]) : Int = {
    xs match {
      case Nil => 0
      case Cons(z,zs) => z+sum(zs)
    }
  }
  
  def product(xs:List[Int]): Int = {
    xs match {
      case Nil => 1
      case Cons(z,zs) => z*product(zs) // performance improvement we can avoid looping if the z is zero using another case as below
    }
  }
  //Combinig sum and product methods into single one using HOF: Higher order function
  def sumProductCombined(xs:List[Int],z:Int)(f:(Int,Int) => Int) : Int = {
    xs match {
      case Nil => z
      case Cons(y,ys) => f(y,sumProductCombined(ys,z)(f))
    }
  }
  
  def productPerfo(xs:List[Int]):Int = {
    xs match {
      case Nil => 1
      case Cons(0,_) => 0
      case Cons(y,ys) => y*productPerfo(ys)
    }
  }
  //This isn't the most robust test—pattern matching on 0.0 will match only the
  //exact value 0.0, not 1e-102 or any other value very close to 0.
  
  def apply[A](as : A*):List[A] = {
    if(as.isEmpty) Nil
    else Cons(as.head,apply(as.tail:_*))
  }

  def tail[A](xs:List[A]):List[A] = {
    xs match {
      case Cons(_,ys) => ys
      case _ => Nil
    }
  }
  
  def drop[A](xs:List[A],n:Int):List[A] = {
    if(n<=0) xs
    else {
      xs match {
        case Nil => Nil
        case Cons(_,ys) => drop(ys,n-1)
      }
    }
  }
  
  def dropWhile[A](xs:List[A],f : A => Boolean) : List[A] = {
    xs match {
      case Nil => xs
      case Cons(z,zs)  => if(f(z)) dropWhile(zs,f)  else xs
    }
  }
  // dropWhile2 removes restriction of type anotation required by dropwhile while calling it
  def dropWhile2[A](xs:List[A])(f:A => Boolean) : List[A] = {
    xs match {
      case Nil => xs
      case Cons(z,zs)  => if(f(z)) dropWhile2(zs)(f)  else xs
    }
  }
  
  def setHead[A](xs:List[A],x:A) : List[A] = {
    xs match {
      case Nil => Nil
      case Cons(z,zs) => Cons(x,zs)
    }
  }
  
  def init[A](xs:List[A]) : List[A] = {
    xs match {
      case Nil => Nil
      case Cons(_,Nil) => Nil
      case Cons(h,t) => Cons(h,init(t))
    }
  }
  
  //The sumProductCombined function can be generalized as below a Very useful function
  //@tailrec cannot be optimised for tail recursion as we are applying function after recursion and 
  // recursion is not end call for this
  def foldRight[A,B](xs:List[A],z:B)(f:(A,B) => B): B = {
    xs match {
      case Nil => z
      case Cons(y,ys) => f(y,foldRight(ys,z)(f))
    }
  }
  
  // Now writting sum in terms of foldRight:
  def sum2(xs:List[Int]):Int = foldRight(xs,0)(_+_)
  
  //Product in terms of foldRight
  def product2(xs:List[Int]):Double = foldRight(xs,1.0)(_*_)
  def checkWhatHappens(xs:List[Int]) = foldRight(xs,Nil:List[Int])(Cons(_,_))
  def length2[A](xs : List[A]):Int = foldRight(xs,0:Int)((a,b) => b+1)
  
  //tailRec can be applied as we are doing recursion as last step
  @tailrec
  def foldLeft[A,B](xs: List[A], z: B)(f: (B, A) => B): B = {
    xs match {
      case Nil => z
      case Cons(y,ys) => foldLeft(ys,f(z,y))(f)
    }
  }
  
  // writing sum in terms of foldLeft
  def sum3(xs:List[Int]):Int = foldLeft(xs, 0)(_+_)
  
  def product3(xs:List[Int]):Int = foldLeft(xs, 1)(_*_)
  
  def length3[A](xs:List[A]):Int = foldLeft(xs,0)((a,b) => a+1)
  
  def reverse3[A](xs:List[A]) = foldLeft(xs,Nil:List[A])((a,b) => Cons(b,a))
  
  def fLUsingFr[A,B](xs:List[A],z:B)(f:(B,A) => B): B = {
    foldRight(xs,(b:B) => b)((a,g) => b => g(f(b,a)))(z)
  }
  def fRUsingFl[A,B](xs:List[A],z:B)(f:(A,B) => B) : B = {
    foldLeft(xs,(b:B) => b)((g,a) => b => g(f(a,b)))(z)
  }
  
  def fRUsingFl2[A,B](xs:List[A],z:B)(f:(A,B) => B) : B = {
    foldLeft(reverse3(xs),z)((b,a) => f(a,b))
  }
  
  def append[A](l: List[A], r: List[A]): List[A] = 
    foldRight(l,r)(Cons(_,_))
    
  def concatAllLists[A](xs: List[List[A]]):List[A] = {
    xs match {
      case Nil => Nil
      case Cons(y,ys) => foldLeft(ys,append(Nil,y))(append)
    }
  }
  
  def concatAllList2[A](xs:List[List[A]]):List[A] = {
    foldRight(xs,Nil:List[A])(append)
  }
  
  def addOneToEachEltOfList(xs:List[Int]):List[Int] = {
    xs match {
      case Nil => Nil
      case Cons(y,ys) => Cons(y+1,addOneToEachEltOfList(ys))
    }
  }
  
  def addOneToEachElement2(xs:List[Int]):List[Int] = foldRight(xs,Nil:List[Int])((h,t) => Cons(h+1,t))

  def doubleToString(xs:List[Double]):List[String] = {
    xs match {
      case Nil => Nil
      case Cons(y,ys) => Cons(y.toString(),doubleToString(ys))
    }
  }
  
  def doubleToString2(xs:List[Double]):List[String] = foldRight(xs,Nil:List[String])((h,t) => Cons(h.toString,t))
  
  def map[A,B](xs:List[A])(f: A => B) : List[B] = {
    xs match {
      case Nil => Nil
      case Cons(y,ys) => Cons(f(y),map(ys)(f)) // this step Consume stack.So may be stack overflow, Tail call optimization not possible
    }
  }
  
  def mapViaFoldRight[A,B](xs:List[A])(f:A => B) : List[B] = foldRight(xs,Nil:List[B])((h,t) => Cons(f(h),t))
  
  def addOneToEachViaMap(xs: List[Int]) : List[Int] = map(xs)(a => a+1)
  
  def doubleToStringViaMap(xs : List[Double]): List[String] = map(xs)(a => a.toString())
  
  def filter[A](xs:List[A])(f: A => Boolean): List[A] = {
    xs match {
      case Nil => Nil
      case Cons(y,ys) => if(f(y)) Cons(y,filter(ys)(f)) else filter(ys)(f)
    }
  }
  
  def excludeOdd(xs : List[Int]) : List[Int] = {
    filter(xs)(_%2==0)
  }
  
  def flatMap[A,B](xs: List[A])(f: A => List[B]): List[B] = {
    xs match {
      case Nil => Nil
      case Cons(y,ys) => append(f(y),flatMap(ys)(f))
    }
  }
  // For the below solution just think what is flatmap. first apply function to each list element which will give a list of Lists
  // Due to Function nature then concat all the list.
  def flatMapViaMap[A,B](xs:List[A])(f: A => List[B]): List[B] = {
    concatAllList2(map(xs)(f))
  }
  
  def filterViaFlatMap[A](xs:List[A])(f:A => Boolean): List[A] = {
    flatMap(xs)(x => if(f(x)) Cons(x,Nil) else Nil)
  }
  
  def filterViaFR[A](xs:List[A])(f: A => Boolean) : List[A] = {
    foldRight(xs,Nil:List[A])((h,t) => if(f(h))Cons(h,t) else t)
  }
  
  def filterViaFL[A](xs:List[A])(f:A => Boolean) : List[A] = {
    fRUsingFl(xs, Nil:List[A])((h,t) => if(f(h))Cons(h,t) else t)
  }
  
  def manipulateTwoList(xs:List[Int],ys:List[Int])(f:(Int,Int) => Int) : List[Int] = {
    if(length3(xs) != length3(ys)) 
      Nil
    else {
      (xs,ys) match {
        case (Nil,Nil) => Nil
        case (Cons(a,as),Cons(b,bs)) => Cons(f(a,b),manipulateTwoList(as, bs)(f))
        case _ => Nil
      }
    }
  }
  
  def genericManipulate2List[A,B,C](xs:List[A],ys:List[B])(f:(A,B) => C) : List[C] = {
     if(length3(xs) != length3(ys)) 
      Nil
    else {
      (xs,ys) match {
        case (Nil,Nil) => Nil
        case (Cons(a,as),Cons(b,bs)) => Cons(f(a,b),genericManipulate2List(as, bs)(f))
        case _ => Nil
      }
    }
  }
    
}