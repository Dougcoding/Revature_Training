import java.sql.DriverManager
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

object jdbc_test {

  def main(args: Array[String]) {
    // connect to the database named "test" on the localhost

    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/project_0"
    val username = "root"
    val password = "redscalapythonpreferredwhataburger"

    val insert_code = "INSERT INTO Persons (bID,LastName,FirstName,Age) VALUES (011,'West','amy',25);"
    val insert_code2 = "CREATE TABLE events( \n  id int auto_increment primary key, \n  event_name varchar(255), \n  visitor varchar(255), \n  properties json, \n  browser json);"
    val insert_code6 = "INSERT INTO bank_account (account_id, full_name, checking_id, savings_id)\nVALUES (1,'{\"first_name\":\"douglas\",\"last_name\":\"lam\"}', 101,201)," +
      "(2,'{\"first_name\":\"jack\", \"last_name\":\"bauer\"}', 102,202);"
    val select_code = "select * from bank_account;"
    val insert_code4 = "delete from bank_account where account_id=1;"
    val insert_code5 = "update bank_account where account_id=1;"
    val lastName = "lam"
    val id=1


    var conn:Connection = DriverManager.getConnection(url, username, password)
    val statement = conn.createStatement()
    val insert_statement:PreparedStatement  = conn.prepareStatement(insert_code6)
    //FOR insert_code5; insert_statement.setString(1, lastName)
    //FOR insert_code5; insert_statement.setInt(2, id)
    //.execute(), executes the insert statement
    insert_statement.execute()

    //uncomment below block comment to use SELECT STATEMENT
    //executeQuery returns a resultSet type
    /*val resultSet = statement.executeQuery(select_code)

    while ( resultSet.next() ) {
      println(resultSet.getString(1)+", " +resultSet.getString(2) +", " +resultSet.getString(3))
    }*/
    conn.close()
  }

}