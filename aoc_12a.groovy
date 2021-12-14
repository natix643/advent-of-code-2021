import groovy.transform.EqualsAndHashCode

input = """
start-A
start-b
A-c
A-b
b-d
A-end
b-end
"""

input = """
fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW
"""
input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

@EqualsAndHashCode(includes = 'name')
class Cave {
    String name
    Set<Cave> neighbors = []

    Cave(String name) {
        this.name = name
    }

    boolean isBig() {
        name.charAt(0).isUpperCase()
    }

    boolean isStart() {
        name == 'start'
    }

    boolean isEnd() {
        name == 'end'
    }

    String toString() {
        "($name -> ${neighbors*.name})"
    }
}

class CaveMap {
    Cave start = new Cave('start')
    Cave end = new Cave('end')
    Map<String, Cave> caves = [
            start: start,
            end  : end
    ]

    String toString() {
        caves.values().join("\n")
    }

    static CaveMap parse(List<String> lines) {
        def map = new CaveMap()
        lines.each { line ->
            def (from, to) = line.split("-").collect { name ->
                map.caves.putIfAbsent(name, new Cave(name))
                map.caves[name]
            }
            from.neighbors << to
            to.neighbors << from
        }
        return map
    }
}

class Path {
    Cave current
    Map<Cave, Integer> history = [:]

    Path append(Cave newCave) {
        def currentVisits = history[current] ?: 0
        return new Path(
                current: newCave,
                history: history + [(current): currentVisits + 1]
        )
    }

    Set<Cave> findNextCaves() {
        return current.neighbors.findAll {
            !it.start
        }.findAll {
            def visits = history[it] ?: 0
            it.isBig() || visits < 1
        }
    }

    String toString() {
        def history = history.collectEntries { cave, visits ->
            [(cave.name): visits]
        }
        return "($current.name >> $history)"
    }
}

List<Path> findAllPaths(CaveMap map) {
    def paths = []
    def queue = new ArrayDeque<Path>([
            new Path(current: map.start)
    ])

    while (!queue.empty) {
        def path = queue.remove()
        if (path.current == map.end) {
            paths.add(path)
        } else {
            def nextPaths = path.findNextCaves().collect {
                path.append(it)
            }
            queue.addAll(nextPaths)
        }
    }
    return paths
}

map = CaveMap.parse(lines)
findAllPaths(map).size()
