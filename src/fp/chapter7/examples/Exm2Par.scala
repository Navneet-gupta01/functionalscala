package fp.chapter7.examples

import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.Callable

object Par {
  type Par[A] = ExecutorService => Future[A]
  
  //unit injects a constant into a parallel computation.
  /// `unit` is represented as a function that returns a `UnitFuture`, which is a simple implementation of
  //`Future` that just wraps a constant value. It doesn't use the `ExecutorService` at all. It's always done
  //and can't be cancelled. Its `get` method simply returns the value that we gave it.
  def unit[A](a: A): Par[A] ={
    println("Inside unit method")
    (es: ExecutorService) => UnitFuture(a)
  }
  
  private case class UnitFuture[A](get: A) extends Future[A] {
    def isDone= true
    def get(timeout: Long,units: TimeUnit) = get
    def isCancelled = false
    def cancel(evenIfRunning: Boolean): Boolean = false
  }
  
  //map2 combines the results of two parallel computations with a binary function.
  //`map2` doesn't evaluate the call to `f` in a separate logical thread, in accord with our design choice of 
  //having `fork` be the sole function in the API for controlling parallelism. We can always do `fork(map2(a,b)(f))`
  //if we want the evaluation of `f` to occur in a separate thread.
  def map2[A,B,C](a: Par[A], b: Par[B])(f: (A,B) => C): Par[C] = {
    println("Inside map2 method")
    (es: ExecutorService) => {
       val af = a(es)
       val bf = b(es)
       UnitFuture(f(af.get, bf.get)) 
      // This implementation of `map2` does _not_ respect timeouts, and eagerly waits for the returned futures. 
      //This means that even if you have passed in "forked" arguments, using this map2 on them will make them wait. 
      //It simply passes the `ExecutorService` on to both `Par` values, waits for the results of the Futures `af` and `bf`, 
      //applies `f` to them, and wraps them in a `UnitFuture`. In order to respect timeouts, we'd need a new `Future` 
      //implementation that records the amount of time spent evaluating `af`, then subtracts that time from the available 
      //time allocated for evaluating `bf`.
    }
  }
  
  //fork spawns a parallel computation. The computation will not be spawned until forced by run.
  // This is the simplest and most natural implementation of `fork`, but there are some problems with it--for one, 
  //the outer `Callable` will block waiting for the "inner" task to complete. Since this blocking occupies a thread 
  //in our thread pool, or whatever resource backs the `ExecutorService`, this implies that we're losing out on some 
  //potential parallelism. Essentially, we're using two threads when one should suffice. This is a symptom of a more 
  //serious problem with the implementation, and we will discuss this later in the chapter.
  def fork[A](a: => Par[A]): Par[A] = es => {
    println("Inside fork method")
    es.submit(new Callable[A] {
      def call = a(es).get
    })
  }
  
  
	//submitting the Callable first, and within that callable, we are submitting another Callable
	//to the ExecutorService and blocking on its result (recall that a(es) will submit a Callable
	//to the ExecutorService and get back a Future). This is a problem if our thread pool has size 1.
	//The outer Callable gets submitted and picked up by the sole thread. Within that thread, before it
	//will complete, we submit and block waiting for the result of another Callable. But there are no
	//threads available to run this Callable. Our code therefore deadlocks.
  
  //problem with the above implementation of fork is that we are invoking submit inside a callable, and we
	// are blocking on the result of what we've submitted. This leads to deadlock when there aren't any remaining threads to run the task
	
	//to avoid deadlock: A Callable should never submit and then block on the result of a Callable.
  
  def fork2[A](fa: => Par[A]): Par[A] = {
    println("Inside fork2 method")
    es => fa(es)
  }
  
  //The only problem is that we aren't actually forking a separate logical thread to evaluate fa. So, fork(hugeComputation)(es)
  //for some ExecutorStrategy, es, runs hugeComputation in the main thread
  
  //
  def async[A](a: => A): Par[A] = {
    println("Inside async method")
    fork(unit(a))
  }
  
  //run extracts a value from a Par by actually performing the computation.
  def run[A](s: ExecutorService)(a: Par[A]): Future[A] = {
    println("Inside run method")
    a(s)
  }
  
  def lazyUnit[A](a: => A): Par[A] = {
    println("Inside lazyUnit method")
    fork(unit(a))
  }
  
  def asyncF[A,B](f: A => B): A => Par[B] = {
    println("Inside asyncF method")
    a => fork(unit(f(a)))
  }
  
  def map[A,B](pa: Par[A])(f: A => B): Par[B] ={
    println("Inside map method")
    map2(pa, unit(()))((a,_) => f(a))
  }
    
  //map takes par of list l and a function which takes a list and sort it using function _.sorted which is a list function
  def sortPar(l: Par[List[Int]]): Par[List[Int]] = {
    println("Inside sortPar method")
    map(l)(_.sorted)
  }
  
  def product[A,B](fa: Par[A], fb: Par[B]): Par[(A,B)] = {
    println("Inside product method")
    map2(fa,fb)((a,b) => (a,b)) 
  }
    
    
  //def map[A,B](fa: Par[A])(f: A => B): Par[B]
  
  def parMap[A,B](l: List[A])(f: A => B): Par[List[B]] = { 
    println("Inside parMap method")
      def compute(lx:List[A]):List[B] = {
        lx match {
          case Nil => Nil
          case x::xs => f(x) :: compute(xs) 
        }
      }
      unit(compute(l))
  }
  
