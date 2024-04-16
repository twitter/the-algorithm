package com.tw ter.t etyp e.t ettext
 mport scala.ut l.match ng.Regex

/**
 * Code used to convert raw user-prov ded text  nto an allowable form.
 */
object Preprocessor {
   mport T etText._
   mport TextMod f cat on.replaceAll

  /**
   * Regex for dos-style l ne end ngs.
   */
  val DosL neEnd ngRegex: Regex = """\r\n""".r

  /**
   * Converts \r\n to just \n.
   */
  def normal zeNewl nes(text: Str ng): Str ng =
    DosL neEnd ngRegex.replaceAll n(text, "\n")

  /**
   * Characters to str p out of t et text at wr e-t  .
   */
  val un codeCharsToStr p: Seq[Char] =
    Seq(
      '\uFFFE', '\uFEFF', // BOM
      '\uFFFF', // Spec al
      '\u200E', '\u200F', // ltr, rtl
      '\u202A', '\u202B', '\u202C', '\u202D', '\u202E', // D rect onal change
      '\u0000', '\u0001', '\u0002', '\u0003', '\u0004', '\u0005', '\u0006', '\u0007', '\u0008',
      '\u0009', '\u000B', '\u000C', '\u000E', '\u000F', '\u0010', '\u0011', '\u0012', '\u0013',
      '\u0014', '\u0015', '\u0016', '\u0017', '\u0018', '\u0019', '\u001A', '\u001B', '\u001C',
      '\u001D', '\u001E', '\u001F', '\u007F',
      '\u2065',
    )

  val Un codeCharsToStr pRegex: Regex = un codeCharsToStr p.mkStr ng("[", "", "]").r

  /**
   * Str ps out control characters and ot r non-textual un code chars that can break xml and/or
   * json render ng, or be used for explo s.
   */
  def str pControlCharacters(text: Str ng): Str ng =
    Un codeCharsToStr pRegex.replaceAll n(text, "")

  val T etyp e674Un codeSequence: Str ng =
    "\u0633\u0645\u064e\u0640\u064e\u0651\u0648\u064f\u0648\u064f\u062d\u062e " +
      "\u0337\u0334\u0310\u062e \u0337\u0334\u0310\u062e \u0337\u0334\u0310\u062e " +
      "\u0627\u0645\u0627\u0631\u062a\u064a\u062e \u0337\u0334\u0310\u062e"

  val T etyp e674Un codeRegex: Regex = T etyp e674Un codeSequence.r

  /**
   * Replace each `T etyp e674Un codeSequence` of t  str ng to REPLACEMENT
   * CHARACTER.
   *
   * Apple has a bug  n  s CoreText l brary. T  a ms to prevent
   *  os cl ents from be ng cras d w n a t et conta ns t  spec f c
   * un code sequence.
   */
  def avo dCoreTextBug(text: Str ng): Str ng =
    T etyp e674Un codeRegex.replaceAll n(text, "\ufffd")

  /**
   * Replace each `T etyp e674Un codeSequence` of t  str ng to a REPLACEMENT
   * CHARACTER, returns a TextMod f cat on object that prov des  nformat on
   * to also update ent y  nd ces.
   */
  def replaceCoreTextBugMod f cat on(text: Str ng): Opt on[TextMod f cat on] =
    replaceAll(text, T etyp e674Un codeRegex, "\ufffd")

  pr vate val preprocessor: Str ng => Str ng =
    ((s: Str ng) => nfcNormal ze(s))
      .andT n(str pControlCharacters _)
      .andT n(tr mBlankCharacters _)
      .andT n(normal zeNewl nes _)
      .andT n(collapseBlankL nes _)
      .andT n(avo dCoreTextBug _)

  /**
   * Performs t  text mod f cat ons that are necessary  n t  wr e-path before extract ng URLs.
   */
  def preprocessText(text: Str ng): Str ng =
    preprocessor(text)

  /**
   * Replaces all `<`, `>`, and '&' chars w h "&lt;", "&gt;", and "&amp;", respect vely.
   *
   * T  or g nal purpose of t  was presumably to prevent scr pt  nject ons w n
   * d splay ng t ets w hout proper escap ng.  Currently, t ets are encoded before
   * t y are stored  n t  database.
   *
   * Note that t  pre-escap ng of & < and > also happens  n t  r ch text ed or  n javascr pt
   */
  def part alHtmlEncode(text: Str ng): Str ng =
    Part alHtmlEncod ng.encode(text)

  /**
   * T  oppos e of part alHtmlEncode,   replaces all "&lt;", "&gt;", and "&amp;" w h
   * `<`, `>`, and '&', respect vely.
   */
  def part alHtmlDecode(text: Str ng): Str ng =
    Part alHtmlEncod ng.decode(text)

