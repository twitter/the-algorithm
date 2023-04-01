import re
from collections import namedtuple, Counter

CharRange = namedtuple("CharRange", "name start end")

NONCHAR = re.compile(r"\W+")


def charset_name(name, start, end):
    if name.lower() == "undefined":
        return "_".join([name.title(), start, end])
    else:
        return NONCHAR.sub("_", name.title())

CHARSET_RANGES = tuple(
    CharRange(charset_name(name, start, end), int(start, 16), int(end, 16))
    for start, end, name in (
        ("0000", "007F", "Basic Latin"),
        ("0080", "00FF", "C1 Controls and Latin-1 Supplement"),
        ("0100", "017F", "Latin Extended-A"),
        ("0180", "024F", "Latin Extended-B"),
        ("0250", "02AF", "IPA Extensions"),
        ("02B0", "02FF", "Spacing Modifier Letters"),
        ("0300", "036F", "Combining Diacritical Marks"),
        ("0370", "03FF", "Greek/Coptic"),
        ("0400", "04FF", "Cyrillic"),
        ("0500", "052F", "Cyrillic Supplement"),
        ("0530", "058F", "Armenian"),
        ("0590", "05FF", "Hebrew"),
        ("0600", "06FF", "Arabic"),
        ("0700", "074F", "Syriac"),
        ("0750", "077F", "Undefined"),
        ("0780", "07BF", "Thaana"),
        ("07C0", "08FF", "Undefined"),
        ("0900", "097F", "Devanagari"),
        ("0980", "09FF", "Bengali/Assamese"),
        ("0A00", "0A7F", "Gurmukhi"),
        ("0A80", "0AFF", "Gujarati"),
        ("0B00", "0B7F", "Oriya"),
        ("0B80", "0BFF", "Tamil"),
        ("0C00", "0C7F", "Telugu"),
        ("0C80", "0CFF", "Kannada"),
        ("0D00", "0DFF", "Malayalam"),
        ("0D80", "0DFF", "Sinhala"),
        ("0E00", "0E7F", "Thai"),
        ("0E80", "0EFF", "Lao"),
        ("0F00", "0FFF", "Tibetan"),
        ("1000", "109F", "Myanmar"),
        ("10A0", "10FF", "Georgian"),
        ("1100", "11FF", "Hangul Jamo"),
        ("1200", "137F", "Ethiopic"),
        ("1380", "139F", "Undefined"),
        ("13A0", "13FF", "Cherokee"),
        ("1400", "167F", "Unified Canadian Aboriginal Syllabics"),
        ("1680", "169F", "Ogham"),
        ("16A0", "16FF", "Runic"),
        ("1700", "171F", "Tagalog"),
        ("1720", "173F", "Hanunoo"),
        ("1740", "175F", "Buhid"),
        ("1760", "177F", "Tagbanwa"),
        ("1780", "17FF", "Khmer"),
        ("1800", "18AF", "Mongolian"),
        ("18B0", "18FF", "Undefined"),
        ("1900", "194F", "Limbu"),
        ("1950", "197F", "Tai Le"),
        ("1980", "19DF", "Undefined"),
        ("19E0", "19FF", "Khmer Symbols"),
        ("1A00", "1CFF", "Undefined"),
        ("1D00", "1D7F", "Phonetic Extensions"),
        ("1D80", "1DFF", "Undefined"),
        ("1E00", "1EFF", "Latin Extended Additional"),
        ("1F00", "1FFF", "Greek Extended"),
        ("2000", "206F", "General Punctuation"),
        ("2070", "209F", "Superscripts and Subscripts"),
        ("20A0", "20CF", "Currency Symbols"),
        ("20D0", "20FF", "Combining Diacritical Marks for Symbols"),
        ("2100", "214F", "Letterlike Symbols"),
        ("2150", "218F", "Number Forms"),
        ("2190", "21FF", "Arrows"),
        ("2200", "22FF", "Mathematical Operators"),
        ("2300", "23FF", "Miscellaneous Technical"),
        ("2400", "243F", "Control Pictures"),
        ("2440", "245F", "Optical Character Recognition"),
        ("2460", "24FF", "Enclosed Alphanumerics"),
        ("2500", "257F", "Box Drawing"),
        ("2580", "259F", "Block Elements"),
        ("25A0", "25FF", "Geometric Shapes"),
        ("2600", "26FF", "Miscellaneous Symbols"),
        ("2700", "27BF", "Dingbats"),
        ("27C0", "27EF", "Miscellaneous Mathematical Symbols-A"),
        ("27F0", "27FF", "Supplemental Arrows-A"),
        ("2800", "28FF", "Braille Patterns"),
        ("2900", "297F", "Supplemental Arrows-B"),
        ("2980", "29FF", "Miscellaneous Mathematical Symbols-B"),
        ("2A00", "2AFF", "Supplemental Mathematical Operators"),
        ("2B00", "2BFF", "Miscellaneous Symbols and Arrows"),
        ("2C00", "2E7F", "Undefined"),
        ("2E80", "2EFF", "CJK Radicals Supplement"),
        ("2F00", "2FDF", "Kangxi Radicals"),
        ("2FE0", "2FEF", "Undefined"),
        ("2FF0", "2FFF", "Ideographic Description Characters"),
        ("3000", "303F", "CJK Symbols and Punctuation"),
        ("3040", "309F", "Hiragana"),
        ("30A0", "30FF", "Katakana"),
        ("3100", "312F", "Bopomofo"),
        ("3130", "318F", "Hangul Compatibility Jamo"),
        ("3190", "319F", "Kanbun (Kunten)"),
        ("31A0", "31BF", "Bopomofo Extended"),
        ("31C0", "31EF", "Undefined"),
        ("31F0", "31FF", "Katakana Phonetic Extensions"),
        ("3200", "32FF", "Enclosed CJK Letters and Months"),
        ("3300", "33FF", "CJK Compatibility"),
        ("3400", "4DBF", "CJK Unified Ideographs Extension A"),
        ("4DC0", "4DFF", "Yijing Hexagram Symbols"),
        ("4E00", "9FAF", "CJK Unified Ideographs"),
        ("9FB0", "9FFF", "Undefined"),
        ("A000", "A48F", "Yi Syllables"),
        ("A490", "A4CF", "Yi Radicals"),
        ("A4D0", "ABFF", "Undefined"),
        ("AC00", "D7AF", "Hangul Syllables"),
        ("D7B0", "D7FF", "Undefined"),
        ("D800", "DBFF", "High Surrogate Area"),
        ("DC00", "DFFF", "Low Surrogate Area"),
        ("E000", "F8FF", "Private Use Area"),
        ("F900", "FAFF", "CJK Compatibility Ideographs"),
        ("FB00", "FB4F", "Alphabetic Presentation Forms"),
        ("FB50", "FDFF", "Arabic Presentation Forms-A"),
        ("FE00", "FE0F", "Variation Selectors"),
        ("FE10", "FE1F", "Undefined"),
        ("FE20", "FE2F", "Combining Half Marks"),
        ("FE30", "FE4F", "CJK Compatibility Forms"),
        ("FE50", "FE6F", "Small Form Variants"),
        ("FE70", "FEFF", "Arabic Presentation Forms-B"),
        ("FF00", "FFEF", "Halfwidth and Fullwidth Forms"),
        ("FFF0", "FFFF", "Specials"),
        ("10000", "1007F", "Linear B Syllabary"),
        ("10080", "100FF", "Linear B Ideograms"),
        ("10100", "1013F", "Aegean Numbers"),
        ("10140", "102FF", "Undefined"),
        ("10300", "1032F", "Old Italic"),
        ("10330", "1034F", "Gothic"),
        ("10380", "1039F", "Ugaritic"),
        ("10400", "1044F", "Deseret"),
        ("10450", "1047F", "Shavian"),
        ("10480", "104AF", "Osmanya"),
        ("104B0", "107FF", "Undefined"),
        ("10800", "1083F", "Cypriot Syllabary"),
        ("10840", "1CFFF", "Undefined"),
        ("1D000", "1D0FF", "Byzantine Musical Symbols"),
        ("1D100", "1D1FF", "Musical Symbols"),
        ("1D200", "1D2FF", "Undefined"),
        ("1D300", "1D35F", "Tai Xuan Jing Symbols"),
        ("1D360", "1D3FF", "Undefined"),
        ("1D400", "1D7FF", "Mathematical Alphanumeric Symbols"),
        ("1D800", "1FFFF", "Undefined"),
        ("20000", "2A6DF", "CJK Unified Ideographs Extension B"),
        ("2A6E0", "2F7FF", "Undefined"),
        ("2F800", "2FA1F", "CJK Compatibility Ideographs Supplement"),
        ("2FAB0", "DFFFF", "Unused"),
        ("E0000", "E007F", "Tags"),
        ("E0080", "E00FF", "Unused"),
        ("E0100", "E01EF", "Variation Selectors Supplement"),
        ("E01F0", "EFFFF", "Unused"),
        ("F0000", "FFFFD", "Supplementary Private Use Area-A"),
        ("FFFFE", "FFFFF", "Unused"),
        ("100000", "10FFFD", "Supplementary Private Use Area-B"),
    )
)


def symbology(s):
    """Return a count of what unicode charsets the string contains."""
    symbols = sorted(ord(c) for c in s)
    current_charset = 0
    char_tally = Counter()
    for symbol in symbols:
        while CHARSET_RANGES[current_charset].end < symbol:
            current_charset += 1
        if CHARSET_RANGES[current_charset].start <= symbol:
            name = CHARSET_RANGES[current_charset].name
        else:
            name = "Unknown"
        char_tally[name] += 1

    return char_tally


def charset_summary(s, prefix=""):
    res = {}
    charsets = symbology(s)
    if charsets:
        res["charset"] = charsets.most_common(1)[0][0]
        res["all_charsets"] = dict(charsets.most_common())
    return res
