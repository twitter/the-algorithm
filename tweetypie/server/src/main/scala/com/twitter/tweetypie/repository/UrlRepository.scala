package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.serv ce.talon.thr ftscala._
 mport com.tw ter.st ch.SeqGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.compat.LegacySeqGroup
 mport com.tw ter.t etyp e.backends.Talon
 mport com.tw ter.t etyp e.cl ent_ d.Cl ent d lper
 mport com.tw ter.t etyp e.core.OverCapac y

case class UrlSlug(text: Str ng) extends AnyVal
case class ExpandedUrl(text: Str ng) extends AnyVal

object UrlRepos ory {
  type Type = UrlSlug => St ch[ExpandedUrl]

  /**
   * Bu lds a UrlRepos ory from a Talon.Expand arrow.
   */
  def apply(
    talonExpand: Talon.Expand,
    t etyp eCl ent d: Str ng,
    statsRece ver: StatsRece ver,
    cl ent d lper: Cl ent d lper,
  ): Type = {
    val observedTalonExpand: Talon.Expand =
      talonExpand
        .trackOutco (statsRece ver, _ => cl ent d lper.effect veCl ent d.getOrElse("unknown"))

    val expandGroup = SeqGroup[ExpandRequest, Try[ExpandResponse]] { requests =>
      LegacySeqGroup.l ftToSeqTry(
        Future.collect(requests.map(r => observedTalonExpand(r).l ftToTry)))
    }

    slug =>
      val request = toExpandRequest(slug, aud  ssage(t etyp eCl ent d, cl ent d lper))

      St ch
        .call(request, expandGroup)
        .lo rFromTry
        .flatMap(toExpandedUrl(slug, _))
  }

  def aud  ssage(t etyp eCl ent d: Str ng, cl ent d lper: Cl ent d lper): Str ng = {
    t etyp eCl ent d + cl ent d lper.effect veCl ent d.mkStr ng(":", "", "")
  }

  def toExpandRequest(slug: UrlSlug, aud  ssage: Str ng): ExpandRequest =
    ExpandRequest(user d = 0, shortUrl = slug.text, fromUser = false, aud Msg = So (aud  ssage))

  def toExpandedUrl(slug: UrlSlug, res: ExpandResponse): St ch[ExpandedUrl] =
    res.responseCode match {
      case ResponseCode.Ok =>
        // use Opt on(res.longUrl) because res.longUrl can be null
        Opt on(res.longUrl) match {
          case None => St ch.NotFound
          case So (longUrl) => St ch.value(ExpandedUrl(longUrl))
        }

      case ResponseCode.Bad nput =>
        St ch.NotFound

      //   shouldn't see ot r ResponseCodes, because Talon.Expand translates t m to
      // except ons, but   have t  catch-all just  n case.
      case _ =>
        St ch.except on(OverCapac y("talon"))
    }
}
