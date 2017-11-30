package main.scala

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory

class OrientDBUtils {
  val hostname = "localhost"
  val port     = 2424
  val username = "root"
  val password = "12345"
  val database = "CoOccurrenceGraph"
  val dbuser = "admin"
  val dbpassword = "admin"
  val label_subject = "subject"
  val label_id = "id"
  val v_table = "dinh"
  val e_table = "cooccurr_with"
  
  def connectDB() : Unit = {
    val uri = ""
    val factory : OrientGraphFactory = new OrientGraphFactory(uri)
  }
}