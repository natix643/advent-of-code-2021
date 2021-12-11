input = """
[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]
"""
//input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

PAIRS = [
        '(': ')',
        '[': ']',
        '{': '}',
        '<': '>'
]

OPENERS = PAIRS.keySet()

POINTS = [
        ')': 1,
        ']': 2,
        '}': 3,
        '>': 4
]

List<String> completeSequence(String line) {
    def stack = new ArrayDeque<String>()
    for (found in line) {
        if (found in OPENERS) {
            stack.push(found)
        } else {
            def top = stack.pop()
            def expected = PAIRS[top]
            if (expected != found) {
                return []
            }
        }
    }
    return stack.toList().collect {
        PAIRS[it]
    }
}

long score(List<String> chars) {
    long score = 0
    for (c in chars) {
        score *= 5
        score += POINTS[c]
    }
    return score
}

scores = lines.collect { completeSequence(it) }
        .findAll()
        .collect { score(it) }
        .toSorted()
println scores[scores.size() / 2 as int]
