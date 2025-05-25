package org.gridgraphics.temperatureVisualisation

import graphClasses.Grid
import javafx.application.Application
import org.gridgraphics.FXGraphics
import java.io.File

fun main() {
    // Source: https://seklima.met.no/
    val path = "src/main/kotlin/org/gridgraphics/temperatureVisualisation/TemperatureData/OsloTromsoTemperatures2024.csv"
    // Read the data from the csv file
    val csvFile = File(path)
    val grid = Grid(31, 25)
    val visitedIds = mutableListOf<Int>()
    val temperatures = mutableListOf<Double>()
    csvFile.useLines { lines ->
        lines.forEachIndexed { i, line ->
            if (i == 0) return@forEachIndexed
            val data = line.split(",")
            val date = data[2]
            val temperature = data[4].toDouble()
            var (x, y) = date.split(".2024").first().split(".").map { it.toInt() - 1 }
            if (i % 2 == 0) {
                y += 13
            }
            println("$x,$y: $temperature")
            visitedIds.add(grid.xy2Id(x, y)!!)
            temperatures.add(temperature)
        }
    }
    val minTemp = temperatures.min()
    val maxTemp = temperatures.max()
    val normalizedTemps = temperatures.map { (it - maxTemp) / (minTemp - maxTemp) }
    FXGraphics.grid = grid
    FXGraphics.sceneWithOverride = 1240.0
    FXGraphics.visitedNodes = visitedIds
    FXGraphics.nodeDistances = normalizedTemps
    FXGraphics.windowTitle = "Normalized temperatures in Oslo vs Tromsø, 2024"
    FXGraphics.startPaused = true
    Application.launch(FXGraphics()::class.java)
}