package fp.chapter5.exercises

import fp.chapter5.exercises.Stream1._

object Stream1Exc1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val a:Stream1[Int] = cons(1,cons(2,cons(3,cons(4,cons(5,empty)))))
                                                  //> a  : fp.chapter5.exercises.Stream1[Int] = Cons(<function0>,<function0>)
  
  val b: Stream1[Int] = cons(6,cons(7,cons(8,empty)))
                                                  //> b  : fp.chapter5.exercises.Stream1[Int] = Cons(<function0>,<function0>)
 
  a.toList                                        //> res0: List[Int] = List(1, 2, 3, 4, 5)
  
  a.toListTR                                      //> res1: List[Int] = List(1, 2, 3, 4, 5)
  
  a.toListFast                                    //> res2: List[Int] = List(1, 2, 3, 4, 5)
  
  a.foldRight(0)(_+_)                             //> res3: Int = 15
  
  a.exists(a => (a%2 == 0))                       //> res4: Boolean = true
  
  a.exists(a => a==true)                          //> res5: Boolean = false
  
  a.existsTR(a => (a%2 == 0))                     //> res6: Boolean = true
  
  a.existsTR(a => a==true)                        //> res7: Boolean = false
  
  a.existsViaFR(a => (a%2 == 0))                  //> res8: Boolean = true
  
  a.existsViaFR(a => a==true)                     //> res9: Boolean = false
  
  a.take(2).toListFast                            //> res10: List[Int] = List(1, 2)
  
  a.takeViaFR(4).toListFast                       //> res11: List[Int] = List(1, 2, 3, 4)
  
  a.toListFast                                    //> res12: List[Int] = List(1, 2, 3, 4, 5)
  
  a.drop(2).toList                                //> res13: List[Int] = List(3, 4, 5)
  
  a.toListFast                                    //> res14: List[Int] = List(1, 2, 3, 4, 5)
  
  a.drop(4).toList                                //> res15: List[Int] = List(5)
  
  a.takeWhile(x => x<3).toListFast                //> res16: List[Int] = List(1, 2)
  
  a.takeWhileViaFR { x => x<5 }.toListFast        //> res17: List[Int] = List(1, 2, 3, 4)
  
  a.forAll { x => x%1==0 }                        //> res18: Boolean = true
  
  a.foldRight(Empty:Stream1[Int])((a,b) => cons(a*2,b)).toListFast
                                                  //> res19: List[Int] = List(2, 4, 6, 8, 10)
  
  a.foldRight(Empty:Stream1[Int])((a,b) => cons(a*2,b)).forAll { x => x%2 ==0 }
                                                  //> res20: Boolean = true
  
   a.foldRight(Empty:Stream1[Int])((a,b) => cons(a*2,b)).forAll { x => x%3 ==0 }
                                                  //> res21: Boolean = false
   
   a.map { x => x*2 }.toListFast                  //> res22: List[Int] = List(2, 4, 6, 8, 10)
   
   a.mapViaFR { x => x*2+10 }.toListFast          //> res23: List[Int] = List(12, 14, 16, 18, 20)
   
   a.map { x => x*2 }.filter { x => x%3==0 }.toListFast
                                                  //> res24: List[Int] = List(6)
   
   a.map { x => x*2 }.filter { x => x%4==0}.toListFast
                                                  //> res25: List[Int] = List(4, 8)
   
   a.map { x => x*2 }.filterViaFR { x => x%3==0 }.toListFast
                                                  //> res26: List[Int] = List(6)
   
   a.map { x => x*2 }.filterViaFR { x => x%4==0 }.toListFast
                                                  //> res27: List[Int] = List(4, 8)
   
   
   a.append(b).toListFast                         //> res28: List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8)
   
   a.flatMap { x => cons(x*2,Empty) }.toListFast  //> res29: List[Int] = List(2, 4, 6, 8, 10)
                                                  
   constant(2).take(5).toListFast                 //> res30: List[Int] = List(2, 2, 2, 2, 2)
   
   from(0).take(10).toListFast                    //> res31: List[Int] = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
   
   fibs.take(10).toListFast                       //> res32: List[Int] = List(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
   
   
   
 	def gets[A](ex : Stream1[A]) = {
 		Stream1.constant(ex).take(10)
 	}                                         //> gets: [A](ex: fp.chapter5.exercises.Stream1[A])fp.chapter5.exercises.Stream
                                                  //| 1[fp.chapter5.exercises.Stream1[A]]
  gets(b)                                         //> res33: fp.chapter5.exercises.Stream1[fp.chapter5.exercises.Stream1[Int]] = 
                                                  //| Cons(<function0>,<function0>)
  
}