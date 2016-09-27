package fp.chapter6.examples

import fp.chapter6.examples.RNG._
object RNGExm2 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val rng = simple(1234544656)                    //> rng  : fp.chapter6.examples.RNG = fp.chapter6.examples.RNG$$anon$1@511baa65
  randomPair(rng)                                 //> res0: (Int, Int) = (675321974,675321974)
  randomPair2(rng)                                //> res1: (Int, Int) = (675321974,-2021072608)
  double(rng)                                     //> res2: (Double, fp.chapter6.examples.RNG) = (0.3144712999064807,fp.chapter6.e
                                                  //| xamples.RNG$$anon$1@68837a77)
  
  intDouble(rng)                                  //> res3: ((Int, Double), fp.chapter6.examples.RNG) = ((675321974,0.941135272821
                                                  //| 9401),fp.chapter6.examples.RNG$$anon$1@327471b5)
  doubleInt(rng)                                  //> res4: ((Double, Int), fp.chapter6.examples.RNG) = ((0.9411352728219401,67532
                                                  //| 1974),fp.chapter6.examples.RNG$$anon$1@3a03464)
  double3(rng)                                    //> res5: ((Double, Double, Double), fp.chapter6.examples.RNG) = ((0.31447129990
                                                  //| 64807,0.9411352728219401,0.6748256192891047),fp.chapter6.examples.RNG$$anon$
                                                  //| 1@2d3fcdbd)
  ints(9)(rng)                                    //> res6: (List[Int], fp.chapter6.examples.RNG) = (List(136162064, 721752425, 16
                                                  //| 4854874, 538836693, 1486393585, 434883886, 1449176982, 2021072608, 675321974
                                                  //| ),fp.chapter6.examples.RNG$$anon$1@3cbbc1e0)
}