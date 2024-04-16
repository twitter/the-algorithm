package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.tw ter.follow_recom ndat ons.conf gap .common.FeatureSw chConf g
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class FeatureHydrat onS cesFSConf g @ nject() () extends FeatureSw chConf g {
  overr de val booleanFSParams: Seq[Param[Boolean] w h FSNa ] = Seq(
    FeatureStoreS ceParams.EnableAlgor hmAggregateFeatures,
    FeatureStoreS ceParams.EnableAuthorTop cAggregateFeatures,
    FeatureStoreS ceParams.EnableCand dateCl entFeatures,
    FeatureStoreS ceParams.EnableCand datePrecomputedNot f cat onFeatures,
    FeatureStoreS ceParams.EnableCand dateUserAuthorRealT  AggregateFeatures,
    FeatureStoreS ceParams.EnableCand dateUserFeatures,
    FeatureStoreS ceParams.EnableCand dateUserResurrect onFeatures,
    FeatureStoreS ceParams.EnableCand dateUserT  l nesAuthorAggregateFeatures,
    FeatureStoreS ceParams.EnableSeparateCl entForT  l nesAuthors,
    FeatureStoreS ceParams.EnableSeparateCl entForG zmoduck,
    FeatureStoreS ceParams.EnableSeparateCl entFor tr cCenterUserCount ng,
    FeatureStoreS ceParams.EnableSeparateCl entForNot f cat ons,
    FeatureStoreS ceParams.EnableS m larToUserFeatures,
    FeatureStoreS ceParams.EnableTargetUserFeatures,
    FeatureStoreS ceParams.EnableTargetUserResurrect onFeatures,
    FeatureStoreS ceParams.EnableTargetUserWtf mpress onFeatures,
    FeatureStoreS ceParams.EnableTop cAggregateFeatures,
    FeatureStoreS ceParams.EnableUserCand dateEdgeFeatures,
    FeatureStoreS ceParams.EnableUserCand dateWtf mpress onCand dateFeatures,
    FeatureStoreS ceParams.EnableUserCl entFeatures,
    FeatureStoreS ceParams.EnableUserTop cFeatures,
    FeatureStoreS ceParams.EnableUserWtfAlgEdgeFeatures,
  )

  overr de val durat onFSParams: Seq[FSBoundedParam[Durat on] w h HasDurat onConvers on] = Seq(
    FeatureStoreS ceParams.GlobalFetchT  out
  )
}
