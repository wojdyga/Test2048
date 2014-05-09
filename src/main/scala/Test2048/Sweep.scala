package main.scala.Test2048

import util.Random

object Sweep {
	sealed trait Moveable[T] {
	  def getValue : T
	  def getPoints : Int
	  
	  private def unit[T] (value : T, p : Int) = new Still(value, p)
	  
	  def map[B](f: T => B) : Moveable[B] = flatMap {x => unit(f(x), 0)}
	  
	  def flatMap[B](f: T => Moveable[B]) : Moveable[B] = {
	    this match {
	      case Moved(v, pv) => f(v) match {	    
	        case Moved(a, pa) => Moved(a, pv+pa)
	        case Still(a, pa) => Moved(a, pv+pa)  
	      }  
	      case Still(v, pv) => f(v) match {
	        case Moved(a, pa) => Moved(a, pv+pa)
	        case Still(a, pa) => Still(a, pv+pa)  
	      } 
	    } 
	  }
	  
	  def moved : Moveable[T] = this flatMap (x => Moved(x, 0))
	} 
	
	case class Moved[T](value : T, points : Int) extends Moveable[T] {
	  def getValue = value
	  def getPoints = points
	}
	
	case class Still[T](value : T, points : Int) extends Moveable[T] {
	  def getValue = value
	  def getPoints = points
	}

	def moveLeft (L : List[Int]) : Moveable[List[Int]] = {
      L match {
	      case Nil => Still(Nil, 0)
	      case List(x) => Still(List(x), 0)
	      case 0 :: 0 :: ln => moveLeft(0 :: ln)
	      case 0 :: x :: ln => moveLeft(x :: ln).moved
	      case x :: 0 :: Nil => Still(x :: 0 :: Nil, 0)
  	      case x :: 0 :: ln => moveLeft(x :: ln)
  	      case x :: y :: ln => 
	        if (x == y) 
	          moveLeft(ln).flatMap(l => Moved((2*x) :: l, 2*x))
	        else
	          moveLeft(y :: ln).map(l => x :: l)
	          //for (l <- moveLeft(y :: ln)) yield (x :: l)
	  }
	}

	def fillToLengthWithZero (L : List[Int], n : Int) = {
		(L ++ List.fill(n)(0)).take (n) 
	}
	
	def left (L : List[Int]) = {
		moveLeft (L).map(l => fillToLengthWithZero (l, L.length))
	}
			 
	def right (L : List[Int]) = {
		left (L.reverse).map(x => x.reverse)  
	}
	
	def leftAll (L : List[List[Int]]) = collate(L map left)
	def rightAll (L : List[List[Int]]) = collate(L map right)
	
	def collate(L : List[Moveable[List[Int]]]) = {
		def startAccEl : Moveable[List[List[Int]]] 
			= Still(Nil, 0)
		
		def foldFunc(cur : Moveable[List[Int]], acc : Moveable[List[List[Int]]]) 
	  		= {
		  //cur.flatMap(l => acc.map(la => cur.getValue :: la))
		  for {
		      l <- cur
		      la <- acc
		  } yield (l :: la)
		}

	  	L.foldRight(startAccEl)(foldFunc)
	}	
	
	def withRandomTile(L : List[List[Int]], random : Int): List[List[Int]] = {
	  val Lf = L.flatten
	  val LzeroPos = Lf.zipWithIndex.filter(p => p._1 == 0)
	  val rpos = Random.nextInt(LzeroPos.length)
	  
	  Lf.updated (LzeroPos(rpos)._2, random).grouped(Lf.length / L.length).toList
	}
}
