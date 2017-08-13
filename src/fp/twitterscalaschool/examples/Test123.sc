package fp.twitterscalaschool.examples

import scala.util.Try

object Test123 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  var str = "12.233434 , 23.232323 "              //> str  : String = "12.233434 , 23.232323 "
  //as.replace(" ", "").split(",")
  val as:Option[Array[String]] = Try {
  	str.replace(" ", "").split(",")
  }.toOption                                      //> as  : Option[Array[String]] = Some([Ljava.lang.String;@2d6e8792)
  
  1.to(10).foldLeft(z)(op)                                     //> res0: scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5, 6, 7
                                                  //| , 8, 9, 10)
}