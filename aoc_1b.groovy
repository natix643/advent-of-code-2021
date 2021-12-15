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

windows = lines.collate(3, 1, false)
sums = windows.collect {
    it*.toInteger().sum()
}
sums.collate(2, 1, false).count {
    a, b -> a < b
}
