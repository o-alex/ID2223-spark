package se.kth.spark

import org.apache.spark._
import org.apache.spark.sql.SQLContext

case class Person(name: String, age: Long)

object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("person").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    val resourceDir = "src/main/resources"
    val jsonPersonPath = resourceDir + "/people.json"
    val p1DF = sqlContext.read.json(jsonPersonPath)
    p1DF.show()
    p1DF.printSchema()
    p1DF.filter(p1DF("age") > 17).show()

    import sqlContext.implicits._

    val txtFilePath = resourceDir + "/people.txt"
    val txtFileRDD = sc.textFile(txtFilePath)
    val p3DF = txtFileRDD.map(_.split(",")).map(p => Person(p(0), p(1).trim.toInt)).toDF()
    p3DF.registerTempTable("people1")
    val teenagers = sqlContext.sql("SELECT name, age FROM people1 WHERE age >= 13 AND age <= 19")
    teenagers.map(t => "Name: " + t(0)).collect().foreach(println)
    teenagers.map(t => "Name: " + t.getAs[String]("name")).collect().foreach(println)
    teenagers.map(_.getValuesMap[Any](List("name", "age"))).collect().foreach(println)
  }
}