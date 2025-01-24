package org.gridgraphics

import javafx.application.Application
import org.gridgraphics.AoCInput.ShadowGrid

var grid = Grid(0, 0)
val fairTime = 84 // 84, 9412
val cheatGoal = 30 // 20, 100
fun main() {
    val input = ShadowGrid.example
    val shadowGrid = input.map { it + it }
    grid = Grid(shadowGrid)
    grid.print()
    grid.connectGrid(::getShadowNeighbours)
    FXGraphics.grid = grid
    val startId = grid.nodes.indexOfFirst { it?.data == 'S' }
    val endId = grid.nodes.indexOfLast { it?.data == 'E' }

    var timeSaved = fairTime
    var c = 0
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

fun getShadowNeighbours(t: Tile) = grid.getStraightNeighbours(t).map {
    if (it.data != '#') it
    else if (it.x < grid.width / 2) grid.xy2Node(it.x + grid.width / 2, it.y)
    else null
}.filterNotNull()
