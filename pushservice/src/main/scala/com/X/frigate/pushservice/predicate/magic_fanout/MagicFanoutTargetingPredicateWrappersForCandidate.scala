package com.X.frigate.pushservice.predicate.magic_fanout

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.store.interests.InterestsLookupRequestWithContext
import com.X.frigate.common.util.FeatureSwitchParams
import com.X.frigate.common.util.MagicFanoutTargetingPredicatesEnum
import com.X.frigate.common.util.MagicFanoutTargetingPredicatesEnum.MagicFanoutTargetingPredicatesEnum
import com.X.frigate.pushservice.model.MagicFanoutEventPushCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate
import com.X.interests.thriftscala.UserInterests
import com.X.storehaus.ReadableStore
import com.X.timelines.configapi.FSEnumParam

object MagicFanoutTargetingPredicateWrappersForCandidate {

  /**
   * Combine Prod and Experimental Targeting predicate logic
   * @return: NamedPredicate[MagicFanoutNewsEventPushCandidate]
   */
  def magicFanoutTargetingPredicate(
    stats: StatsReceiver,
    config: Config
  ): NamedPredicate[MagicFanoutEventPushCandidate] = {
    val name = "magic_fanout_targeting_predicate"
    Predicate
      .fromAsync { candidate: MagicFanoutEventPushCandidate =>
        val mfTargetingPredicateParam = getTargetingPredicateParams(candidate)
        val mfTargetingPredicate = MagicFanoutTargetingPredicateMapForCandidate
          .apply(config)
          .get(candidate.target.params(mfTargetingPredicateParam))
        mfTargetingPredicate match {
          case Some(predicate) =>
            predicate.apply(Seq(candidate)).map(_.head)
          case None =>
            throw new Exception(
              s"MFTargetingPredicateMap doesnt contain value for TargetingParam: ${FeatureSwitchParams.MFTargetingPredicate}")
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }

  private def getTargetingPredicateParams(
    candidate: MagicFanoutEventPushCandidate
  ): FSEnumParam[MagicFanoutTargetingPredicatesEnum.type] = {
    if (candidate.commonRecType == CommonRecommendationType.MagicFanoutSportsEvent) {
      FeatureSwitchParams.MFCricketTargetingPredicate
    } else FeatureSwitchParams.MFTargetingPredicate
  }

  /**
   * SimCluster and ERG and Topic Follows Targeting Predicate
   */
  def simClusterErgTopicFollowsTargetingPredicate(
    implicit stats: StatsReceiver,
    interestsLookupStore: ReadableStore[InterestsLookupRequestWithContext, UserInterests]
  ): NamedPredicate[MagicFanoutEventPushCandidate] = {
    simClusterErgTargetingPredicate
      .or(MagicFanoutPredicatesForCandidate.magicFanoutTopicFollowsTargetingPredicate)
      .withName("sim_cluster_erg_topic_follows_targeting")
  }

  /**
   * SimCluster and ERG and Topic Follows Targeting Predicate
   */
  def simClusterErgTopicFollowsUserFollowsTargetingPredicate(
    implicit stats: StatsReceiver,
    interestsLookupStore: ReadableStore[InterestsLookupRequestWithContext, UserInterests]
  ): NamedPredicate[MagicFanoutEventPushCandidate] = {
    simClusterErgTopicFollowsTargetingPredicate
      .or(
        MagicFanoutPredicatesForCandidate.followRankThreshold(
          PushFeatureSwitchParams.MagicFanoutRealgraphRankThreshold))
      .withName("sim_cluster_erg_topic_follows_user_follows_targeting")
  }

  /**
   * SimCluster and ERG Targeting Predicate
   */
  def simClusterErgTargetingPredicate(
    implicit stats: StatsReceiver
  ): NamedPredicate[MagicFanoutEventPushCandidate] = {
    MagicFanoutPredicatesForCandidate.magicFanoutSimClusterTargetingPredicate
      .or(MagicFanoutPredicatesForCandidate.magicFanoutErgInterestRankThresholdPredicate)
      .withName("sim_cluster_erg_targeting")
  }
}

/**
 * Object to initalze and get predicate map
 */
object MagicFanoutTargetingPredicateMapForCandidate {

  /**
   * Called from the Config.scala at the time of server initialization
   * @param statsReceiver: implict stats receiver
   * @return Map[MagicFanoutTargetingPredicatesEnum, NamedPredicate[MagicFanoutNewsEventPushCandidate]]
   */
  def apply(
    config: Config
  ): Map[MagicFanoutTargetingPredicatesEnum, NamedPredicate[MagicFanoutEventPushCandidate]] = {
    Map(
      MagicFanoutTargetingPredicatesEnum.SimClusterAndERGAndTopicFollows -> MagicFanoutTargetingPredicateWrappersForCandidate
        .simClusterErgTopicFollowsTargetingPredicate(
          config.statsReceiver,
          config.interestsWithLookupContextStore),
      MagicFanoutTargetingPredicatesEnum.SimClusterAndERG -> MagicFanoutTargetingPredicateWrappersForCandidate
        .simClusterErgTargetingPredicate(config.statsReceiver),
      MagicFanoutTargetingPredicatesEnum.SimCluster -> MagicFanoutPredicatesForCandidate
        .magicFanoutSimClusterTargetingPredicate(config.statsReceiver),
      MagicFanoutTargetingPredicatesEnum.ERG -> MagicFanoutPredicatesForCandidate
        .magicFanoutErgInterestRankThresholdPredicate(config.statsReceiver),
      MagicFanoutTargetingPredicatesEnum.TopicFollows -> MagicFanoutPredicatesForCandidate
        .magicFanoutTopicFollowsTargetingPredicate(
          config.statsReceiver,
          config.interestsWithLookupContextStore),
      MagicFanoutTargetingPredicatesEnum.UserFollows -> MagicFanoutPredicatesForCandidate
        .followRankThreshold(
          PushFeatureSwitchParams.MagicFanoutRealgraphRankThreshold
        )(config.statsReceiver),
      MagicFanoutTargetingPredicatesEnum.SimClusterAndERGAndTopicFollowsAndUserFollows ->
        MagicFanoutTargetingPredicateWrappersForCandidate
          .simClusterErgTopicFollowsUserFollowsTargetingPredicate(
            config.statsReceiver,
            config.interestsWithLookupContextStore
          )
    )
  }
}
