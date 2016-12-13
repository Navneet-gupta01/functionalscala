package fp.chapter8.exercises

object Exercises3 {
  
}

trait Prop { 
  def check : Boolean
  
  //def &&&(p: Prop): Prop = if(this.check) this else p
  
  def &&(p: Prop): Prop = new Prop {
    def check = Prop.this.check && p.check
  }
}