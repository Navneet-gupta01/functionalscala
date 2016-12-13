package fp.chapter8.exercises

import fp.chapter6.examples.State
import fp.chapter6.examples.RNG
import fp.chapter8.examples.Gen

object Exercises4 {
  
  def choose(start: Int, stopExclusive: Int): Gen[Int] = 
    Gen(State(RNG.nonNegativeInt)).map(a => start + a % (stopExclusive-start))
    
  
}