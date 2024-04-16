package com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces

 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{Author => AuthorEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{AuthorTop c => AuthorTop cEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{Cand dateUser => Cand dateUserEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{Top c => Top cEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{User => UserEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.core.{UserCand date => UserCand dateEnt y}
 mport com.tw ter.ml.featurestore.catalog.ent  es.onboard ng.UserWtfAlgor hmEnt y
 mport com.tw ter.ml.featurestore.catalog.ent  es.onboard ng.{
  WtfAlgor hm => WtfAlgor hm dEnt y
}
 mport com.tw ter.ml.featurestore.catalog.ent  es.onboard ng.{
  WtfAlgor hmType => WtfAlgor hmTypeEnt y
}
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCl ents.FullPr maryCl entVers on
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCl ents.NumCl ents
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCl ents.Pr maryCl ent
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCl ents.Pr maryCl entVers on
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCl ents.Pr maryDev ceManufacturer
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCl ents.Pr maryMob leSdkVers on
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCl ents.SecondaryCl ent
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCounts.Favor es
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCounts.Follo rs
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCounts.Follow ng
 mport com.tw ter.ml.featurestore.catalog.features.core.UserCounts.T ets
 mport com.tw ter.ml.featurestore.catalog.features.custo r_j ney.PostNuxAlgor hm dAggregateFeatureGroup
 mport com.tw ter.ml.featurestore.catalog.features.custo r_j ney.PostNuxAlgor hmTypeAggregateFeatureGroup
 mport com.tw ter.ml.featurestore.catalog.features.custo r_j ney.{Ut ls => FeatureGroupUt ls}
 mport com.tw ter.ml.featurestore.catalog.features. nterests_d scovery.UserTop cRelat onsh ps.Follo dTop cs
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumFavor es
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumFavor esRece ved
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumFollowBacks
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumFollows
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumFollowsRece ved
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumLog nDays
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumLog nT et mpress ons
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumMuteBacks
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumMuted
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumOr g nalT ets
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumQual yFollowRece ved
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumQuoteRet ets
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumQuoteRet etsRece ved
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumRepl es
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumRepl esRece ved
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumRet ets
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumRet etsRece ved
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumSpamBlocked
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumSpamBlockedBacks
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumT et mpress ons
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumT ets
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumUnfollowBacks
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumUnfollows
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumUserAct veM nutes
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumWasMutualFollo d
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumWasMutualUnfollo d
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng. tr cCenterUserCounts.NumWasUnfollo d
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl ne.Country
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl ne.Follo rsOverFollow ngRat o
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl ne.Language
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl ne.MutualFollowsOverFollo rsRat o
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl ne.MutualFollowsOverFollow ngRat o
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl ne.NumFollo rs
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl ne.NumFollow ngs
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl ne.NumMutualFollows
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl ne.T epCred
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl ne.UserState
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl neEdge.HaveSa Country
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl neEdge.HaveSa Language
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl neEdge.HaveSa UserState
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl neEdge.NumFollo rsGap
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl neEdge.NumFollow ngsGap
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl neEdge.NumMutualFollowsGap
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.PostNuxOffl neEdge.T epCredGap
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.Rat o.Follo rsFollow ngs
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.Rat o.MutualFollowsFollow ng
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.S mclusterUser nterested nCand dateKnownFor.Has ntersect on
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.S mclusterUser nterested nCand dateKnownFor. ntersect onCand dateKnownForScore
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.S mclusterUser nterested nCand dateKnownFor. ntersect onCluster ds
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.S mclusterUser nterested nCand dateKnownFor. ntersect onUserFavCand dateKnownForScore
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.S mclusterUser nterested nCand dateKnownFor. ntersect onUserFavScore
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.S mclusterUser nterested nCand dateKnownFor. ntersect onUserFollowCand dateKnownForScore
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.S mclusterUser nterested nCand dateKnownFor. ntersect onUserFollowScore
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.UserWtfAlgor hmAggregate
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WhoToFollow mpress on.Ho T  l neWtfCand dateCounts
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WhoToFollow mpress on.Ho T  l neWtfCand date mpress onCounts
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WhoToFollow mpress on.Ho T  l neWtfCand date mpress onLatestT  stamp
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WhoToFollow mpress on.Ho T  l neWtfLatestT  stamp
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.FollowRate
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.Follows
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.FollowsT etFavRate
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.FollowsT etRepl es
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.FollowsT etReplyRate
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.FollowsT etRet etRate
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.FollowsT etRet ets
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.FollowsW hT etFavs
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.FollowsW hT et mpress ons
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.HasAnyEngage nts
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.HasForwardEngage nts
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate.HasReverseEngage nts
 mport com.tw ter.ml.featurestore.catalog.features.onboard ng.WtfUserAlgor hmAggregate. mpress ons
 mport com.tw ter.ml.featurestore.catalog.features.rux.UserResurrect on.DaysS nceRecentResurrect on
 mport com.tw ter.ml.featurestore.catalog.features.t  l nes.AuthorTop cAggregates
 mport com.tw ter.ml.featurestore.catalog.features.t  l nes.Engage ntsRece vedByAuthorRealT  Aggregates
 mport com.tw ter.ml.featurestore.catalog.features.t  l nes.Negat veEngage ntsRece vedByAuthorRealT  Aggregates
 mport com.tw ter.ml.featurestore.catalog.features.t  l nes.Or g nalAuthorAggregates
 mport com.tw ter.ml.featurestore.catalog.features.t  l nes.Top cEngage ntRealT  Aggregates
 mport com.tw ter.ml.featurestore.catalog.features.t  l nes.Top cEngage ntUserStateRealT  Aggregates
 mport com.tw ter.ml.featurestore.catalog.features.t  l nes.Top cNegat veEngage ntUserStateRealT  Aggregates
 mport com.tw ter.ml.featurestore.catalog.features.t  l nes.UserEngage ntAuthorUserStateRealT  Aggregates
 mport com.tw ter.ml.featurestore.catalog.features.t  l nes.UserNegat veEngage ntAuthorUserStateRealT  Aggregates
 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.ml.featurestore.l b.User d
 mport com.tw ter.ml.featurestore.l b.feature.BoundFeature
 mport com.tw ter.ml.featurestore.l b.feature.Feature

object FeatureStoreFeatures {
   mport FeatureStoreRawFeatures._
  ///////////////////////////// Target user features ////////////////////////
  val targetUserFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    (userKeyedFeatures ++ userAlgor hmAggregateFeatures).map(_.b nd(UserEnt y))

  val targetUserResurrect onFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    userResurrect onFeatures.map(_.b nd(UserEnt y))
  val targetUserWtf mpress onFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    wtf mpress onUserFeatures.map(_.b nd(UserEnt y))
  val targetUserUserAuthorUserStateRealT  AggregatesFeature: Set[BoundFeature[_ <: Ent y d, _]] =
    userAuthorUserStateRealT  AggregatesFeature.map(_.b nd(UserEnt y))

  val targetUserStatusFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    userStatusFeatures.map(_.b nd(UserEnt y).logar hm1p)
  val targetUser tr cCountFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    mcFeatures.map(_.b nd(UserEnt y).logar hm1p)

  val targetUserCl entFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    cl entFeatures.map(_.b nd(UserEnt y))

  ///////////////////////////// Cand date user features ////////////////////////
  val cand dateUserFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    userKeyedFeatures.map(_.b nd(Cand dateUserEnt y))
  val cand dateUserAuthorRealT  AggregateFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    authorAggregateFeatures.map(_.b nd(Cand dateUserEnt y))
  val cand dateUserResurrect onFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    userResurrect onFeatures.map(_.b nd(Cand dateUserEnt y))

  val cand dateUserStatusFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    userStatusFeatures.map(_.b nd(Cand dateUserEnt y).logar hm1p)
  val cand dateUserT  l nesAuthorAggregateFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    Set(t  l nesAuthorAggregateFeatures.b nd(Cand dateUserEnt y))
  val cand dateUser tr cCountFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    mcFeatures.map(_.b nd(Cand dateUserEnt y).logar hm1p)

  val cand dateUserCl entFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    cl entFeatures.map(_.b nd(Cand dateUserEnt y))

  val s m larToUserFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    (userKeyedFeatures ++ authorAggregateFeatures).map(_.b nd(AuthorEnt y))

  val s m larToUserStatusFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    userStatusFeatures.map(_.b nd(AuthorEnt y).logar hm1p)
  val s m larToUserT  l nesAuthorAggregateFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    Set(t  l nesAuthorAggregateFeatures.b nd(AuthorEnt y))
  val s m larToUser tr cCountFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    mcFeatures.map(_.b nd(AuthorEnt y).logar hm1p)

  val userCand dateEdgeFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    (s mclusterUV ntersect onFeatures ++ userCand datePostNuxEdgeFeatures).map(
      _.b nd(UserCand dateEnt y))
  val userCand dateWtf mpress onCand dateFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    wtf mpress onCand dateFeatures.map(_.b nd(UserCand dateEnt y))

  /**
   * Aggregate features based on cand date s ce algor hms.
   */
  val postNuxAlgor hm dAggregateFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    Set(PostNuxAlgor hm dAggregateFeatureGroup.FeaturesAsDataRecord)
      .map(_.b nd(WtfAlgor hm dEnt y))

  /**
   * Aggregate features based on cand date s ce algor hm types. T re are 4 at t  mo nt:
   * Geo, Soc al, Act v y and  nterest.
   */
  val postNuxAlgor hmTypeAggregateFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    Set(PostNuxAlgor hmTypeAggregateFeatureGroup.FeaturesAsDataRecord)
      .map(_.b nd(WtfAlgor hmTypeEnt y))

  // user wtf-Algor hm features
  val userWtfAlgor hmEdgeFeatures: Set[BoundFeature[_ <: Ent y d, _]] =
    FeatureGroupUt ls.getT  l nesAggregat onFra workComb nedFeatures(
      UserWtfAlgor hmAggregate,
      UserWtfAlgor hmEnt y,
      FeatureGroupUt ls.getMaxSumAvgAggregate(UserWtfAlgor hmAggregate)
    )

  /**
   *   have to add t  max/sum/avg-aggregated features to t  set of all features so that   can
   * reg ster t m us ng FRS's [[FrsFeatureJsonExporter]].
   *
   * Any add  onal such aggregated features that are  ncluded  n [[FeatureStoreS ce]] cl ent
   * should be reg stered  re as  ll.
   */
  val maxSumAvgAggregatedFeatureContext: FeatureContext = new FeatureContext()
    .addFeatures(
      UserWtfAlgor hmAggregate.getSecondaryAggregatedFeatureContext
    )

  // top c features
  val top cAggregateFeatures: Set[BoundFeature[_ <: Ent y d, _]] = Set(
    Top cEngage ntRealT  Aggregates.FeaturesAsDataRecord,
    Top cNegat veEngage ntUserStateRealT  Aggregates.FeaturesAsDataRecord,
    Top cEngage ntUserStateRealT  Aggregates.FeaturesAsDataRecord
  ).map(_.b nd(Top cEnt y))
  val userTop cFeatures: Set[BoundFeature[_ <: Ent y d, _]] = Set(Follo dTop cs.b nd(UserEnt y))
  val authorTop cFeatures: Set[BoundFeature[_ <: Ent y d, _]] = Set(
    AuthorTop cAggregates.FeaturesAsDataRecord.b nd(AuthorTop cEnt y))
  val top cFeatures = top cAggregateFeatures ++ userTop cFeatures ++ authorTop cFeatures

}

object FeatureStoreRawFeatures {
  val mcFeatures = Set(
    NumT ets,
    NumRet ets,
    NumOr g nalT ets,
    NumRet etsRece ved,
    NumFavor esRece ved,
    NumRepl esRece ved,
    NumQuoteRet etsRece ved,
    NumFollowsRece ved,
    NumFollowBacks,
    NumFollows,
    NumUnfollows,
    NumUnfollowBacks,
    NumQual yFollowRece ved,
    NumQuoteRet ets,
    NumFavor es,
    NumRepl es,
    NumLog nT et mpress ons,
    NumT et mpress ons,
    NumLog nDays,
    NumUserAct veM nutes,
    NumMuted,
    NumSpamBlocked,
    NumMuteBacks,
    NumSpamBlockedBacks,
    NumWasMutualFollo d,
    NumWasMutualUnfollo d,
    NumWasUnfollo d
  )
  // based off users ce, and each feature represents t  cumulat ve 'sent' counts
  val userStatusFeatures = Set(
    Favor es,
    Follo rs,
    Follow ng,
    T ets
  )
  // rat o features created from comb n ng ot r features
  val userRat oFeatures = Set(MutualFollowsFollow ng, Follo rsFollow ngs)
  // features related to user log n  tory
  val userResurrect onFeatures: Set[Feature[User d,  nt]] = Set(
    DaysS nceRecentResurrect on
  )

  // real-t    aggregate features borro d from t  l nes
  val authorAggregateFeatures = Set(
    Engage ntsRece vedByAuthorRealT  Aggregates.FeaturesAsDataRecord,
    Negat veEngage ntsRece vedByAuthorRealT  Aggregates.FeaturesAsDataRecord,
  )

  val t  l nesAuthorAggregateFeatures = Or g nalAuthorAggregates.FeaturesAsDataRecord

  val userAuthorUserStateRealT  AggregatesFeature: Set[Feature[User d, DataRecord]] = Set(
    UserEngage ntAuthorUserStateRealT  Aggregates.FeaturesAsDataRecord,
    UserNegat veEngage ntAuthorUserStateRealT  Aggregates.FeaturesAsDataRecord
  )
  // post nux per-user offl ne features
  val userOffl neFeatures = Set(
    NumFollow ngs,
    NumFollo rs,
    NumMutualFollows,
    T epCred,
    UserState,
    Language,
    Country,
    MutualFollowsOverFollow ngRat o,
    MutualFollowsOverFollo rsRat o,
    Follo rsOverFollow ngRat o,
  )
  // matc d post nux offl ne features bet en user and cand date
  val userCand datePostNuxEdgeFeatures = Set(
    HaveSa UserState,
    HaveSa Language,
    HaveSa Country,
    NumFollow ngsGap,
    NumFollo rsGap,
    NumMutualFollowsGap,
    T epCredGap,
  )
  // user algor hm aggregate features
  val userAlgor hmAggregateFeatures = Set(
     mpress ons,
    Follows,
    FollowRate,
    FollowsW hT et mpress ons,
    FollowsW hT etFavs,
    FollowsT etFavRate,
    FollowsT etRepl es,
    FollowsT etReplyRate,
    FollowsT etRet ets,
    FollowsT etRet etRate,
    HasForwardEngage nts,
    HasReverseEngage nts,
    HasAnyEngage nts,
  )
  val userKeyedFeatures = userRat oFeatures ++ userOffl neFeatures
  val wtf mpress onUserFeatures =
    Set(Ho T  l neWtfCand dateCounts, Ho T  l neWtfLatestT  stamp)
  val wtf mpress onCand dateFeatures =
    Set(Ho T  l neWtfCand date mpress onCounts, Ho T  l neWtfCand date mpress onLatestT  stamp)
  val s mclusterUV ntersect onFeatures = Set(
     ntersect onCluster ds,
    Has ntersect on,
     ntersect onUserFollowScore,
     ntersect onUserFavScore,
     ntersect onCand dateKnownForScore,
     ntersect onUserFollowCand dateKnownForScore,
     ntersect onUserFavCand dateKnownForScore
  )

  // Cl ent features
  val cl entFeatures = Set(
    NumCl ents,
    Pr maryCl ent,
    Pr maryCl entVers on,
    FullPr maryCl entVers on,
    Pr maryDev ceManufacturer,
    Pr maryMob leSdkVers on,
    SecondaryCl ent
  )
}
