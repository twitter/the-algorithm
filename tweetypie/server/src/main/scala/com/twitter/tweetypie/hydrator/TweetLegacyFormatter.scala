package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e. d a. d a
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.serverut l.ExtendedT et tadataBu lder
 mport com.tw ter.t etyp e.thr ftscala.UrlEnt y
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.t etyp e.thr ftscala.ent  es. mpl c s._
 mport com.tw ter.t etyp e.t ettext.Offset
 mport com.tw ter.t etyp e.t ettext.TextMod f cat on
 mport com.tw ter.t etyp e.t ettext.T etText
 mport com.tw ter.t etyp e.ut l.Ed ControlUt l
 mport com.tw ter.t etyp e.ut l.T etLenses

/**
 * T  hydrator  s t  backwards-compat b l y layer to support QT, Ed  T ets & M xed  d a
 * T ets render ng on legacy non-updated cl ents. Legacy render ng prov des a way for every cl ent
 * to consu  t se T ets unt l t  cl ent  s upgraded. For Ed  and M xed  d a T ets, t 
 * T et's self-permal nk  s appended to t  v s ble text. For Quot ng T ets, t  Quoted T et's
 * permal nk  s appended to t  text. For T ets that  et mult ple cr er a for legacy render ng
 * (e.g. QT conta n ng M xed  d a), only one permal nk  s appended and t  self-permal nk takes
 * precedence.
 */
object T etLegacyFormatter {

  pr vate[t ] val log = Logger(getClass)

   mport T etText._

  def legacyQtPermal nk(
    td: T etData,
    opts: T etQuery.Opt ons
  ): Opt on[ShortenedUrl] = {
    val t et = td.t et
    val t etText = T etLenses.text(t et)
    val urls = T etLenses.urls(t et)
    val ctx = T etCtx.from(td, opts)
    val qtPermal nk: Opt on[ShortenedUrl] = t et.quotedT et.flatMap(_.permal nk)
    val qtShortUrl = qtPermal nk.map(_.shortUrl)

    def urlsConta ns(url: Str ng): Boolean =
      urls.ex sts(_.url == url)

    val doLegacyQtFormatt ng =
      !opts.s mpleQuotedT et && !ctx. sRet et &&
        qtPermal nk. sDef ned && qtShortUrl. sDef ned &&
        !qtShortUrl.ex sts(t etText.conta ns) &&
        !qtShortUrl.ex sts(urlsConta ns)

     f (doLegacyQtFormatt ng) qtPermal nk else None
  }

  def legacySelfPermal nk(
    td: T etData
  ): Opt on[ShortenedUrl] = {
    val t et = td.t et
    val selfPermal nk = t et.selfPermal nk
    val t etText = T etLenses.text(t et)
    val urls = T etLenses.urls(t et)
    val selfShortUrl = selfPermal nk.map(_.shortUrl)

    def urlsConta ns(url: Str ng): Boolean =
      urls.ex sts(_.url == url)

    val doLegacyFormatt ng =
      selfPermal nk. sDef ned && selfShortUrl. sDef ned &&
        !selfShortUrl.ex sts(t etText.conta ns) &&
        !selfShortUrl.ex sts(urlsConta ns) &&
        needsLegacyFormatt ng(td)

     f (doLegacyFormatt ng) selfPermal nk else None
  }

  def  sM xed d aT et(t et: T et): Boolean =
    t et. d a.ex sts( d a. sM xed d a)

  def bu ldUrlEnt y(from: Short, to: Short, permal nk: ShortenedUrl): UrlEnt y =
    UrlEnt y(
      from ndex = from,
      to ndex = to,
      url = permal nk.shortUrl,
      expanded = So (permal nk.longUrl),
      d splay = So (permal nk.d splayText)
    )

  pr vate[t ] def  sVal dV s bleRange(
    t et dForLogg ng: T et d,
    textRange: TextRange,
    textLength:  nt
  ) = {
    val  sVal d = textRange.from ndex <= textRange.to ndex && textRange.to ndex <= textLength
     f (! sVal d) {
      log.warn(s"T et $t et dForLogg ng has  nval d v s bleTextRange: $textRange")
    }
     sVal d
  }

