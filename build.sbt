name := "AkkaCity"

version := "1.0"

scalaVersion := "2.12.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.14",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.14",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.14",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

resolvers ++= Seq(
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/",
  "Akka Release Repository" at "http://repo.akka.io/releases/"
)

    