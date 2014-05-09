package main.scala.Test2048

object work1 {
  //val L = List(List(2,2,4,4), List(2,0,0,2))
  //L map left
  //leftAll(L)
  //List(List(0,0,0,0), List(2,4,8,2), List(2,0,2,0), List(8,4,2,8)) map Sweep.left
  //collate (List(List(0,0,0,0), List(2,4,8,2), List(2,0,2,0), List(8,4,2,8)) map Sweep.left)
  //val input = List(List(2,4,8,16), List(16,8,4,2), List(2,0,0,0), List(0,0,0,0))
  //val result = input map left
  //collate(result)
  Sweep.right (List(0,0,0,2))                     //> List(2, 0, 0, 0)
                                                  //| des
                                                  //| List(2, 0, 0)
                                                  //| des
                                                  //| List(2, 0)
                                                  //| lop
                                                  //| res0: main.scala.Test2048.Sweep.Moveable[List[Int]] = Still(List(0, 0, 0, 2)
                                                  //| ,0)
  
  //val input = List(List(32,64,256,2), List(4,8,16,64), List(0,0,2,8), List(0,0,0,0))
  //input map left
  //left(List(0,0,2,8))
}