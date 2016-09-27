package fp.chapter4.exercises

import fp.chapter4.exercises.Option._
object OptionTest {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  var b = Some(4)                                 //> b  : fp.chapter4.exercises.Some[Int] = Some(4)
  
  b.map(a => a*2)                                 //> res0: fp.chapter4.exercises.Option[Int] = Some(8)
  
  var c:Seq[Double] = Seq(1.0,2.0,3.0)            //> c  : Seq[Double] = List(1.0, 2.0, 3.0)
  variance(c)                                     //> res1: fp.chapter4.exercises.Option[Double] = Some(0.6666666666666666)
  mean(c)                                         //> res2: fp.chapter4.exercises.Option[Double] = Some(2.0)
  
  variance2(c)                                    //> res3: fp.chapter4.exercises.Option[Double] = Some(0.6666666666666666)
  
  val list: List[Int] = List(1,2,3,4,5)           //> list  : List[Int] = List(1, 2, 3, 4, 5)
  
  travers2(list)(a => Some(a.toDouble))           //> res4: fp.chapter4.exercises.Option[List[Double]] = Some(List(1.0, 2.0, 3.0, 
                                                  //| 4.0, 5.0))
  traverse(list)(a => Some(a.toString))           //> res5: fp.chapter4.exercises.Option[List[String]] = Some(List(1, 2, 3, 4, 5))
                                                  //| 
  
  val listOfOptions = List(Some(1),Some(2),Some(3),Some(4),Some(5))
                                                  //> listOfOptions  : List[fp.chapter4.exercises.Some[Int]] = List(Some(1), Some(
                                                  //| 2), Some(3), Some(4), Some(5))
  val listOfOptions2 = List(Some(2),Some(8),Some(3),Some(12), None)
                                                  //> listOfOptions2  : List[Product with Serializable with fp.chapter4.exercises.
                                                  //| Option[Int]] = List(Some(2), Some(8), Some(3), Some(12), None)
  sequenceViaTraverse(listOfOptions)              //> res6: fp.chapter4.exercises.Option[List[Int]] = Some(List(1, 2, 3, 4, 5))
  
  sequenceViaTraverse(listOfOptions2)             //> res7: fp.chapter4.exercises.Option[List[Int]] = None
}