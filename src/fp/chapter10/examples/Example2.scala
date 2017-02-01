package fp.chapter10.examples

import fp.chapter10.examples.Monoid.{wcMonoid,foldMapV}

sealed trait WordCount
case class Stub(chars: String) extends WordCount
case class Part(lStub: String, words: Int, rStub: String) extends WordCount

object WordCount {
  def count(s: String): Int = {
    // A single character's count. Whitespace does not count,
    // and non-whitespace starts a new Stub.
    def wc(c: Char): WordCount =
      if (c.isWhitespace)
        Part("", 0, "")
      else
        Stub(c.toString)
    
        
    // `unstub(s)` is 0 if `s` is empty, otherwise 1.
    def unstub(s: String) = s.length min 1
    
    
    foldMapV(s.toIndexedSeq, wcMonoid)(wc) match {
      case Stub(s) => unstub(s)
      case Part(l, w, r) => unstub(l) + w + unstub(r)
    }
    
  }
}