package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.convers ons.Durat onOps._

object FeatureStoreS ceParams {
  case object EnableTop cAggregateFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableTop cAggregateFeatures,
        default = true
      )
  case object EnableAlgor hmAggregateFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableAlgor hmAggregateFeatures,
        default = false
      )
  case object EnableAuthorTop cAggregateFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableAuthorTop cAggregateFeatures,
        default = true
      )
  case object EnableUserTop cFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableUserTop cFeatures,
        default = false
      )
  case object EnableTargetUserFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableTargetUserFeatures,
        default = true
      )
  case object EnableTargetUserUserAuthorUserStateRealT  AggregatesFeature
      extends FSParam[Boolean](
        na  =
          FeatureHydrat onS cesFeatureSw chKeys.EnableTargetUserUserAuthorUserStateRealT  AggregatesFeature,
        default = true
      )
  case object EnableTargetUserResurrect onFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableTargetUserResurrect onFeatures,
        default = true
      )
  case object EnableTargetUserWtf mpress onFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableTargetUserWtf mpress onFeatures,
        default = true
      )
  case object EnableCand dateUserFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableCand dateUserFeatures,
        default = true
      )
  case object EnableCand dateUserAuthorRealT  AggregateFeatures
      extends FSParam[Boolean](
        na  =
          FeatureHydrat onS cesFeatureSw chKeys.EnableCand dateUserAuthorRealT  AggregateFeatures,
        default = true
      )
  case object EnableCand dateUserResurrect onFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableCand dateUserResurrect onFeatures,
        default = true
      )
  case object EnableCand dateUserT  l nesAuthorAggregateFeatures
      extends FSParam[Boolean](
        na  =
          FeatureHydrat onS cesFeatureSw chKeys.EnableCand dateUserT  l nesAuthorAggregateFeatures,
        default = true
      )
  case object EnableUserCand dateEdgeFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableUserCand dateEdgeFeatures,
        default = true
      )
  case object EnableUserCand dateWtf mpress onCand dateFeatures
      extends FSParam[Boolean](
        na  =
          FeatureHydrat onS cesFeatureSw chKeys.EnableUserCand dateWtf mpress onCand dateFeatures,
        default = true
      )
  case object EnableUserWtfAlgEdgeFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableUserWtfAlgEdgeFeatures,
        default = false
      )
  case object EnableS m larToUserFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableS m larToUserFeatures,
        default = true
      )

  case object EnableCand datePrecomputedNot f cat onFeatures
      extends FSParam[Boolean](
        na  =
          FeatureHydrat onS cesFeatureSw chKeys.EnableCand datePrecomputedNot f cat onFeatures,
        default = false
      )

  case object EnableCand dateCl entFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableCand dateCl entFeatures,
        default = false
      )

  case object EnableUserCl entFeatures
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.EnableUserCl entFeatures,
        default = false
      )

  case object EnableSeparateCl entForT  l nesAuthors
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.UseSeparateCl entForT  l nesAuthor,
        default = false
      )

  case object EnableSeparateCl entFor tr cCenterUserCount ng
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.UseSeparateCl ent tr cCenterUserCount ng,
        default = false
      )

  case object EnableSeparateCl entForNot f cat ons
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.UseSeparateCl entForNot f cat ons,
        default = false
      )

  case object EnableSeparateCl entForG zmoduck
      extends FSParam[Boolean](
        na  = FeatureHydrat onS cesFeatureSw chKeys.UseSeparateCl entForG zmoduck,
        default = false
      )

  case object GlobalFetchT  out
      extends FSBoundedParam[Durat on](
        na  = FeatureHydrat onS cesFeatureSw chKeys.FeatureHydrat onT  out,
        default = 240.m ll second,
        m n = 100.m ll second,
        max = 400.m ll second)
      w h HasDurat onConvers on {
    overr de def durat onConvers on: Durat onConvers on = Durat onConvers on.FromM ll s
  }
}
