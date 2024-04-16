package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.common_ nternal.analyt cs.tw ter_cl ent_user_agent_parser.UserAgent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features.Pers stenceEntr esFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ServedT et dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ServedT etPrev ew dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.WhoToFollowExcludedUser dsFeature
 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.model.request.For Product
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nem xer.cl ents.pers stence.T  l neResponseBatc sCl ent
 mport com.tw ter.t  l nem xer.cl ents.pers stence.T  l neResponseV3
 mport com.tw ter.t  l nes.ut l.cl ent_ nfo.Cl entPlatform
 mport com.tw ter.t  l neserv ce.model.T  l neQuery
 mport com.tw ter.t  l neserv ce.model.core.T  l neK nd
 mport com.tw ter.t  l neserv ce.model.r ch.Ent y dType
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class Pers stenceStoreQueryFeatureHydrator @ nject() (
  t  l neResponseBatc sCl ent: T  l neResponseBatc sCl ent[T  l neResponseV3],
  statsRece ver: StatsRece ver)
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("Pers stenceStore")

  pr vate val scopedStatsRece ver = statsRece ver.scope(getClass.getS mpleNa )
  pr vate val servedT et dsS zeStat = scopedStatsRece ver.stat("ServedT et dsS ze")

  pr vate val WhoToFollowExcludedUser dsL m  = 1000
  pr vate val ServedT et dsDurat on = 10.m nutes
  pr vate val ServedT et dsL m  = 100
  pr vate val ServedT etPrev ew dsDurat on = 10.h s
  pr vate val ServedT etPrev ew dsL m  = 10

  overr de val features: Set[Feature[_, _]] =
    Set(
      ServedT et dsFeature,
      ServedT etPrev ew dsFeature,
      Pers stenceEntr esFeature,
      WhoToFollowExcludedUser dsFeature)

  pr vate val supportedCl ents = Seq(
    Cl entPlatform. Phone,
    Cl entPlatform. Pad,
    Cl entPlatform.Mac,
    Cl entPlatform.Andro d,
    Cl entPlatform. b,
    Cl entPlatform.R b,
    Cl entPlatform.T etDeckGryphon
  )

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    val t  l neK nd = query.product match {
      case Follow ngProduct => T  l neK nd.ho Latest
      case For Product => T  l neK nd.ho 
      case ot r => throw new UnsupportedOperat onExcept on(s"Unknown product: $ot r")
    }
    val t  l neQuery = T  l neQuery( d = query.getRequ redUser d, k nd = t  l neK nd)

    St ch.callFuture {
      t  l neResponseBatc sCl ent
        .get(query = t  l neQuery, cl entPlatforms = supportedCl ents)
        .map { t  l neResponses =>
          // Note that t  WTF entr es are not be ng scoped by Cl entPlatform
          val whoToFollowUser ds = t  l neResponses
            .flatMap { t  l neResponse =>
              t  l neResponse.entr es
                .f lter(_.ent y dType == Ent y dType.WhoToFollow)
                .flatMap(_. em ds.toSeq.flatMap(_.flatMap(_.user d)))
            }.take(WhoToFollowExcludedUser dsL m )

          val cl entPlatform = Cl entPlatform.fromQueryOpt ons(
            cl entApp d = query.cl entContext.app d,
            userAgent = query.cl entContext.userAgent.flatMap(UserAgent.fromStr ng))

          val servedT et ds = t  l neResponses
            .f lter(_.cl entPlatform == cl entPlatform)
            .f lter(_.servedT   >= T  .now - ServedT et dsDurat on)
            .sortBy(-_.servedT  . nM ll seconds)
            .flatMap(
              _.entr es.flatMap(_.t et ds( ncludeS ceT ets = true)).take(ServedT et dsL m ))

          servedT et dsS zeStat.add(servedT et ds.s ze)

          val servedT etPrev ew ds = t  l neResponses
            .f lter(_.cl entPlatform == cl entPlatform)
            .f lter(_.servedT   >= T  .now - ServedT etPrev ew dsDurat on)
            .sortBy(-_.servedT  . nM ll seconds)
            .flatMap(_.entr es
              .f lter(_.ent y dType == Ent y dType.T etPrev ew)
              .flatMap(_.t et ds( ncludeS ceT ets = true)).take(ServedT etPrev ew dsL m ))

          FeatureMapBu lder()
            .add(ServedT et dsFeature, servedT et ds)
            .add(ServedT etPrev ew dsFeature, servedT etPrev ew ds)
            .add(Pers stenceEntr esFeature, t  l neResponses)
            .add(WhoToFollowExcludedUser dsFeature, whoToFollowUser ds)
            .bu ld()
        }
    }
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.7, 50, 60, 60)
  )
}
