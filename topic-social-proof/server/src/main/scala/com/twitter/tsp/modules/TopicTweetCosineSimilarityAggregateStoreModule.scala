package com.twitter.tsp.modules

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.thriftscala.Score
import com.twitter.simclusters_v2.thriftscala.ScoreId
import com.twitter.simclusters_v2.thriftscala.TopicId
import com.twitter.storehaus.ReadableStore
import com.twitter.tsp.stores.TopicTweetsCosineSimilarityAggregateStore
import com.twitter.tsp.stores.TopicTweetsCosineSimilarityAggregateStore.ScoreKey

object TopicTweetCosineSimilarityAggregateStoreModule extends TwitterModule {

  @Provides
  @Singleton
  def providesTopicTweetCosineSimilarityAggregateStore(
    representationScorerStore: ReadableStore[ScoreId, Score],
    statsReceiver: StatsReceiver,
  ): ReadableStore[(TopicId, TweetId, Seq[ScoreKey]), Map[ScoreKey, Double]] = {
    TopicTweetsCosineSimilarityAggregateStore(representationScorerStore)(
      statsReceiver.scope("topicTweetsCosineSimilarityAggregateStore"))
  }
}
