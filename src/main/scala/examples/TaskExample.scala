package examples

import monix.execution.Cancelable

object TaskExample {
  def main(args: Array[String]): Unit = {
    import monix.eval.Task
    import monix.execution.CancelableFuture
    import monix.execution.Scheduler.Implicits.global

    val task: Task[Int] = Task {
      println("ATEFAK")
      1 + 1
    }.memoize

    val cancelable: Cancelable = task.runAsync {
      case Right(value) => println(value)
      case Left(ex) => println(s"ERROR: ${ex.getMessage}")
    }

    val future: CancelableFuture[Int] = task.runToFuture
    val future2: CancelableFuture[Int] = task.runToFuture
    future.foreach(i => println(s"#1: $i "))
    future.foreach(i => println(s"#2: $i"))
  }
}
