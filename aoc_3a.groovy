//input = """
//00100
//11110
//10110
//10111
//10101
//01111
//00111
//11100
//10000
//11001
//00010
//01010
//"""
input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

digitsCount = lines[0].length()
accumulator = Collections.nCopies(digitsCount, 0).toList()

lines.each {
    def digits = it.split("")*.toInteger()
    digits.eachWithIndex { d, i ->
        if (d == 1) {
            (accumulator[i])++
        } else {
            (accumulator[i])--
        }
    }
}

gammaDigits = accumulator.collect { it > 0 ? 1 : 0 }
epsilonDigits = accumulator.collect { it > 0 ? 0 : 1 }

gamma = Integer.parseInt(gammaDigits.join(), 2)
epsilon = Integer.parseInt(epsilonDigits.join(), 2)

gamma * epsilon
