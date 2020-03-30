# Solr Connector

[![Build Status](https://travis-ci.com/music-of-the-ainur/solr.almaren.svg?branch=master)](https://travis-ci.com/music-of-the-ainur/solr.almaren)

```
libraryDependencies += "com.github.music-of-the-ainur" %% "solr-almaren" % "0.2.5-2-4"
```

Solr Connector was implemented using [https://github.com/lucidworks/spark-solr](https://github.com/lucidworks/spark-solr). The *Solr Connector* just works on Solr Cloud.
For all the options available for the connector check on this [link](https://github.com/lucidworks/spark-solr#configuration-and-tuning).

```
spark-shell --master local[*] --packages "com.github.music-of-the-ainur:almaren-framework_2.11:0.2.3-2-4,com.github.music-of-the-ainur:solr-almaren_2.11:0.2.4-2-4" --repositories https://repo.boundlessgeo.com/main/
```


## Source and Target

```scala
import com.github.music.of.the.ainur.almaren.Almaren
import com.github.music.of.the.ainur.almaren.solr.Solr.SolrImplicit

val almaren:Almaren = Almaren("App Name")

almaren.builder.sourceSolr("collection","zkHost1:2181,zkHost2:2181",options)

almaren.builder.targetSolr("collection","zkHost1:2181,zkHost2:2181",options)

```

## Example

```scala
import com.github.music.of.the.ainur.almaren.solr.Solr.SolrImplicit
import com.github.music.of.the.ainur.almaren.builder.Core.Implicit
import com.github.music.of.the.ainur.almaren.Almaren
import org.apache.spark.sql.SaveMode

val almaren = Almaren("App Name")

almaren.builder
    .sourceSql("""SELECT sha2(concat_ws("",array(*)),256) as id,*,current_timestamp from deputies""")
    .coalesce(30)
    .targetSolr("deputies","cloudera:2181,cloudera1:2181,cloudera2:2181/solr",Map("batch_size" -> "100000","commit_within" -> "10000"),SaveMode.Overwrite)
    .batch
```
