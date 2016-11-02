name := "ID2223-spark"

organization := "se.kth.spark"

version := "1.0"

scalaVersion := "2.10.4"

//resolvers += Resolver.mavenLocal

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.1" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.6.1" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "1.6.1" % "provided"

mainClass in assembly := Some("se.sics.spark.Main")

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
