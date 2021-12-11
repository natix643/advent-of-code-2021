input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

pairs = lines.collate(2, 1, false)
pairs.collect { a, b -> (a as int) < (b as int) }
        .findAll()
        .size()
