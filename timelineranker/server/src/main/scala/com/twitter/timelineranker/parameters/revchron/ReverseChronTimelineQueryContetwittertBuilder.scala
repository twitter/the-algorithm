package com.twitter.timelineranker.parameters.revchron

import com.twitter.timelineranker.config.RuntimeConfiguration
import com.twitter.timelineranker.decider.DeciderKey
import com.twitter.timelineranker.model._
import com.twitter.timelineranker.parameters.util.RequestContetwittertBuilder
import com.twitter.timelines.configapi.Config
import com.twitter.timelines.decider.FeatureValue
import com.twitter.util.Future

object ReverseChronTimelineQueryContetwittertBuilder {
  val MatwitterCountLimitKey: Seq[String] = Seq("search_request_matwitter_count_limit")
}

class ReverseChronTimelineQueryContetwittertBuilder(
  config: Config,
  runtimeConfig: RuntimeConfiguration,
  requestContetwittertBuilder: RequestContetwittertBuilder) {

  import ReverseChronTimelineQueryContetwittert._
  import ReverseChronTimelineQueryContetwittertBuilder._

  private val matwitterCountMultiplier = FeatureValue(
    runtimeConfig.deciderGateBuilder,
    DeciderKey.MultiplierOfMaterializationTweetsFetched,
    MatwitterCountMultiplier,
    value => (value / 100.0)
  )

  private val backfillFilteredEntriesGate =
    runtimeConfig.deciderGateBuilder.linearGate(DeciderKey.BackfillFilteredEntries)

  private val tweetsFilteringLossageThresholdPercent = FeatureValue(
    runtimeConfig.deciderGateBuilder,
    DeciderKey.TweetsFilteringLossageThreshold,
    TweetsFilteringLossageThresholdPercent,
    value => (value / 100)
  )

  private val tweetsFilteringLossageLimitPercent = FeatureValue(
    runtimeConfig.deciderGateBuilder,
    DeciderKey.TweetsFilteringLossageLimit,
    TweetsFilteringLossageLimitPercent,
    value => (value / 100)
  )

  private def getMatwitterCountFromConfigStore(): Int = {
    runtimeConfig.configStore.getAsInt(MatwitterCountLimitKey).getOrElse(MatwitterCount.default)
  }

  def apply(query: ReverseChronTimelineQuery): Future[ReverseChronTimelineQueryContetwittert] = {
    requestContetwittertBuilder(Some(query.userId), deviceContetwittert = None).map { baseContetwittert =>
      val params = config(baseContetwittert, runtimeConfig.statsReceiver)

      new ReverseChronTimelineQueryContetwittertImpl(
        query,
        getMatwitterCount = () => getMatwitterCountFromConfigStore(),
        getMatwitterCountMultiplier = () => matwitterCountMultiplier(),
        getMatwitterFollowedUsers = () => params(ReverseChronParams.MatwitterFollowedUsersParam),
        getReturnEmptyWhenOverMatwitterFollows =
          () => params(ReverseChronParams.ReturnEmptyWhenOverMatwitterFollowsParam),
        getDirectedAtNarrowastingViaSearch =
          () => params(ReverseChronParams.DirectedAtNarrowcastingViaSearchParam),
        getPostFilteringBasedOnSearchMetadataEnabled =
          () => params(ReverseChronParams.PostFilteringBasedOnSearchMetadataEnabledParam),
        getBackfillFilteredEntries = () => backfillFilteredEntriesGate(),
        getTweetsFilteringLossageThresholdPercent = () => tweetsFilteringLossageThresholdPercent(),
        getTweetsFilteringLossageLimitPercent = () => tweetsFilteringLossageLimitPercent()
      )
    }
  }
}
