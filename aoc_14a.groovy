input = """
NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C
"""
input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

template = lines.first()
rules = lines.drop(1).collectEntries { line ->
    def (from, to) = line.split(' -> ')
    [(from): to]
}

String step(String template) {
    return template.toList().collate(2, 1, false).collect { first, second ->
        def toInsert = rules["$first$second"]
        "$first$toInsert$second"
    }.inject { accumulator, next ->
        accumulator + next.substring(1)
    }
}

polymer = (1..10).inject(template) { accumulator, i ->
    step(accumulator)
}

counts = polymer.toList().groupBy { it }
        .collectEntries { k, v -> [(k): v.size()] }
min = counts.min { it.value }.value
max = counts.max { it.value }.value
max - min
