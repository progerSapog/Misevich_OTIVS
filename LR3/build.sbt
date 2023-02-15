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