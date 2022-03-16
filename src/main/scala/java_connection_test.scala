import java.io.FileInputStream
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException





object java_connection_test extends App{



    import java.io.FileInputStream
    import java.io.IOException
    import java.sql.DriverManager
    import java.sql.SQLException

    @throws[SQLException]
    def getConnection: Connection = {
      var conn:Connection = null


        try { // load the properties file

          // assign db parameters
          val url = "jdbc:mysql://localhost:3306/project_0"
          val user = "root"
          val password = "redscalapythonpreferredwhataburger"
          // create a connection to the database
          conn = DriverManager.getConnection(url, user, password)
        } catch {
          case e: IOException =>
            println(e.getMessage)
          case f: SQLException =>
            println(f.getMessage)
        } //finally if (conn != null) conn.close()

      conn
    }


  // create a new connection from java_connection

    var conn:Connection = getConnection
    val try_var = try {// print out a message
      //println(String.format("Connected to database %s " + "successfully.", conn.getCatalog))
      val the_query = conn.createStatement.executeQuery("select * from bank_account;")
      while (the_query.next()){
          println(
            the_query.getString("account_id") + "\t" +
            the_query.getString("first_name") + "\t" +
            the_query.getString("last_name") + "\t" +
            the_query.getString("checking_id")  + "\t" +
            the_query.getString("savings_id"))

      }
    }
    catch {
      case ex: SQLException =>
        System.out.println(ex.getMessage)
    } finally if (conn != null) conn.close()
  try_var

}
