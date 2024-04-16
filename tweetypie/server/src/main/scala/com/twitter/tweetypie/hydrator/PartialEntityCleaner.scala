package com.tw ter.t etyp e
package hydrator

 mport com.tw ter. d aserv ces.commons.t et d a.thr ftscala._
 mport com.tw ter.t etyp e. d a._
 mport com.tw ter.t etyp e.thr ftscala._
 mport scala.collect on.Set

/**
 * Removes part al Url,  d a, and  nt on ent  es that  re not
 * fully hydrated. Rat r than return ng no value or a value w h
 *  ncomplete ent  es on an ent y hydrat on fa lure,   gracefully
 * degrade to just om t ng those ent  es. T  step needs to be
 * appl ed  n t  post-cac  f lter, so that   don't cac  t  value
 * w h m ss ng ent  es.
 *
 * A  d aEnt y w ll f rst be converted back to a UrlEnt y  f    s only
 * part ally hydrated.   f t  result ng UrlEnt y  s  self t n only part ally
 * hydrated,   w ll get dropped also.
 */
object Part alEnt yCleaner {
  def apply(stats: StatsRece ver): Mutat on[T et] = {
    val scopedStats = stats.scope("part al_ent y_cleaner")
    Mutat on
      .all(
        Seq(
          T etLenses.urls.mutat on(urls.countMutat ons(scopedStats.counter("urls"))),
          T etLenses. d a.mutat on( d a.countMutat ons(scopedStats.counter(" d a"))),
          T etLenses. nt ons.mutat on( nt ons.countMutat ons(scopedStats.counter(" nt ons")))
        )
      )
      .only f(! sRet et(_))
  }

  pr vate[t ] def clean[E]( sPart al: E => Boolean) =
    Mutat on[Seq[E]] {  ems =>
       ems.part  on( sPart al) match {
        case (N l, nonPart al) => None
        case (part al, nonPart al) => So (nonPart al)
      }
    }

  pr vate[t ] val  nt ons =
    clean[ nt onEnt y](e => e.user d. sEmpty || e.na . sEmpty)

  pr vate[t ] val urls =
    clean[UrlEnt y](e =>
       sNullOrEmpty(e.url) ||  sNullOrEmpty(e.expanded) ||  sNullOrEmpty(e.d splay))

  pr vate[t ] val  d a =
    Mutat on[Seq[ d aEnt y]] {  d aEnt  es =>
       d aEnt  es.part  on( sPart al d a) match {
        case (N l, nonPart al) => None
        case (part al, nonPart al) => So (nonPart al)
      }
    }

  def  sPart al d a(e:  d aEnt y): Boolean =
    e.from ndex < 0 ||
      e.to ndex <= 0 ||
       sNullOrEmpty(e.url) ||
       sNullOrEmpty(e.d splayUrl) ||
       sNullOrEmpty(e. d aUrl) ||
       sNullOrEmpty(e. d aUrlHttps) ||
       sNullOrEmpty(e.expandedUrl) ||
      e. d a nfo. sEmpty ||
      e. d aKey. sEmpty ||
      ( d aKeyClass f er. s mage( d aKeyUt l.get(e)) && conta ns nval dS zeVar ant(e.s zes))

  pr vate[t ] val user nt ons =
    clean[User nt on](e => e.screenNa . sEmpty || e.na . sEmpty)

  def  sNullOrEmpty(optStr ng: Opt on[Str ng]): Boolean =
    optStr ng. sEmpty || optStr ng.ex sts( sNullOrEmpty(_))

  def  sNullOrEmpty(str: Str ng): Boolean = str == null || str. sEmpty

  def conta ns nval dS zeVar ant(s zes: Set[ d aS ze]): Boolean =
    s zes.ex sts(s ze => s ze.  ght == 0 || s ze.w dth == 0)
}
