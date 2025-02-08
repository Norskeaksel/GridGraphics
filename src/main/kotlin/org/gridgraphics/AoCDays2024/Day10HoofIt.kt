package org.gridgraphics.AoCDays2024

import graphClasses.DFS
import graphClasses.Grid
import javafx.application.Application
import org.gridgraphics.AoCInput2024.UphillGrids
import org.gridgraphics.FXGraphics

fun main() {
    val grid = Grid(UphillGrids.trueInput)
    grid.print()
    grid.getNodes().forEach { t ->
        grid.getStraightNeighbours(t).forEach { n ->
            if (n.data == t.data as Char + 1) grid.addEdge(t, n)
        }
    }
    val visitedNodes = mutableListOf<Int>()
    grid.getNodes().forEach {
        val dfs = DFS(grid)
        if (it.data != '0') return@forEach
        dfs.dfsSimple(grid.node2Id(it))
        val currentVisitedNodes = dfs.getCurrentVisitedIds()
        visitedNodes.addAll(currentVisitedNodes)
    }
    val ans = visitedNodes.count { grid.id2Node(it)?.data == '9' }
    println(ans)
    FXGraphics.grid = grid
    FXGraphics.visitedNodes = visitedNodes
    FXGraphics.nodeDistances = grid.ids2Nodes(visitedNodes).map { it.data.toString().toDouble() }
    Application.launch(FXGraphics()::class.java)
}
