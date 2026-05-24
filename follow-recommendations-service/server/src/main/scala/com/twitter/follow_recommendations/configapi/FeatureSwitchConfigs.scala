package com.twitter.follow_recommendations.configapi

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.candidate_sources.base.SocialProofEnforcedCandidateSourceFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.crowd_search_accounts.CrowdSearchAccountsFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopGeoQualityFollowSourceFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.top_organic_follows_accounts.TopOrganicFollowsAccountsFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.geo.PopGeoSourceFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.ppmi_locale_follow.PPMILocaleFollowSourceFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.real_graph.RealGraphOonFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.recent_engagement.RepeatedProfileVisitsFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.sims.SimsSourceFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.sims_expansion.RecentEngagementSimilarUsersFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.sims_expansion.SimsExpansionFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.socialgraph.RecentFollowingRecentFollowingExpansionSourceFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.stp.OfflineStpSourceFsConfig
import com.twitter.follow_recommendations.common.candidate_sources.stp.OnlineSTPSourceFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.triangular_loops.TriangularLoopsFSConfig
import com.twitter.follow_recommendations.common.candidate_sources.user_user_graph.UserUserGraphFSConfig
import com.twitter.follow_recommendations.common.feature_hydration.sources.FeatureHydrationSourcesFSConfig
import com.twitter.follow_recommendations.common.rankers.weighted_candidate_source_ranker.WeightedCandidateSourceRankerFSConfig
import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.follow_recommendations.flows.content_recommender_flow.ContentRecommenderFlowFSConfig
import com.twitter.follow_recommendations.common.predicates.gizmoduck.GizmoduckPredicateFSConfig
import com.twitter.follow_recommendations.common.predicates.hss.HssPredicateFSConfig
import com.twitter.follow_recommendations.common.predicates.sgs.SgsPredicateFSConfig
import com.twitter.follow_recommendations.flows.post_nux_ml.PostNuxMlFlowFSConfig
import com.twitter.logging.Logger
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeatureSwitchConfigs @Inject() (
  globalFeatureSwitchConfig: GlobalFeatureSwitchConfig,
  featureHydrationSourcesFSConfig: FeatureHydrationSourcesFSConfig,
  weightedCandidateSourceRankerFSConfig: WeightedCandidateSourceRankerFSConfig,
  // Flow related config
  contentRecommenderFlowFSConfig: ContentRecommenderFlowFSConfig,
  postNuxMlFlowFSConfig: PostNuxMlFlowFSConfig,
  // Candidate source related config
  crowdSearchAccountsFSConfig: CrowdSearchAccountsFSConfig,
  offlineStpSourceFsConfig: OfflineStpSourceFsConfig,
  onlineSTPSourceFSConfig: OnlineSTPSourceFSConfig,
  popGeoSourceFSConfig: PopGeoSourceFSConfig,
  popGeoQualityFollowFSConfig: PopGeoQualityFollowSourceFSConfig,
  realGraphOonFSConfig: RealGraphOonFSConfig,
  repeatedProfileVisitsFSConfig: RepeatedProfileVisitsFSConfig,
  recentEngagementSimilarUsersFSConfig: RecentEngagementSimilarUsersFSConfig,
  recentFollowingRecentFollowingExpansionSourceFSConfig: RecentFollowingRecentFollowingExpansionSourceFSConfig,
  simsExpansionFSConfig: SimsExpansionFSConfig,
  simsSourceFSConfig: SimsSourceFSConfig,
  socialProofEnforcedCandidateSourceFSConfig: SocialProofEnforcedCandidateSourceFSConfig,
  triangularLoopsFSConfig: TriangularLoopsFSConfig,
  userUserGraphFSConfig: UserUserGraphFSConfig,
  // Predicate related configs
  gizmoduckPredicateFSConfig: GizmoduckPredicateFSConfig,
  hssPredicateFSConfig: HssPredicateFSConfig,
  sgsPredicateFSConfig: SgsPredicateFSConfig,
  ppmiLocaleSourceFSConfig: PPMILocaleFollowSourceFSConfig,
  topOrganicFollowsAccountsFSConfig: TopOrganicFollowsAccountsFSConfig,
  statsReceiver: StatsReceiver) {

  val logger = Logger(classOf[FeatureSwitchConfigs])

  val mergedFSConfig =
    FeatureSwitchConfig.merge(
      Seq(
        globalFeatureSwitchConfig,
        featureHydrationSourcesFSConfig,
        weightedCandidateSourceRankerFSConfig,
        // Flow related config
        contentRecommenderFlowFSConfig,
        postNuxMlFlowFSConfig,
        // Candidate source related config
        crowdSearchAccountsFSConfig,
        offlineStpSourceFsConfig,
        onlineSTPSourceFSConfig,
        popGeoSourceFSConfig,
        popGeoQualityFollowFSConfig,
        realGraphOonFSConfig,
        repeatedProfileVisitsFSConfig,
        recentEngagementSimilarUsersFSConfig,
        recentFollowingRecentFollowingExpansionSourceFSConfig,
        simsExpansionFSConfig,
        simsSourceFSConfig,
        socialProofEnforcedCandidateSourceFSConfig,
        triangularLoopsFSConfig,
        userUserGraphFSConfig,
        // Predicate related configs:
        gizmoduckPredicateFSConfig,
        hssPredicateFSConfig,
        sgsPredicateFSConfig,
        ppmiLocaleSourceFSConfig,
        topOrganicFollowsAccountsFSConfig,
      )
    )

  /**
   * enum params have to be listed in this main file together as otherwise we'll have to pass in
   * some signature like `Seq[FSEnumParams[_]]` which are generics of generics and won't compile.
   * we only have enumFsParams from globalFeatureSwitchConfig at the moment
   */
  val enumOverrides = globalFeatureSwitchConfig.enumFsParams.flatMap { enumParam =>
    FeatureSwitchOverrideUtil.getEnumFSOverrides(statsReceiver, logger, enumParam)
  }

  val gatedOverrides = mergedFSConfig.gatedOverridesMap.flatMap {
    case (fsName, overrides) =>
      FeatureSwitchOverrideUtil.gatedOverrides(fsName, overrides: _*)
  }

  val enumSeqOverrides = globalFeatureSwitchConfig.enumSeqFsParams.flatMap { enumSeqParam =>
    FeatureSwitchOverrideUtil.getEnumSeqFSOverrides(statsReceiver, logger, enumSeqParam)
  }

  val overrides =
    FeatureSwitchOverrideUtil
      .getBooleanFSOverrides(mergedFSConfig.booleanFSParams: _*) ++
      FeatureSwitchOverrideUtil
        .getBoundedIntFSOverrides(mergedFSConfig.intFSParams: _*) ++
      FeatureSwitchOverrideUtil
        .getBoundedLongFSOverrides(mergedFSConfig.longFSParams: _*) ++
      FeatureSwitchOverrideUtil
        .getBoundedDoubleFSOverrides(mergedFSConfig.doubleFSParams: _*) ++
      FeatureSwitchOverrideUtil
        .getDurationFSOverrides(mergedFSConfig.durationFSParams: _*) ++
      FeatureSwitchOverrideUtil
        .getBoundedOptionalDoubleOverrides(mergedFSConfig.optionalDoubleFSParams: _*) ++
      FeatureSwitchOverrideUtil.getStringSeqFSOverrides(mergedFSConfig.stringSeqFSParams: _*) ++
      enumOverrides ++
      gatedOverrides ++
      enumSeqOverrides

  val config = BaseConfigBuilder(overrides).build("FollowRecommendationServiceFeatureSwitches")
}
