package fp.chapter7.examples

import fp.chapter7.examples.Exm1SeqSum._

object SeqSumExm1 {
  println("Welcome to the Scala worksheet")
  
  sum(IndexedSeq(1,2,3,4,5))
  
  var a = List(1,3,2).map { x => x*2 }
  
  a.toArray
  
  List('A','B','C','D').toArray.length
  
  val s = List(('A',1),('B',2),('C',3))
  
  s.contains(('A',x:Int))
}