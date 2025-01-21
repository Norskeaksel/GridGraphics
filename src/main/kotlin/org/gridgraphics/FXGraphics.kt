package org.gridgraphics

import javafx.animation.KeyFrame
import javafx.animation.PauseTransition
import javafx.animation.Timeline
import javafx.application.Application
import javafx.event.ActionEvent
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.util.Duration


class FXGraphics : Application() {
    companion object {
        var grid = Grid(0, 0)
        var visitedNodes = listOf<Tile>()
        var nodeDistances = listOf<Int>()
        var animationTimeOverride: Duration? = null
    }

    var animationKeyFrameTime = animationTimeOverride ?: Duration.millis(10_000.0 / visitedNodes.size)
    val sceneWith = 1000.0
    val sceneHeight = 1000.0
    val canvas = Canvas(sceneWith, sceneHeight)
    val gc = canvas.graphicsContext2D
    val xNodes = sceneWith / (grid.width)
    val yNodes = sceneHeight / grid.height
    val minEdgeLength = xNodes.coerceAtMost(yNodes)

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Drawing Operations Test"
        val root = Group()
        gc.fill = Color.BLACK
        grid.getNodes().forEach { node ->
            drawSquare(node.x, node.y, Color.BLACK)
        }
        root.children.add(canvas)
        primaryStage.scene = Scene(root)
        primaryStage.show()
        animateVisitedNodes()
    }

    private fun drawSquare(x: Int, y: Int, color: Color) {
        gc.fill = color
        gc.fillRect(x * minEdgeLength, y * minEdgeLength, minEdgeLength - 1, minEdgeLength - 1)
    }

    private fun animateVisitedNodes() {
        val timeline = Timeline()
        println("animationKeyFrameTime: $animationKeyFrameTime")
        val maxDepth = nodeDistances.maxOrNull()?.toDouble() ?: 0.0
        visitedNodes.forEachIndexed { i, node ->
            val color = getInterpolatedColor(nodeDistances[i].toDouble(), maxDepth)
            val keyFrame = KeyFrame(
                animationKeyFrameTime.multiply(i.toDouble()), squareDrawer(
                    node, color
                )
            )
            timeline.keyFrames.add(keyFrame)
        }
        val pause = PauseTransition(Duration.seconds(.5))
        pause.setOnFinished { timeline.play() }
        pause.play()
        //timeline.play()
    }

    private fun squareDrawer(node: Tile, color: Color): (ActionEvent) -> Unit {
        return { drawSquare(node.x, node.y, color) }
    }


    fun getInterpolatedColor(value: Double, max: Double, min: Double = 0.0): Color {
        if (value !in min..max) {
            error("Value $value is not in range $min..$max")
        }
        val normalized =
            (value - min) / (max - min) - 1e-6 // -1e-6 to avoid 1.0 and thus trying to interpolate out of bounds
        val colors = arrayOf(
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.LIGHTSKYBLUE,
            Color.BLUE,
        )
        val index = (normalized * (colors.size - 1)).toInt()
        return colors[index].interpolate(colors[index + 1], (normalized * (colors.size - 1)) % 1)
    }

}
