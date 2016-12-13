package fp.chapter8.examples
import fp.chapter8.examples.Gen2._
import fp.chapter7.examples.Par
import fp.chapter7.examples.Par._
import fp.chapter8.examples.Prop4._
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object testExample9 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val smallInt = Gen2.choose(-10,10)              //> smallInt  : fp.chapter8.examples.Gen2[Int] = Gen2(State(<function1>),Cons(<f
                                                  //| unction0>,<function0>))
  
	val maxProp = forAll(listOf(smallInt)) { l =>
	  val max = l.max
	  !l.exists(_ > max)
	}                                         //> Inside unit method
                                                  //| Inside unit method
                                                  //| scala.NotImplementedError: an implementation is missing
                                                  //| 	at scala.Predef$.$qmark$qmark$qmark(Predef.scala:230)
                                                  //| 	at fp.chapter5.exercises.Stream1$class.headOption(Exc1Stream1.scala:112)
                                                  //| 
                                                  //| 	at fp.chapter5.exercises.Cons.headOption(Exc1Stream1.scala:164)
                                                  //| 	at fp.chapter8.examples.Gen2$.interleave(Example6.scala:123)
                                                  //| 	at fp.chapter8.examples.Gen2$.weighted(Example6.scala:112)
                                                  //| 	at fp.chapter8.examples.Prop4$.<init>(Example9.scala:123)
                                                  //| 	at fp.chapter8.examples.Prop4$.<clinit>(Example9.scala)
                                                  //| 	at fp.chapter8.examples.testExample9$$anonfun$main$1.apply$mcV$sp(fp.cha
                                                  //| pter8.examples.testExample9.scala:14)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execut
                                                  //| Output exceeds cutoff limit.
	
	maxProp
	
	Prop4.run(maxProp)
	
	
	listOf1(smallInt)
	
  val maxProp2 = forAll(listOf1(smallInt)) { l =>
	  val max = l.max
	  !l.exists(_ > max)
	}
	
	maxProp2
	
	Prop4.run(maxProp2)
                      
	
	val sortedProp = forAll(listOf(smallInt)) { l =>
	  val ls = l.sorted
	  l.isEmpty || !ls.zip(ls.tail).exists { case (a,b) => a > b }
	}
	
	sortedProp
	
	Prop4.run(sortedProp)
                                                  
                                                  
 	val p2 = check {
	  val p = Par.map(Par.unit(1))(_ + 1)
	  val p2 = Par.unit(2)
	  p(ES).get == p2(ES).get
	}
	
	
	Prop4.run(p2)
	
	val p3 = check {
		Par.equal(
			Par.map(Par.unit(1))(_ + 1),
    	Par.unit(2)
		)(ES).get
	}
	
	Prop4.run(p3)
 
  
  val p1 = Prop4.forAll(Gen2.unit(Par.unit(1)))(i =>
  	Par.map(i)( _ + 1)(ES).get == Par.unit(2)(ES).get
  )
  
  Prop4.run(p1)
  
  val p4 = checkPar {
  	equal(
			Par.map(Par.unit(1))(_ + 1),
			Par.unit(2)
  	)
  }
  
  Prop4.run(p4)
  
}