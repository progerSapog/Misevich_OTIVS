ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "LR3"
  )

val scalikejdbcVersion = "4.0.0"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion,
  "org.scalikejdbc" %% "scalikejdbc-test" % scalikejdbcVersion % Test,
  "org.scalikejdbc" %% "scalikejdbc-interpolation" % scalikejdbcVersion,
  "org.scalikejdbc" %% "scalikejdbc-core" % scalikejdbcVersion,
  "org.scalikejdbc" %% "scalikejdbc-config" % scalikejdbcVersion,
  "ch.qos.logback"  %  "logback-classic"   % "1.2.11",
  "org.postgresql" % "postgresql" % "42.3.6"
)
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"
//libraryDependencies += "org.scalatest" % "scalatest_2.9.2" % "1.9.2" % "test" withSources() withJavadoc()
libraryDependencies  ++= Seq(
  // other dependencies here
  "org.scalanlp" %% "breeze" % "2.1.0",
  // native libraries are not included by default. add this if you want them (as of 0.7)
  // native libraries greatly improve performance, but increase jar sizes.
  // It also packages various blas implementations, which have licenses that may or may not
  // be compatible with the Apache License. No GPL code, as best I know.
  "org.scalanlp" %% "breeze-natives" % "2.1.0",
  // the visualization library is distributed separately as well.
  // It depends on LGPL code.
  "org.scalanlp" %% "breeze-viz" % "2.1.0"
  )