package fp.chapter5.exercises

import fp.chapter5.exercises.Stream._
object StreamExc1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val a = cons(21,cons(5,cons(3,cons(1, cons(2, empty)))))
                                                  //> a  : fp.chapter5.exercises.Stream[Int] = fp.chapter5.exercises.Stream$$anon$
                                                  //| 2@36d64342
  
  a.toList                                        //> res0: List[Int] = List(21, 5, 3, 1, 2)
  a                                               //> res1: fp.chapter5.exercises.Stream[Int] = fp.chapter5.exercises.Stream$$anon
                                                  //| $2@36d64342
  a.take(3)                                       //> scala.MatchError: Some((1,fp.chapter5.exercises.Stream$$anon$2@1936f0f5)) (o
                                                  //| f class scala.Some)
                                                  //| 	at fp.chapter5.exercises.Stream$class.take(Exc1Stream.scala:11)
                                                  //| 	at fp.chapter5.exercises.Stream$$anon$2.take(Exc1Stream.scala:40)
                                                  //| 	at fp.chapter5.exercises.Stream$class.take(Exc1Stream.scala:12)
                                                  //| 	at fp.chapter5.exercises.Stream$$anon$2.take(Exc1Stream.scala:40)
                                                  //| 	at fp.chapter5.exercises.Stream$class.take(Exc1Stream.scala:12)
                                                  //| 	at fp.chapter5.exercises.Stream$$anon$2.take(Exc1Stream.scala:40)
                                                  //| 	at fp.chapter5.exercises.Stream$class.take(Exc1Stream.scala:12)
                                                  //| 	at fp.chapter5.exercises.Stream$$anon$2.take(Exc1Stream.scala:40)
                                                  //| 	at fp.chapter5.exercises.StreamExc1$$anonfun$main$1.apply$mcV$sp(fp.chap
                                                  //| ter5.exercises.StreamExc1.scala:11)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orkshe
                                                  //| Output exceeds cutoff limit.
  
  
}