import sbt.Package.ManifestAttributes

name := "Test2048"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test"

proguardSettings

ProguardKeys.options in Proguard := Seq(
"-injars /home/alek/progfun-workspace/Test2048/target/scala-2.10/classes",
"-injars /home/alek/progfun-workspace/Test2048/src(META-INF/MANIFEST.MF)",
"-injars /home/alek/.sbt/boot/scala-2.10.2/lib/scala-library.jar(!META-INF/MANIFEST.MF)",
"-libraryjars /usr/lib64/jvm/java-1.7.0-openjdk-1.7.0/jre/lib/rt.jar",
"-outjars /home/alek/progfun-workspace/Test2048/target/scala-2.10/proguard/test2048_2.10-1.0.jar",
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

