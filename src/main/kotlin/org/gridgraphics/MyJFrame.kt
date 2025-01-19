package org.gridgraphics

import java.awt.Color
import java.awt.Graphics2D
import java.lang.Thread.sleep
import javax.swing.JFrame

class MyJFrame(sceneWith:Int, sceneHeight:Int): JFrame() {
    val g2d: Graphics2D
    init {
        title = "Drawing Operations Test"
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(sceneWith, sceneHeight)
        isVisible = true
        g2d = graphics as Graphics2D
    }
}