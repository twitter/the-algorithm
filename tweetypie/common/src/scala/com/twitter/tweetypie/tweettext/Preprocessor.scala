package com.twittew.tweetypie.tweettext
impowt scawa.utiw.matching.wegex

/**
 * c-code used to convewt w-waw usew-pwovided t-text into a-an awwowabwe fowm. (///ˬ///✿)
 */
o-object pwepwocessow {
  i-impowt tweettext._
  i-impowt textmodification.wepwaceaww

  /**
   * w-wegex fow dos-stywe wine endings. (U ﹏ U)
   */
  vaw doswineendingwegex: wegex = """\w\n""".w

  /**
   * c-convewts \w\n to just \n. ^^;;
   */
  def nyowmawizenewwines(text: s-stwing): stwing =
    doswineendingwegex.wepwaceawwin(text, 🥺 "\n")

  /**
   * c-chawactews to stwip out of tweet text at wwite-time. òωó
   */
  vaw unicodechawstostwip: s-seq[chaw] =
    seq(
      '\ufffe', XD '\ufeff', :3 // b-bom
      '\uffff', (U ﹏ U) // s-speciaw
      '\u200e', >w< '\u200f', /(^•ω•^) // wtw, wtw
      '\u202a', (⑅˘꒳˘) '\u202b', ʘwʘ '\u202c', rawr x3 '\u202d', '\u202e', (˘ω˘) // diwectionaw change
      '\u0000', o.O '\u0001', 😳 '\u0002', '\u0003', o.O '\u0004', ^^;; '\u0005', '\u0006', ( ͡o ω ͡o ) '\u0007', ^^;; '\u0008',
      '\u0009', ^^;; '\u000b', XD '\u000c', '\u000e', 🥺 '\u000f', (///ˬ///✿) '\u0010', '\u0011', (U ᵕ U❁) '\u0012', ^^;; '\u0013',
      '\u0014', ^^;; '\u0015', rawr '\u0016', '\u0017', (˘ω˘) '\u0018', 🥺 '\u0019', '\u001a', nyaa~~ '\u001b', :3 '\u001c',
      '\u001d', /(^•ω•^) '\u001e', ^•ﻌ•^ '\u001f', '\u007f', UwU
      '\u2065', 😳😳😳
    )

  vaw unicodechawstostwipwegex: w-wegex = unicodechawstostwip.mkstwing("[", OwO "", "]").w

  /**
   * stwips out contwow chawactews and othew nyon-textuaw unicode c-chaws that can bweak xmw and/ow
   * j-json wendewing, ^•ﻌ•^ o-ow be used f-fow expwoits. (ꈍᴗꈍ)
   */
  d-def stwipcontwowchawactews(text: stwing): stwing =
    unicodechawstostwipwegex.wepwaceawwin(text, (⑅˘꒳˘) "")

  v-vaw tweetypie674unicodesequence: stwing =
    "\u0633\u0645\u064e\u0640\u064e\u0651\u0648\u064f\u0648\u064f\u062d\u062e " +
      "\u0337\u0334\u0310\u062e \u0337\u0334\u0310\u062e \u0337\u0334\u0310\u062e " +
      "\u0627\u0645\u0627\u0631\u062a\u064a\u062e \u0337\u0334\u0310\u062e"

  vaw tweetypie674unicodewegex: w-wegex = tweetypie674unicodesequence.w

  /**
   * wepwace each `tweetypie674unicodesequence` of this stwing to wepwacement
   * chawactew. (⑅˘꒳˘)
   *
   * a-appwe has a bug in its cowetext w-wibwawy. (ˆ ﻌ ˆ)♡ this a-aims to pwevent
   * i-ios cwients fwom being cwashed when a tweet contains the s-specific
   * u-unicode sequence. /(^•ω•^)
   */
  def avoidcowetextbug(text: s-stwing): stwing =
    t-tweetypie674unicodewegex.wepwaceawwin(text, òωó "\ufffd")

  /**
   * wepwace e-each `tweetypie674unicodesequence` of this s-stwing to a wepwacement
   * chawactew, (⑅˘꒳˘) wetuwns a-a textmodification object that pwovides i-infowmation
   * to awso u-update entity indices. (U ᵕ U❁)
   */
  d-def wepwacecowetextbugmodification(text: stwing): option[textmodification] =
    wepwaceaww(text, >w< tweetypie674unicodewegex, σωσ "\ufffd")

  pwivate vaw pwepwocessow: s-stwing => stwing =
    ((s: stwing) => n-nyfcnowmawize(s))
      .andthen(stwipcontwowchawactews _)
      .andthen(twimbwankchawactews _)
      .andthen(nowmawizenewwines _)
      .andthen(cowwapsebwankwines _)
      .andthen(avoidcowetextbug _)

  /**
   * pewfowms the t-text modifications t-that awe nyecessawy i-in the wwite-path befowe extwacting uwws. -.-
   */
  def pwepwocesstext(text: s-stwing): stwing =
    pwepwocessow(text)

  /**
   * wepwaces aww `<`, o.O `>`, ^^ and '&' chaws with "&wt;", >_< "&gt;", >w< a-and "&amp;", >_< wespectivewy. >w<
   *
   * the owiginaw p-puwpose of this w-was pwesumabwy t-to pwevent scwipt injections when
   * d-dispwaying t-tweets without p-pwopew escaping. rawr  c-cuwwentwy, rawr x3 tweets awe encoded befowe
   * they a-awe stowed in t-the database. ( ͡o ω ͡o )
   *
   * n-nyote t-that the pwe-escaping o-of & < and > awso happens in the wich text editow in javascwipt
   */
  d-def pawtiawhtmwencode(text: stwing): stwing =
    pawtiawhtmwencoding.encode(text)

  /**
   * the o-opposite of pawtiawhtmwencode, (˘ω˘) it wepwaces aww "&wt;", 😳 "&gt;", OwO and "&amp;" with
   * `<`, (˘ω˘) `>`, òωó and '&', wespectivewy. ( ͡o ω ͡o )
   */
  def p-pawtiawhtmwdecode(text: s-stwing): s-stwing =
    pawtiawhtmwencoding.decode(text)

  /**
   *
   * d-detects aww fowms of whitespace, UwU c-considewing a-as whitespace the fowwowing:
   * this wegex detects chawactews that awways ow often awe wendewed a-as bwank space. /(^•ω•^) we use
   * this t-to pwevent usews fwom insewting e-excess bwank w-wines and fwom tweeting effectivewy
   * bwank tweets. (ꈍᴗꈍ)
   *
   * n-nyote that these a-awe nyot aww semanticawwy "whitespace", 😳 so this w-wegex shouwd nyot b-be used
   * to pwocess nyon-bwank text, mya e.g. to sepawate wowds. mya
   *
   * codepoints bewow a-and the `\p{z}` w-wegex chawactew p-pwopewty awias awe defined in the u-unicode
   * chawactew d-database (ucd) at https://unicode.owg/ucd/ a-and https://unicode.owg/wepowts/tw44/
   *
   * the `\p{z}` wegex chawactew pwopewty awias is defined specificawwy i-in ucd as:
   *
   * z-zs |	space_sepawatow	    | a space chawactew (of v-vawious n-nyon-zewo widths)
   * zw	| wine_sepawatow	    | u+2028 wine s-sepawatow onwy
   * zp	| pawagwaph_sepawatow	| u+2029 pawagwaph sepawatow onwy
   * z	| sepawatow	          | z-zs | zw | zp
   * wef: https://unicode.owg/wepowts/tw44/#gc_vawues_tabwe
   *
   *  u+0009  howizontaw t-tab (incwuded i-in \s)
   *  u+000b  vewticaw tab (incwuded in \s)
   *  u+000c  f-fowm feed  (incwuded i-in \s)
   *  u+000d  cawwiage wetuwn  (incwuded in \s)
   *  u-u+0020  space  (incwuded i-in \s)
   *  u+0085  nyext wine (incwuded in \u0085)
   *  u+061c  a-awabic wettew mawk (incwuded i-in \u061c)
   *  u-u+00a0  no-bweak space (incwuded i-in \p{z})
   *  u+00ad  soft-hyphen m-mawkew (incwuded i-in \u00ad)
   *  u-u+1680  ogham space mawk (incwuded i-in \p{z})
   *  u-u+180e  mongowian vowew sepawatow (incwuded i-in \p{z} o-on jdk8 and incwuded i-in \u180e on jdk11)
   *  u+2000  en quad (incwuded i-in \p{z})
   *  u+2001  e-em quad (incwuded i-in \p{z})
   *  u+2002  en space (incwuded in \p{z})
   *  u+2003  em space (incwuded in \p{z})
   *  u-u+2004  t-thwee-pew-em space (incwuded in \p{z})
   *  u+2005  f-fouw-pew-em s-space (incwuded in \p{z})
   *  u-u+2006  six-pew-em space (incwuded in \p{z})
   *  u+2007  figuwe space (incwuded in \p{z})
   *  u-u+2008  punctuation space (incwuded i-in \p{z})
   *  u+2009  t-thin space (incwuded in \p{z})
   *  u-u+200a  haiw space (incwuded i-in \p{z})
   *  u-u+200b  zewo-width (incwuded i-in \u200b-\u200d)
   *  u-u+200c  z-zewo-width nyon-joinew  (incwuded in \u200b-\u200d)
   *  u+200d  zewo-width joinew (incwuded in \u200b-\u200d)
   *  u+2028  wine sepawatow (incwuded i-in \p{z})
   *  u-u+2029  pawagwaph s-sepawatow (incwuded in \p{z})
   *  u-u+202f  nyawwow nyo-bweak space (incwuded in \p{z})
   *  u-u+205f  medium m-mathematicaw space (incwuded i-in \p{z})
   *  u+2061  function appwication (incwuded i-in \u2061-\u2064)
   *  u-u+2062  invisibwe times (incwuded i-in \u2061-\u2064)
   *  u-u+2063  invisibwe sepawatow (incwuded in \u2061-\u2064)
   *  u+2064  invisibwe pwus (incwuded i-in \u2061-\u2064)
   *  u-u+2066  weft-to-wight i-isowate (incwuded i-in \u2066-\u2069)
   *  u-u+2067  wight-to-weft isowate (incwuded i-in \u2066-\u2069)
   *  u-u+2068  fiwst stwong isowate (incwuded i-in \u2066-\u2069)
   *  u-u+2069  pop diwectionaw isowate (incwuded i-in \u2066-\u2069)
   *  u+206a  inhibit symmetwic swapping (incwuded i-in \u206a-\u206f)
   *  u+206b  a-activate symmetwic s-swapping (incwuded in \u206a-\u206f)
   *  u+206c  i-inhibit awabic fowm shaping (incwuded in \u206a-\u206f)
   *  u-u+206d  activate a-awabic fowm s-shaping (incwuded in \u206a-\u206f)
   *  u+206e  nyationaw digit s-shapes (incwuded in \u206a-\u206f)
   *  u+206f  n-nyominaw digit s-shapes (incwuded in \u206a-\u206f)
   *  u-u+2800  bwaiwwe pattewn b-bwank (incwuded i-in \u2800)
   *  u+3164  honguw fiwwew (see u-ucd ignowabwe_code_point)
   *  u+ffa0  hawfwidth honguw fiwwew (see u-ucd ignowabwe_code_point)
   *  u-u+3000  ideogwaphic space (incwuded i-in \p{z})
   *  u+feff  z-zewo-width nyo-bweak s-space (expwicitwy i-incwuded in \ufeff)
   */
  vaw bwanktextwegex: wegex =
    """[\s\p{z}\u180e\u0085\u00ad\u061c\u200b-\u200d\u2061-\u2064\u2066-\u2069\u206a-\u206f\u2800\u3164\ufeff\uffa0]*""".w

  /**
   * some of the above bwank chawactews awe vawid at the stawt of a tweet (and iwwewevant at the end)
   * such as chawactews that change the d-diwection of text. /(^•ω•^) w-when twimming fwom the stawt
   * ow end of t-text we use a smowew s-set of chawactews
   */
  vaw b-bwankwhenweadingowtwaiwingwegex: wegex = """[\s\p{z}\u180e\u0085\u200b\ufeff]*""".w

  /**
   * m-matches consecutive bwanks, ^^;; stawting a-at a nyewwine. 🥺
   */
  vaw c-consecutivebwankwineswegex: wegex = ("""\n(""" + bwanktextwegex + """\n){2,}""").w

  v-vaw weadingbwankchawactewswegex: wegex = ("^" + b-bwankwhenweadingowtwaiwingwegex).w
  v-vaw twaiwingbwankchawactewswegex: wegex = (bwankwhenweadingowtwaiwingwegex + "$").w

  /**
   * i-is t-the given text e-empty ow contains n-nyothing but whitespace?
   */
  d-def isbwank(text: s-stwing): boowean =
    b-bwanktextwegex.pattewn.matchew(text).matches()

  /**
   * s-see http://confwuence.wocaw.twittew.com/dispway/pwod/dispwaying+wine+bweaks+in+tweets
   *
   * c-cowwapses consecutive bwanks w-wines down to a-a singwe bwank w-wine. ^^  we can assume that
   * a-aww nyewwines have awweady been nyowmawized to just \n, ^•ﻌ•^ s-so we don't have to wowwy a-about
   * \w\n. /(^•ω•^)
   */
  d-def cowwapsebwankwinesmodification(text: s-stwing): option[textmodification] =
    wepwaceaww(text, ^^ c-consecutivebwankwineswegex, 🥺 "\n\n")

  def cowwapsebwankwines(text: s-stwing): stwing =
    consecutivebwankwineswegex.wepwaceawwin(text, (U ᵕ U❁) "\n\n")

  d-def twimbwankchawactews(text: stwing): s-stwing =
    twaiwingbwankchawactewswegex.wepwacefiwstin(
      weadingbwankchawactewswegex.wepwacefiwstin(text, 😳😳😳 ""),
      ""
    )

  /** chawactews that awe nyot visibwe o-on theiw own. nyaa~~ some of these a-awe used in combination w-with
   * othew visibwe chawactews, and thewefowe cannot b-be awways stwipped fwom tweets. (˘ω˘)
   */
  p-pwivate[tweettext] v-vaw i-invisibwechawactews: seq[chaw] =
    seq(
      '\u2060', >_< '\u2061', '\u2062', XD '\u2063', rawr x3 '\u2064', '\u206a', ( ͡o ω ͡o ) '\u206b', :3 '\u206c', '\u206d', mya
      '\u206d', σωσ '\u206e', '\u206f', (ꈍᴗꈍ) '\u200c', OwO
      '\u200d', o.O // n-nyon-pwinting c-chaws with vawid use in a-awabic
      '\u2009', 😳😳😳 '\u200a', /(^•ω•^) '\u200b', // incwude vewy skinny spaces too
      '\ufe00', '\ufe01', OwO '\ufe02', ^^ '\ufe03', '\ufe04', (///ˬ///✿) '\ufe05', (///ˬ///✿) '\ufe06', '\ufe07', (///ˬ///✿) '\ufe08',
      '\ufe09', '\ufe0a', ʘwʘ '\ufe0b', ^•ﻌ•^ '\ufe0c', '\ufe0d', OwO '\ufe0e', (U ﹏ U) '\ufe0f',
    )

  p-pwivate[tweetypie] vaw invisibweunicodepattewn: w-wegex =
    ("^[" + i-invisibwechawactews.mkstwing + "]+$").w

  d-def isinvisibwechaw(input: chaw): b-boowean = {
    i-invisibwechawactews c-contains i-input
  }

  /** if stwing is onwy "invisibwe chawactews", (ˆ ﻌ ˆ)♡ w-wepwace f-fuww stwing w-with whitespace. (⑅˘꒳˘)
   * t-the puwpose o-of this method i-is to wemove invisibwe c-chawactews w-when onwy invisibwe chawactews
   * a-appeaw between two uwws, (U ﹏ U) w-which can be a secuwity vuwnewabiwity d-due to misweading b-behaviow. o.O t-these
   * chawactews cannot be wemoved as a wuwe appwied to the t-tweet, mya because t-they awe used i-in
   * conjuction with othew chawactews. XD
   */
  def wepwaceinvisibweswithwhitespace(text: stwing): s-stwing = {
    t-text match {
      case invisibwe @ i-invisibweunicodepattewn() => " " * t-tweettext.codepointwength(invisibwe)
      case othew => othew
    }
  }
}
