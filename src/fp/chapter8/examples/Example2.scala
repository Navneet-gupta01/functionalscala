package fp.chapter8.examples

import fp.chapter8.examples.Prop1.SuccessCount
import fp.chapter8.examples.Prop2._

object Prop1 {
  type SuccessCount = Int
}

// Earlier we hav gone to have Prop as below 
//trait Prop { def check: Boolean}

// But this doesnt seems to suffice. We need to know, If a property fails, we'd like to perhaps know how 
//many tests succeeded first, and what the arguments were that resulted in the failure.
// so we will redefine it as follow


trait Prop1 {
  type FialedCase
  def check: Either[FialedCase,SuccessCount]
}

//There's a problem, what type do we return in the failure case?
//do we really care though about the type of the value that caused the property to fail? Not exactly. 
//We would only care about the type if we were going to do further computation with this value.

//Most likely we are just going to end up printing this value to the screen, for inspection by the person running these tests.

// so for failure case we will put it type as string

object Prop2 {
  type SuccessCount1 = Int
  type FailedCase1 = String
}

trait Prop2 {
  def check : Either[FailedCase1,SuccessCount1]
}