package fp.chapter2.exercises

// Excersise 3 : Partial1 Page:29

object Ex3Partial1 {

  def partial1[A,B,C](a:A, f:(A,B) => C): (B => C) = {
    (b) => f(a,b)
  }
  
}