package fp.chapter2.examples

object example1 {
  
  def abs(n :Int) : Int = {
    if(n<0) -n else n
  }
  
  def formatAbs(x: Int) = {
    val msg = "The absolute value of %d is %d"
    val msg1 = msg.format(x,abs(x))
    // using String InterPolation
    val msg2 = s"The absolute value of $x is ${abs(x)}"
    "msg1 == " + msg1 + ".  msg2 == " + msg2
  }
  
}