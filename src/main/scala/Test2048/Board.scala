package main.scala.Test2048

import common._
import Sweep.{withRandomTile, Moveable, Still, Moved, leftAll, rightAll}

class Board(x : Int, y : Int) {
  var tiles : Tiles = withRandomTile(withRandomTile(List.fill(x, y)(0), 2), 2)
  var points : Int = 0
  var lastPoints : Int = 0
  
  private def doSweep(op : Tiles => Moveable[Tiles], tx : Tiles) : Tiles = {
    op(tx) match {
      case Still(t, p) => {
        lastPoints = p
        points += p
        t
      }
      case Moved(t, p) => {
        points += p
        lastPoints = p
        withRandomTile(t, 2)
      }
    }
  }
  
  def sweepLeft = tiles = doSweep(leftAll, tiles)
  
  def sweepRight = tiles = doSweep(rightAll, tiles)
  
  def sweepUp = tiles = doSweep(leftAll, tiles.transpose).transpose
  
  def sweepDown = tiles = doSweep(rightAll, tiles.transpose).transpose
}
