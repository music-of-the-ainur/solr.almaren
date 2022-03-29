# Solr Connector

[![Build Status](https://travis-ci.com/music-of-the-ainur/solr.almaren.svg?branch=master)](https://travis-ci.com/music-of-the-ainur/solr.almaren)

```
libraryDependencies += "com.github.music-of-the-ainur" %% "solr-almaren" % "0.3.0-2.4"
```

To run in spark-shell:

```
spark-shell --packages "com.github.music-of-the-ainur:solr-almaren_2.12:0.3.0-$SPARK_VERSION,com.github.music-of-the-ainur:almaren-framework_2.12:0.9.3-$SPARK_VERSION"
```


Solr Connector was implemented using [https://github.com/lucidworks/spark-solr](https://github.com/lucidworks/spark-solr). The *Solr Connector* just works on Solr Cloud.
For all the options available for the connector check on this [link](https://github.com/lucidworks/spark-solr#configuration-and-tuning).

```
spark-shell --master "local[*]" --packages "com.github.music-of-the-ainur:almaren-framework_2.12:0.9.3-2.4,com.github.music-of-the-ainur:solr-almaren_2.12:0.3.0-2.4"
```

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

