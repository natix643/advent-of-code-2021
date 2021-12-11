input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

windows = lines.collate(3, 1, false)
summedWindows = windows.collect {
    it*.toInteger().sum()
}
windowPairs = summedWindows.collate(2, 1, false)
windowPairs.collect { a, b -> a < b }
        .findAll()
        .size()
