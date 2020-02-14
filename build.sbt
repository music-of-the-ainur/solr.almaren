ThisBuild / name := "solr.almaren"
ThisBuild / organization := "com.github.music-of-the-ainur"

lazy val scala211 = "2.11.10"

crossScalaVersions := Seq(scala211)
ThisBuild / scalaVersion := scala211

val sparkVersion = "2.4.4"

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "com.github.music-of-the-ainur" %% "almaren-framework" % "0.2.2-2-4" % "provided",
  "com.lucidworks.spark" % "spark-solr" % "3.7.0",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8"

enablePlugins(GitVersioning)

resolvers += "spring" at "https://repo.spring.io/plugins-release"
