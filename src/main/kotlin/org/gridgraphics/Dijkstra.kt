package org.gridgraphics

import java.util.*

class Dijkstra(private val graph: AdjacencyList) {
    constructor(grid: Grid) : this(grid.getAdjacencyList())
    val distance = DoubleArray(graph.size) { Double.POSITIVE_INFINITY }
    val parent = IntArray(graph.size) { -1 }

    private fun resetDistances() = distance.fill(Double.POSITIVE_INFINITY)
    private fun resetParents() = parent.fill(-1)

    fun dijkstra(start: Int) {
        resetDistances()
        resetParents()
        distance[start] = 0.0
        val visited = BooleanArray(graph.size)
        val pq = PriorityQueue<Edge> { a, b -> a.first.compareTo(b.first) }
        pq.add(Edge(0.0, start))
        while (pq.isNotEmpty()) {
            val u = pq.poll().second
            if (visited[u]) continue
            visited[u] = true
            graph[u].forEach { e ->
                val newDist = distance[u] + e.first
                val oldDist = distance[e.second]
                if (newDist < oldDist) {
                    distance[e.second] = newDist
                    parent[e.second] = u
                    if (!visited[e.second])
                        pq.add(Edge(newDist, e.second))
                }
            }
        }
    }
}