package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.tco_ut l.D splayUrl
 mport com.tw ter.tco_ut l. nval dUrlExcept on
 mport com.tw ter.tco_ut l.TcoSlug
 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._
 mport scala.ut l.control.NonFatal

object UrlEnt  esHydrator {
  type Type = ValueHydrator[Seq[UrlEnt y], T etCtx]

  def once(h: ValueHydrator[UrlEnt y, T etCtx]): Type =
    T etHydrat on.completeOnlyOnce(
      queryF lter = queryF lter,
      hydrat onType = Hydrat onType.Urls,
      hydrator = h.l ftSeq
    )

  def queryF lter(opts: T etQuery.Opt ons): Boolean =
    opts. nclude.t etF elds.conta ns(T et.UrlsF eld. d)
}

/**
 * Hydrates UrlEnt  es.   f t re  s a fa lure to hydrate an ent y, t  ent y  s left
 * unhydrated, so that   can try aga n later.  T  Part alEnt yCleaner w ll remove
 * t  part al ent y before return ng to cl ents.
 */
object UrlEnt yHydrator {

  /**
   * a funct on type that takes a shorten-url and an expanded-url, and generates a
   * "d splay url" (wh ch  sn't really a url).  t  may fa l  f t  expanded-url
   * can't be parsed as a val d url,  n wh ch case None  s returned.
   */
  type Truncator = (Str ng, Str ng) => Opt on[Str ng]

  val hydratedF eld: F eldByPath = f eldByPath(T et.UrlsF eld)
  val log: Logger = Logger(getClass)

  def apply(repo: UrlRepos ory.Type, stats: StatsRece ver): ValueHydrator[UrlEnt y, T etCtx] = {
    val toD splayUrl = truncator(stats)

    ValueHydrator[UrlEnt y, T etCtx] { (curr, _) =>
      val slug = getTcoSlug(curr)

      val result: St ch[Opt on[Try[ExpandedUrl]]] = St ch.collect(slug.map(repo(_).l ftToTry))

      result.map {
        case So (Return(expandedUrl)) =>
          ValueState.mod f ed(update(curr, expandedUrl, toD splayUrl))

        case None =>
          ValueState.unmod f ed(curr)

        case So (Throw(NotFound)) =>
          //  f t  UrlEnt y conta ns an  nval d t.co slug that can't be resolved,
          // leave t  ent y unhydrated, to be removed later by t  Part alEnt yCleaner.
          //   don't cons der t  a part al because t   nput  s  nval d and  s not
          // expected to succeed.
          ValueState.unmod f ed(curr)

        case So (Throw(_)) =>
          // On fa lure, use t  t.co l nk as t  expanded url so that    s st ll cl ckable,
          // but also st ll flag t  fa lure
          ValueState.part al(
            update(curr, ExpandedUrl(curr.url), toD splayUrl),
            hydratedF eld
          )
      }
    }.only f((curr, ctx) => !ctx. sRet et &&  sUnhydrated(curr))
  }

  /**
   * a UrlEnt y needs hydrat on  f t  expanded url  s e  r unset or set to t 
   * shortened url .
   */
  def  sUnhydrated(ent y: UrlEnt y): Boolean =
    ent y.expanded. sEmpty || hydrat onFa led(ent y)

  /**
   * D d t  hydrat on of t  URL ent y fa l?
   */
  def hydrat onFa led(ent y: UrlEnt y): Boolean =
    ent y.expanded.conta ns(ent y.url)

  def update(ent y: UrlEnt y, expandedUrl: ExpandedUrl, toD splayUrl: Truncator): UrlEnt y =
    ent y.copy(
      expanded = So (expandedUrl.text),
      d splay = toD splayUrl(ent y.url, expandedUrl.text)
    )

  def getTcoSlug(ent y: UrlEnt y): Opt on[UrlSlug] =
    TcoSlug.unapply(ent y.url).map(UrlSlug(_))

  def truncator(stats: StatsRece ver): Truncator = {
    val truncat onStats = stats.scope("truncat ons")
    val truncat onsCounter = truncat onStats.counter("count")
    val truncat onExcept onsCounter = truncat onStats.counter("except ons")

    (shortUrl, expandedUrl) =>
      try {
        truncat onsCounter. ncr()
        So (D splayUrl(shortUrl, So (expandedUrl), true))
      } catch {
        case NonFatal(ex) =>
          truncat onExcept onsCounter. ncr()
          truncat onStats.counter(ex.getClass.getNa ). ncr()
          ex match {
            case  nval dUrlExcept on(_) =>
              log.warn(s"fa led to truncate: `$shortUrl` / `$expandedUrl`")
            case _ =>
              log.warn(s"fa led to truncate: `$shortUrl` / `$expandedUrl`", ex)
          }
          None
      }
  }
}
