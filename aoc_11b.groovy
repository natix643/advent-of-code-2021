input = """
5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526
"""
input = """
4134384626
7114585257
1582536488
4865715538
5733423513
8532144181
1288614583
2248711141
6415871681
7881531438
"""
lines = input.readLines().findAll()

class Octopus {
    int x
    int y
    int energy

    String toString() {
        def e = energy >= 10 ? 'X' : energy.toString()
        return "([$y,$x]=$e)"
    }
}

class Grid {
    int height
    int width
    private List<List<Octopus>> matrix

    List<Octopus> getOctopuses() {
        return matrix.collectMany { it }
    }

    Grid(List<String> lines) {
        height = lines.size()
        width = lines[0].length()
        matrix = lines.indexed().collect { y, line ->
            line.toList().indexed().collect { x, value ->
                new Octopus(
                        x: x,
                        y: y,
                        energy: value as int
                )
            }
        }
    }

    String toString() {
        def out = new StringBuilder()
        for (line in matrix) {
            for (octopus in line) {
                def energy = octopus.energy
                out << (energy >= 10 ? 'X' : energy)
            }
            out << '\n'
        }
        return out.toString()
    }
}

List<Octopus> findNeighbors(Grid grid, Octopus octopus) {
    def x = octopus.x
    def y = octopus.y
    return [
            [x - 1, y - 1],
            [x, y - 1],
            [x + 1, y - 1],
            [x + 1, y],
            [x + 1, y + 1],
            [x, y + 1],
            [x - 1, y + 1],
            [x - 1, y]
    ].findAll { xx, yy ->
        xx >= 0 && xx < grid.width && yy >= 0 && yy < grid.height
    }.collect { xx, yy ->
        grid.matrix[yy][xx] as Octopus
    }
}

boolean step(Grid grid) {
    int flashes = 0
    def octopuses = grid.octopuses
    def queue = new ArrayDeque<Octopus>(octopuses)

    while (!queue.empty) {
        def next = queue.remove()
        switch (next.energy) {
            case { it >= 10 }:
                break
            case 9:
                next.energy++
                flashes++
                queue.addAll(findNeighbors(grid, next))
                break
            default:
                next.energy++
                break
        }
    }
    octopuses.findAll {
        it.energy >= 10
    }.each {
        it.energy = 0
    }
    return octopuses.every {
        it.energy == 0
    }
}

grid = new Grid(lines)
(1..Integer.MAX_VALUE).find {
    step(grid)
}
