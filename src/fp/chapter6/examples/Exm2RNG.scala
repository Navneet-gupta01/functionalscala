package fp.chapter6.examples

import scala.annotation.tailrec

trait RNG {
  def nextDouble : (Double,RNG) = ???
  def nextBoolean : (Boolean,RNG) = ???
  def nextInt : (Int,RNG)
}



object RNG {
  
  //<< is left binary shift
  //>>> is right binary shift with zero fill
  def simple(seed: Long): RNG = new RNG {
    def nextInt = {
      val seed2 = (seed*0x5DEECE66DL + 0xBL) &
      ((1L << 48) - 1)
      ((seed2 >>> 16).asInstanceOf[Int],
      simple(seed2))
    }
  }
  
  //this method will generate same RNG everytime since it is not using the rng(_), 
  def randomPair(rng: RNG): (Int,Int) = {
    val (i1,_) = rng.nextInt
    val (i2,_) = rng.nextInt
    (i1,i2)
  }
  
  // to mitigate above problem we can pass generate RNG from first call to second call
  def randomPair2(rng: RNG): (Int,Int) = {
    val (i1,rng1) = rng.nextInt
    val (i2,rng2) = rng1.nextInt
    (i1,i2)
  }
  
  def positiveInt(rng:RNG): (Int, RNG) = {
    val (i1,rng1) = rng.nextInt
    if(Int.MinValue == i1) positiveInt(rng1)
    else (i1.abs,rng1)
  }
  
  // We need to be quite careful not to skew the generator.
  // Since `Int.Minvalue` is 1 smaller than `-(Int.MaxValue)`,
  // it suffices to increment the negative numbers by 1 and make them positive.
  // This maps Int.MinValue to Int.MaxValue and -1 to 0.
  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (i, r) = rng.nextInt
    (if (i < 0) -(i + 1) else i, r)
  }
  
  def double(rng: RNG): (Double, RNG) = {
    val (i1,rng1) =  positiveInt(rng)
    (i1.toDouble/Int.MaxValue,rng1)
  }
  
  def intDouble(rng: RNG): ((Int,Double), RNG) = {
    val (i1,rng1) = positiveInt(rng)
    val (d1,rng2) = double(rng1)
    ((i1,d1),rng2)
  }
  
  def doubleInt(rng: RNG): ((Double,Int), RNG) = {
    val ((i1,d1),rng1) = intDouble(rng)
    ((d1,i1),rng1)
  }
  
  def double3(rng: RNG): ((Double,Double,Double), RNG) = {
    val (d1,rng1) = double(rng)
    val (d2,rng2) = double(rng1)
    val (d3,rng3) = double(rng2)
    ((d1,d2,d3),rng3)
  }
  
  def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
    @tailrec
    def go(rng:RNG,as : List[Int],count:Int):(List[Int],RNG) = {
      if(count>0) {
        val (i1,rng1) = positiveInt(rng)
        go(rng1,i1::as,count-1)
      } else (as,rng)
    }
    go(rng,Nil,count)  
  }
  
  def boolean(rng: RNG): (Boolean, RNG) =
    rng.nextInt match { case (i,rng2) => (i%2==0,rng2) }
  
  
}