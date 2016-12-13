package fp.chapter7.examples

import fp.chapter7.examples.Exm1SeqSum._

object SeqSumExm1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  sum(IndexedSeq(1,2,3,4,5))                      //> res0: Int = 15
  
  var a = List(1,3,2).map { x => x*2 }            //> a  : List[Int] = List(2, 6, 4)
  
  a.toArray                                       //> res1: Array[Int] = Array(2, 6, 4)
  
  List('A','B','C','D').toArray.length            //> res2: Int = 4
  
  val s = List(('A',1),('B',2),('C',3))           //> s  : List[(Char, Int)] = List((A,1), (B,2), (C,3))
  
  //s.contains(('A',x:Int))
  
  var s1 = List('C','A','B','D','G','E')          //> s1  : List[Char] = List(C, A, B, D, G, E)
  
  s1.sorted                                       //> res3: List[Char] = List(A, B, C, D, E, G)
}