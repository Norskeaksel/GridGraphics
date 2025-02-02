package org.gridgraphics

import graphClasses.BFS
import graphClasses.Grid
import graphClasses.Tile
import graphClasses.getPath
import javafx.application.Application
import org.gridgraphics.AoCInput.ShadowGrid

fun main() {
    val fairTime = 9412 // 84, 9412
    val cheatGoal = 100 // 30, 100
    val input = ShadowGrid.trueInput
    val shadowGrid = input.map { it + it }
    val grid = Grid(shadowGrid)
    grid.print()

    fun getShadowNeighbours(t: Tile) = grid.getStraightNeighbours(t).mapNotNull {
        if (it.data != '#') it
        else if (t.x < grid.width / 2) grid.xy2Node(it.x + grid.width / 2, it.y)
        else null
    }

    grid.connectGrid(::getShadowNeighbours)
    FXGraphics.grid = grid
    val startId = grid.nodes.indexOfFirst { it?.data == 'S' }
    val endId = grid.nodes.indexOfLast { it?.data == 'E' }

    var timeSaved = fairTime
    var c = -1
    var bfs = BFS(grid)
    while (timeSaved >= cheatGoal) {
        bfs = BFS(grid)
        c++
        bfs.bfsIterative(listOf(startId))
        val cheatDist = bfs.distances[endId]
        timeSaved = (fairTime - cheatDist)
        println("timeSaved: $timeSaved")
        grid.removeCheatPath(getPath(endId, bfs.parent))
    }
    println(c)
    FXGraphics.visitedNodes = bfs.currentVisited
    FXGraphics.nodeDistances = bfs.currentVisitedDistances
    FXGraphics.finalPath = getPath(endId, bfs.parent)
    Application.launch(FXGraphics()::class.java)
}
