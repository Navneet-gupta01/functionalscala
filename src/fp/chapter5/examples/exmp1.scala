package fp.chapter5.examples

object exmp1 {
  
  def if2[A](cond: Boolean, onTrue: => A, onFalse: => A): A =
    if (cond) onTrue else onFalse
    
  def pair(i: => Int) = (i, i)
  
  def pair2(i: => Int) = { lazy val j = i; (j, j) }
  
  def pair3(i: => Int) = {val j = i; (j,j)}
}