  // T  Funct on c cks  f legacy formatt ng  s requ red for Ed  & M xed  d a T ets.
  // Calls FeatureSw c s.matchRec p ent wh ch  s an expens ve call,
  // so caut on  s taken to call   only once and only w n needed.
  def needsLegacyFormatt ng(
    td: T etData
  ): Boolean = {
    val  sEd  = Ed ControlUt l. sEd T et(td.t et)
    val  sM xed d a =  sM xed d aT et(td.t et)
    val  sNoteT et = td.t et.noteT et. sDef ned

     f ( sEd  ||  sM xed d a ||  sNoteT et) {

      // T se feature sw c s are d sabled unless greater than certa n andro d,  os vers ons
      // & all vers ons of RWEB.
      val T etEd Consumpt onEnabledKey = "t et_ed _consumpt on_enabled"
      val M xed d aEnabledKey = "m xed_ d a_enabled"
      val NoteT etConsumpt onEnabledKey = "note_t et_consumpt on_enabled"

      def fsEnabled(fsKey: Str ng): Boolean = {
        td.featureSw chResults
          .flatMap(_.getBoolean(fsKey, shouldLog mpress on = false))
          .getOrElse(false)
      }

      val t etEd Consumpt onEnabled = fsEnabled(T etEd Consumpt onEnabledKey)
      val m xed d aEnabled = fsEnabled(M xed d aEnabledKey)
      val noteT etConsumpt onEnabled = fsEnabled(NoteT etConsumpt onEnabledKey)

      ( sEd  && !t etEd Consumpt onEnabled) ||
      ( sM xed d a && !m xed d aEnabled) ||
      ( sNoteT et && !noteT etConsumpt onEnabled)
    } else {
      false
    }
  }

