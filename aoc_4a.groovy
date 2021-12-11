import groovy.transform.ToString

//input = """7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
//
//22 13 17 11  0
// 8  2 23  4 24
//21  9 14 16  7
// 6 10  3 18  5
// 1 12 20 15 19
//
// 3 15  0  2 22
// 9 18 13 17  5
//19  8  7 25 23
//20 11 10 24  4
//14 21 16 12  6
//
//14 21 17 24  4
//10 16 15  9 19
//18  8 23 26 20
//22 11 13  6  5
// 2  0 12  3  7"""
input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines()

class Tile {
    int number
    boolean drawn = false

    String toString() {
        return drawn ? "$number*" : number
    }
}

@ToString
class Board {
    List<List<Tile>> tiles = [
            [null, null, null, null, null],
            [null, null, null, null, null],
            [null, null, null, null, null],
            [null, null, null, null, null],
            [null, null, null, null, null]
    ]

    String toString() {
        return tiles.join("\n")
    }
}

void drawn(Board board, int draw) {
    board.tiles
            .collectMany { it }
            .find { it.number == draw }?.drawn = true
}

boolean bingo(Board board) {
    def anyRow = (0..<5).any { rowIndex ->
        def row = board.tiles[rowIndex]
        row.every { it.drawn }
    }
    def anyColumn = (0..<5).any { colIndex ->
        def column = board.tiles.collect { it[colIndex] }
        column.every { it.drawn }
    }
    return anyRow || anyColumn
}

int score(Board board, int currentDraw) {
    def unDrawn = board.tiles
            .collectMany { it }
            .findAll { !it.drawn }
    return unDrawn*.number.sum() * currentDraw
}

draws = lines[0].split(",")*.toInteger()
List<Board> boards = []

lines = lines.drop(1)
while (!lines.empty) {
    def nextLines = lines.take(6)
    lines = lines.drop(6)

    def board = new Board()
    nextLines.drop(1).eachWithIndex { line, i ->
        line.split()*.toInteger().eachWithIndex { number, j ->
            board.tiles[i][j] = new Tile(number: number)
        }
    }
    boards << board
}

for (draw in draws) {
    boards.each {
        drawn(it, draw)
    }

    def winner = boards.find {
        bingo(it)
    }
    if (winner) {
        println("winner:\n$winner")
        println("score: ${score(winner, draw)}")
        break
    }
}

