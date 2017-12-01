package main.scala

object MainProgram {
  def main(args: Array[String]): Unit = {
    val connect = new OrientDBUtils
    connect.connectDBUsingGraphAPI()
  }
}