package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etut l.T etPermal nk
 mport com.tw ter.t etyp e.core.F lteredState
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * Adds QuotedT et structs to t ets that conta n a t et permal nk url at t  end of t 
 * t et text. After  ntroduct on of QT +  d a,   stopped stor ng  nner t et permal nks
 *  n t  outer t et text. So t  hydrator would run only for below cases:
 *
 * -  tor cal quote t ets wh ch have  nner t et url  n t  t et text and url ent  es.
 * - new quote t ets created w h pasted t et permal nks, go ng forward   want to pers st
 *   quoted_t et struct  n MH for t se t ets
 */
object QuotedT etRefHydrator {
  type Type = ValueHydrator[Opt on[QuotedT et], Ctx]

  case class Ctx(urlEnt  es: Seq[UrlEnt y], underly ngT etCtx: T etCtx) extends T etCtx.Proxy

  val hydratedF eld: F eldByPath = f eldByPath(T et.QuotedT etF eld)

  pr vate val part al = ValueState.part al(None, hydratedF eld)

  val queryOpt ons: T etQuery.Opt ons =
    T etQuery.Opt ons(
       nclude = T etQuery. nclude(Set(T et.CoreDataF eld. d)),
      // Don't enforce v s b l y f lter ng w n load ng t  QuotedT et struct because    s
      // cac able. T  f lter ng happens  n QuoteT etV s b l yHydrator.
      enforceV s b l yF lter ng = false,
      forUser d = None
    )

  def once(h: Type): Type =
    T etHydrat on.completeOnlyOnce(
      queryF lter = queryF lter,
      hydrat onType = Hydrat onType.QuotedT etRef,
      dependsOn = Set(Hydrat onType.Urls),
      hydrator = h
    )

  case class UrlHydrat onFa led(url: Str ng) extends Except on

  /**
   *  erate through UrlEnt y objects  n reverse to  dent fy a quoted-t et  D
   * to hydrate. Quoted t ets are  nd cated by a T etPermal nk  n t  t et text
   * that references an older t et  D.  f a quoted t et permal nk  s found, also
   * return t  correspond ng UrlEnt y.
   *
   * @throws UrlHydrat onFa led  f   encounter a part al URL ent y before
   *   f nd ng a t et permal nk URL.
   */
  def quotedT et d(ctx: Ctx): Opt on[(UrlEnt y, T et d)] =
    ctx.urlEnt  es.reverse erator //   want t  r ghtmost t et permal nk
      .map { e: UrlEnt y =>
         f (UrlEnt yHydrator.hydrat onFa led(e)) throw UrlHydrat onFa led(e.url)
        else (e, e.expanded)
      }
      .collectF rst {
        case (e, So (T etPermal nk(_, quotedT et d))) => (e, quotedT et d)
      }
      // Prevent t et-quot ng cycles
      .f lter { case (_, quotedT et d) => ctx.t et d > quotedT et d }

  def bu ldShortenedUrl(e: UrlEnt y): ShortenedUrl =
    ShortenedUrl(
      shortUrl = e.url,
      // Read ng from MH w ll also default t  follow ng to "".
      // QuotedT etRefUrlsHydrator w ll hydrate t se cases
      longUrl = e.expanded.getOrElse(""),
      d splayText = e.d splay.getOrElse("")
    )

  /**
   *   run t  hydrator only  f:
   *
   * - quoted_t et struct  s empty
   * - quoted_t et  s present but permal nk  s not
   * - url ent  es  s present. QT hydrat on depends on urls - long term goal
   *    s to ent rely rely on pers sted quoted_t et struct  n MH
   * - requested t et  s not a ret et
   *
   * Hydrat on steps:
   * -   determ ne t  last t et permal nk from url ent  es
   * - Extract t   nner t et  d from t  permal nk
   * - Query t et repo w h  nner t et  d
   * - Construct quoted_t et struct from hydrated t et object and last permal nk
   */
  def apply(repo: T etRepos ory.Type): Type =
    ValueHydrator[Opt on[QuotedT et], Ctx] { (_, ctx) =>
      // propagate errors from quotedT et d  n St ch
      St ch(quotedT et d(ctx)).l ftToTry.flatMap {
        case Return(So ((lastPermal nkEnt y, quotedT et d))) =>
          repo(quotedT et d, queryOpt ons).l ftToTry.map {
            case Return(t et) =>
              ValueState.mod f ed(
                So (asQuotedT et(t et, lastPermal nkEnt y))
              )
            case Throw(NotFound | _: F lteredState) => ValueState.Unmod f edNone
            case Throw(_) => part al
          }
        case Return(None) => St ch(ValueState.Unmod f edNone)
        case Throw(_) => St ch(part al)
      }
    }.only f { (curr, ctx) =>
      (curr. sEmpty || curr.ex sts(_.permal nk. sEmpty)) &&
      !ctx. sRet et && ctx.urlEnt  es.nonEmpty
    }

  def queryF lter(opts: T etQuery.Opt ons): Boolean =
    opts. nclude.t etF elds(T et.QuotedT etF eld. d)

  /**
   *   construct T et.quoted_t et from hydrated  nner t et.
   * Note:  f t   nner t et  s a Ret et,   populate t  quoted_t et struct from s ce t et.
   */
  def asQuotedT et(t et: T et, ent y: UrlEnt y): QuotedT et = {
    val shortenedUrl = So (bu ldShortenedUrl(ent y))
    getShare(t et) match {
      case None => QuotedT et(t et. d, getUser d(t et), shortenedUrl)
      case So (share) => QuotedT et(share.s ceStatus d, share.s ceUser d, shortenedUrl)
    }
  }
}
