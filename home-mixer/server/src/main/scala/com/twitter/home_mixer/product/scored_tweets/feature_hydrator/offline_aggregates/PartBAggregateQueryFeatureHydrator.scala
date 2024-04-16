package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates

 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.T  l neAggregate tadataRepos ory
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.T  l neAggregatePartBRepos ory
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .DataRecord rger
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.servo.repos ory.Repos ory
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.data_process ng.jobs.t  l ne_rank ng_user_features.T  l nesPartBStoreReg ster
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.AggregateType
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.StoreConf g
 mport com.tw ter.t  l nes.pred ct on.adapters.request_context.RequestContextAdapter
 mport com.tw ter.t  l nes.pred ct on.common.aggregates.T  l nesAggregat onConf g
 mport com.tw ter.t  l nes.suggests.common.dense_data_record.thr ftscala.DenseFeature tadata
 mport com.tw ter.user_sess on_store.thr ftjava.UserSess on
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object PartBAggregateRootFeature extends BaseAggregateRootFeature {
  overr de val aggregateStores: Set[StoreConf g[_]] = T  l nesPartBStoreReg ster.allStores
}

object UserAggregateFeature
    extends DataRecord nAFeature[P pel neQuery]
    w h FeatureW hDefaultOnFa lure[P pel neQuery, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class PartBAggregateQueryFeatureHydrator @ nject() (
  @Na d(T  l neAggregatePartBRepos ory)
  repos ory: Repos ory[Long, Opt on[UserSess on]],
  @Na d(T  l neAggregate tadataRepos ory)
   tadataRepos ory: Repos ory[ nt, Opt on[DenseFeature tadata]])
    extends BaseAggregateQueryFeatureHydrator(
      repos ory,
       tadataRepos ory,
      PartBAggregateRootFeature
    ) {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("PartBAggregateQuery")

  overr de val features: Set[Feature[_, _]] =
    Set(PartBAggregateRootFeature, UserAggregateFeature)

  pr vate val userAggregateFeature nfo = new AggregateFeature nfo(
    aggregateGroups = Set(
      T  l nesAggregat onConf g.userAggregatesV2,
      T  l nesAggregat onConf g.userAggregatesV5Cont nuous,
      T  l nesAggregat onConf g.userAggregatesV6,
      T  l nesAggregat onConf g.tw terW deUserAggregates,
    ),
    aggregateType = AggregateType.User
  )

  pr vate val userH AggregateFeature nfo = new AggregateFeature nfo(
    aggregateGroups = Set(
      T  l nesAggregat onConf g.userRequestH Aggregates,
    ),
    aggregateType = AggregateType.UserRequestH 
  )

  pr vate val userDowAggregateFeature nfo = new AggregateFeature nfo(
    aggregateGroups = Set(
      T  l nesAggregat onConf g.userRequestDowAggregates
    ),
    aggregateType = AggregateType.UserRequestDow
  )

  requ re(
    userAggregateFeature nfo.feature == PartBAggregateRootFeature,
    "UserAggregates feature must be prov ded by t  PartB data s ce.")
  requ re(
    userH AggregateFeature nfo.feature == PartBAggregateRootFeature,
    "UserRequstH Aggregates feature must be prov ded by t  PartB data s ce.")
  requ re(
    userDowAggregateFeature nfo.feature == PartBAggregateRootFeature,
    "UserRequestDowAggregates feature must be prov ded by t  PartB data s ce.")

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    // Hydrate T  l neAggregatePartBFeature and UserAggregateFeature sequent ally.
    super.hydrate(query).map { featureMap =>
      val t  : T   = T  .now
      val h OfDay = RequestContextAdapter.h FromT  stamp(t  . nM ll seconds)
      val dayOf ek = RequestContextAdapter.dowFromT  stamp(t  . nM ll seconds)

      val dr = featureMap
        .get(PartBAggregateRootFeature).map { featuresW h tadata =>
          val userAggregatesDr =
            featuresW h tadata.userAggregatesOpt
              .map(featuresW h tadata.toDataRecord)
          val userRequestH AggregatesDr =
            Opt on(featuresW h tadata.userRequestH Aggregates.get(h OfDay))
              .map(featuresW h tadata.toDataRecord)
          val userRequestDowAggregatesDr =
            Opt on(featuresW h tadata.userRequestDowAggregates.get(dayOf ek))
              .map(featuresW h tadata.toDataRecord)

          dropUnknownFeatures(userAggregatesDr, userAggregateFeature nfo.featureContext)

          dropUnknownFeatures(
            userRequestH AggregatesDr,
            userH AggregateFeature nfo.featureContext)

          dropUnknownFeatures(
            userRequestDowAggregatesDr,
            userDowAggregateFeature nfo.featureContext)

           rgeDataRecordOpts(
            userAggregatesDr,
            userRequestH AggregatesDr,
            userRequestDowAggregatesDr)

        }.getOrElse(new DataRecord())

      featureMap + (UserAggregateFeature, dr)
    }
  }

  pr vate val dr rger = new DataRecord rger
  pr vate def  rgeDataRecordOpts(dataRecordOpts: Opt on[DataRecord]*): DataRecord =
    dataRecordOpts.flatten.foldLeft(new DataRecord) { (l, r) =>
      dr rger. rge(l, r)
      l
    }

  pr vate def dropUnknownFeatures(
    dataRecordOpt: Opt on[DataRecord],
    featureContext: FeatureContext
  ): Un  =
    dataRecordOpt.foreach(new R chDataRecord(_, featureContext).dropUnknownFeatures())

}
