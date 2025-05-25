# GridGraphics 

This repo uses javafx to visualize grids and paths taken through them.
Colors are used to represent the distance from the start tile.
The main file is the [FXGraphics.kt](src/main/kotlin/org/gridgraphics/FXGraphics.kt) class.
Packages under [gridgraphics](src/main/kotlin/org/gridgraphics) contain examples of grid visualizations with the FXGraphics class.


## Dependencies:
The repo contains classes from a different repo.
To run the code, you need to have cloned my [GraphLibrary](https://github.com/Norskeaksel/GraphLibrary) and added the graphClasses module from that project as a source.
This can be done in Intellij by going to project settings and Importing the module: 

![Screenshot from 2025-02-08 15-42-08](https://github.com/user-attachments/assets/1d1c4153-0c98-4ac8-a611-bbbeb03de88a)

## Example usage:

```kotlin
import graphClasses.BFS
import graphClasses.Grid
import graphClasses.getPath
import javafx.application.Application

fun main() {
    val gridWidth = 5
    val gridHeight = 5
    val grid = Grid(gridWidth, gridHeight)
    grid.connectGridDefault()
    val bfs = BFS(grid)
    val bfsStartIds = listOf(
        0,
        gridWidth - 1,
        grid.xy2Id(0, gridHeight - 1)!!,
        gridWidth * gridHeight - 1
    )
    val goalId = gridWidth * gridHeight / 2
    bfs.bfsIterative(bfsStartIds)
    val path = getPath(goalId, bfs.parents)
    FXGraphics.grid = grid
    FXGraphics.visitedNodes = bfs.currentVisited
    FXGraphics.nodeDistances = bfs.currentVisitedDistances
    FXGraphics.finalPath = path
    Application.launch(FXGraphics()::class.java)
}
```
This code can also be found and ran in the [ExampleUsage.kt](src/main/kotlin/org/gridgraphics/ExampleUsage.kt) file.
The following is a gif of the vizualization produced by the code above:
![Alt Text](ExampleBFSVizualisation.gif)