  /**
   *
   * Detects all forms of wh espace, cons der ng as wh espace t  follow ng:
   * T  regex detects characters that always or often are rendered as blank space.   use
   * t  to prevent users from  nsert ng excess blank l nes and from t et ng effect vely
   * blank t ets.
   *
   * Note that t se are not all semant cally "wh espace", so t  regex should not be used
   * to process non-blank text, e.g. to separate words.
   *
   * Codepo nts below and t  `\p{Z}` regex character property al as are def ned  n t  Un code
   * Character Database (UCD) at https://un code.org/ucd/ and https://un code.org/reports/tr44/
   *
   * T  `\p{Z}` regex character property al as  s def ned spec f cally  n UCD as:
   *
   * Zs |	Space_Separator	    | a space character (of var ous non-zero w dths)
   * Zl	| L ne_Separator	    | U+2028 L NE SEPARATOR only
   * Zp	| Paragraph_Separator	| U+2029 PARAGRAPH SEPARATOR only
   * Z	| Separator	          | Zs | Zl | Zp
   * ref: https://un code.org/reports/tr44/#GC_Values_Table
   *
   *  U+0009  Hor zontal Tab ( ncluded  n \s)
   *  U+000B  Vert cal Tab ( ncluded  n \s)
   *  U+000C  Form feed  ( ncluded  n \s)
   *  U+000D  Carr age return  ( ncluded  n \s)
   *  U+0020  space  ( ncluded  n \s)
   *  U+0085  Next l ne ( ncluded  n \u0085)
   *  U+061C  arab c letter mark ( ncluded  n \u061C)
   *  U+00A0  no-break space ( ncluded  n \p{Z})
   *  U+00AD  soft-hyp n marker ( ncluded  n \u00AD)
   *  U+1680  ogham space mark ( ncluded  n \p{Z})
   *  U+180E  mongol an vo l separator ( ncluded  n \p{Z} on jdk8 and  ncluded  n \u180E on jdk11)
   *  U+2000  en quad ( ncluded  n \p{Z})
   *  U+2001  em quad ( ncluded  n \p{Z})
   *  U+2002  en space ( ncluded  n \p{Z})
   *  U+2003  em space ( ncluded  n \p{Z})
   *  U+2004  three-per-em space ( ncluded  n \p{Z})
   *  U+2005  f -per-em space ( ncluded  n \p{Z})
   *  U+2006  s x-per-em space ( ncluded  n \p{Z})
   *  U+2007  f gure space ( ncluded  n \p{Z})
   *  U+2008  punctuat on space ( ncluded  n \p{Z})
   *  U+2009  th n space ( ncluded  n \p{Z})
   *  U+200A  ha r space ( ncluded  n \p{Z})
   *  U+200B  zero-w dth ( ncluded  n \u200B-\u200D)
   *  U+200C  zero-w dth non-jo ner  ( ncluded  n \u200B-\u200D)
   *  U+200D  zero-w dth jo ner ( ncluded  n \u200B-\u200D)
   *  U+2028  l ne separator ( ncluded  n \p{Z})
   *  U+2029  paragraph separator ( ncluded  n \p{Z})
   *  U+202F  narrow no-break space ( ncluded  n \p{Z})
   *  U+205F   d um mat mat cal space ( ncluded  n \p{Z})
   *  U+2061  funct on appl cat on ( ncluded  n \u2061-\u2064)
   *  U+2062   nv s ble t  s ( ncluded  n \u2061-\u2064)
   *  U+2063   nv s ble separator ( ncluded  n \u2061-\u2064)
   *  U+2064   nv s ble plus ( ncluded  n \u2061-\u2064)
   *  U+2066  left-to-r ght  solate ( ncluded  n \u2066-\u2069)
   *  U+2067  r ght-to-left  solate ( ncluded  n \u2066-\u2069)
   *  U+2068  f rst strong  solate ( ncluded  n \u2066-\u2069)
   *  U+2069  pop d rect onal  solate ( ncluded  n \u2066-\u2069)
   *  U+206A   nh b  sym tr c swapp ng ( ncluded  n \u206A-\u206F)
   *  U+206B  act vate sym tr c swapp ng ( ncluded  n \u206A-\u206F)
   *  U+206C   nh b  arab c form shap ng ( ncluded  n \u206A-\u206F)
   *  U+206D  act vate arab c form shap ng ( ncluded  n \u206A-\u206F)
   *  U+206E  nat onal d g  shapes ( ncluded  n \u206A-\u206F)
   *  U+206F  nom nal d g  shapes ( ncluded  n \u206A-\u206F)
   *  U+2800  bra lle pattern blank ( ncluded  n \u2800)
   *  U+3164  hongul f ller (see UCD  gnorable_Code_Po nt)
   *  U+FFA0  halfw dth hongul f ller (see UCD  gnorable_Code_Po nt)
   *  U+3000   deograph c space ( ncluded  n \p{Z})
   *  U+FEFF  zero-w dth no-break space (expl c ly  ncluded  n \uFEFF)
   */
  val BlankTextRegex: Regex =
    """[\s\p{Z}\u180E\u0085\u00AD\u061C\u200B-\u200D\u2061-\u2064\u2066-\u2069\u206A-\u206F\u2800\u3164\uFEFF\uFFA0]*""".r

