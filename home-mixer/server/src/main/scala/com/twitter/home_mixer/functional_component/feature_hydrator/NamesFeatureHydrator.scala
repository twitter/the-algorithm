package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.g zmoduck.{thr ftscala => gt}
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Favor edByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Follo dByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.RealNa sFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScreenNa sFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceUser dFeature
 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.param.Ho GlobalParams.EnableNahFeedback nfoParam
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.g zmoduck.G zmoduck
 mport com.tw ter.ut l.Return
 mport javax. nject. nject
 mport javax. nject.S ngleton

protected case class Prof leNa s(screenNa : Str ng, realNa : Str ng)

@S ngleton
class Na sFeatureHydrator @ nject() (g zmoduck: G zmoduck)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h Cond  onally[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("Na s")

  overr de val features: Set[Feature[_, _]] = Set(ScreenNa sFeature, RealNa sFeature)

  overr de def only f(query: P pel neQuery): Boolean = query.product match {
    case Follow ngProduct => query.params(EnableNahFeedback nfoParam)
    case _ => true
  }

  pr vate val queryF elds: Set[gt.QueryF elds] = Set(gt.QueryF elds.Prof le)

  /**
   * T  U  currently only ever d splays t  f rst 2 na s  n soc al context l nes
   * E.g. "User and 3 ot rs l ke" or "UserA and UserB l ked"
   */
  pr vate val MaxCountUsers = 2

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {

    val cand dateUser dsMap = cand dates.map { cand date =>
      cand date.cand date. d ->
        (cand date.features.getOrElse(Favor edByUser dsFeature, N l).take(MaxCountUsers) ++
          cand date.features.getOrElse(Follo dByUser dsFeature, N l).take(MaxCountUsers) ++
          cand date.features.getOrElse(Author dFeature, None) ++
          cand date.features.getOrElse(S ceUser dFeature, None)).d st nct
    }.toMap

    val d st nctUser ds = cand dateUser dsMap.values.flatten.toSeq.d st nct

    St ch
      .collectToTry(d st nctUser ds.map(user d => g zmoduck.getUserBy d(user d, queryF elds)))
      .map { allUsers =>
        val  dToProf leNa sMap = allUsers.flatMap {
          case Return(allUser) =>
            allUser.prof le
              .map(prof le => allUser. d -> Prof leNa s(prof le.screenNa , prof le.na ))
          case _ => None
        }.toMap

        val val dUser ds =  dToProf leNa sMap.keySet

        cand dates.map { cand date =>
          val comb nedMap = cand dateUser dsMap
            .getOrElse(cand date.cand date. d, N l)
            .flatMap {
              case user d  f val dUser ds.conta ns(user d) =>
                 dToProf leNa sMap.get(user d).map(prof leNa s => user d -> prof leNa s)
              case _ => None
            }

          val perCand dateRealNa Map = comb nedMap.map { case (k, v) => k -> v.realNa  }.toMap
          val perCand dateScreenNa Map = comb nedMap.map { case (k, v) => k -> v.screenNa  }.toMap

          FeatureMapBu lder()
            .add(ScreenNa sFeature, perCand dateScreenNa Map)
            .add(RealNa sFeature, perCand dateRealNa Map)
            .bu ld()
        }
      }
  }
}
