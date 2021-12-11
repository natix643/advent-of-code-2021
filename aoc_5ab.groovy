import static java.lang.Math.max
import static java.lang.Math.min

input = """
0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2
"""
input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

class Point {
    int x
    int y

    String toString() {
        "$x,$y"
    }
}

class Diagram {
    final int height
    final int width
    final List<List<Integer>> positions

    Diagram(Map args) {
        this.height = args.height as int
        this.width = args.width as int
        this.positions = (new int[height][width] as List).collect { it.toList() }
    }

    String toString() {
        def out = new StringBuilder()
        for (y in (0..<positions.size())) {
            for (x in (0..<positions.get(y).size())) {
                def position = positions.get(y).get(x)
                out << (position ?: '.')
            }
            out << "\n"
        }
        return out.toString()
    }
}

allPoints = lines.collectMany { line ->
    def (start, end) = line.split(' -> ').collect {
        def (x, y) = it.split(",")*.toInteger()
        new Point(x: x, y: y)
    }
    if (start.x == end.x) {
        def min = min(start.y, end.y)
        def max = max(start.y, end.y)
        return (min..max).collect {
            new Point(x: start.x, y: it)
        }
    } else if (start.y == end.y) {
        def min = min(start.x, end.x)
        def max = max(start.x, end.x)
        return (min..max).collect {
            new Point(x: it, y: start.y)
        }
    } else {
        if (start.x > end.x && start.y > end.y || start.x < end.x && start.y < end.y) {
            def minX = min(start.x, end.x)
            def diff = max(start.x, end.x) - minX
            def minY = min(start.y, end.y)
            return (0..diff).collect {
                new Point(x: minX + it, y: minY + it)
            }
        } else {
            def minX = min(start.x, end.x)
            def diff = max(start.x, end.x) - minX
            def maxY = max(start.y, end.y)
            return (0..diff).collect {
                new Point(x: minX + it, y: maxY - it)
            }
        }
    }
}

height = allPoints.max { it.y }.y + 1
width = allPoints.max { it.x }.x + 1

diagram = new Diagram(height: height, width: width)

allPoints.each {
    def position = diagram.positions[it.y][it.x]
    if (position) {
        (diagram.positions[it.y][it.x])++
    } else {
        diagram.positions[it.y][it.x] = 1
    }
}

diagram.positions.flatten().count { it >= 2 }
