# Solr Connector

[![Build Status](https://travis-ci.com/music-of-the-ainur/solr.almaren.svg?branch=master)](https://travis-ci.com/music-of-the-ainur/solr.almaren)

```
libraryDependencies += "com.github.music-of-the-ainur" %% "solr-almaren" % "0.0.3-2-4"
```

Solr Connector was implemented using [https://github.com/lucidworks/spark-solr](https://github.com/lucidworks/spark-solr). The *Solr Connector* just works on Solr Cloud.
For all the options available for the connector check on this [link](https://github.com/lucidworks/spark-solr#configuration-and-tuning).

## Source and Target

```scala
import com.github.music.of.the.ainur.almaren.Almaren
import com.github.music.of.the.ainur.almaren.solr.Solr.SolrImplicit

val almaren:Almaren = Almaren("App Name")

almaren.builder.sourceSolr("collection","zkHost1:2181,zkHost2:2181",options)

almaren.builder.targetSolr("collection","zkHost1:2181,zkHost2:2181",options)

```
