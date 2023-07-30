package com.X.timelineranker.parameters

import com.X.finagle.stats.StatsReceiver
import com.X.servo.decider.DeciderGateBuilder
import com.X.timelineranker.parameters.entity_tweets.EntityTweetsProduction
import com.X.timelineranker.parameters.recap.RecapProduction
import com.X.timelineranker.parameters.recap_author.RecapAuthorProduction
import com.X.timelineranker.parameters.recap_hydration.RecapHydrationProduction
import com.X.timelineranker.parameters.in_network_tweets.InNetworkTweetProduction
import com.X.timelineranker.parameters.revchron.ReverseChronProduction
import com.X.timelineranker.parameters.uteg_liked_by_tweets.UtegLikedByTweetsProduction
import com.X.timelineranker.parameters.monitoring.MonitoringProduction
import com.X.timelines.configapi.CompositeConfig
import com.X.timelines.configapi.Config

/**
 * Builds global composite config containing prioritized "layers" of parameter overrides
 * based on whitelists, experiments, and deciders. Generated config can be used in tests with
 * mocked decider and whitelist.
 */
class ConfigBuilder(deciderGateBuilder: DeciderGateBuilder, statsReceiver: StatsReceiver) {

  /**
   * Production config which includes all configs which contribute to production behavior. At
   * minimum, it should include all configs containing decider-based param overrides.
   *
   * It is important that the production config include all production param overrides as it is
   * used to build holdback experiment configs; If the production config doesn't include all param
   * overrides supporting production behavior then holdback experiment "production" buckets will
   * not reflect production behavior.
   */
  val prodConfig: Config = new CompositeConfig(
    Seq(
      new RecapProduction(deciderGateBuilder, statsReceiver).config,
      new InNetworkTweetProduction(deciderGateBuilder).config,
      new ReverseChronProduction(deciderGateBuilder).config,
      new EntityTweetsProduction(deciderGateBuilder).config,
      new RecapAuthorProduction(deciderGateBuilder).config,
      new RecapHydrationProduction(deciderGateBuilder).config,
      new UtegLikedByTweetsProduction(deciderGateBuilder).config,
      MonitoringProduction.config
    ),
    "prodConfig"
  )

  val whitelistConfig: Config = new CompositeConfig(
    Seq(
      // No whitelists configured at present.
    ),
    "whitelistConfig"
  )

  val rootConfig: Config = new CompositeConfig(
    Seq(
      whitelistConfig,
      prodConfig
    ),
    "rootConfig"
  )
}
