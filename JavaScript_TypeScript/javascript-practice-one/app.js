const ContentType = {
    HTML: "HTML",
    CSS: "CSS",
    TEXT: "TEXT",
}

function parseCss(css) {
    let cssTargets = new Map();
    let start = 0
    for(let i = 0; i < css.length; ++i) {
        if(css[i] == '{') {
            let target = css.substring(start, i)
            if(cssTargets.has(target)) {
                cssTargets.set(target, cssTargets.get(target) + 1)
            } else { 
                cssTargets.set(target, 1)
            }
        } else if(css[i] == '}') {
            start = i+2
        }
    }
    return Object.fromEntries(cssTargets)
}

function parseHtml(html) {
    let tags = new Map();
    for(let i = 0; i < html.length; ++i) {
        if(html[i] == '<') {
            if(html[i+1] == '/') continue
            let start = i
            while(html[i] != '>') ++i
            let tag = html.substring(start+1, i)
            if(tags.has(tag)) {
                tags.set(tag, tags.get(tag) + 1)
            } else { 
                tags.set(tag, 1)
            }
        }
    }
    return Object.fromEntries(tags)
}

function parseText(text) {
    let count = 0
    for(let i = 0; i < text.length; ++i) {
        if(text[i] == '\n') {
            count++;
        }
    }
    return count
}

function analyzeContent(stringToAnalyze) {
    if (stringToAnalyze.includes('{') && stringToAnalyze.includes('}')) {
       return cssObject = {
            contentType: ContentType.CSS,
            cssTargets: parseCss(stringToAnalyze)
       } 
    } else if (stringToAnalyze.includes('<') && stringToAnalyze.includes('>')) {
       return htmlObject = {
            contentType: ContentType.HTML,
            tags: parseHtml(stringToAnalyze)
       } 
    } else {
        return textObject = {
            contentType: ContentType.TEXT,
            lineNumber: parseText(stringToAnalyze)
        }
    }
}

let textResult = analyzeContent("this is a test\nSeems to work")
let cssResult = analyzeContent("body{blabla} a{color:#fff} a{ padding:0}")
let htmlResult = analyzeContent("<html><div></div><div></div></html>")