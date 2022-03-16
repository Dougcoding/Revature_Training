import java.io.InputStream
import scala.io.Source.fromFile
import ujson._
object test_json extends App{

  val filePath = f"C:\\Users\\Douglas\\Desktop\\Douglas_Lam_Project0\\Scala1\\src\\main\\scala\\data.json"
  //print(filePath)
  //val json_toString = fromFile(filePath).mkString
  //val jsonString = os.read(filePath)
  val data = ujson.read(getClass.getResourceAsStream("/data.json"))

  //println(data.value)

  /*
  println(data("banking_account")(0)("account_id").num.toInt)
  println(data("banking_account")(0)("first_name").str)
  println(data("banking_account")(0)("last_name").str)
  println(data("banking_account")(0)("checking_id").num.toInt)
  println(data("banking_account")(0)("savings_id").num.toInt)
  */
  //.bool

  data("banking_account").arr.foreach { i =>
    println(i("account_id") + ": " + i("first_name") + i("last_name") + i("checking_id").toString() + i("savings_id").toString())
  }
  data("deposit_withdrawal").arr.foreach { i =>
    println(i("account_id") + ": " + i("amount").toString())
  }
  data("checking").arr.foreach { i =>
    println(i("checking_id") + ": " + i("checking_balance").toString())
  }
  data("savings").arr.foreach { i =>
    println(i("savings_id") + ": " + i("savings_balance").toString())
  }
}
