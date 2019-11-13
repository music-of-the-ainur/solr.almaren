# Solr Connector

[![Build Status](https://travis-ci.com/modakanalytics/solr-connector.almaren.svg?token=TEB3zRDqVUuChez9334q&branch=master)](https://travis-ci.com/modakanalytics/solr-connector.almaren)

Solr Connector was implemented using [https://github.com/lucidworks/spark-solr](https://github.com/lucidworks/spark-solr).

## Source

```scala
import com.github.music.of.the.ainur.almaren.Almaren
import com.github.music.of.the.ainur.almaren.builder.Core.Implicit
import com.github.music.of.the.ainur.almaren.solr.Solr.SolrImplicit

val movies = almaren.builder
    .sourceSql("collection","localhost:2181")
```


## Target

```scala
import com.github.music.of.the.ainur.almaren.Almaren
import com.github.music.of.the.ainur.almaren.builder.Core.Implicit
import com.github.music.of.the.ainur.almaren.solr.Solr.SolrImplicit

val movies = almaren.builder
    .targetSolr("collection","localhost:2181")
```
