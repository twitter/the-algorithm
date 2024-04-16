package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.RealNa sFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata._
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.str ngcenter.cl ent.core.ExternalStr ng

pr vate[decorator] case class Soc alContext dAndScreenNa (
  soc alContext d: Long,
  screenNa : Str ng)

object EngagerSoc alContextBu lder {
  pr vate val User dRequestParamNa  = "user_ d"
  pr vate val D rect nject onContentS ceRequestParamNa  = "d s"
  pr vate val D rect nject on dRequestParamNa  = "d  d"
  pr vate val D rect nject onContentS ceSoc alProofUsers = "soc alproofusers"
  pr vate val Soc alProofUrl = ""
}

case class EngagerSoc alContextBu lder(
  contextType: GeneralContextType,
  str ngCenter: Str ngCenter,
  oneUserStr ng: ExternalStr ng,
  twoUsersStr ng: ExternalStr ng,
  moreUsersStr ng: ExternalStr ng,
  t  l neT le: ExternalStr ng) {
   mport EngagerSoc alContextBu lder._

  def apply(
    soc alContext ds: Seq[Long],
    query: P pel neQuery,
    cand dateFeatures: FeatureMap
  ): Opt on[Soc alContext] = {
    val realNa s = cand dateFeatures.getOrElse(RealNa sFeature, Map.empty[Long, Str ng])
    val val dSoc alContext dAndScreenNa s = soc alContext ds.flatMap { soc alContext d =>
      realNa s
        .get(soc alContext d).map(screenNa  =>
          Soc alContext dAndScreenNa (soc alContext d, screenNa ))
    }

    val dSoc alContext dAndScreenNa s match {
      case Seq(user) =>
        val soc alContextStr ng =
          str ngCenter.prepare(oneUserStr ng, Map("user" -> user.screenNa ))
        So (mkOneUserSoc alContext(soc alContextStr ng, user.soc alContext d))
      case Seq(f rstUser, secondUser) =>
        val soc alContextStr ng =
          str ngCenter
            .prepare(
              twoUsersStr ng,
              Map("user1" -> f rstUser.screenNa , "user2" -> secondUser.screenNa ))
        So (
          mkManyUserSoc alContext(
            soc alContextStr ng,
            query.getRequ redUser d,
            val dSoc alContext dAndScreenNa s.map(_.soc alContext d)))

      case f rstUser +: ot rUsers =>
        val ot rUsersCount = ot rUsers.s ze
        val soc alContextStr ng =
          str ngCenter
            .prepare(
              moreUsersStr ng,
              Map("user" -> f rstUser.screenNa , "count" -> ot rUsersCount))
        So (
          mkManyUserSoc alContext(
            soc alContextStr ng,
            query.getRequ redUser d,
            val dSoc alContext dAndScreenNa s.map(_.soc alContext d)))
      case _ => None
    }
  }

  pr vate def mkOneUserSoc alContext(soc alContextStr ng: Str ng, user d: Long): GeneralContext = {
    GeneralContext(
      contextType = contextType,
      text = soc alContextStr ng,
      url = None,
      context mageUrls = None,
      land ngUrl = So (
        Url(
          urlType = DeepL nk,
          url = "",
          urtEndpo ntOpt ons = None
        )
      )
    )
  }

  pr vate def mkManyUserSoc alContext(
    soc alContextStr ng: Str ng,
    v e r d: Long,
    soc alContext ds: Seq[Long]
  ): GeneralContext = {
    GeneralContext(
      contextType = contextType,
      text = soc alContextStr ng,
      url = None,
      context mageUrls = None,
      land ngUrl = So (
        Url(
          urlType = UrtEndpo nt,
          url = Soc alProofUrl,
          urtEndpo ntOpt ons = So (UrtEndpo ntOpt ons(
            requestParams = So (Map(
              User dRequestParamNa  -> v e r d.toStr ng,
              D rect nject onContentS ceRequestParamNa  -> D rect nject onContentS ceSoc alProofUsers,
              D rect nject on dRequestParamNa  -> soc alContext ds.mkStr ng(",")
            )),
            t le = So (str ngCenter.prepare(t  l neT le)),
            cac  d = None,
            subt le = None
          ))
        ))
    )
  }
}
