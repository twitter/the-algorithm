package com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.model_based_top c_recom ndat ons

 mport com.tw ter.ml.ap .{Feature, FeatureContext}
 mport com.tw ter.ml.ap .constant.SharedFeatures

object UserFeatures {
  val User dFeature = SharedFeatures.USER_ D // User- d

  val UserS mClusterFeatures =
    new Feature.SparseCont nuous(
      "user.s mclusters. nterested_ n"
    ) // User's  nterested n s mcluster embeddd ng

  val UserCountryFeature = new Feature.Text("user.country") // user's country code

  val UserLanguageFeature = new Feature.Text("user.language") // user's language

  val Follo dTop c dFeatures =
    new Feature.SparseB nary(
      "follo d_top cs. d"
    ) // SparseB nary features for t  set of follo d top cs

  val Not nterestedTop c dFeatures =
    new Feature.SparseB nary(
      "not_ nterested_top cs. d"
    ) // SparseB nary features for t  set of not- nterested top cs

  val Follo dTop cS mClusterAvgFeatures =
    new Feature.SparseCont nuous(
      "follo d_top cs.s mclusters.avg"
    ) // Average S mCluster Embedd ng of t  follo d top cs

  val Not nterestedTop cS mClusterAvgFeatures =
    new Feature.SparseCont nuous(
      "not_ nterested_top cs.s mclusters.avg"
    ) // Average S mCluster Embedd ng of t  follo d top cs

  val TargetTop c dFeatures = new Feature.D screte("target_top c. d") // target top c- d

  val TargetTop cS mClustersFeature =
    new Feature.SparseCont nuous(
      "target_top c.s mclusters"
    ) // S mCluster embedd ng of t  target top c

  val FeatureContext = new FeatureContext(
    User dFeature,
    UserS mClusterFeatures,
    UserCountryFeature,
    UserLanguageFeature,
    Follo dTop c dFeatures,
    Not nterestedTop c dFeatures,
    Follo dTop cS mClusterAvgFeatures,
    Not nterestedTop cS mClusterAvgFeatures,
    TargetTop c dFeatures,
    TargetTop cS mClustersFeature
  )
}
