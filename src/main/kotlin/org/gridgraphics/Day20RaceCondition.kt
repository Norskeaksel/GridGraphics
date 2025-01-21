package org.gridgraphics

import javafx.application.Application
import javafx.util.Duration
import org.gridgraphics.AoCInput.ShadowGrid

var grid = Grid(0, 0)
val exampleFairDist = 84
val inputFairDist = 9412
fun main() {
    val input = ShadowGrid.example
    val shadowGrid = input.map { it + it }
    grid = Grid(shadowGrid)
    grid.print()
    grid.connectGrid(::getShadowNeighbours)
    val startId = grid.nodes.indexOfFirst { it?.data == 'S' }
    val endId = grid.nodes.indexOfFirst { it?.data == 'E' }
    /*val dijkstra = Dijkstra(grid)
    dijkstra.dijkstra(startId)
    val path = dijkstra.getPath(endId)
    println(path.size -1)*/
    val bfs = BFS(grid)
    bfs.bfsIterative(listOf(startId))
    FXGraphics.grid = grid
    FXGraphics.visitedNodes = bfs.currentVisited.map { grid.nodes[it]!! }
    FXGraphics.nodeDistances = bfs.currentVisitedDistances
    FXGraphics.animationTimeOverride = Duration(500.0)
    Application.launch(FXGraphics()::class.java)
}

fun getShadowNeighbours(t: Tile) = grid.getStraightNeighbours(t).map {
    if (it.data != '#') it
    else if(it.x < grid.width / 2) grid.xy2Node(it.x + grid.width / 2, it.y)!!
    else null
}.filterNotNull()
