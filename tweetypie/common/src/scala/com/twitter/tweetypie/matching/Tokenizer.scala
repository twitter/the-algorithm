package com.tw ter.t etyp e.match ng

 mport com.tw ter.common.text.language.LocaleUt l
 mport com.tw ter.common_ nternal.text.p pel ne.Tw terTextNormal zer
 mport com.tw ter.common_ nternal.text.p pel ne.Tw terTextToken zer
 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on
 mport com.tw ter.concurrent.Once
 mport com.tw ter. o.Stream O
 mport java.ut l.Locale
 mport scala.collect on.JavaConverters._

/**
 * Extract a sequence of normal zed tokens from t   nput text. T 
 * normal zat on and token zat on are properly conf gured for keyword
 * match ng bet en texts.
 */
tra  Token zer {
  def token ze( nput: Str ng): TokenSequence
}

object Token zer {

  /**
   * W n a Pengu n vers on  s not expl c ly spec f ed, use t 
   * vers on of Pengu n to perform normal zat on and token zat on.  f
   *   cac  token zed text, be sure to store t  vers on as  ll, to
   * avo d compar ng text that was normal zed w h d fferent algor hms.
   */
  val DefaultPengu nVers on: Pengu nVers on = Pengu nVers on.PENGU N_6

  /**
   *  f   already know t  locale of t  text that  s be ng token zed,
   * use t   thod to get a token zer that  s much more eff c ent than
   * t  T et or Query token zer, s nce   does not have to perform
   * language detect on.
   */
  def forLocale(locale: Locale): Token zer = get(locale, DefaultPengu nVers on)

  /**
   * Obta n a `Token zer` that w ll token ze t  text for t  g ven
   * locale and vers on of t  Pengu n l brary.
   */
  def get(locale: Locale, vers on: Pengu nVers on): Token zer =
    Token zerFactor es(vers on).forLocale(locale)

  /**
   * Encapsulates t  conf gurat on and use of [[Tw terTextToken zer]]
   * and [[Tw terTextNormal zer]].
   */
  pr vate[t ] class Token zerFactory(vers on: Pengu nVers on) {
    // T  normal zer  s thread-safe, so share one  nstance.
    pr vate[t ] val normal zer =
      (new Tw terTextNormal zer.Bu lder(vers on)).bu ld()

    // T  Tw terTextToken zer  s relat vely expens ve to bu ld,
    // and  s not thread safe, so keep  nstances of    n a
    // ThreadLocal.
    pr vate[t ] val local =
      new ThreadLocal[Tw terTextToken zer] {
        overr de def  n  alValue: Tw terTextToken zer =
          (new Tw terTextToken zer.Bu lder(vers on)).bu ld()
      }

    /**
     * Obta n a [[Token zer]] for t  comb nat on of [[Pengu nVers on]]
     * and [[Locale]].
     */
    def forLocale(locale: Locale): Token zer =
      new Token zer {
        overr de def token ze( nput: Str ng): TokenSequence = {
          val stream = local.get.getTw terTokenStreamFor(locale)
          stream.reset(normal zer.normal ze( nput, locale))
          val bu lder =  ndexedSeq.newBu lder[CharSequence]
          wh le (stream. ncre ntToken) bu lder += stream.term()
          TokenSequence(bu lder.result())
        }
      }
  }

  /**
   * S nce t re are a small number of Pengu n vers ons, eagerly
   *  n  al ze a Token zerFactory for each vers on, to avo d manag ng
   * mutable state.
   */
  pr vate[t ] val Token zerFactor es: Pengu nVers on => Token zerFactory =
    Pengu nVers on.values.map(v => v -> new Token zerFactory(v)).toMap

  /**
   * T  set of locales used  n warmup. T se locales are  nt oned  n
   * t  log c of Tw terTextToken zer and Tw terTextNormal zer.
   */
  pr vate[t ] val WarmUpLocales: Seq[Locale] =
    Seq
      .concat(
        Seq(
          Locale.JAPANESE,
          Locale.KOREAN,
          LocaleUt l.UNKNOWN,
          LocaleUt l.THA ,
          LocaleUt l.ARAB C,
          LocaleUt l.SWED SH
        ),
        LocaleUt l.CH NESE_JAPANESE_LOCALES.asScala,
        LocaleUt l.CJK_LOCALES.asScala
      )
      .toSet
      .toArray
      .toSeq

  /**
   * Load t  default  nputs that are used for warm ng up t  l brary.
   */
  def warmUpCorpus(): Seq[Str ng] = {
    val stream = getClass.getRes ceAsStream("warmup-text.txt")
    val bytes =
      try Stream O.buffer(stream)
      f nally stream.close()
    bytes.toStr ng("UTF-8").l nes erator.toArray.toSeq
  }

  /**
   * Exerc se t  funct onal y of t  l brary on t  spec f ed
   * str ngs.  n general, prefer [[warmUp]] to t   thod.
   */
  def warmUpW h(ver: Pengu nVers on, texts:  erable[Str ng]): Un  =
    texts.foreach { txt =>
      // Exerc se each locale
      WarmUpLocales.foreach { loc =>
        Token zer.get(loc, ver).token ze(txt)
        UserMutes.bu lder().w hPengu nVers on(ver).w hLocale(loc).val date(txt)
      }

      // Exerc se language detect on
      T etToken zer.get(ver).token ze(txt)
      UserMutes.bu lder().w hPengu nVers on(ver).val date(txt)
    }

  pr vate[t ] val warmUpOnce = Once(warmUpW h(DefaultPengu nVers on, warmUpCorpus()))

  /**
   * T  creat on of t  f rst Tw terTextToken zer  s relat vely
   * expens ve, and token z ng so  texts may cause s gn f cant
   *  n  al zat on.
   *
   * T   thod exerc ses t  funct onal y of t  l brary
   * w h a range of texts  n order to perform as much  n  al zat on as
   * poss ble before t  l brary  s used  n a latency-sens  ve way.
   *
   * T  warmup rout ne w ll only run once. Subsequent  nvocat ons of
   * `warmUp` w ll no do add  onal work, and w ll return once warmup  s
   * complete.
   *
   * T  warmup w ll take on t  order of seconds.
   */
  def warmUp(): Un  = warmUpOnce()
}
