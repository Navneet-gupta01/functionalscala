package fp.chapter5.examples

import fp.chapter5.examples.exmp1._
object exmpl1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  if2(false,sys.error("Condition is flase"),3)    //> res0: Int = 3
  if2(false,sys.error("Condition is true"),4)     //> res1: Int = 4
  def func = {println("hi");1+42}                 //> func: => Int
  //pair(println("Hi"); 1+423)
  func                                            //> hi
                                                  //| res2: Int = 43
  pair(func)                                      //> hi
                                                  //| hi
                                                  //| res3: (Int, Int) = (43,43)
  
  pair {println("Hi There !"); 1+54}              //> Hi There !
                                                  //| Hi There !
                                                  //| res4: (Int, Int) = (55,55)
  
  pair2(func)                                     //> hi
                                                  //| res5: (Int, Int) = (43,43)
  pair2 {println("hi"); 1+54}                     //> hi
                                                  //| res6: (Int, Int) = (55,55)
  pair3(func)                                     //> hi
                                                  //| res7: (Int, Int) = (43,43)
  pair3 {println("hi"); 1+54}                     //> hi
                                                  //| res8: (Int, Int) = (55,55)
  
  
  
}