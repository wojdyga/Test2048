import sbt.Package.ManifestAttributes

name := "Test2048"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"

proguardSettings

ProguardKeys.options in Proguard := Seq(
"-injars <user.dir>/target/scala-2.10/classes",
"-injars <user.dir>/src(META-INF/MANIFEST.MF)",
"-injars <user.home>/.sbt/boot/scala-2.10.2/lib/scala-library.jar(!META-INF/MANIFEST.MF)",
"-libraryjars <java.home>/lib/rt.jar",
"-outjars <user.dir>/target/scala-2.10/proguard/test2048_2.10-1.0.jar",
"-dontnote", 
"-dontwarn", 
"-ignorewarnings", 
"-dontoptimize")

ProguardKeys.options in Proguard += ProguardOptions.keepMain("main.scala.Test2048GUI.Display2048")

packageOptions in packageBin := Seq(
ManifestAttributes(
 ("Main-Class", "main.scala.Test2048GUI.Display2048"), 
 ("Built-By","alek.wojdyga@gmail.com")
)
)

