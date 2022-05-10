object ExampleApp extends App {
  println("hello world")
  
//  val userInput = scala.io.StdIn.readLine()
//
//  println(userInput)

  val myList = List("apple", "pear", "lemon", "orange")

  println(myList.take(9))

  val listSize = 10

  val newList = (0 to listSize).collect { n =>
    myList(n % myList.size)
  }

  println(newList)
}