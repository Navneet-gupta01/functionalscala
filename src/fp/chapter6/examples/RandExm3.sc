package fp.chapter6.examples

import fp.chapter6.examples.RNG_2._
object RandExm3 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val rng = simple(542466567)                     //> rng  : fp.chapter6.examples.RNG_2 = fp.chapter6.examples.RNG_2$$anon$1@7a79b
                                                  //| e86
  int(rng)                                        //> res0: (Int, fp.chapter6.examples.RNG_2) = (-520754463,fp.chapter6.examples.R
                                                  //| NG_2$$anon$1@77b52d12)
  unit(12)(rng)                                   //> res1: (Int, fp.chapter6.examples.RNG_2) = (12,fp.chapter6.examples.RNG_2$$an
                                                  //| on$1@7a79be86)
  positiveInt(rng)                                //> res2: (Int, fp.chapter6.examples.RNG_2) = (520754463,fp.chapter6.examples.RN
                                                  //| G_2$$anon$1@2d554825)
  Int.MaxValue                                    //> res3: Int(2147483647) = 2147483647
  
  positiveInt(rng)._1.toDouble/Int.MaxValue       //> res4: Double = 0.2424951937247511
  positiveInt(rng)._1.toDouble/Int.MaxValue*6     //> res5: Double = 1.4549711623485067
  
  positiveMax(6)(rng)                             //> res6: (Double, fp.chapter6.examples.RNG_2) = (1.4549711623485067,fp.chapter6
                                                  //| .examples.RNG_2$$anon$1@47f6473)
  
  double(rng)                                     //> res7: (Double, fp.chapter6.examples.RNG_2) = (-0.24249519361183047,fp.chapte
                                                  //| r6.examples.RNG_2$$anon$1@15975490)
  
  intDouble(rng)                                  //> res8: ((Int, Double), fp.chapter6.examples.RNG_2) = ((520754463,-0.934483092
                                                  //| 7439034),fp.chapter6.examples.RNG_2$$anon$1@617c74e5)
  
  doubleInt(rng)                                  //> res9: ((Double, Int), fp.chapter6.examples.RNG_2) = ((-0.9344830927439034,52
                                                  //| 0754463),fp.chapter6.examples.RNG_2$$anon$1@34b7bfc0)
  //var a: Rand[Int] = rng => a:Int => unit(a)
  var count : Int = 4                             //> count  : Int = 4
  List.fill(count)(0)                             //> res10: List[Int] = List(0, 0, 0, 0)
	var a = List(1,2,3,4)                     //> a  : List[Int] = List(1, 2, 3, 4)
	a.map(x => x*2)                           //> res11: List[Int] = List(2, 4, 6, 8)
	
	a.map(x => unit(x))                       //> res12: List[fp.chapter6.examples.RNG_2.Rand[Int]] = List(<function1>, <funct
                                                  //| ion1>, <function1>, <function1>)

	var bd = List[Int]()                      //> bd  : List[Int] = List()
	var cd = Nil:List[Int]                    //> cd  : List[Int] = List()
	bd==cd                                    //> res13: Boolean = true
}