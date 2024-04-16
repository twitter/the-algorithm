package com.tw ter.ho _m xer.funct onal_component.f lter

 mport com.tw ter.common_ nternal.analyt cs.tw ter_cl ent_user_agent_parser.UserAgent
 mport com.tw ter.ho _m xer.model.Ho Features. sAncestorCand dateFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Pers stenceEntr esFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nem xer. nject on.store.pers stence.T  l nePers stenceUt ls
 mport com.tw ter.t  l nes.ut l.cl ent_ nfo.Cl entPlatform

object Prev ouslyServedAncestorsF lter
    extends F lter[P pel neQuery, T etCand date]
    w h T  l nePers stenceUt ls {

  overr de val  dent f er: F lter dent f er = F lter dent f er("Prev ouslyServedAncestors")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = {
    val cl entPlatform = Cl entPlatform.fromQueryOpt ons(
      cl entApp d = query.cl entContext.app d,
      userAgent = query.cl entContext.userAgent.flatMap(UserAgent.fromStr ng))
    val entr es =
      query.features.map(_.getOrElse(Pers stenceEntr esFeature, Seq.empty)).toSeq.flatten
    val t et ds = appl cableResponses(cl entPlatform, entr es)
      .flatMap(_.entr es.flatMap(_.t et ds( ncludeS ceT ets = true))).toSet
    val ancestor ds =
      cand dates
        .f lter(_.features.getOrElse( sAncestorCand dateFeature, false)).map(_.cand date. d).toSet

    val (removed, kept) =
      cand dates
        .map(_.cand date).part  on(cand date =>
          t et ds.conta ns(cand date. d) && ancestor ds.conta ns(cand date. d))

    St ch.value(F lterResult(kept = kept, removed = removed))
  }
}
