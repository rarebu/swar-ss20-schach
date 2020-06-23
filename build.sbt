name          := "Schach"
organization  := "de.htwg.se"
version       := "0.0.2"
scalaVersion  := "2.12.8"

//lazy val root = (project in file("."))
//  .aggregate(logic, user)
//
//
//
//lazy val logic = (project in file("logic"))
//
//lazy val user = (project in file("user"))





libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
libraryDependencies += "junit" % "junit" % "4.12" % "test"
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1"
libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.12"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.5"
libraryDependencies += "de.heikoseeberger" %% "akka-http-play-json" % "1.32.0"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"
libraryDependencies += "com.google.inject" % "guice" % "4.1.0"


//libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"



//*******************************************************************************//
//Libraries that we will use in later lectures compatible with this scala version
// uncomment to use!!

//libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.1"

//libraryDependencies += "com.google.inject" % "guice" % "4.1.0"

//libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"

libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.10"

libraryDependencies += "com.typesafe.slick" %% "slick" % "3.3.1"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.26"
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.1"
libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0"