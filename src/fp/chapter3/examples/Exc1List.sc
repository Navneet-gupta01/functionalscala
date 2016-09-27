package fp.chapter3.examples

import fp.chapter3.examples.List._
object Exc1List {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  var a:List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Nil))))
                                                  //> a  : fp.chapter3.examples.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Nil))))
  val b = Cons(0,Cons(1,Cons(2,Cons(3,Nil))))     //> b  : fp.chapter3.examples.Cons[Int] = Cons(0,Cons(1,Cons(2,Cons(3,Nil))))
  val c = Cons(0,Cons(1,Nil))                     //> c  : fp.chapter3.examples.Cons[Int] = Cons(0,Cons(1,Nil))
  
  val d:List[Double] = Cons(1,Cons(2,Cons(3,Cons(4,Nil))))
                                                  //> d  : fp.chapter3.examples.List[Double] = Cons(1.0,Cons(2.0,Cons(3.0,Cons(4.0
                                                  //| ,Nil))))
  
  sum(a)                                          //> res0: Int = 10
  sumProductCombined(a,0)(_+_)                    //> res1: Int = 10
  
  sum(b)                                          //> res2: Int = 6
  sumProductCombined(b,0)(_+_)                    //> res3: Int = 6
 
  product(a)                                      //> res4: Int = 24
  sumProductCombined(a,1)(_*_)                    //> res5: Int = 24
  productPerfo(a)                                 //> res6: Int = 24
  
  product(b)                                      //> res7: Int = 0
  sumProductCombined(b,1)(_*_)                    //> res8: Int = 0
  productPerfo(b)                                 //> res9: Int = 0
  
  
 
  val x = List(1,2,3,4,5) match {
		case Cons(x, Cons(2, Cons(4, _))) => x
		case Nil => 42
		case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
		case Cons(h, t) => h + sum(t)
		case _ => 101
	}                                         //> x  : Int = 3
	
	tail(a)                                   //> res10: fp.chapter3.examples.List[Int] = Cons(2,Cons(3,Cons(4,Nil)))
	tail(b)                                   //> res11: fp.chapter3.examples.List[Int] = Cons(1,Cons(2,Cons(3,Nil)))
	drop(a,2)                                 //> res12: fp.chapter3.examples.List[Int] = Cons(3,Cons(4,Nil))
	drop(b,3)                                 //> res13: fp.chapter3.examples.List[Int] = Cons(3,Nil)
	dropWhile(a,(c:Int) => if(c<=2) true else false)
                                                  //> res14: fp.chapter3.examples.List[Int] = Cons(3,Cons(4,Nil))
	// Calling dropWhile2 removes type anotation
	dropWhile2(a)(x => x<2)                   //> res15: fp.chapter3.examples.List[Int] = Cons(2,Cons(3,Cons(4,Nil)))
	
	dropWhile(b, (c:Int) => if(c<=2) true else false)
                                                  //> res16: fp.chapter3.examples.List[Int] = Cons(3,Nil)
	
	dropWhile2(b)(x => x<=2)                  //> res17: fp.chapter3.examples.List[Int] = Cons(3,Nil)
	
	setHead(a,12)                             //> res18: fp.chapter3.examples.List[Int] = Cons(12,Cons(2,Cons(3,Cons(4,Nil)))
                                                  //| )
	setHead(b,11)                             //> res19: fp.chapter3.examples.List[Int] = Cons(11,Cons(1,Cons(2,Cons(3,Nil)))
                                                  //| )
	
	sum2(a)                                   //> res20: Int = 10
	sum2(b)                                   //> res21: Int = 6
	product2(a)                               //> res22: Double = 24.0
	product2(b)                               //> res23: Double = 0.0
	checkWhatHappens(a)                       //> res24: fp.chapter3.examples.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Nil))))
                                                  //| 
	checkWhatHappens(b)                       //> res25: fp.chapter3.examples.List[Int] = Cons(0,Cons(1,Cons(2,Cons(3,Nil))))
                                                  //| 

	length2(a)                                //> res26: Int = 4
	length2(b)                                //> res27: Int = 4
	length2(c)                                //> res28: Int = 2
	
	sum3(a)                                   //> res29: Int = 10
	sum3(b)                                   //> res30: Int = 6
	sum3(c)                                   //> res31: Int = 1
	product3(a)                               //> res32: Int = 24
	product3(b)                               //> res33: Int = 0
	product3(c)                               //> res34: Int = 0
	length3(a)                                //> res35: Int = 4
	
	length3(b)                                //> res36: Int = 4
	length3(c)                                //> res37: Int = 2
	
	reverse3(a)                               //> res38: fp.chapter3.examples.List[Int] = Cons(4,Cons(3,Cons(2,Cons(1,Nil))))
                                                  //| 
	reverse3(b)                               //> res39: fp.chapter3.examples.List[Int] = Cons(3,Cons(2,Cons(1,Cons(0,Nil))))
                                                  //| 
	reverse3(c)                               //> res40: fp.chapter3.examples.List[Int] = Cons(1,Cons(0,Nil))
	
	concatAllLists(Cons(a,Cons(b,Cons(c,Nil))))
                                                  //> res41: fp.chapter3.examples.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Cons(0,
                                                  //| Cons(1,Cons(2,Cons(3,Cons(0,Cons(1,Nil))))))))))
	
	addOneToEachEltOfList(a)                  //> res42: fp.chapter3.examples.List[Int] = Cons(2,Cons(3,Cons(4,Cons(5,Nil))))
                                                  //| 
	addOneToEachViaMap(a)                     //> res43: fp.chapter3.examples.List[Int] = Cons(2,Cons(3,Cons(4,Cons(5,Nil))))
                                                  //| 
	addOneToEachEltOfList(b)                  //> res44: fp.chapter3.examples.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Nil))))
                                                  //| 
	addOneToEachViaMap(b)                     //> res45: fp.chapter3.examples.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Nil))))
                                                  //| 
	
	addOneToEachEltOfList(c)                  //> res46: fp.chapter3.examples.List[Int] = Cons(1,Cons(2,Nil))
	addOneToEachViaMap(c)                     //> res47: fp.chapter3.examples.List[Int] = Cons(1,Cons(2,Nil))
	
	doubleToString(d)                         //> res48: fp.chapter3.examples.List[String] = Cons(1.0,Cons(2.0,Cons(3.0,Cons(
                                                  //| 4.0,Nil))))
	doubleToStringViaMap(d)                   //> res49: fp.chapter3.examples.List[String] = Cons(1.0,Cons(2.0,Cons(3.0,Cons(
                                                  //| 4.0,Nil))))
  map(a)(a => a+1)                                //> res50: fp.chapter3.examples.List[Int] = Cons(2,Cons(3,Cons(4,Cons(5,Nil))))
                                                  //| 
  
  excludeOdd(a)                                   //> res51: fp.chapter3.examples.List[Int] = Cons(2,Cons(4,Nil))
  excludeOdd(b)                                   //> res52: fp.chapter3.examples.List[Int] = Cons(0,Cons(2,Nil))
  excludeOdd(c)                                   //> res53: fp.chapter3.examples.List[Int] = Cons(0,Nil)
  
  flatMap(a)(i => Cons(i,Cons(i,Nil)))            //> res54: fp.chapter3.examples.List[Int] = Cons(1,Cons(1,Cons(2,Cons(2,Cons(3,
                                                  //| Cons(3,Cons(4,Cons(4,Nil))))))))
  filterViaFlatMap(a)(_%2 == 0)                   //> res55: fp.chapter3.examples.List[Int] = Cons(2,Cons(4,Nil))
  filterViaFlatMap(b)(_%2 == 0)                   //> res56: fp.chapter3.examples.List[Int] = Cons(0,Cons(2,Nil))
  filterViaFlatMap(c)(_%2 == 0)                   //> res57: fp.chapter3.examples.List[Int] = Cons(0,Nil)
  
  val e = a                                       //> e  : fp.chapter3.examples.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Nil))))
  
  manipulateTwoList(a,e)((c,d) => c+d)            //> res58: fp.chapter3.examples.List[Int] = Cons(2,Cons(4,Cons(6,Cons(8,Nil))))
                                                  //| 
  
  genericManipulate2List(a, e)((c,d) => c+d)      //> res59: fp.chapter3.examples.List[Int] = Cons(2,Cons(4,Cons(6,Cons(8,Nil))))
                                                  //| 
  
  
 
}