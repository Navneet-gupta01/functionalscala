package fp.chapter6.examples

trait RNG_2 {
  def nextInt : (Int,RNG_2)
}

object RNG_2 {
  
  def simple(seed: Long): RNG_2 = new RNG_2 {
    def nextInt = {
      val seed2 = (seed*0x5DEECE66DL + 0xBL) &
      ((1L << 48) - 1)
      ((seed2 >>> 16).asInstanceOf[Int],
      simple(seed2))
    }
  }
  
  type Rand[+A] = RNG_2 => (A,RNG_2)
  
  val int: Rand[Int] = _.nextInt
  
  def unit[A](a: A): Rand[A] =
    rng => (a, rng)
 
  def map[A,B](s: Rand[A])(f: A => B): Rand[B] = {
      rng => {
        val (i1,r1) = s(rng)
        (f(i1),r1)
      }
  }
    
  def positiveInt : Rand[Int] = rng => {
    val (i1,rng1) = rng.nextInt
    if(Int.MinValue == i1) positiveInt(rng1)
    else (i1.abs,rng1)
  } 
  
  def positiveMax(n: Int): Rand[Double] = map(positiveInt)(a => ((a.toDouble/Int.MaxValue)*n))
  
  def double: Rand[Double] = map(positiveInt)(a => a.toDouble/(Int.MaxValue+1))
  
  def map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = rng => {
    val (i1,r1) = ra(rng)
    val (i2,r2) = rb(r1)
    (f(i1,i2),r2)
  }
  
  def intDouble = map2(positiveInt,double)((a,b) => (a,b))
  
  def doubleInt = map2(positiveInt,double)((a,b) => (b,a))
  
  //TODO
  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = rng => {
    fs match {
      case x::xs => {
        val (i1,r1) = x(rng)
        sequence(xs)(r1)
      }
      case Nil => (Nil,rng)            
    }
  }

  def sequence2[A](fs: List[Rand[A]]): Rand[List[A]] =
    fs.foldRight(unit(Nil:List[A]))((f, acc) => map2(f, acc)(_ :: _))
    
    //TODO
  def ints(count: Int)(rng: RNG_2): (List[Int], RNG_2) = sequence2(List.fill(count)(int))(rng)
  
  def flatMap[A,B](f: Rand[A])(g: A => Rand[B]): Rand[B] = rng => {
    f(rng) match {
      case (a,rng1) => g(a)(rng1)
    }
  }
  /*def map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = rng => {
    val (i1,r1) = ra(rng)
    val (i2,r2) = rb(r1)
    (f(i1,i2),r2)
  }
  def map[A,B](s: Rand[A])(f: A => B): Rand[B] = {
      rng => {
        val (i1,r1) = s(rng)
        (f(i1),r1)
      }
  }
  rng => {
  	ra(rng) match {
  		case (a,rng1) => map(rb)(b => f(a,b))(rng1)
  											rng1 => rb(rng1) match {case (b,rng2) => (f(a,b),rng2)}
  										
  	}
  }
  
  * */
 
  
  def positiveIntViaflatMap : Rand[Int] = flatMap(int)(a => {if(a==Int.MinValue)positiveIntViaflatMap  else int})
  
  def mapViaflatMap[A,B](s: Rand[A])(f: A => B): Rand[B] = flatMap(s){a => unit(f(a))}
  
  def map2ViaFlatMap[A,B,C](ra:Rand[A],rb: Rand[B])(f: (A,B) => C): Rand[C] = flatMap(ra)(a => map(rb)(b => f(a,b) ))

}