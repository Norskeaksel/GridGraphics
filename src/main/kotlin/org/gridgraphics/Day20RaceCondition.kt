package org.gridgraphics

import javafx.application.Application
import org.gridgraphics.AoCInput.ShadowGrid

var grid = Grid(0, 0)
fun main() {
val input = ShadowGrid.example
    val height = input.size
    val width = input[0].length
    val shadowGrid = input.map { it + it }
    grid = Grid(shadowGrid)
    grid.print()
    grid.markCharAsWall('#')
    grid.connectGrid(grid::getStraightNeighbours)
    val startId = grid.nodes.indexOfFirst { it?.data == 'S' }
    val endId = grid.nodes.indexOfFirst { it?.data == 'E' }
    val dijkstra = Dijkstra(grid.getAdjacencyList())
    dijkstra.dijkstra(startId)
    val path = dijkstra.getPath(endId)
    FXGraphics.grid = grid
    FXGraphics.visitedNodes = grid.ids2Nodes(path)
    FXGraphics.nodeDistances = path.map { dijkstra.distance[it].toInt() }
    Application.launch(FXGraphics()::class.java)
}

fun getShadowNeighbours(t:Tile) = grid.getStraightNeighbours(t).map { it to grid }