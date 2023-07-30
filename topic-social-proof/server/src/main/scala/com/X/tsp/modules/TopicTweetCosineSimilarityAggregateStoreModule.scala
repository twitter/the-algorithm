package com.X.tsp.modules

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.simclusters_v2.common.TweetId
import com.X.simclusters_v2.thriftscala.Score
import com.X.simclusters_v2.thriftscala.ScoreId
import com.X.simclusters_v2.thriftscala.TopicId
import com.X.storehaus.ReadableStore
import com.X.tsp.stores.TopicTweetsCosineSimilarityAggregateStore
import com.X.tsp.stores.TopicTweetsCosineSimilarityAggregateStore.ScoreKey

object TopicTweetCosineSimilarityAggregateStoreModule extends XModule {

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
