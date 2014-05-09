Test2048
========

My Scala implementation (with a monad!) of a addictive 2048 game

## Motivation 
I really, really like the [2048 game](http://gabrielecirulli.github.io/2048/). 
Also, I wanted to build some non-trivial Scala-Java app which can be packaged 
to a single, no-dependency jar.
Actually, it worked out pretty well, check below.

## Side effects (!)
This project show a couple of Scala-related things:
* How to write unit tests using scalatest
* How to make a binary package (the Scala dependent jar) with sbt
* How to make a Scala-independent, obfuscated binary packed with sbt+proguard
* How to write a monad and use it in for comprehension (yay!)

## Binary files
You can download the binary package [test2048_2.10-1.0.jar](http://1drv.ms/1mKypNa).
It requires Java 7 (JRE) to run, simply do
```
java -jar test2048_2.10-1.0.jar
```
