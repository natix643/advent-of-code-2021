input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

distance = 0
depth = 0
aim = 0

lines.each {
    def parts = it.split()
    def direction = parts[0]
    def amount = parts[1] as int

    switch (direction) {
        case "forward":
            distance += amount
            depth += aim * amount
            break
        case "up":
            aim -= amount
            break
        case "down":
            aim += amount
            break
    }
}
println "distance = $distance"
println "depth = $depth"
println "resut = ${distance * depth}"
