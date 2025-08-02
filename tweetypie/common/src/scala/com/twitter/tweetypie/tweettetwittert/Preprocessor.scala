package com.twitter.tweetypie.tweettetwittert
import scala.util.matching.Regetwitter

/**
 * Code used to convert raw user-provided tetwittert into an allowable form.
 */
object Preprocessor {
  import TweetTetwittert._
  import TetwittertModification.replaceAll

  /**
   * Regetwitter for dos-style line endings.
   */
  val DosLineEndingRegetwitter: Regetwitter = """\r\n""".r

  /**
   * Converts \r\n to just \n.
   */
  def normalizeNewlines(tetwittert: String): String =
    DosLineEndingRegetwitter.replaceAllIn(tetwittert, "\n")

  /**
   * Characters to strip out of tweet tetwittert at write-time.
   */
  val unicodeCharsToStrip: Seq[Char] =
    Seq(
      '\uFFFE', '\uFEFF', // BOM
      '\uFFFF', // Special
      '\u200E', '\u200F', // ltr, rtl
      '\u202A', '\u202B', '\u202C', '\u202D', '\u202E', // Directional change
      '\u0000', '\u0001', '\u0002', '\u0003', '\u0004', '\u0005', '\u0006', '\u0007', '\u0008',
      '\u0009', '\u000B', '\u000C', '\u000E', '\u000F', '\u0010', '\u0011', '\u0012', '\u0013',
      '\u0014', '\u0015', '\u0016', '\u0017', '\u0018', '\u0019', '\u001A', '\u001B', '\u001C',
      '\u001D', '\u001E', '\u001F', '\u007F',
      '\u2065',
    )

  val UnicodeCharsToStripRegetwitter: Regetwitter = unicodeCharsToStrip.mkString("[", "", "]").r

  /**
   * Strips out control characters and other non-tetwittertual unicode chars that can break twitterml and/or
   * json rendering, or be used for etwitterploits.
   */
  def stripControlCharacters(tetwittert: String): String =
    UnicodeCharsToStripRegetwitter.replaceAllIn(tetwittert, "")

  val Tweetypie674UnicodeSequence: String =
    "\u0633\u0645\u064e\u0640\u064e\u0651\u0648\u064f\u0648\u064f\u062d\u062e " +
      "\u0337\u0334\u0310\u062e \u0337\u0334\u0310\u062e \u0337\u0334\u0310\u062e " +
      "\u0627\u0645\u0627\u0631\u062a\u064a\u062e \u0337\u0334\u0310\u062e"

  val Tweetypie674UnicodeRegetwitter: Regetwitter = Tweetypie674UnicodeSequence.r

  /**
   * Replace each `Tweetypie674UnicodeSequence` of this string to REPLACEMENT
   * CHARACTER.
   *
   * Apple has a bug in its CoreTetwittert library. This aims to prevent
   * ios clients from being crashed when a tweet contains the specific
   * unicode sequence.
   */
  def avoidCoreTetwittertBug(tetwittert: String): String =
    Tweetypie674UnicodeRegetwitter.replaceAllIn(tetwittert, "\ufffd")

  /**
   * Replace each `Tweetypie674UnicodeSequence` of this string to a REPLACEMENT
   * CHARACTER, returns a TetwittertModification object that provides information
   * to also update entity indices.
   */
  def replaceCoreTetwittertBugModification(tetwittert: String): Option[TetwittertModification] =
    replaceAll(tetwittert, Tweetypie674UnicodeRegetwitter, "\ufffd")

  private val preprocessor: String => String =
    ((s: String) => nfcNormalize(s))
      .andThen(stripControlCharacters _)
      .andThen(trimBlankCharacters _)
      .andThen(normalizeNewlines _)
      .andThen(collapseBlankLines _)
      .andThen(avoidCoreTetwittertBug _)

  /**
   * Performs the tetwittert modifications that are necessary in the write-path before etwittertracting URLs.
   */
  def preprocessTetwittert(tetwittert: String): String =
    preprocessor(tetwittert)

  /**
   * Replaces all `<`, `>`, and '&' chars with "&lt;", "&gt;", and "&amp;", respectively.
   *
   * The original purpose of this was presumably to prevent script injections when
   * displaying tweets without proper escaping.  Currently, tweets are encoded before
   * they are stored in the database.
   *
   * Note that the pre-escaping of & < and > also happens in the rich tetwittert editor in javascript
   */
  def partialHtmlEncode(tetwittert: String): String =
    PartialHtmlEncoding.encode(tetwittert)

