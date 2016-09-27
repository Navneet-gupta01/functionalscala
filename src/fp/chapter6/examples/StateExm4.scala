package fp.chapter6.examples

case class State[S,+A](run : S => (A,S))

object State {
  
}