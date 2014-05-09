package main.scala.Test2048GUI

import main.scala.Test2048._
import javax.swing.{JComponent, JFrame, JLabel, Timer, SwingUtilities, JButton, JPanel, ImageIcon}
import javax.swing.border.{EmptyBorder}
import java.awt.{Graphics, Graphics2D, GridLayout, BorderLayout, Color, Dimension, Rectangle, Polygon, Font}
import java.awt.event.{ActionListener, ActionEvent, KeyListener, KeyAdapter, KeyEvent}

object Display2048 extends App {
  def TILEWIDTH = 64
  def TILEHEIGHT = 64
  def WIDTH = 4
  def HEIGHT = 4
  
  val board = new Board(WIDTH, HEIGHT)
  
  val frame = new JFrame("2048") { frame =>
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    setBackground(Color.BLACK)
    
    object MyKeyListener extends KeyAdapter {
      override def keyTyped(e : KeyEvent) = {
      updateBoardAfter(
        e.getKeyChar() match {
          case 'a' => board.sweepLeft
          case 'd' => board.sweepRight
          case 's' => board.sweepDown
          case 'w' => board.sweepUp
          case _ => ()
        }
      )
      }
    }
    setFocusable(true)
    addKeyListener(MyKeyListener)
    
    def updateBoardAfter (action : Unit) = {
      boardDisplay.updateWith(board)
      if (board.lastPoints > 0) {
        scoreLabel.setText (board.points.toString)
      }
    }
        
    object boardDisplay extends JComponent {
      setPreferredSize(new Dimension(WIDTH * TILEWIDTH, HEIGHT * TILEHEIGHT))
      
      def tileColor(v : Int) = {
        v match {
          case 2 => new Color(16,16,16)
          case 4 => new Color(16,16,160)
          case 8 => new Color(16,160,16)
          case 16 => new Color(160,16,16)
          case 32 => new Color(160,160,16)
          case 64 => new Color(16,160,160)
          case 128 => new Color(160,16,160)
          case 256 => new Color(238, 160, 14)
          case 512 => new Color(255, 215, 0)
          case 1024 => new Color(255, 236, 139)
          case x => new Color(250, 250, 210)
        }
      }
      var tiles : List[List[(Int, Color)]] = List.fill(WIDTH, HEIGHT)((0, Color.BLACK))
      def updateWith(b : Board) = {
        tiles = b.tiles map (l => l map (x => (x, tileColor(x))))
        repaint()
      }
      override def paint(g: Graphics) {
        super.paint(g)
        var y = 0
        for (tl <- tiles) {
          var x = 0
          for (t <- tl) {
            g.setColor(t._2)
            g.fillRect(x, y, TILEWIDTH-1, TILEHEIGHT-1)
            if (t._1 > 0) {
              g.setColor(Color.LIGHT_GRAY)
              var size = TILEHEIGHT / 2
              g.setFont(new Font(g.getFont.getName, g.getFont.getStyle(), size))
              val s = t._1.toString
              var w = g.getFontMetrics.stringWidth(s)
              if (w > 4 * TILEWIDTH / 5) 
                size = 5 * TILEHEIGHT / 16
              g.setFont(new Font(g.getFont.getName, g.getFont.getStyle(), size))
              w = g.getFontMetrics.stringWidth(s)
              val h = g.getFontMetrics.getHeight
              g.drawString(s, x+TILEWIDTH/2 - w/2, y+TILEHEIGHT/2+h/2)
            }
            x += TILEWIDTH
          }
          y += TILEHEIGHT
        }    	
      }
    }
    
    object infoPanel extends JPanel {
      setLayout(new BorderLayout)
      add(new JLabel(new ImageIcon(getClass.getResource(/*"/main/resources*/ "/wasd.png"))), BorderLayout.CENTER)
    }
    
    val scoreLabel = new JLabel("0")
    scoreLabel.setFont(new Font("Serif", Font.PLAIN, TILEHEIGHT / 2))
    
    object scorePanel extends JPanel {
      setBorder(new EmptyBorder(2, 4, 2, 4))
      setLayout(new BorderLayout)
      val p = new JPanel()
      p.setLayout(new BorderLayout())
      p.add(new JLabel("Points: "), BorderLayout.NORTH)
      p.add(scoreLabel, BorderLayout.SOUTH)
      add(p, BorderLayout.EAST)
      val logoLabel = new JLabel(new ImageIcon(getClass.getResource(/*"/main/resources*/ "/logo.png")))
      add(logoLabel,  BorderLayout.WEST)
    }
    
    setContentPane(new JComponent {
      setBorder(new EmptyBorder(10, 10, 10, 10))
      setLayout(new BorderLayout)
      add(infoPanel, BorderLayout.SOUTH)
      add(boardDisplay, BorderLayout.CENTER)
      add(scorePanel, BorderLayout.NORTH)
    })
    boardDisplay.updateWith(board)
    pack
    setResizable(false)
    setVisible(true)
  }
}