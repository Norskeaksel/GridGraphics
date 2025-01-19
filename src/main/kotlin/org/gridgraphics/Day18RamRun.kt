package org.gridgraphics

import javafx.application.Application
import org.gridgraphics.AoCInput.CorruptingGrid

fun main(){
    val gridSize = 71
    val input = CorruptingGrid.trueInput
    val lineCount = 1024
    val grid = Grid(gridSize, gridSize)
    input.forEachIndexed { i, line ->
        if (i >= lineCount)
            return@forEachIndexed

        val (x, y) = line.split(",").map { it.toInt() }
        val corruptId = grid.xy2Id(x, y)!!
        grid.nodes[corruptId] = null
    }
    grid.connectGrid(grid::getStraightNeighbours)
    val bfs = BFS(grid)
    bfs.bfsIterative(listOf(0))
    val ans = bfs.distances[grid.xy2Id(gridSize - 1, gridSize - 1)!!].toInt()
    println(ans)
    FXGraphics.grid = grid
    FXGraphics.visitedNodes = grid.ids2Nodes(bfs.currentVisited)
    FXGraphics.nodeDistances = bfs.currentVisitedDistances
    Application.launch(FXGraphics()::class.java)
}