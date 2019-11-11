package com.github.music.of.the.ainur.almaren.solr

import org.apache.spark.sql.{DataFrame,SaveMode}
import com.github.music.of.the.ainur.almaren.Container
import com.github.music.of.the.ainur.almaren.builder.Core
import com.github.music.of.the.ainur.almaren.state.core.{Target,Source}
import com.lucidworks.spark._

private[ainur] class SourceSolr(collection: String,zkHost: String, options:Map[String,String]) extends Source {
  def source(df: DataFrame): DataFrame = {
    logger.info(s"collection:{$collection}, zkHost:{$zkHost}, options:{$options}")
    df.sparkSession.read.format("solr")
      .option("collection", collection)
      .option("zkhost", zkHost)
      .options(options)
      .load
  }
}

private[ainur] class TargetSolr(collection: String,zkHost: String, saveMode:SaveMode, options:Map[String,String]) extends Target {
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

private[ainur] trait SolrConnector extends Core {
  def targetSolr(collection: String,zkHost: String = "localhost:8983",saveMode:SaveMode = SaveMode.ErrorIfExists, options:Map[String,String] = Map()): Option[List[Container]] =
    new TargetSolr(collection,zkHost,saveMode,options)

  def sourceSolr(collection: String,zkHost: String = "localhost:8983", options:Map[String,String] = Map()): Option[List[Container]] =
    new SourceSolr(collection,zkHost,options)
}

object Solr {
  implicit class Implicit(val container: Option[List[Container]]) extends SolrConnector
}
