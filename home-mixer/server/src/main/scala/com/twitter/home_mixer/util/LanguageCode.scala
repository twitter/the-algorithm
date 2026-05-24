package com.twitter.home_mixer.util

object LanguageCode {
  final val Japanese = "jp"
  final val English = "en"
  final val Unknown = "zxx"

  val AllowedLanguageCodes = Set(
    "art", // Emojis
    "qam", // Mentions
    "qct", // Cashtag
    "qht", // Hashtag
    "qme", // Multiple entities
    "qst", // Short text
    "und", // Undefined
    "zxx" // Links
  )

  val languageToISO: Map[String, String] = Map(
    "arabic" -> "ar",
    "danish" -> "da",
    "german" -> "de",
    "greek" -> "el",
    "english" -> "en",
    "esperanto" -> "eo",
    "spanish" -> "es",
    "persian" -> "fa",
    "finnish" -> "fi",
    "french" -> "fr",
    "hebrew" -> "he",
    "hungarian" -> "hu",
    "indonesian" -> "id",
    "icelandic" -> "is",
    "italian" -> "it",
    "japanese" -> "ja",
    "korean" -> "ko",
    "lithuanian" -> "lt",
    "dutch" -> "nl",
    "norwegian" -> "no",
    "polish" -> "pl",
    "portuguese" -> "pt",
    "russian" -> "ru",
    "swedish" -> "sv",
    "thai" -> "th",
    "urdu" -> "ur",
    "chinese" -> "zh",
    "turkish" -> "tr",
    "tagalog" -> "tl",
    "hindi" -> "hi",
    "malay" -> "ms",
    "amharic" -> "am",
    "bengali" -> "bn",
    "tibetan" -> "bo",
    "dhivehi" -> "dv",
    "gujarati" -> "gu",
    "armenian" -> "hy",
    "inuktitut" -> "iu",
    "georgian" -> "ka",
    "khmer" -> "km",
    "kannada" -> "kn",
    "lao" -> "lo",
    "malayalam" -> "ml",
    "myanmar" -> "my",
    "oriya" -> "or",
    "panjabi" -> "pa",
    "sinhala" -> "si",
    "tamil" -> "ta",
    "telugu" -> "te",
    "vietnamese" -> "vi",
    "bulgarian" -> "bg",
    "nepali" -> "ne",
    "estonian" -> "et",
    "haitian" -> "ht",
    "latvian" -> "lv",
    "slovak" -> "sk",
    "slovenian" -> "sl",
    "ukrainian" -> "uk",
    "basque" -> "eu",
    "bosnian" -> "bs",
    "catalan" -> "ca",
    "croatian" -> "hr",
    "czech" -> "cs",
    "hindi latin" -> "hi",
    "marathi" -> "mr",
    "pashto" -> "ps",
    "romanian" -> "ro",
    "serbian" -> "sr",
    "sindhi" -> "sd",
    "welsh" -> "cy",
    "uyghur" -> "ug"
  )
}
