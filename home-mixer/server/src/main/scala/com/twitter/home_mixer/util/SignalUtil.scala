package com.twitter.home_mixer.util

import com.twitter.home_mixer.model.HomeFeatures.LowSignalUserFeature
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.usersignalservice.{thriftscala => uss}

object SignalUtil {

  val ExplicitSignals: Seq[uss.SignalType] = Seq(
    uss.SignalType.TweetFavorite,
    uss.SignalType.Retweet,
    uss.SignalType.Reply,
    uss.SignalType.TweetBookmarkV1,
    uss.SignalType.TweetShareV1
  )

  private val SmallFollowGraphSize = 5

  def isLowSignalUser(query: PipelineQuery): Boolean = {
    val followGraphSize = query.features.map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty).size)
    val smallFollowGraph = followGraphSize.exists(_ < SmallFollowGraphSize)
    val lowSignal = query.features.map(_.getOrElse(LowSignalUserFeature, false)).getOrElse(false)
    lowSignal && smallFollowGraph
  }
}
