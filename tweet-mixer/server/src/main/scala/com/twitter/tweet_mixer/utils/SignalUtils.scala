package com.twitter.tweet_mixer.utils

import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.tweet_mixer.feature.LowSignalUserFeature
import com.twitter.usersignalservice.{thriftscala => uss}

object SignalUtils {

  val ExplicitSignals: Set[uss.SignalType] = Set(
    uss.SignalType.TweetFavorite,
    uss.SignalType.Retweet,
    uss.SignalType.Reply,
    uss.SignalType.TweetBookmarkV1,
    uss.SignalType.TweetShareV1,
  )

  private val SmallFollowGraphSize = 5

  def isLowSignalUser(query: PipelineQuery, followGraphSize: Option[Int]): Boolean = {
    val smallFollowGraph = followGraphSize.exists(_ < SmallFollowGraphSize)
    val lowSignal = query.features.map(_.getOrElse(LowSignalUserFeature, false)).getOrElse(false)
    lowSignal && smallFollowGraph
  }
}
