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
input = new File(/C:\Users\jirka\Downloads\input.txt/)
lines = input.readLines().findAll()

PAIRS = [
        '(': ')',
        '[': ']',
        '{': '}',
        '<': '>'
]

OPENERS = PAIRS.keySet()

POINTS = [
        ')': 3,
        ']': 57,
        '}': 1197,
        '>': 25137
]

String findIllegalChar(String line) {
    def stack = new ArrayDeque<String>()
    for (found in line) {
        if (found in OPENERS) {
            stack.push(found)
        } else {
            def top = stack.pop()
            def expected = PAIRS[top]
            if (expected != found) {
                return found
            }
        }
    }
    return null
}

lines.collect { findIllegalChar(it) }
        .findAll()
        .collect { POINTS[it] }
        .sum()