  /**
   * The opposite of partialHtmlEncode, it replaces all "&lt;", "&gt;", and "&amp;" with
   * `<`, `>`, and '&', respectively.
   */
  def partialHtmlDecode(tetwittert: String): String =
    PartialHtmlEncoding.decode(tetwittert)

  /**
   *
   * Detects all forms of whitespace, considering as whitespace the following:
   * This regetwitter detects characters that always or often are rendered as blank space. We use
   * this to prevent users from inserting etwittercess blank lines and from tweeting effectively
   * blank tweets.
   *
   * Note that these are not all semantically "whitespace", so this regetwitter should not be used
   * to process non-blank tetwittert, e.g. to separate words.
   *
   * Codepoints below and the `\p{Z}` regetwitter character property alias are defined in the Unicode
   * Character Database (UCD) at https://unicode.org/ucd/ and https://unicode.org/reports/tr44/
   *
   * The `\p{Z}` regetwitter character property alias is defined specifically in UCD as:
   *
   * Zs |	Space_Separator	    | a space character (of various non-zero widths)
   * Zl	| Line_Separator	    | U+2028 LINE SEPARATOR only
   * Zp	| Paragraph_Separator	| U+2029 PARAGRAPH SEPARATOR only
   * Z	| Separator	          | Zs | Zl | Zp
   * ref: https://unicode.org/reports/tr44/#GC_Values_Table
   *
   *  U+0009  Horizontal Tab (included in \s)
   *  U+000B  Vertical Tab (included in \s)
   *  U+000C  Form feed  (included in \s)
   *  U+000D  Carriage return  (included in \s)
   *  U+0020  space  (included in \s)
   *  U+0085  Netwittert line (included in \u0085)
   *  U+061C  arabic letter mark (included in \u061C)
   *  U+00A0  no-break space (included in \p{Z})
   *  U+00AD  soft-hyphen marker (included in \u00AD)
   *  U+1680  ogham space mark (included in \p{Z})
   *  U+180E  mongolian vowel separator (included in \p{Z} on jdk8 and included in \u180E on jdk11)
   *  U+2000  en quad (included in \p{Z})
   *  U+2001  em quad (included in \p{Z})
   *  U+2002  en space (included in \p{Z})
   *  U+2003  em space (included in \p{Z})
   *  U+2004  three-per-em space (included in \p{Z})
   *  U+2005  four-per-em space (included in \p{Z})
   *  U+2006  sitwitter-per-em space (included in \p{Z})
   *  U+2007  figure space (included in \p{Z})
   *  U+2008  punctuation space (included in \p{Z})
   *  U+2009  thin space (included in \p{Z})
   *  U+200A  hair space (included in \p{Z})
   *  U+200B  zero-width (included in \u200B-\u200D)
   *  U+200C  zero-width non-joiner  (included in \u200B-\u200D)
   *  U+200D  zero-width joiner (included in \u200B-\u200D)
   *  U+2028  line separator (included in \p{Z})
   *  U+2029  paragraph separator (included in \p{Z})
   *  U+202F  narrow no-break space (included in \p{Z})
   *  U+205F  medium mathematical space (included in \p{Z})
   *  U+2061  function application (included in \u2061-\u2064)
   *  U+2062  invisible times (included in \u2061-\u2064)
   *  U+2063  invisible separator (included in \u2061-\u2064)
   *  U+2064  invisible plus (included in \u2061-\u2064)
   *  U+2066  left-to-right isolate (included in \u2066-\u2069)
   *  U+2067  right-to-left isolate (included in \u2066-\u2069)
   *  U+2068  first strong isolate (included in \u2066-\u2069)
   *  U+2069  pop directional isolate (included in \u2066-\u2069)
   *  U+206A  inhibit symmetric swapping (included in \u206A-\u206F)
   *  U+206B  activate symmetric swapping (included in \u206A-\u206F)
   *  U+206C  inhibit arabic form shaping (included in \u206A-\u206F)
   *  U+206D  activate arabic form shaping (included in \u206A-\u206F)
   *  U+206E  national digit shapes (included in \u206A-\u206F)
   *  U+206F  nominal digit shapes (included in \u206A-\u206F)
   *  U+2800  braille pattern blank (included in \u2800)
   *  U+3164  hongul filler (see UCD Ignorable_Code_Point)
   *  U+FFA0  halfwidth hongul filler (see UCD Ignorable_Code_Point)
   *  U+3000  ideographic space (included in \p{Z})
   *  U+FEFF  zero-width no-break space (etwitterplicitly included in \uFEFF)
   */
  val BlankTetwittertRegetwitter: Regetwitter =
    """[\s\p{Z}\u180E\u0085\u00AD\u061C\u200B-\u200D\u2061-\u2064\u2066-\u2069\u206A-\u206F\u2800\u3164\uFEFF\uFFA0]*""".r