  def parMap3[A,B](l:List[A])(f: A => B): Par[List[B]] = {
    println("Inside parMap3 method")
    val a = l.map(x => asyncF(f)(x))
    sequence(a)
  }
  
  def parMap2[A,B](l:List[A])(f: A => B): Par[List[B]] = {
    println("Inside parMap2 method")
    sequence(l.map(asyncF(f)))
  }

  def sequence[A](l: List[Par[A]]): Par[List[A]] = {
    println("Inside sequence method")
    l.foldRight[Par[List[A]]](unit(List()))((h,t) => map2(h,t)(_::_))
  }
  // This implementation forks the recursive step off to a new logical thread,
  // making it effectively tail-recursive. However, we are constructing
  // a right-nested parallel program, and we can get better performance by
  // dividing the list in half, and runn
  def sequenceRight[A](l : List[Par[A]]): Par[List[A]] = {
    println("Inside sequenceRight method")
    l match {
      case Nil => unit(Nil)
      case (h::t) => map2(h,fork(sequenceRight(t)))(_::_)
    }
  }
  
  def sequenceBalanced[A](as : IndexedSeq[Par[A]]): Par[IndexedSeq[A]] = fork {
     println("Inside sequenceBalanced method")
    if(as.isEmpty) unit(Vector())
    else if(as.length ==1) map(as.head)(a => Vector(a))
    else {
      val (l,r) = as.splitAt(as.length/2)
      map2(sequenceBalanced(l),sequenceBalanced(r))(_ ++_)
    }
  }
  
  //parFilter, which filters elements of a list in parallel.
  // def map[A,B](pa: Par[A])(f: A => B): Par[B]
  def parFilter[A](l: List[A])(f: A => Boolean): Par[List[A]] = {
     println("Inside parFilter method")
    val list: List[Par[List[A]]] = l map (asyncF((a:A) =>if(f(a))List(a) else List()))
        map(sequence(list))(_.flatten)
  }
  
  def equal[A](e: ExecutorService)(p: Par[A], p2: Par[A]): Boolean = {
    println("Inside equal method")
      p(e).get == p2(e).get
  }
  
  def delay[A](fa: => Par[A]): Par[A] = {
    println("Inside delay method")
    es => fa(es)
  }
  
  def equal[A](p: Par[A], p2: Par[A]): Par[Boolean] =
    Par.map2(p,p2)(_ == _)
  
  // function to choose between two forking computations based on the result of an initial computation.
  def choice[A](a: Par[Boolean])(ifTrue: Par[A], ifFalse: Par[A]): Par[A] = {
    println("Inside choice method")
    es => 
      if(run(es)(a).get) ifTrue(es)
      else ifFalse(es)
  }
  
   def choiceN[A](a: Par[Int])(choices: List[Par[A]]): Par[A] = {
     println("Inside choiceN method")
     es => 
       val indexToRun = run(es)(a).get
       run(es)(choices(indexToRun))
   }
  
  // def map[A,B](pa: Par[A])(f: A => B): Par[B]
   def choiceViaChoiceN[A](a: Par[Boolean])(ifTrue: Par[A], ifFalse: Par[A]): Par[A] = {
     println("Inside choiceViaChoiceN method")
     choiceN(map(a)(b => if(b) 0 else 1))(List(ifTrue,ifFalse))
   }
  
   def choiceMap[A,B](a: Par[A])(choices: Map[A,Par[B]]): Par[B] = {
     println("Inside choiceMap method")
     es => 
       val keyToRun = run(es)(a).get
       run(es)(choices(keyToRun))
   }
   
   def chooser[A,B](a: Par[A])(choices: A => Par[B]): Par[B] = {
     println("Inside chooser method")
     es =>
       val a1 = run(es)(a).get
       run(es)(choices(a1))
   }
   
   def choiceViaChooser[A](a: Par[Boolean])(ifTrue: Par[A], ifFalse: Par[A]): Par[A] = {
     println("Inside choiceViaChooser method")
     chooser(a)(b => if(b)ifTrue else ifFalse)
   }
   
   def flatMap[A,B](a: Par[A])(f: A => Par[B]): Par[B] = {
     println("Inside flatMap method")
     es => 
       val a1 = run(es)(a).get
       run(es)(f(a1))
   }
   
   def choiceViaFlatMap[A](a: Par[Boolean])(ifTrue: Par[A], ifFalse: Par[A]): Par[A] = {
     println("Inside choiceViaChooser method")
     flatMap(a)(b => if(b)ifTrue else ifFalse)
   }
   
   def choiceNViaChooser[A](a: Par[Int])(choices: List[Par[A]]): Par[A] = {
     println("Inside choiceNViaChooser method")
     chooser(a)(idx => choices(idx))
   }
   
   def choiceNViaFlatMap[A](a: Par[Int])(choices: List[Par[A]]): Par[A] = {
     println("Inside choiceNViaFlatMap method")
     flatMap(a)(idx => choices(idx))
   }
   
   //we call it join since conceptually, it is a parallel computation that when run, will run the inner computation, 
   //wait for it to finish (much like Thread.join ), then return its result.
   def join[A](a: Par[Par[A]]): Par[A] = {
     println("Inside join method")
     es => run(es)(run(es)(a).get())
   }
   
   def joinViaFlatMap[A](a: Par[Par[A]]): Par[A] = {
     println("Inside joinViaFlatMap method")
     flatMap(a)(x => x)
   }
   
   def flatMapViaJoin[A,B](a: Par[A])(f: A => Par[B]): Par[B] = {
     println("Inside flatMapViaJoin method")
     join(map(a)(f))
   }
}