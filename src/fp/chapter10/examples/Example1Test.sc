package fp.chapter10.examples


object Example1Test {
	import fp.chapter10.examples.Monoid._
	
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val l = List("Hi","How","Are","U","?","Is " ," Everyting  " ,"  fine"," ?  ")
                                                  //> l  : List[String] = List(Hi, How, Are, U, ?, "Is ", " Everyting  ", "  fine"
                                                  //| , " ?  ")
  val a = l.map {x => wordsMonoid(x)}             //> a  : List[fp.chapter10.examples.Monoid[String]] = List(fp.chapter10.examples
                                                  //| .Monoid$$anon$11@5ebec15, fp.chapter10.examples.Monoid$$anon$11@21bcffb5, fp
                                                  //| .chapter10.examples.Monoid$$anon$11@380fb434, fp.chapter10.examples.Monoid$$
                                                  //| anon$11@668bc3d5, fp.chapter10.examples.Monoid$$anon$11@3cda1055, fp.chapter
                                                  //| 10.examples.Monoid$$anon$11@7a5d012c, fp.chapter10.examples.Monoid$$anon$11@
                                                  //| 3fb6a447, fp.chapter10.examples.Monoid$$anon$11@79b4d0f, fp.chapter10.exampl
                                                  //| es.Monoid$$anon$11@6b2fad11)
  "/"+concanate(l, wordsMonoid(" "))+"/"          //> res0: String = /Hi How Are U ? Is Everyting fine ?/
}