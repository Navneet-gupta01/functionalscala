package fp.chapter1.examples

object example1 {
  case class Player(name: String,score : Int)
  
  def printWinner(p: Player) : Unit = {
    println(p.name + "  is winner !!");
  }
  
  def declareWinner(p1: Player, p2 :Player): Unit = {
    if(p1.score > p2.score) {
      printWinner(p1)
    } else 
      printWinner(p2)
  }
  
  // now refactor above code as below
  
  def winner(p1 : Player,p2:Player) : Player = {
    if(p1.score > p2.score) p1 else p2
  }
  
  def declareWinnerRefactored(p1:Player,p2:Player): Unit = {
    printWinner(winner(p1,p2))
  }
}