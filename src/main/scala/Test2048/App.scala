package main.scala.Test2048

object MainApp extends App {
	def consume2 (L : List[Int]) : Either[List[Int], List[Int]] = { 
			L match { 
			case Nil => Left{Nil} 
			case List(x) => Left{List(x)} 
			case x :: y :: r =>  
			if ((x == y) && (x != 0)) { 
				consume2(r) match { 
				case Left(l) => Right(2*x :: l) 
				case Right(l) => Right(2*x :: l) 
				}                 
			} else { 
				consume2(y :: r) match { 
				case Left(l) => Left(x :: l) 
				case Right(l) => Right(x :: l) 
				} 
			} 
			} 
	} 

	def fillToLengthWithZero (L : List[Int], n : Int)  
	= (L ++ List.fill(n)(0)).take (n) 

	def shiftLeft (L : List[Int]) = { 
		val n = L.length 
				consume2(L.filter (_ != 0)) match { 
				case Left(l) => Left(fillToLengthWithZero (l, n)) 
				case Right(l) => Right(fillToLengthWithZero (l, n)) 
		} 
	} 

	def shiftRight (L : List[Int]) = { 
		shiftLeft (L.reverse).fold(x => Left(x.reverse), x => Right(x.reverse)) 
	} 

	val l1 = List(List(2,0,4,4), List(0,0,0,0), List(2,0,0,2), List(2,4,2,4)) 
	
	println(Sweep.withRandomTile(l1, 2+2*util.Random.nextInt(2)))

	//println(l1 map shiftLeft) 
	//println(l1 map shiftRight) 

	val l2 = l1.transpose 

	//println(l2 map shiftLeft) 
	//println(l2 map shiftRight) 
	
	//println (shiftLeft (List(2,0,0,4)))
	
	sealed trait Moveable[T] {
	  def twoFold (f : T => T) = {
	    this match {
	      case Moved(v) => Moved(f(v))
	      case Still(v) => Still(f(v))
	    }
	  }
	  
	  def getValue : T
	  
	  private def unit[T] (value : T) = new Still(value)
	  
	  def map[B](f: T => B) : Moveable[B] = flatMap {x => unit(f(x))}
	  
	  def flatMap[B](f: T => Moveable[B]) : Moveable[B] = {
	    this match {
	      case Moved(v) => f(v) match {
	        case Moved(a) => Moved(a)
	        case Still(a) => Moved(a)  
	      }  
	      case Still(v) => f(v) 
	    } 
	  }
	} 
	
	case class Moved[T](value : T) extends Moveable[T] {
	  def getValue = value
	}
	
	case class Still[T](value : T) extends Moveable[T] {
	  def getValue = value
	}

	def shiftLeftMoved (L : List[Int]) : Moveable[List[Int]] = {
	  L match {
	    case Nil => Still(Nil)
	    case List(x) => Still(List(x))
	    case 0 :: ln => shiftLeftMoved(ln).flatMap(x => Moved(x))
	    case x :: 0 :: ln => shiftLeftMoved(x :: ln).flatMap(x => Moved(x))
	    case x :: y :: ln => 
	      if (x == y) 
	        shiftLeftMoved(ln).flatMap(l => Moved((2*x) :: l))
	      else
	        shiftLeftMoved(y :: ln).map(l => x :: l)
	  }
	}

	def shiftLeftMovedGood (L : List[Int]) = {
		shiftLeftMoved (L).twoFold(l => fillToLengthWithZero (l, L.length))
	}
	
	//println (shiftLeftMovedGood (List(2,0,0,4)))
	
	def f(cur : Moveable[List[Int]], acc : Moveable[List[List[Int]]]) = cur.map (l => l :: acc.getValue)
	  
	def el : Moveable[List[List[Int]]] = Still(Nil)
	
	//println((l1 map shiftLeftMovedGood).foldRight(el)(f))
	
	def shiftLeft2 (L : List[Int]) : Either[List[Int], List[Int]] = {
	  L match {
	    case Nil => Left{Nil}
	    case List(0) => Left(List(0))
	    case 0 :: ln => Right{shiftLeft2(ln).fold(x => x, x => x)}
	    case x :: 0 :: ln => Right{shiftLeft2(x :: ln).fold(x => x, x => x)}
	    case x :: y :: ln => 
	      if (x == y) 
	    	Right{2*x :: (shiftLeft2(ln).fold(x => x, x => x))}
	      else
	        shiftLeft(ln).fold(l => Left{x :: y :: l}, l => Right{x :: y :: l})
	  }
	}
	
	def shiftLeftGood (L : List[Int]) = {
		shiftLeft2 (L).fold(l => Left{fillToLengthWithZero (l, L.length)}, r => Right{fillToLengthWithZero (r, L.length)})
	}
	
	//println (shiftLeftGood (List(2,0,0,4)))
}
