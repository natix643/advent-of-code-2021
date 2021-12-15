input = """
199
200
208
210
200
207
240
269
260
263
"""
input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

lines.collate(2, 1, false)
        .collect { it*.toInteger() }
        .count { a, b -> a < b }
