package org.gridgraphics

import graphClasses.DFS
import graphClasses.Grid
import javafx.application.Application
import org.gridgraphics.AoCInput.UphillGrids

fun main() {
    val grid = Grid(UphillGrids.trueInput)
    grid.print()
    grid.getNodes().forEach { t ->
        grid.getStraightNeighbours(t).forEach { n ->
            if (n.data == t.data as Char + 1) grid.addEdge(t, n)
        }
    }
    val visitedNodes = mutableListOf<Int>()
    val dfs = DFS(grid)
    grid.getNodes().forEach {
        if (it.data != '0') return@forEach
        dfs.dfsRecursive(grid.node2Id(it))
        val currentVisitedNodes = dfs.getCurrentVisited()
        visitedNodes.addAll(currentVisitedNodes)
    }
    val ans = visitedNodes.count { grid.id2Node(it)?.data == '9' }
    println(ans)
    FXGraphics.grid = grid
    FXGraphics.visitedNodes =visitedNodes
    FXGraphics.nodeDistances = dfs.getVisitedDepths()
    Application.launch(FXGraphics()::class.java)
}
