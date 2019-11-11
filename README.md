# Solr Connector

Solr Connector was implemented using (https://github.com/lucidworks/spark-solr)[https://github.com/lucidworks/spark-solr].

## Source

```scala
import com.github.music.of.the.ainur.almaren.Almaren
import com.github.music.of.the.ainur.almaren.solr.Solr._

val movies = almaren.builder
    .sourceSql("collection","localhost:2181")
```


## Target

```scala
import com.github.music.of.the.ainur.almaren.Almaren
import com.github.music.of.the.ainur.almaren.solr.Solr._

val movies = almaren.builder
    .targetSolr("collection","localhost:2181")
```
