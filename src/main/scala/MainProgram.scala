package main.scala

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory

object MainProgram {
  def main(args: Array[String]): Unit = {
    val factory: OrientGraphFactory = OrientDBUtils.connectDBUsingGraphAPI()
    //OrientDBUtils.insertVertex(factory, "Name", "Tri")
    val datas = Map("Nguyen" -> "Name", "Ho" -> "Name", "Duy" -> "Name")
    OrientDBUtils.insertVertexInBatches(factory, datas)
  }
}