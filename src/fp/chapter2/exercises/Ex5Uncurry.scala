package fp.chapter2.exercises

//Exapmle 5 : UnCurry Page:30
object Ex5Uncurry {
  
  def unCurry[A,B,C](f: A => (B => C)): (A,B)=>C = {
    (a,b) => f(a)(b)
  }
  
}