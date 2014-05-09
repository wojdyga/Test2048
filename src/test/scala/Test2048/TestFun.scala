package main.scala.Test2048

import org.scalatest.FunSuite

class TestFun extends FunSuite {
  test("all zeroes") {
    assert(Sweep.left(List(0,0,0,0)) == Sweep.Still(List(0,0,0,0), 0))
  }

  test("all twoes") {
    assert(Sweep.left(List(2,2,2,2)) == Sweep.Moved(List(4,4,0,0), 8))
  }

  test("2,0,0,2") {
    assert(Sweep.left(List(2,0,0,2)) == Sweep.Moved(List(4,0,0,0), 4))
  }

  test("2,4,0,4") {
    assert(Sweep.left(List(2,4,0,4)) == Sweep.Moved(List(2,8,0,0), 8))
  }

  test("0,0,0,2 left") {
    assert(Sweep.left(List(0,0,0,2)) == Sweep.Moved(List(2,0,0,0), 0))
  }
  
  test("0,0,0,2 right") {
    assert(Sweep.right(List(0,0,0,2)) == Sweep.Still(List(0,0,0,2), 0))
  }

  test("2,2,4,4") {
    assert(Sweep.left(List(2,2,4,4)) == Sweep.Moved(List(4,8,0,0), 12))
  }

  test("2,0,2,0") {
    assert(Sweep.left(List(2,0,2,0)) == Sweep.Moved(List(4,0,0,0), 4))
  }

  test("0,2,0,2") {
    assert(Sweep.left(List(0,2,0,2)) == Sweep.Moved(List(4,0,0,0), 4))
  }

 test("2,4,2,4") {
    assert(Sweep.left(List(2,4,2,4)) == Sweep.Still(List(2,4,2,4), 0))
  }

  test("2,4,8,2") {
    assert(Sweep.left(List(2,4,8,2)) == Sweep.Still(List(2,4,8,2), 0))
  }

  test("8,4,2,8") {
    assert(Sweep.left(List(8,4,2,8)) == Sweep.Still(List(8,4,2,8), 0))
  }

  test("map list 1") {
   val result = List(List(0,0,0,2), List(2,2,4,4), List(2,0,2,0), List(0,2,0,2)) map Sweep.left
   assert(result == List(Sweep.Moved(List(2,0,0,0), 0), Sweep.Moved(List(4,8,0,0), 12), Sweep.Moved(List(4,0,0,0), 4), Sweep.Moved(List(4,0,0,0), 4)))
  }

  test("map list 2") {
   val result = List(List(0,0,0,0), List(2,4,8,2), List(2,0,2,0), List(8,4,2,8)) map Sweep.left
   assert(result == List(Sweep.Still(List(0,0,0,0), 0), Sweep.Still(List(2,4,8,2), 0), Sweep.Moved(List(4,0,0,0), 4), Sweep.Still(List(8,4,2,8), 0)))
  }

  test("map list 3") {
   val result = List(List(2,4,2,4), List(2,4,8,2), List(8,4,2,8), List(2,4,8,2)) map Sweep.left
   assert(result == List(Sweep.Still(List(2,4,2,4), 0), Sweep.Still(List(2,4,8,2), 0), Sweep.Still(List(8,4,2,8), 0), Sweep.Still(List(2,4,8,2), 0)))
  }

  test("map list 1 collate") {
   val result = List(List(0,0,0,2), List(2,2,4,4), List(2,0,2,0), List(0,2,0,2)) map Sweep.left
   assert(Sweep.collate(result) == Sweep.Moved(List(List(2,0,0,0), List(4,8,0,0), List(4,0,0,0), List(4,0,0,0)), 20))
  }

  test("map list 2 collate") {
   val result = List(List(0,0,0,0), List(2,4,8,2), List(2,0,2,0), List(8,4,2,8)) map Sweep.left
   assert(Sweep.collate(result) == Sweep.Moved(List(List(0,0,0,0), List(2,4,8,2), List(4,0,0,0), List(8,4,2,8)), 4))
  }

  test("map list 3 collate") {
   val result = List(List(2,4,2,4), List(2,4,8,2), List(8,4,2,8), List(2,4,8,2)) map Sweep.left
   assert(Sweep.collate(result) == Sweep.Still(List(List(2,4,2,4), List(2,4,8,2), List(8,4,2,8), List(2,4,8,2)), 0))
  }
  
  test("map list 3 collate nosweep") {
    val input = List(List(2,4,8,16), List(16,8,4,2), List(2,0,0,0), List(0,0,0,0))
    val result = input map Sweep.left
    assert(Sweep.collate(result) == Sweep.Still(input, 0)) 
  }
  
  test("observered bug 1") {
    val input = List(List(32,64,256,2), List(4,8,16,64), List(0,0,2,8), List(0,0,0,0))
    val result = input map Sweep.left
    assert(Sweep.collate(result) == Sweep.Moved(List(List(32,64,256,2), List(4,8,16,64), List(2,8,0,0), List(0,0,0,0)), 0))
  }
}
