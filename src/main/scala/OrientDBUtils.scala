package main.scala

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx

object OrientDBUtils {
  /*
  OrientDB supports three different kinds of storages, depending on the Database URL used:
   - Persistent Embedded Graph Database: Links to the application as a JAR, (that is, with no network transfer). Use PLocal with the plocal prefix. For instance, plocal:/tmp/graph/test.
   - In-Memory Embedded Graph Database: Keeps all data in memory. Use the memory prefix, for instance memory:test.
   - Persistent Remote Graph Database Uses a binary protocol to send and receive data from a remote OrientDB server. Use the remote prefix, for instance remote:localhost/test Note that this requires an OrientDB server instance up and running at the specific address, (in this case, localhost). Remote databases can be persistent or in-memory as well.
   */
  val hostType = "plocal:" //plocal: or remote: or memory:
  val hostAddress = "/home/duytri/Downloads/Apps/orientdb-community-importers-2.2.30/databases" //if hostType is "memory:", set it empty
  //val port     = 2424
  val username = "root"
  val password = "12345"
  val database = "CoOccurrenceGraph"
  val dbUser = "admin"
  val dbPassword = "admin"
  val labelSubject = "subject"
  val labelName = "name"
  val tab_v = "dinh"
  val tab_e = "cooccurr_with"

  /**
   * Hàm thiết lập kết nối đến OrientDB sử dụng Java Native Driver Graph API
   * @return GraphFactory để khởi tạo kết nối database khi cần
   */
  def connectDBUsingGraphAPI(): OrientGraphFactory = {
    var uri = hostType
    if ((uri == "plocal:") || (uri == "remote:")) {
      uri += hostAddress + "/" + database
    } else if ((uri == "memory:")) {
      uri += database
    } else {
      println("Thông tin kết nối cơ sở dữ liệu không chính xác. Vui lòng kiểm tra lại!")
      return null
    }

    var factory: OrientGraphFactory = null
    if ((hostType == "plocal:") || (hostType == "memory:")) {
      factory = new OrientGraphFactory(uri)
    } else {
      factory = new OrientGraphFactory(uri, dbUser, dbPassword)
    }

    if (!factory.exists) println("Database chưa tồn tại, chương trình sẽ tiến hành khởi tạo!") //Remote databases must already exist
    
    //Using Transaction Instance of Database
    val graph: OrientGraph = factory.getTx 
    /*
    Prior to version 2.1.7, to work with a graph always use transactional OrientGraph instances and never the non-transactional instances to avoid graph corruption from multi-threaded updates.
		Non-transactional graph instances are created with .getNoTx()
		This instance is only useful when you don't work with data, but want to define the database schema or for bulk inserts.
		*/

    try {
      //Nếu chưa có database thì khởi tạo cấu trúc database
      if (graph.getVertexType(tab_v) == null) {
        val vertexType: OrientVertexType = graph.createVertexType(tab_v)
        vertexType.createProperty(labelSubject, OType.STRING)
        vertexType.createProperty(labelName, OType.STRING)

        val edgeType: OrientEdgeType = graph.createEdgeType(tab_e)
      }
    } catch {
      case t: Throwable => {
        println("************************ ERROR ************************")
        println("Có LỖI xảy ra!!")
        t.printStackTrace() // TODO: handle error
        println("************************ ERROR ************************")
        graph.rollback
      }
    } finally {
      println("Kết nối thành công với cơ sở dữ liệu!")
      graph.shutdown
    }
    
    //Using Non-Transaction Instance of Database
    /*val graph: OrientGraphNoTx = factory.getNoTx
    
    try {
      //Nếu chưa có database thì khởi tạo cấu trúc database
      if (graph.getVertexType(tab_v) == null) {
        val vertexType: OrientVertexType = graph.createVertexType(tab_v)
        vertexType.createProperty(labelSubject, OType.STRING)
        vertexType.createProperty(labelName, OType.STRING)

        val edgeType: OrientEdgeType = graph.createEdgeType(tab_e)
      }
    } catch {
      case t: Throwable => {
        println("************************ ERROR ************************")
        println("Có LỖI xảy ra!!")
        t.printStackTrace() // TODO: handle error
        println("************************ ERROR ************************")
      }
    } finally {
      println("Kết nối thành công với cơ sở dữ liệu!")
      graph.shutdown
    }*/
    
    factory
  }

  def insertVertex(factory: OrientGraphFactory, subject: String, Name: String): Boolean = {
    val graph: OrientGraph = factory.getTx
    try {
      val vertex: Vertex = graph.addVertex("class:" + tab_v, labelSubject, subject, labelName, Name)

      graph.commit
    } catch {
      case t: Throwable => {
        println("************************ ERROR ************************")
        println("Có LỖI xảy ra!!")
        t.printStackTrace() // TODO: handle error
        println("************************ ERROR ************************")
        graph.rollback
        return false
      }
    } finally {
      println("Thực hiện thành công lệnh thêm dữ liệu!")
      graph.shutdown
    }
    return true

  }
}