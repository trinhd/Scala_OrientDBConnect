package main.scala

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory

object MainProgram {
  def main(args: Array[String]): Unit = {
    /*val OrientDBUtils = new OrientDBUtils
    val factory: OrientGraphFactory = OrientDBUtils.connectDBUsingGraphAPI()
    val v1 = OrientDBUtils.insertVertex(factory, "Name", "Hoa")
    val v2 = OrientDBUtils.insertVertex(factory, "Name", "Tri")
    val edgeID = OrientDBUtils.insertEdge(factory, v1, v2)
    val datas = Map("Nguyen" -> "Name", "Ho" -> "Name", "Duy" -> "Name")
    OrientDBUtils.insertVertexInBatches(factory, datas)*/
    
    val OrientDBUtilsDoc = new OrientDBUtils("remote:","localhost", "CoOccurrenceGraph","admin", "admin", "root", "12345")
    val pool = OrientDBUtilsDoc.connectDBUsingDocAPI
    OrientDBUtilsDoc.insertDoc(pool, List(("topic", "CCCCC"), ("Name", "DDDDDDDDD")))
    OrientDBUtilsDoc.insertDoc(pool, List(("topic", "DDDDD"), ("Name", "EEEEEEEEE")))
    
    //factory.close
    pool.close
  }
}