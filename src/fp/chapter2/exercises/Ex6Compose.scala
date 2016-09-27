package fp.chapter2.exercises

//Exercise 6 : Compose page: 30
object Ex6Compose {
  
  def compose[A,B,C](f:B=>C, g: A=>B) : A => C = {
    a => f(g(a))
  }
}