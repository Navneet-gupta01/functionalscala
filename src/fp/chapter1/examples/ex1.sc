package fp.chapter1.examples

import fp.chapter1.examples.example1.{Player,winner,printWinner,declareWinner}

object ex1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val pla = List(Player("Sue",7),Player("Bob",8),Player("Joe",9))
                                                  //> pla  : List[fp.chapter1.examples.example1.Player] = List(Player(Sue,7), Play
                                                  //| er(Bob,8), Player(Joe,9))
  
  val p1 = pla.reduceLeft(winner)                 //> p1  : fp.chapter1.examples.example1.Player = Player(Joe,9)
  
  printWinner(p1)                                 //> Joe  is winner !!
 
  // val p2 = pla.reduceLeft(declareWinner) // Not Extendable solution.
 
}