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

  // Create twitter table with data
  val jsonData = bootstrap

  // Save Twitter data to Solr
  val twitter = almaren.builder
    .sourceSql("select id,text from twitter")
    .targetSolr("gettingstarted","localhost:9983",SaveMode.Overwrite,Map("batch_size" -> "500","commit_within" -> "1"))
  almaren.batch(twitter)

  // Read Data From Solr
  val readTwitter = almaren.builder.sourceSolr("gettingstarted","localhost:9983")
  val solrData = almaren.batch(readTwitter)

  // Test count
  val inputCount = jsonData.count()
  val solrDataCount = solrData.count()

  test("number of records should match") {
    assert(inputCount == solrDataCount)
  }

  def bootstrap = {
    import spark.implicits._
    val res = spark.read.json("src/test/resources/sample_data/twitter_search_data.json")
    res.createTempView("twitter")
    res
  }

  after {
    spark.stop
  }
}
