package fp.chapter7.examples

//import fp.chapter7.examples.Par._
import java.util.concurrent.Executors

object Exm2ParTest {
  println("Welcome to the Scala worksheet")
  
  var list = List(List(1),List(2),List(),List(4),List(),List(5))
  
  list.flatten
  
  val b = Par.async(42+1)
  
  //Par.
	//val S = Executors.newFixedThreadPool(1)
	
	//val z = Par.equal(S)(b, Par.fork(b))
	//println("got Resposne = "+ z)
	//submitting the Callable first, and within that callable, we are submitting another Callable
	//to the ExecutorService and blocking on its result (recall that a(es) will submit a Callable
	//to the ExecutorService and get back a Future). This is a problem if our thread pool has size 1.
	//The outer Callable gets submitted and picked up by the sole thread. Within that thread, before it
	//will complete, we submit and block waiting for the result of another Callable. But there are no
	//threads available to run this Callable. Our code therefore deadlocks.
	
	// Now trying to execute with more threads
	
	val S1 = Executors.newFixedThreadPool(5)
	val z1 = Par.equal(S1)(b,Par.fork(b))
	println("Got Response = " + z1)
	
	//problem with the above implementation of fork is that we are invoking submit inside a callable, and we
	// are blocking on the result of what we've submitted. This leads to deadlock when there aren't any remaining threads to run the task
	
	//to avoid deadlock: A Callable should never submit and then block on the result of a Callable.
	
	
	
	
	
	
}