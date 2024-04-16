package com.tw ter.ho _m xer.funct onal_component.gate

 mport com.tw ter.common_ nternal.analyt cs.tw ter_cl ent_user_agent_parser.UserAgent
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nem xer.cl ents.pers stence.T  l neResponseV3
 mport com.tw ter.t  l nem xer. nject on.store.pers stence.T  l nePers stenceUt ls
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.t  l nes.ut l.cl ent_ nfo.Cl entPlatform
 mport com.tw ter.t  l neserv ce.model.r ch.Ent y dType
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  

/**
 * Gate used to reduce t  frequency of  nject ons. Note that t  actual  nterval bet en  nject ons may be
 * less than t  spec f ed m n nject on ntervalParam  f data  s unava lable or m ss ng. For example, be ng deleted by
 * t  pers stence store v a a TTL or s m lar  chan sm.
 *
 * @param m n nject on ntervalParam t  des red m n mum  nterval bet en  nject ons
 * @param pers stenceEntr esFeature t  feature for retr ev ng pers sted t  l ne responses
 */
case class T  l nesPers stenceStoreLast nject onGate(
  m n nject on ntervalParam: Param[Durat on],
  pers stenceEntr esFeature: Feature[P pel neQuery, Seq[T  l neResponseV3]],
  ent y dType: Ent y dType.Value)
    extends Gate[P pel neQuery]
    w h T  l nePers stenceUt ls {

  overr de val  dent f er: Gate dent f er = Gate dent f er("T  l nesPers stenceStoreLast nject on")

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] =
    St ch(
      query.queryT  .s nce(getLast nject onT  (query)) > query.params(m n nject on ntervalParam))

  pr vate def getLast nject onT  (query: P pel neQuery) = query.features
    .flatMap { featureMap =>
      val t  l neResponses = featureMap.getOrElse(pers stenceEntr esFeature, Seq.empty)
      val cl entPlatform = Cl entPlatform.fromQueryOpt ons(
        cl entApp d = query.cl entContext.app d,
        userAgent = query.cl entContext.userAgent.flatMap(UserAgent.fromStr ng)
      )
      val sortedResponses = responseByCl ent(cl entPlatform, t  l neResponses)
      val latestResponseW hEnt y dTypeEntry =
        sortedResponses.f nd(_.entr es.ex sts(_.ent y dType == ent y dType))

      latestResponseW hEnt y dTypeEntry.map(_.servedT  )
    }.getOrElse(T  .Bottom)
}