  //g ven a permal nk, t  t et text gets updated
  def updateTextAndURLsAnd d a(
    permal nk: ShortenedUrl,
    t et: T et,
    statsRece ver: StatsRece ver
  ): T et = {

    val or g nalText = T etLenses.text(t et)
    val or g nalTextLength = codePo ntLength(or g nalText)

    // Default t  v s ble range to t  whole t et  f t  ex st ng v s ble range  s  nval d.
    val v s bleRange: TextRange =
      T etLenses
        .v s bleTextRange(t et)
        .f lter((r: TextRange) =>  sVal dV s bleRange(t et. d, r, or g nalTextLength))
        .getOrElse(TextRange(0, or g nalTextLength))

    val permal nkShortUrl = permal nk.shortUrl
    val  nsertAtCodePo nt = Offset.CodePo nt(v s bleRange.to ndex)

    /*
     *  nsert on at pos  on 0  mpl es that t  or g nal t et text has no
     * v s ble text, so t  result ng text should be only t  url w hout
     * lead ng padd ng.
     */
    val padLeft =  f ( nsertAtCodePo nt.to nt > 0) " " else ""

    /*
     * Empty v s ble text at pos  on 0  mpl es that t  or g nal t et text
     * only conta ns a URL  n t  h dden suff x area, wh ch would not already
     * be padded.
     */
    val padR ght =  f (v s bleRange == TextRange(0, 0)) " " else ""
    val paddedShortUrl = s"$padLeft$permal nkShortUrl$padR ght"

    val t etTextMod f cat on = TextMod f cat on. nsertAt(
      or g nalText,
       nsertAtCodePo nt,
      paddedShortUrl
    )

    /*
     * As   mod f ed t et text and appended t et permal nk above
     *   have to correct t  url and  d a ent  es accord ngly as t y are
     * expected to be present  n t  h dden suff x of text.
     *
     * -   compute t  new (from, to)  nd ces for t  url ent y
     * - bu ld new url ent y for quoted t et permal nk or self permal nk for Ed / MM T ets
     * - sh ft url ent  es wh ch are after v s ble range end
     * - sh ft  d a ent  es assoc ated w h above url ent  es
     */
    val shortUrlLength = codePo ntLength(permal nkShortUrl)
    val from ndex =  nsertAtCodePo nt.to nt + codePo ntLength(padLeft)
    val to ndex = from ndex + shortUrlLength

    val t etUrlEnt y = bu ldUrlEnt y(
      from = from ndex.toShort,
      to = to ndex.toShort,
      permal nk = permal nk
    )

    val t et d a =  f ( sM xed d aT et(t et)) {
      T etLenses. d a(t et).take(1)
    } else {
      T etLenses. d a(t et)
    }

    val mod f ed d a = t etTextMod f cat on.re ndexEnt  es(t et d a)
    val mod f edUrls =
      t etTextMod f cat on.re ndexEnt  es(T etLenses.urls(t et)) :+ t etUrlEnt y
    val mod f edText = t etTextMod f cat on.updated

    /*
     * V s ble Text Range computat on d ffers by scenar o
     * == Any T et w h  d a ==
     * T et text has a  d a url *after* t  v s ble text range
     * or g nal  text: [v s ble text] https://t.co/ d aUrl
     * or g nal range:  ^START  END^
     *
     * Append t  permal nk URL to t  *v s ble text* so non-upgraded cl ents can see  
     * mod f ed  text: [v s ble text https://t.co/permal nk] https://t.co/ d aUrl
     * mod f ed range:  ^START                         END^
     * v s ble range expanded, permal nk  s v s ble
     *
     * == Non-QT T et w/o  d a ==
     * or g nal  text: [v s ble text]
     * or g nal range: None (default: whole text  s v s ble)
     *
     * mod f ed  text: [v s ble text https://t.co/selfPermal nk]
     * mod f ed range: None (default: whole text  s v s ble)
     * tra l ng self permal nk w ll be v s ble
     *
     * == QT w/o  d a ==
     * or g nal  text: [v s ble text]
     * or g nal range: None (default: whole text  s v s ble)
     *
     * mod f ed  text: [v s ble text] https://t.co/qtPermal nk
     * mod f ed range:  ^START  END^
     * tra l ng QT permal nk  s *h dden* because legacy cl ents that process t  v s ble text range know how to d splay QTs
     *
     * == Non-QT Repl es w/o  d a ==
     * or g nal  text: @user [v s ble text]
     * or g nal range:        ^START  END^
     *
     * mod f ed  text: @user [v s ble text https://t.co/selfPermal nk]
     * mod f ed range:        ^START                             END^
     * v s ble range expanded, self permal nk  s v s ble
     *
     * == QT Repl es w/o  d a ==
     * or g nal  text: @user [v s ble text]
     * or g nal range:        ^START  END^
     *
     * mod f ed  text: @user [v s ble text] https://t.co/qtPermal nk
     * mod f ed range:        ^START  END^
     * v s ble range rema ns t  sa , tra l ng QT permal nk  s h dden
     *
     */

    val mod f edV s bleTextRange =
       f (mod f ed d a.nonEmpty ||
        Ed ControlUt l. sEd T et(t et) ||
        t et.noteT et. sDef ned) {
        So (
          v s bleRange.copy(
            to ndex = v s bleRange.to ndex + codePo ntLength(padLeft) + shortUrlLength
          )
        )
      } else {
        So (v s bleRange)
      }

    val updatedT et =
      Lens.setAll(
        t et,
        T etLenses.text -> mod f edText,
        T etLenses.urls -> mod f edUrls.sortBy(_.from ndex),
        T etLenses. d a -> mod f ed d a.sortBy(_.from ndex),
        T etLenses.v s bleTextRange -> mod f edV s bleTextRange
      )

    /**
     * compute extended t et  tadata w n text length > 140
     * and apply t  f nal lens to return a mod f ed t et
     */
    val totalD splayLength = d splayLength(mod f edText)
     f (totalD splayLength > Or g nalMaxD splayLength) {
      updatedT et.selfPermal nk match {
        case So (permal nk) =>
          val extendedT et tadata = ExtendedT et tadataBu lder(updatedT et, permal nk)
          updatedT et.copy(
            extendedT et tadata = So (extendedT et tadata)
          )
        case None =>
          /**
           *  T  case shouldn't happen as T etBu lder currently populates
           *  selfPermal nk for extended t ets.  n QT +  d a,   w ll
           *  use Attach ntBu lder to store selfPermal nk dur ng wr es,
           *   f text d splay length  s go ng to exceed 140 after QT url append.
           */
          log.error(
            s"Fa led to compute extended  tadata for t et: ${t et. d} w h " +
              s"d splay length: ${totalD splayLength}, as self-permal nk  s empty."
          )
          statsRece ver.counter("self_permal nk_not_found"). ncr()
          t et
      }
    } else {
      updatedT et
    }
  }

  def apply(
    statsRece ver: StatsRece ver
  ): T etDataValueHydrator = {
    ValueHydrator[T etData, T etQuery.Opt ons] { (td, opts) =>
      // Prefer any requ red self permal nk render ng over QT permal nk render ng because a
      // cl ent that doesn't understand t  attr butes of t  T et ( .e. Ed , M xed
      //  d a) won't be able to render t  T et properly at all, regardless of w t r
      //  's a QT. By preferr ng a v s ble self-permal nk, t  v e r  s l nked to an
      // R b v ew of t  T et wh ch can fully d splay all of  s features.
      val permal nk: Opt on[ShortenedUrl] =
        legacySelfPermal nk(td)
          .orElse(legacyQtPermal nk(td, opts))

      permal nk match {
        case So (permal nk) =>
          val updatedT et = updateTextAndURLsAnd d a(permal nk, td.t et, statsRece ver)
          St ch(ValueState.delta(td, td.copy(t et = updatedT et)))
        case _ =>
          St ch(ValueState.unmod f ed(td))
      }
    }
  }
}
