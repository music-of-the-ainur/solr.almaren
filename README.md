# Solr Connector

[![Build Status](https://github.com/music-of-the-ainur/solr.almaren/actions/workflows/solr-almaren-githubactions.yml/badge.svg)](https://github.com/music-of-the-ainur/solr.almaren/actions/workflows/solr-almaren-githubactions.yml)

Solr Connector was implemented using [https://github.com/lucidworks/spark-solr](https://github.com/lucidworks/spark-solr). The *Solr Connector* just works on Solr Cloud.
For all the options available for the connector check on this [link](https://github.com/lucidworks/spark-solr#configuration-and-tuning).

To add Solr Connector dependency to your sbt build:

```
libraryDependencies += "com.github.music-of-the-ainur" %% "solr-almaren" % "0.3.4-3.1"
```

To run in spark-shell:

```
spark-shell --master "local[*]" --packages "com.github.music-of-the-ainur:almaren-framework_2.12:0.9.8-3.1,com.github.music-of-the-ainur:solr-almaren_2.12:0.3.4-3.1"
```

### Connector Usage

#### Maven / Ivy Package Usage
The connector is also available from the
[Maven Central](https://mvnrepository.com/artifact/com.github.music-of-the-ainur)
repository. It can be used using the `--packages` option or the
`spark.jars.packages` configuration property. Use the following value

| version                   | Connector Artifact                                          |
|---------------------------|-------------------------------------------------------------|
| Spark 3.3.x and scala 2.12 | `com.github.music-of-the-ainur:solr-almaren_2.12:0.3.4-3.3` |
| Spark 3.2.x and scala 2.12 | `com.github.music-of-the-ainur:solr-almaren_2.12:0.3.4-3.2` |
| Spark 3.1.x and scala 2.12 | `com.github.music-of-the-ainur:solr-almaren_2.12:0.3.4-3.1` |
| Spark 2.4.x and scala 2.11 | `com.github.music-of-the-ainur:solr-almaren_2.11:0.3.4-2.4` |


## Source and Target

### Source 
#### Parameteres

| Parameters            | Description                                                                  |
|-----------------------|------------------------------------------------------------------------------|
| collection            | collection name                                                              |
| ZookeeperHost(zkhost) | localhost:9983                                                               |
| options               | Description(Value)                                                           |
|-----------------------|------------------------------------------------------------------------------|
| query                 | limits the rows you want to load into Spark("body_t:solr")                   |
| fields                | specify a subset of fields("id,author_s,favorited_b")                        |
| filters               | to apply filters on the values in documents("firstName:Sam,lastName:Powell") |
| rows                  | specify the number of rows to be displayed on the page(100)                  |
| max_rows              | Limits the result set to a maximum number of rows(5000)                      |
| request_handler       | Set the Solr request handler for queries("/export","/select")                |
#### Example


```scala
import com.github.music.of.the.ainur.almaren.Almaren
import com.github.music.of.the.ainur.almaren.solr.Solr.SolrImplicit

val almaren = Almaren("App Name")

almaren.builder.sourceSolr("collection","zkHost1:2181,zkHost2:2181",Map("field_names" -> "first_name,last_name","rows" -> 100))

almaren.builder.targetSolr("collection","zkHost1:2181,zkHost2:2181",options)

```



### Target:
#### Parameters

| Parameters            | Description                                                    |
|-----------------------|----------------------------------------------------------------|
| collection            | collection name                                                |
| ZookeeperHost(zkhost) | localhost:9983                                                 |
| Savemode              | SaveMode.ErrorIfExists                                         |
| options               | Description(Value)                                             |
|-----------------------|----------------------------------------------------------------|
| soft_commit_secs      | set soft_commit_sec(10 seconds)                                |
| commit_within         | force commit to happen after specified time(5000 milliSeconds) |
| batch_size            | number of documents sent in a HTTP call (1000)                 |
| gen_uniq_key          | generating unique key for each document(true)                  |
| solr_field_types      | specify field types for solr("rating:string,title:text_en")    |

#### Example

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

