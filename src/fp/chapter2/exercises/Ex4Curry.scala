package fp.chapter2.exercises

// Example 4 : Currying page:30
object Ex4Curry {
  
  def curry[A,B,C](f:(A,B) => C): A => (B => C) = {
    a => (b => f(a,b))
  }
}