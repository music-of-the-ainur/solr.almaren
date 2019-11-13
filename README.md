# Solr Connector

[![Build Status](https://travis-ci.com/modakanalytics/solr.almaren.svg?token=TEB3zRDqVUuChez9334q&branch=master)](https://travis-ci.com/modakanalytics/solr.almaren)

Solr Connector was implemented using [https://github.com/lucidworks/spark-solr](https://github.com/lucidworks/spark-solr). The *Solr Connector* just works on Solr Cloud.
For all the options available for the connector check on this [https://github.com/lucidworks/spark-solr#configuration-and-tuning](link).

## Source and Target

```scala
import com.github.music.of.the.ainur.almaren.solr.Solr.SolrImplicit

almaren.builder.sourceSql("collection","zkHost1:2181,zkHost2:2181",options)

almaren.builder.targetSolr("collection","zkHost1:2181,zkHost2:2181",options)

```
