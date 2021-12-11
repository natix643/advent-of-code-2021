input = """
2199943210
3987894921
9856789892
8767896789
9899965678
"""
input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

class Color {
    static ANSI_RESET = "\u001B[0m"
    static ANSI_BLACK = "\u001B[30m"
    static ANSI_RED = "\u001B[31m"
    static ANSI_GREEN = "\u001B[32m"
    static ANSI_YELLOW = "\u001B[33m"
    static ANSI_BLUE = "\u001B[34m"
    static ANSI_PURPLE = "\u001B[35m"
    static ANSI_CYAN = "\u001B[36m"
    static ANSI_WHITE = "\u001B[37m"

    static PALETTE = [
            ANSI_RED,
            ANSI_GREEN,
            ANSI_YELLOW,
            ANSI_BLUE,
            ANSI_PURPLE,
            ANSI_CYAN
    ]

    static grey(int lightness) {
        return "\u001b[38;5;${237 + 2 * lightness}m"
    }
}

class Point {
    int x
    int y
    int value
    boolean visited = false
    String color
}

class Heightmap {
    List<List<Point>> points
    int height
    int width

    String toString() {
        def out = new StringBuilder()
        for (y in (0..<points.size())) {
            for (x in (0..<points[y].size())) {
                def point = points[y][x]
                if (point) {
                    out << "${Color.grey(point.value)}${point.value}${Color.ANSI_RESET}"
                } else {
                    out << 'X'
                }
//                if (point != null && point.value < 9) {
//                    if (point.color) {
//                        out << "${point.color}${point.value}${Color.ANSI_RESET}"
//                    } else {
//                        out << point.value
//                    }
//                } else if (point?.value == 9) {
//                    out << ' '
//                } else {
//                    out << 'X'
//                }
            }
            out << '\n'
        }
        return out
    }
}

Heightmap create(List<String> lines) {
    def height = lines.size()
    def width = lines[0].size()

    def heightmap = new Heightmap(
            points: new Integer[height][width].toList().collect { it.toList() },
            height: height,
            width: width
    )
    for (y in (0..<height)) {
        for (x in (0..<width)) {
            heightmap.points[y][x] = new Point(
                    x: x,
                    y: y,
                    value: lines[y][x].toInteger()
            )
        }
    }
    return heightmap
}

List<Point> neighbors(Heightmap heightmap, Point point) {
    def x = point.x
    def y = point.y
    def positions = [
            [y - 1, x],
            [y + 1, x],
            [y, x - 1],
            [y, x + 1]
    ].findAll { yy, xx ->
        yy >= 0 && yy < heightmap.height &&
                xx >= 0 && xx < heightmap.width
    }
    return positions.collect { yy, xx ->
        heightmap.points[yy][xx]
    }.findAll {
        it.value < 9 && !it.visited
    }
}

List<Integer> findBasinSizes(Heightmap heightmap) {
    def basins = []

    for (y in (0..<heightmap.height)) {
        for (x in (0..<heightmap.width)) {
            def point = heightmap.points[y][x]
            if (point.value == 9 || point.visited) {
                continue
            }

            int basin = 0
            def queue = new ArrayDeque<Point>([point])

            while (!queue.empty) {
                def next = queue.remove()
                if (!next.visited) {
                    next.visited = true
                    next.color = Color.PALETTE[basins.size() % Color.PALETTE.size()]
                    basin++
                    queue.addAll(neighbors(heightmap, next))
                }
            }
            basins << basin
        }
    }
    return basins
}

heightmap = create(lines)
println "$heightmap"

def result = findBasinSizes(heightmap)
        .toSorted()
        .reverse()
        .take(3)
        .inject { a, b -> a * b }

println "$heightmap"
println result

