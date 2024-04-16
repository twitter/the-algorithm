package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graph_feature_serv ce.{thr ftscala => gfs}
 mport com.tw ter.ho _m xer.model.Ho Features.Follo dByUser dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.From nNetworkS ceFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.GraphTwoHopRepos ory
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.ho _m xer.ut l.ObservedKeyValueResultHandler
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.pred ct on.adapters.two_hop_features.TwoHopFeaturesAdapter
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object GraphTwoHopFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class GraphTwoHopFeatureHydrator @ nject() (
  @Na d(GraphTwoHopRepos ory) cl ent: KeyValueRepos ory[(Seq[Long], Long), Long, Seq[
    gfs. ntersect onValue
  ]],
  overr de val statsRece ver: StatsRece ver)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h ObservedKeyValueResultHandler {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("GraphTwoHop")

  overr de val features: Set[Feature[_, _]] = Set(GraphTwoHopFeature, Follo dByUser dsFeature)

  overr de val statScope: Str ng =  dent f er.toStr ng

  pr vate val twoHopFeaturesAdapter = new TwoHopFeaturesAdapter

  pr vate val FollowFeatureType = gfs.FeatureType(gfs.EdgeType.Follow ng, gfs.EdgeType.Follo dBy)

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    // Apply f lters to  n network cand dates for ret ets only.
    val ( nNetworkCand dates, oonCand dates) = cand dates.part  on { cand date =>
      cand date.features.getOrElse(From nNetworkS ceFeature, false)
    }

    val  nNetworkCand datesToHydrate =
       nNetworkCand dates.f lter(_.features.getOrElse( sRet etFeature, false))

    val cand datesToHydrate = ( nNetworkCand datesToHydrate ++ oonCand dates)
      .flatMap(cand date => Cand datesUt l.getOr g nalAuthor d(cand date.features)).d st nct

    val response = cl ent((cand datesToHydrate, query.getRequ redUser d))

    response.map { result =>
      cand dates.map { cand date =>
        val or g nalAuthor d = Cand datesUt l.getOr g nalAuthor d(cand date.features)

        val value = observedGet(key = or g nalAuthor d, keyValueResult = result)
        val transfor dValue = postTransfor r(value)
        val follo dByUser ds = value.toOpt on
          .flatMap(getFollo dByUser ds(_))
          .getOrElse(Seq.empty)

        FeatureMapBu lder()
          .add(GraphTwoHopFeature, transfor dValue)
          .add(Follo dByUser dsFeature, follo dByUser ds)
          .bu ld()
      }
    }
  }

  pr vate def getFollo dByUser ds( nput: Opt on[Seq[gfs. ntersect onValue]]): Opt on[Seq[Long]] =
     nput.map(_.f lter(_.featureType == FollowFeatureType).flatMap(_. ntersect on ds).flatten)

  pr vate def postTransfor r( nput: Try[Opt on[Seq[gfs. ntersect onValue]]]): Try[DataRecord] =
     nput.map(twoHopFeaturesAdapter.adaptToDataRecords(_).asScala. ad)
}
