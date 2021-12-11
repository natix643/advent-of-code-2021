input = """
2199943210
3987894921
9856789892
8767896789
9899965678
"""
//input = new File(/C:\Users\jirka\Downloads\input.txt/)

lines = input.readLines().findAll()

class Heightmap {
    final List<List<Integer>> points
    final int height
    final int width

    Heightmap(int height, int width) {
        def array = new Integer[height][width]
        this.points = array.toList()
        this.height = height
        this.width = width
    }

    String toString() {
        def out = new StringBuilder()
        for (y in (0..<points.size())) {
            for (x in (0..<points[y].size())) {
                if (points[y][x] < 9) {
                    out << (points[y][x])
                } else {
                    out << ' '
                }
            }
            out << '\n'
        }
        return out
    }
}

def isLowPoint(Heightmap map, int y, int x) {
    def neighbors = [
            [y - 1, x],
            [y + 1, x],
            [y, x - 1],
            [y, x + 1]
    ].findAll { yy, xx ->
        yy >= 0 && yy < map.height &&
                xx >= 0 && xx < map.width
    }.collect { yy, xx ->
        map.points[yy][xx]
    }
    return neighbors.every { it > map.points[y][x] }
}

height = lines.size()
width = lines[0].size()

def heightmap = new Heightmap(height, width)

for (y in (0..<height)) {
    for (x in (0..<width)) {
        heightmap.points[y][x] = (lines[y][x]).toInteger()
    }
}

println "\n$heightmap"

lowPoints = (0..<height).collectMany { y ->
    (0..<width).collectMany { x ->
        if (isLowPoint(heightmap, y, x)) {
            [heightmap.points[y][x]]
        } else []
    }
}

riskSum = lowPoints.collect { it + 1 }.sum()
riskSum
