package org.gridgraphics

class BFS (val graph: AdjacencyList) {
    constructor(grid: Grid) : this(grid.getAdjacencyList())

    val size = graph.size
    var visited = BooleanArray(size)
    val distances = DoubleArray(size)
    var currentVisited = mutableListOf<Int>()
    var currentVisitedDistances = mutableListOf<Int>()

    fun bfsIterative(startIds: List<Int>) {
        currentVisited.clear()
        distances.fill(-1.0)
        val queue = ArrayDeque<Int>()
        startIds.forEach {
            queue.add(it)
            distances[it] = 0.0
        }
        while (queue.isNotEmpty()) {
            val currentId = queue.first()
            queue.removeFirst()
            if(visited[currentId])
                continue
            visited[currentId] = true
            currentVisited.add(currentId)
            val currentDistance = distances[currentId]
            currentVisitedDistances.add(currentDistance.toInt())
            graph[currentId].forEach { (d, v) ->
                if(!visited[v]) {
                    queue.add(v)
                    distances[v] = currentDistance + d
                }
            }
        }
    }

    fun bfsRecursive(startIds: List<Int>) {
        currentVisited.clear()
        distances.fill(-1.0)
        startIds.forEach {
            distances[it] = 0.0
        }
        DeepRecursiveFunction<ArrayDeque<Int>, Unit> { queue ->
            if(queue.isEmpty())
                return@DeepRecursiveFunction
            val id = queue.first()
            queue.removeFirst()
            visited[id] = true
            currentVisited.add(id)
            graph[id].forEach { (d, v) ->
                if(!visited[v]) {
                    queue.add(v)
                    distances[v] = distances[id] + d
                }
            }
            this.callRecursive(queue)
        }.invoke(ArrayDeque(startIds))
    }
}