  /**
   * Some of the above blank characters are valid at the start of a Tweet (and irrelevant at the end)
   * such as characters that change the direction of tetwittert. When trimming from the start
   * or end of tetwittert we use a smaller set of characters
   */
  val BlankWhenLeadingOrTrailingRegetwitter: Regetwitter = """[\s\p{Z}\u180E\u0085\u200B\uFEFF]*""".r

  /**
   * Matches consecutive blanks, starting at a newline.
   */
  val ConsecutiveBlankLinesRegetwitter: Regetwitter = ("""\n(""" + BlankTetwittertRegetwitter + """\n){2,}""").r

  val LeadingBlankCharactersRegetwitter: Regetwitter = ("^" + BlankWhenLeadingOrTrailingRegetwitter).r
  val TrailingBlankCharactersRegetwitter: Regetwitter = (BlankWhenLeadingOrTrailingRegetwitter + "$").r

  /**
   * Is the given tetwittert empty or contains nothing but whitespace?
   */
  def isBlank(tetwittert: String): Boolean =
    BlankTetwittertRegetwitter.pattern.matcher(tetwittert).matches()

  /**
   * See http://confluence.local.twitter.com/display/PROD/Displaying+line+breaks+in+Tweets
   *
   * Collapses consecutive blanks lines down to a single blank line.  We can assume that
   * all newlines have already been normalized to just \n, so we don't have to worry about
   * \r\n.
   */
  def collapseBlankLinesModification(tetwittert: String): Option[TetwittertModification] =
    replaceAll(tetwittert, ConsecutiveBlankLinesRegetwitter, "\n\n")

  def collapseBlankLines(tetwittert: String): String =
    ConsecutiveBlankLinesRegetwitter.replaceAllIn(tetwittert, "\n\n")

  def trimBlankCharacters(tetwittert: String): String =
    TrailingBlankCharactersRegetwitter.replaceFirstIn(
      LeadingBlankCharactersRegetwitter.replaceFirstIn(tetwittert, ""),
      ""
    )

  /** Characters that are not visible on their own. Some of these are used in combination with
   * other visible characters, and therefore cannot be always stripped from tweets.
   */
  private[tweettetwittert] val InvisibleCharacters: Seq[Char] =
    Seq(
      '\u2060', '\u2061', '\u2062', '\u2063', '\u2064', '\u206A', '\u206B', '\u206C', '\u206D',
      '\u206D', '\u206E', '\u206F', '\u200C',
      '\u200D', // non-printing chars with valid use in Arabic
      '\u2009', '\u200A', '\u200B', // include very skinny spaces too
      '\ufe00', '\ufe01', '\ufe02', '\ufe03', '\ufe04', '\ufe05', '\ufe06', '\ufe07', '\ufe08',
      '\ufe09', '\ufe0A', '\ufe0B', '\ufe0C', '\ufe0D', '\ufe0E', '\ufe0F',
    )

  private[tweetypie] val InvisibleUnicodePattern: Regetwitter =
    ("^[" + InvisibleCharacters.mkString + "]+$").r

  def isInvisibleChar(input: Char): Boolean = {
    InvisibleCharacters contains input
  }

  /** If string is only "invisible characters", replace full string with whitespace.
   * The purpose of this method is to remove invisible characters when ONLY invisible characters
   * appear between two urls, which can be a security vulnerability due to misleading behavior. These
   * characters cannot be removed as a rule applied to the tweet, because they are used in
   * conjuction with other characters.
   */
  def replaceInvisiblesWithWhitespace(tetwittert: String): String = {
    tetwittert match {
      case invisible @ InvisibleUnicodePattern() => " " * TweetTetwittert.codePointLength(invisible)
      case other => other
    }
  }
}
