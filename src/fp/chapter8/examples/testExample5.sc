package fp.chapter8.examples


object testExample5 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  List.fill(10)(3)                                //> res0: List[Int] = List(3, 3, 3, 3, 3, 3, 3, 3, 3, 3)
  
  val l = List(2,6,3,4,9,4,7,1,5,10,17,12,19,15)  //> l  : List[Int] = List(2, 6, 3, 4, 9, 4, 7, 1, 5, 10, 17, 12, 19, 15)
  
  val ls = l.sorted                               //> ls  : List[Int] = List(1, 2, 3, 4, 4, 5, 6, 7, 9, 10, 12, 15, 17, 19)
  
  ls.tail                                         //> res1: List[Int] = List(2, 3, 4, 4, 5, 6, 7, 9, 10, 12, 15, 17, 19)
  ls.zip(ls.tail)                                 //> res2: List[(Int, Int)] = List((1,2), (2,3), (3,4), (4,4), (4,5), (5,6), (6,7
                                                  //| ), (7,9), (9,10), (10,12), (12,15), (15,17), (17,19))
}