  /**
   * So  of t  above blank characters are val d at t  start of a T et (and  rrelevant at t  end)
   * such as characters that change t  d rect on of text. W n tr mm ng from t  start
   * or end of text   use a smaller set of characters
   */
  val BlankW nLead ngOrTra l ngRegex: Regex = """[\s\p{Z}\u180E\u0085\u200B\uFEFF]*""".r

  /**
   * Matc s consecut ve blanks, start ng at a newl ne.
   */
  val Consecut veBlankL nesRegex: Regex = ("""\n(""" + BlankTextRegex + """\n){2,}""").r

  val Lead ngBlankCharactersRegex: Regex = ("^" + BlankW nLead ngOrTra l ngRegex).r
  val Tra l ngBlankCharactersRegex: Regex = (BlankW nLead ngOrTra l ngRegex + "$").r

  /**
   *  s t  g ven text empty or conta ns noth ng but wh espace?
   */
  def  sBlank(text: Str ng): Boolean =
    BlankTextRegex.pattern.matc r(text).matc s()

  /**
   * See http://confluence.local.tw ter.com/d splay/PROD/D splay ng+l ne+breaks+ n+T ets
   *
   * Collapses consecut ve blanks l nes down to a s ngle blank l ne.    can assu  that
   * all newl nes have already been normal zed to just \n, so   don't have to worry about
   * \r\n.
   */
  def collapseBlankL nesMod f cat on(text: Str ng): Opt on[TextMod f cat on] =
    replaceAll(text, Consecut veBlankL nesRegex, "\n\n")

  def collapseBlankL nes(text: Str ng): Str ng =
    Consecut veBlankL nesRegex.replaceAll n(text, "\n\n")

  def tr mBlankCharacters(text: Str ng): Str ng =
    Tra l ngBlankCharactersRegex.replaceF rst n(
      Lead ngBlankCharactersRegex.replaceF rst n(text, ""),
      ""
    )

  /** Characters that are not v s ble on t  r own. So  of t se are used  n comb nat on w h
   * ot r v s ble characters, and t refore cannot be always str pped from t ets.
   */
  pr vate[t ettext] val  nv s bleCharacters: Seq[Char] =
    Seq(
      '\u2060', '\u2061', '\u2062', '\u2063', '\u2064', '\u206A', '\u206B', '\u206C', '\u206D',
      '\u206D', '\u206E', '\u206F', '\u200C',
      '\u200D', // non-pr nt ng chars w h val d use  n Arab c
      '\u2009', '\u200A', '\u200B', //  nclude very sk nny spaces too
      '\ufe00', '\ufe01', '\ufe02', '\ufe03', '\ufe04', '\ufe05', '\ufe06', '\ufe07', '\ufe08',
      '\ufe09', '\ufe0A', '\ufe0B', '\ufe0C', '\ufe0D', '\ufe0E', '\ufe0F',
    )

  pr vate[t etyp e] val  nv s bleUn codePattern: Regex =
    ("^[" +  nv s bleCharacters.mkStr ng + "]+$").r

  def  s nv s bleChar( nput: Char): Boolean = {
     nv s bleCharacters conta ns  nput
  }

  /**  f str ng  s only " nv s ble characters", replace full str ng w h wh espace.
   * T  purpose of t   thod  s to remove  nv s ble characters w n ONLY  nv s ble characters
   * appear bet en two urls, wh ch can be a secur y vulnerab l y due to m slead ng behav or. T se
   * characters cannot be removed as a rule appl ed to t  t et, because t y are used  n
   * conjuct on w h ot r characters.
   */
  def replace nv s blesW hWh espace(text: Str ng): Str ng = {
    text match {
      case  nv s ble @  nv s bleUn codePattern() => " " * T etText.codePo ntLength( nv s ble)
      case ot r => ot r
    }
  }
}
