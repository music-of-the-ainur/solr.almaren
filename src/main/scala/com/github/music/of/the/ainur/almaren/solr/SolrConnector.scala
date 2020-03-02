package com.github.music.of.the.ainur.almaren.solr

import org.apache.spark.sql.{DataFrame,SaveMode}
import com.github.music.of.the.ainur.almaren.Tree
import com.github.music.of.the.ainur.almaren.builder.Core
import com.github.music.of.the.ainur.almaren.state.core.{Target,Source}
import com.lucidworks.spark._

private[almaren] case class SourceSolr(collection: String,zkHost: String, options:Map[String,String]) extends Source {
  def source(df: DataFrame): DataFrame = {
    logger.info(s"collection:{$collection}, zkHost:{$zkHost}, options:{$options}")
    df.sparkSession.read.format("solr")
      .option("collection", collection)
      .option("zkhost", zkHost)
      .options(options)
      .load
  }
}

private[almaren] case class TargetSolr(collection: String,zkHost: String, options:Map[String,String],saveMode:SaveMode) extends Target {
  def target(df: DataFrame): DataFrame = {
    logger.info(s"collection:{$collection}, zkHost:{$zkHost}, saveMode:{$saveMode}, options:{$options}")
    df.write.format("solr")
      .option("collection", collection)
      .option("zkhost", zkHost)
      .options(options)
      .mode(saveMode)
      .save
    df
  }
}

private[almaren] trait SolrConnector extends Core {
  def targetSolr(collection: String,zkHost: String = "localhost:9983",options:Map[String,String] = Map(),saveMode:SaveMode = SaveMode.ErrorIfExists): Option[Tree] =
     TargetSolr(collection,zkHost,options,saveMode)

  def sourceSolr(collection: String,zkHost: String = "localhost:9983", options:Map[String,String] = Map()): Option[Tree] =
    SourceSolr(collection,zkHost,options)
}

object Solr {
  implicit class SolrImplicit(val container: Option[Tree]) extends SolrConnector
}
