package com.X.simclustersann.modules

import com.google.inject.Provides
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.simclusters_v2.common.ClusterId
import com.X.simclusters_v2.common.SimClustersEmbedding
import com.X.simclusters_v2.common.TweetId
import com.X.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.X.storehaus.ReadableStore
import javax.inject.Singleton
import com.X.simclustersann.candidate_source.ApproximateCosineSimilarity
import com.X.simclustersann.candidate_source.ExperimentalApproximateCosineSimilarity
import com.X.simclustersann.candidate_source.OptimizedApproximateCosineSimilarity
import com.X.simclustersann.candidate_source.SimClustersANNCandidateSource

object SimClustersANNCandidateSourceModule extends XModule {

  val acsFlag = flag[String](
    name = "approximate_cosine_similarity",
    default = "original",
    help =
      "Select different implementations of the approximate cosine similarity algorithm, for testing optimizations",
  )
  @Singleton
  @Provides
  def provides(
    embeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding],
    cachedClusterTweetIndexStore: ReadableStore[ClusterId, Seq[(TweetId, Double)]],
    statsReceiver: StatsReceiver
  ): SimClustersANNCandidateSource = {

    val approximateCosineSimilarity = acsFlag() match {
      case "original" => ApproximateCosineSimilarity
      case "optimized" => OptimizedApproximateCosineSimilarity
      case "experimental" => ExperimentalApproximateCosineSimilarity
      case _ => ApproximateCosineSimilarity
    }

    new SimClustersANNCandidateSource(
      approximateCosineSimilarity = approximateCosineSimilarity,
      clusterTweetCandidatesStore = cachedClusterTweetIndexStore,
      simClustersEmbeddingStore = embeddingStore,
      statsReceiver = statsReceiver.scope("simClustersANNCandidateSource")
    )
  }
}
