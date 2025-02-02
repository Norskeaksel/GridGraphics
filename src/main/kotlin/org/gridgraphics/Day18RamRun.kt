package org.gridgraphics

import graphClasses.BFS
import graphClasses.Grid
import javafx.application.Application
import org.gridgraphics.AoCInput.CorruptingGrid

fun main(){
    val gridSize = 71
    val input = CorruptingGrid.trueInput
    val lineCount = 1024
    val grid = Grid(gridSize, gridSize)
    for((i, line) in input.withIndex()){
        if(i >= lineCount) break
        val (x, y) = line.split(",").map { it.toInt() }
        val corruptId = grid.xy2Id(x, y)!!
        grid.nodes[corruptId] = null
    }
    grid.connectGrid(grid::getStraightNeighbours)
    val bfs = BFS(grid)
    bfs.bfsIterative(listOf(0))
    val ans = bfs.distances[grid.xy2Id(gridSize - 1, gridSize - 1)!!]
    println(ans)
    FXGraphics.grid = grid
    FXGraphics.visitedNodes = bfs.currentVisited
    FXGraphics.nodeDistances = bfs.currentVisitedDistances
    FXGraphics.finalPath = getPath(grid.xy2Id(gridSize - 1, gridSize - 1), bfs.parent)
    Application.launch(FXGraphics()::class.java)
}