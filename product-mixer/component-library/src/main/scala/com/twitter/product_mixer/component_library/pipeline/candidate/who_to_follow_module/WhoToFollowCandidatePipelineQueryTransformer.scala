package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module

 mport com.tw ter.peopled scovery.ap .thr ftscala.Cl entContext
 mport com.tw ter.peopled scovery.ap .thr ftscala.GetModuleRequest
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

object WhoToFollowCand dateP pel neQueryTransfor r {
  val D splayLocat on = "t  l ne"
  val SupportedLa ts = Seq("user-b o-l st")
  val La tVers on = 2
}

case class WhoToFollowCand dateP pel neQueryTransfor r[-Query <: P pel neQuery](
  d splayLocat onParam: Param[Str ng],
  supportedLa tsParam: Param[Seq[Str ng]],
  la tVers onParam: Param[ nt],
  excludedUser dsFeature: Opt on[Feature[P pel neQuery, Seq[Long]]],
) extends Cand dateP pel neQueryTransfor r[Query, GetModuleRequest] {

  overr de def transform( nput: Query): GetModuleRequest =
    GetModuleRequest(
      cl entContext = Cl entContext(
        user d =  nput.getRequ redUser d,
        dev ce d =  nput.cl entContext.dev ce d,
        userAgent =  nput.cl entContext.userAgent,
        countryCode =  nput.cl entContext.countryCode,
        languageCode =  nput.cl entContext.languageCode,
      ),
      d splayLocat on =  nput.params(d splayLocat onParam),
      supportedLa ts =  nput.params(supportedLa tsParam),
      la tVers on =  nput.params(la tVers onParam),
      excludedUser ds =
        excludedUser dsFeature.flatMap(feature =>  nput.features.map(_.get(feature))),
       ncludePromoted = So (true),
    )
}
