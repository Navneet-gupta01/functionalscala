package fp.chapter7.examples

import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class ExecutorService11 {
  def submit[A](a: Callable[A]): Future1[A] = ???
}
trait Future1[A] {
  def get: A
  def get(timeout: Long, unit: TimeUnit): A
  def cancel(evenIfRunning: Boolean): Boolean
  def isDone: Boolean
  def isCancelled: Boolean
}