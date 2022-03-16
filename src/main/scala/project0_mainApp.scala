import java_connection_test.{conn, getConnection}
import test_json.{data, getClass}

import java.io.IOException
import java.sql.{Connection, DriverManager, PreparedStatement, SQLException}
import scala.io.Source.fromFile
import scala.io.StdIn.readLine
import scala.util.control._
import java.io.InputStream
import scala.io.Source.fromFile
import ujson._
object project0_mainApp extends App {
  //this function creates a JDBR connection to a MySQL server
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





  val filePath = "C:\\Users\\Douglas\\Desktop\\Douglas_Lam_Project0\\Scala1\\src\\main\\scala\\data.json"
  val json_toString = fromFile(filePath).mkString
  //println(json_toString)


  println("Welcome to the Bank App!")
  var test_talk = ""
  val loop = new Breaks
  loop.breakable {
  while (test_talk != "quit" || test_talk != "q") {
    var conn:Connection = getConnection

    test_talk = readLine("Enter a command: [c]hecking, [s]avings, [l]oad, [p]review, or [q]uit: ").toLowerCase()
    if (test_talk == "checking" || test_talk == "c") {
      try
      { //read account id, deposit OR withdrawal, amount of deposit or withdrawal, call jdbc connection to take SQL statement to INSERT into checking/savings table, and another SQL query to UPDATE the deposit_withdrawl table with the amount and deposit OR withdrawal action
        var accountid_readline = readLine("Enter the account id: ").toInt
        var add_remove_readLine = readLine("Enter 1 for deposit, 0 for withdrawal: ").toInt
        var amount_readLine = readLine("Enter amount: ").toInt
        var pstmt = conn.prepareStatement("INSERT INTO deposit_withdrawal (account_id,add_remove,checking_savings,amount) VALUES (?,?,1,?);")
        //these three lines interpolate into the pstmt conn.prepareStatement; It's done this way instead of s"$variable" because it prevents SQL injection; it is one-based and not zero-based index
        pstmt.setInt(1, accountid_readline)
        pstmt.setInt(2, add_remove_readLine)
        pstmt.setInt(3, amount_readLine)
        //deposit into checking
        if (add_remove_readLine == 1) {
          var pstmt_update = conn.prepareStatement("update checking set checking_balance = checking_balance + ? where checking_id = (select checking_id from bank_account where account_id=?);")
          pstmt_update.setInt(1, amount_readLine)
          pstmt_update.setInt(2, accountid_readline)
          pstmt_update.execute()
          pstmt.execute()
          conn.close()
          println(s"\nAccount ID: $accountid_readline\nYou've deposited $amount_readLine to your checking account!\n")

          //withdrawal from checking
        } else {
          var pstmt_update = conn.prepareStatement("update checking set checking_balance = checking_balance - ? where checking_id = (select checking_id from bank_account where account_id=?);")
          pstmt_update.setInt(1, amount_readLine)
          pstmt_update.setInt(2, accountid_readline)
          pstmt_update.execute()
          pstmt.execute()
          conn.close()
          println(s"\nAccount ID: $accountid_readline\nYou've withdrawn $amount_readLine from your checking account!\n")
        }
        println("Is there anything else you'd like to do?")
      } catch {case a: Exception => println("Incorrect input")}
    }
    else if (test_talk == "savings" || test_talk == "s") {
      try {
        var accountid_readline = readLine("Enter the account id: ").toInt
        var add_remove_readLine = readLine("Enter 1 for deposit, 0 for withdrawal: ").toInt
        var amount_readLine = readLine("Enter amount: ").toInt
        var pstmt = conn.prepareStatement("INSERT INTO deposit_withdrawal (account_id,add_remove,checking_savings,amount) VALUES (?,?,0,?);")
        pstmt.setInt(1, accountid_readline)
        pstmt.setInt(2, add_remove_readLine)
        pstmt.setInt(3, amount_readLine)
        //deposit into savings
        if (add_remove_readLine == 1) {
          var pstmt_update = conn.prepareStatement("update savings set savings_balance = savings_balance + ? where savings_id = (select savings_id from bank_account where account_id=?);")
          pstmt_update.setInt(1, amount_readLine)
          pstmt_update.setInt(2, accountid_readline)
          pstmt_update.execute()
          pstmt.execute()
          conn.close()
          println(s"\nAccount ID: $accountid_readline\nYou've deposited $amount_readLine to your savings account!\n")
          //withdrawal from savings
        } else {
          var pstmt_update = conn.prepareStatement("update savings set savings_balance = savings_balance - ? where savings_id = (select savings_id from bank_account where account_id=?);")
          pstmt_update.setInt(1, amount_readLine)
          pstmt_update.setInt(2, accountid_readline)
          pstmt_update.execute()
          pstmt.execute()
          conn.close()
          println(s"\nAccount ID: $accountid_readline\nYou've withdrawn $amount_readLine from your savings account!\n")
        }
        println("Is there anything else you'd like to do?")
      } catch {case a:Exception => println("Incorrect input")}
    }
    else if (test_talk == "load" || test_talk == "l") {
      try {
        var yes_no = readLine("Do you want to load the json file containing the MySQL tables? [y]es or [n]? ")
        if (yes_no == "yes" || yes_no == "y") {
          val filePath = f"C:\\Users\\Douglas\\Desktop\\Douglas_Lam_Project0\\Scala1\\src\\main\\scala\\data.json"
          val data = ujson.read(getClass.getResourceAsStream("/data.json"))
          var pstmt = conn.prepareStatement("INSERT INTO bank_account (account_id,first_name,last_name,checking_id,savings_id) VALUES (?,?,?,?,?);")
          var pstmt2 = conn.prepareStatement("INSERT INTO checking (checking_id,checking_balance) VALUES (?,?);")
          var pstmt3 = conn.prepareStatement("INSERT INTO savings (savings_id,savings_balance) VALUES (?,?);")
          data("banking_account").arr.foreach { i =>
            pstmt.setInt(1, i("account_id").num.toInt)
            pstmt.setString(2, i("first_name").str)
            pstmt.setString(3, i("last_name").str)
            pstmt.setInt(4, i("checking_id").num.toInt)
            pstmt.setInt(5, i("savings_id").num.toInt)
            pstmt.execute()
          }
          data("checking").arr.foreach { i =>
            pstmt2.setInt(1, i("checking_id").num.toInt)
            pstmt2.setInt(2, i("checking_balance").num.toInt)
            pstmt2.execute()
          }
          data("savings").arr.foreach { i =>
            pstmt3.setInt(1, i("savings_id").num.toInt)
            pstmt3.setInt(2, i("savings_balance").num.toInt)
            pstmt3.execute()
          }
          conn.close()
          println("The tables have been loaded.")
          println("Is there anything else you'd like to do?")
        }
        else {
          println("Is there anything else you'd like to do?")
        }
      } catch {case a:Exception => println("Incorrect input")}
    }
    else if (test_talk == "preview" || test_talk == "p") {
      try {
        var yourid = readLine("What is your account id? ").toInt
        var show_stmt = readLine("Preview the tables by entering [1]bank_account, [2]checking, [3]savings: ").toInt

        if (show_stmt == 1) {
          var prepare_query = conn.prepareStatement("select * from bank_account where account_id = ?;")
          prepare_query.setInt(1, yourid)
          var the_query = prepare_query.executeQuery()
          while (the_query.next()) {
            println(
              "account id: " + the_query.getString("account_id") + "\n" +
                "first name: " + the_query.getString("first_name") + "\n" +
                "last name: " + the_query.getString("last_name") + "\n" +
                "checking id: " + the_query.getString("checking_id") + "\n" +
                "savings id: " + the_query.getString("savings_id"))

          }
          conn.close()
        }
        else if (show_stmt == 2) {
          var prepare_query = conn.prepareStatement("select * from checking where checking_id = (select checking_id from bank_account where account_id=?);")
          prepare_query.setInt(1, yourid)
          var the_query = prepare_query.executeQuery()
          while (the_query.next()) {
            println(
              "checking id: " + the_query.getString("checking_id") + "\n" +
                "checking balance: " + the_query.getString("checking_balance"))
          }
          conn.close()
        }
        else if (show_stmt == 3) {
          var prepare_query = conn.prepareStatement("select * from savings where savings_id = (select savings_id from bank_account where account_id=?);")
          prepare_query.setInt(1, yourid)
          var the_query = prepare_query.executeQuery()
          while (the_query.next()) {
            println(
              "savings id: " + the_query.getString("savings_id") + "\n" +
                "savings balance: " + the_query.getString("savings_balance"))
          }
          conn.close()
        }
        else {
          println(s"$show_stmt, is not a valid option.")
        }
      } catch {case a:Exception => println("Incorrect input")}
    }
    else if (test_talk == "quit" || test_talk == "q") {
      println("Existing Bank App. Goodbye!")
      loop.break
    }
    else {
      println(s"$test_talk, is not a valid command.")
    }
  } //while end
}//loop end
}
