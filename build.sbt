name := "App2"
version := "0.1"
scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.3"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.3"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.4.3"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.25"
// https://mvnrepository.com/artifact/com.lihaoyi/upickle
libraryDependencies += "com.lihaoyi" %% "upickle" % "1.5.0"
// https://mvnrepository.com/artifact/com.lihaoyi/os-lib
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.8.1"



