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

numbers = lines.collect {
    it.split("")*.toInteger()
}

List<Integer> filterOxygen(List<List<Integer>> numbers, int index) {
    if (numbers.size() == 1 || index == numbers.size() - 1) {
        return numbers[0]
    }
    def zeros = numbers.findAll { it[index] == 0 }
    def ones = numbers.findAll { it[index] == 1 }
    def nextNumbers = [ones, zeros].max { it.size() }
    return filterOxygen(nextNumbers, index + 1)
}

List<Integer> filterCo2(List<List<Integer>> numbers, int index) {
    if (numbers.size() == 1 || index == numbers.size() - 1) {
        return numbers[0]
    }
    def zeros = numbers.findAll { it[index] == 0 }
    def ones = numbers.findAll { it[index] == 1 }
    def nextNumbers = [zeros, ones].min { it.size() }
    return filterCo2(nextNumbers, index + 1)
}

oxygenDigits = filterOxygen(numbers, 0)
co2Digits = filterCo2(numbers, 0)

oxygen = Integer.parseInt(oxygenDigits.join(), 2)
co2 = Integer.parseInt(co2Digits.join(), 2)

oxygen * co2
