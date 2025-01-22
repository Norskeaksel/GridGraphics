package org.gridgraphics

fun getPath(destination: Int, parent: IntArray): List<Int> {
    val path = mutableListOf<Int>()
    var current = destination
    while (parent[current] != -1) {
        path.add(current)
        current = parent[current]
    }
    path.add(current)
    return path.reversed()
}