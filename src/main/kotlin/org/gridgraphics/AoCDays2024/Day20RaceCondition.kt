package org.gridgraphics.AoCDays2024

import graphClasses.BFS
import graphClasses.Grid
import graphClasses.Tile
import graphClasses.getPath
import javafx.application.Application
import org.gridgraphics.AoCDays2024.AoCInput2024.ShadowGrid
import org.gridgraphics.FXGraphics

fun main() {
    val fairTime = 84 // 84, 9412
    val cheatGoal = 30 // 30, 100, 50
    val input = ShadowGrid.example
    val shadowGrid = input.map { it + it }
    val grid = Grid(shadowGrid)
    grid.print()
    fun getShadowNeighbours(t: Tile) = grid.getStraightNeighbours(t).mapNotNull {
        if (it.data != '#') it
        else if (t.x < grid.width / 2) grid.xy2Node(it.x + grid.width / 2, it.y)
        else null
    }

    grid.connectGrid(::getShadowNeighbours)
    val startId = grid.nodes.indexOfFirst { it?.data == 'S' }
    val endId = grid.nodes.indexOfLast { it?.data == 'E' }

    var timeSaved = fairTime
    var c = -1
    var bfs = BFS(grid)
    while (timeSaved >= cheatGoal) {
        c++
        bfs = BFS(grid)
        bfs.bfsIterative(startId)
        val cheatDist = bfs.distances[endId]
        timeSaved = (fairTime - cheatDist).toInt()
        println("There is one cheat that saves $timeSaved picoseconds.")
        val path = getPath(endId, bfs.parents)
        grid.removeCheatPath(path)
    }
    println("Cheets: $c")
    FXGraphics.grid = grid
    FXGraphics.visitedNodes = bfs.currentVisited
    FXGraphics.nodeDistances = bfs.currentVisitedDistances
    FXGraphics.finalPath = getPath(endId, bfs.parents)
    FXGraphics.sceneWithOverride = 2000.0
    FXGraphics.startPaused = true
    Application.launch(FXGraphics()::class.java)
}
