package fp.chapter2.exercises

import fp.chapter2.exercises.Exc2IsSorted.{isSorted,isSortedBoth}
object exc2IsSorted {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  isSorted(Array(1,2,3,4,5,6),(a:Int,b:Int) => {if(a>b)true else false})
                                                  //> res0: Boolean = true
  
  isSortedBoth(Array(1,2,3,4,5,6),(a:Int,b:Int) => {if(a>b)true else false})
                                                  //> res1: Boolean = true
  
  
  isSorted(Array(6,5,4,3,2,1),(a:Int,b:Int) => {if(a>b)true else false})
                                                  //> res2: Boolean = false
  
  isSortedBoth(Array(6,5,4,3,2,1),(a:Int,b:Int) => {if(a>b)true else false})
                                                  //> res3: Boolean = true
  
  isSorted(Array(1,1,1,1),(a:Int,b:Int) => {if(a>b)true else false})
                                                  //> res4: Boolean = true
  
  isSortedBoth(Array(6,5,4,3,2,1),(a:Int,b:Int) => {if(a>b)true else false})
                                                  //> res5: Boolean = true
  
  isSortedBoth(Array(1,4,2,6,3,4,3),(a:Int,b:Int) => {if(a>b)true else false})
                                                  //> res6: Boolean = false
                                                  
  1>1                                             //> res7: Boolean(false) = false
  
  
}