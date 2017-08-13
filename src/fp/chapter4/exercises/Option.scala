package fp.chapter4.exercises

import java.util.regex.{Pattern,PatternSyntaxException}

trait Option[+A] {
  
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(x) => Some(f(x))
  }
    
  def flatMap[B](f: A => Option[B]): Option[B] = this match {
    case None => None
    case Some(x) => f(x)
  }
  // Copied But its good
  def flatMap2[B](f: A => Option[B]): Option[B] = map(f) getOrElse None
  
  def getOrElse[B >: A](default: => B): B = this match {
    case None => default
    case Some(x) => x
  }
  
  def orElse[B >: A](ob: => Option[B]): Option[B] = this match {
    case None => ob
    case Some(a) => Some(a)
  }
  
  def filter(f: A => Boolean): Option[A] = this match {
    case Some(a) if(f(a)) => Some(a)
    case _ => None
  }
  
  def filter2(f:A => Boolean): Option[A] = flatMap(a => if(f(a)) Some(a) else None)
  
  
}

case object None extends Option[Nothing]
case class Some[+A](x:A) extends Option[A]

object Option {
  
   def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length) 
  
    
  def variance(xs:Seq[Double]) : Option[Double] = {
    val m:Double = mean(xs).getOrElse(0)
    val z:Seq[Double] = xs.map{ x => math.pow(x-m, 2.0)}
    mean(z)
  }
   
  def variance2(xs:Seq[Double]) : Option[Double] = {
    mean(xs).flatMap { x => mean(xs.map { y => math.pow(x-y, 2) }) }
  }
  
  def pattern(s: String): Option[Pattern] =
    try {
      Some(Pattern.compile(s))
    } catch {
      case e: PatternSyntaxException => None
    }

  def mkMatcher(pat: String): Option[String => Boolean] =
    pattern(pat) map (p => (s: String) => p.matcher(s).matches)
  
  def mkMatcher_1(pat: String): Option[String => Boolean] =
    for {
      p <- pattern(pat)
    } yield ((s: String) => p.matcher(s).matches)
    
    
  def doesMatch(pat: String, s: String): Option[Boolean] =
    for {
      p <- mkMatcher_1(pat)
    } yield p(s)

    
  def bothMatch(pat: String, pat2: String, s: String): Option[Boolean] =
    for {
      f <- mkMatcher(pat)
      g <- mkMatcher(pat2)
    } yield f(s) && g(s)

  def bothMatch_1(pat: String, pat2: String, s: String): Option[Boolean] = 
    mkMatcher(pat) flatMap (f =>
      mkMatcher(pat2) map (g => f(s) && g(s)))
      
  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = 
    (a,b) match {
      case (Some(x),Some(y)) => Some(f(x,y))
      case (_,_) => None
    }
    
  def map2_1[A,B,C](a:Option[A],b:Option[B])(f:(A,B) => C) : Option[C] = a flatMap2 { x => b map { y => f(x,y) } }
    
  def bothMatch_2(pat1: String, pat2: String, s: String): Option[Boolean] = 
    map2(mkMatcher(pat1),mkMatcher(pat2))((a,b) => a(s)&&b(s))
    
  
 /* def sequence[A](a: List[Option[A]]): Option[List[A]] = {
   foldRight(a,Nil:List[A])(a => a match {
     case None => None
     case 
   })
  }*/
  
  def sequence[A](a:List[Option[A]]): Option[List[A]] = {
    def getList(l:List[Option[A]]):List[A] = {
      a match {
        case Nil => Nil
        case None::xs => Nil
        case Some(a)::as => a::getList(as)
      }
    }
    val list = getList(a)
    list match {
      case Nil => None
      case _ => Some(list)
    }
  }
  
  def sequence3[A](a:List[Option[A]]): Option[List[A]] = {
    def getList(l:List[Option[A]]):List[A] = {
      a match {
        case Nil => Nil
        case x::xs => 
          x match {
            case None => Nil
            case Some(a) => a::getList(xs)
          }
      }
    }
    val list = getList(a)
    list match {
      case Nil => None
      case _ => Some(list)
    }
  }
  
  def parsePatterns(a: List[String]): Option[List[Pattern]] =
    sequence(a map pattern)

  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
    a match {
      case Nil => Some(Nil)
      case x::xs => f(x) flatMap { x => traverse(xs)(f) map { y => x::y } } 
    }
  }
  
  /*def sequence2[A](a:List[Option[A]]):Option[List[A]] = {
    a.foldRight(Some(Nil))()
  }*/
  
  def sequenceViaTraverse[A](a:List[Option[A]]) : Option[List[A]] = {
    a.foldRight(Some(Nil):Option[List[A]])((a,b:Option[List[A]]) => a flatMap { x => b map { y => x::y } })
  }
  
  def travers2[A,B](a:List[A])(f:A => Option[B]): Option[List[B]] = {
    a.foldRight(Some(Nil):Option[List[B]])((a,b) => b flatMap { x => f(a) map { y => y :: x } })
  }




}
