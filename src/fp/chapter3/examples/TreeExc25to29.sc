package fp.chapter3.examples

import fp.chapter3.examples.Tree._
object tree {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  var tree:Tree[Int] = Branch(Leaf(1),Branch(Leaf(2),Branch(Leaf(1),Leaf(2))))
                                                  //> tree  : fp.chapter3.examples.Tree[Int] = Branch(Leaf(1),Branch(Leaf(2),Branc
                                                  //| h(Leaf(1),Leaf(2))))
  
  var t:Tree[Int] = Branch(Branch(Branch(Branch(Leaf(1),Branch(Leaf(2),Leaf(3))),Branch(Leaf(4),Leaf(5))),Branch(Branch(Branch(Branch(Branch(Leaf(6),Leaf(7)),Leaf(8)),Leaf(9)),Leaf(10)),Leaf(11))),Branch(Leaf(1),Branch(Leaf(2),Branch(Leaf(1),Leaf(2)))))
                                                  //> t  : fp.chapter3.examples.Tree[Int] = Branch(Branch(Branch(Branch(Leaf(1),Br
                                                  //| anch(Leaf(2),Leaf(3))),Branch(Leaf(4),Leaf(5))),Branch(Branch(Branch(Branch(
                                                  //| Branch(Leaf(6),Leaf(7)),Leaf(8)),Leaf(9)),Leaf(10)),Leaf(11))),Branch(Leaf(1
                                                  //| ),Branch(Leaf(2),Branch(Leaf(1),Leaf(2)))))
 	size(tree)                                //> res0: Int = 7
 	sizeViaFold(tree)                         //> res1: Int = 7
 	size(t)                                   //> res2: Int = 29
 	sizeViaFold(t)                            //> res3: Int = 29
 	
 	maximum(tree)                             //> res4: Int = 2
 	maxViaFold(tree)                          //> res5: Int = 2
 	maximum(t)                                //> res6: Int = 11
 	maxViaFold(t)                             //> res7: Int = 11
 	
 	depth(tree)                               //> res8: Int = 3
 	depthViaFold(tree)                        //> res9: Int = 3
 	depth(t)                                  //> res10: Int = 7
 	depthViaFold(t)                           //> res11: Int = 7
 	
 	mapOnTree(tree)(a=>a*2)                   //> res12: fp.chapter3.examples.Tree[Int] = Branch(Leaf(2),Branch(Leaf(4),Branch
                                                  //| (Leaf(2),Leaf(4))))
 	mapViaFold(tree)(a=>a*2)                  //> res13: fp.chapter3.examples.Tree[Int] = Branch(Leaf(2),Branch(Leaf(4),Branch
                                                  //| (Leaf(2),Leaf(4))))
 	mapOnTree(t)(a=>a*2)                      //> res14: fp.chapter3.examples.Tree[Int] = Branch(Branch(Branch(Branch(Leaf(2),
                                                  //| Branch(Leaf(4),Leaf(6))),Branch(Leaf(8),Leaf(10))),Branch(Branch(Branch(Bran
                                                  //| ch(Branch(Leaf(12),Leaf(14)),Leaf(16)),Leaf(18)),Leaf(20)),Leaf(22))),Branch
                                                  //| (Leaf(2),Branch(Leaf(4),Branch(Leaf(2),Leaf(4)))))
 	mapViaFold(t)(a=>a*2)                     //> res15: fp.chapter3.examples.Tree[Int] = Branch(Branch(Branch(Branch(Leaf(2),
                                                  //| Branch(Leaf(4),Leaf(6))),Branch(Leaf(8),Leaf(10))),Branch(Branch(Branch(Bran
                                                  //| ch(Branch(Leaf(12),Leaf(14)),Leaf(16)),Leaf(18)),Leaf(20)),Leaf(22))),Branch
                                                  //| (Leaf(2),Branch(Leaf(4),Branch(Leaf(2),Leaf(4)))))
 	
 	
}