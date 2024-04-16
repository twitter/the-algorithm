package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common

sealed tra  FeatureS ce d

object FeatureS ce d {
  object Cand dateAlgor hmS ce d extends FeatureS ce d
  object Cl entContextS ce d extends FeatureS ce d
  object FeatureStoreS ce d extends FeatureS ce d
  object FeatureStoreT  l nesAuthorS ce d extends FeatureS ce d
  object FeatureStoreG zmoduckS ce d extends FeatureS ce d
  object FeatureStoreUser tr cCountsS ce d extends FeatureS ce d
  object FeatureStoreNot f cat onS ce d extends FeatureS ce d

  object FeatureStorePrecomputedNot f cat onS ce d extends FeatureS ce d
  object FeatureStorePostNuxAlgor hmS ce d extends FeatureS ce d
  @deprecated object StratoFeatureHydrat onS ce d extends FeatureS ce d
  object PreFetc dFeatureS ce d extends FeatureS ce d
  object UserScor ngFeatureS ce d extends FeatureS ce d
}
