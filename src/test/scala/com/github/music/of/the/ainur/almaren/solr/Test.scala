package com.github.music.of.the.ainur.almaren.solr

import org.apache.spark.sql.SaveMode
import org.scalatest._
import com.github.music.of.the.ainur.almaren.Almaren
import com.github.music.of.the.ainur.almaren.builder.Core.Implicit
import com.github.music.of.the.ainur.almaren.solr.Solr.SolrImplicit

class Test extends FunSuite with BeforeAndAfter {

  val almaren = Almaren("App Test")

  val spark = almaren.spark
    .master("local[*]")
    .config("spark.sql.shuffle.partitions", "1")
    .getOrCreate()
  
  spark.sparkContext.setLogLevel("ERROR")

  bootstrap

  val twitter = almaren.builder
    .sourceSql("select monotonically_increasing_id() as id,* from twitter")
    .coalesce(10)
    .targetSolr("twitter","localhost:9983",SaveMode.Overwrite,Map("batch_size" -> "500"))

  almaren.batch(twitter)

  def bootstrap = {
    import spark.implicits._
    val res = spark.read.json("/Users/mantovani/projects/modakanalytics/solr-connector.almaren/src/test/resources/sample_data/twitter_search_data.json")
    res.createTempView("twitter")
  }

  after {
    spark.stop
  }
}
