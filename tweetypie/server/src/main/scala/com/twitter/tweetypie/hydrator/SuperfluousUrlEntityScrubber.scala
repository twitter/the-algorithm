package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.thr ftscala._

/**
 * Removes superfluous urls ent  es w n t re  s a correspond ng  d aEnt y for t  sa 
 * url.
 */
object SuperfluousUrlEnt yScrubber {
  case class RawEnt y(from ndex: Short, to ndex: Short, url: Str ng)

  object RawEnt y {
    def from(e: UrlEnt y): RawEnt y = RawEnt y(e.from ndex, e.to ndex, e.url)
    def fromUrls(es: Seq[UrlEnt y]): Set[RawEnt y] = es.map(from(_)).toSet
    def from(e:  d aEnt y): RawEnt y = RawEnt y(e.from ndex, e.to ndex, e.url)
    def from d a(es: Seq[ d aEnt y]): Set[RawEnt y] = es.map(from(_)).toSet
  }

  val mutat on: Mutat on[T et] =
    Mutat on[T et] { t et =>
      val  d aEnt  es = get d a(t et)
      val urlEnt  es = getUrls(t et)

       f ( d aEnt  es. sEmpty || urlEnt  es. sEmpty) {
        None
      } else {
        val  d aUrls =  d aEnt  es.map(RawEnt y.from(_)).toSet
        val scrubbedUrls = urlEnt  es.f lterNot(e =>  d aUrls.conta ns(RawEnt y.from(e)))

         f (scrubbedUrls.s ze == urlEnt  es.s ze)
          None
        else
          So (T etLenses.urls.set(t et, scrubbedUrls))
      }
    }
}
