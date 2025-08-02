package com.twitter.simclustersann.modules

import com.google.inject.Provides
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.common.ClusterId
import com.twitter.simclusters_v2.common.SimClustersEmbedding
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.twitter.storehaus.ReadableStore
import javatwitter.inject.Singleton
import com.twitter.simclustersann.candidate_source.ApprotwitterimateCosineSimilarity
import com.twitter.simclustersann.candidate_source.EtwitterperimentalApprotwitterimateCosineSimilarity
import com.twitter.simclustersann.candidate_source.OptimizedApprotwitterimateCosineSimilarity
import com.twitter.simclustersann.candidate_source.SimClustersANNCandidateSource

object SimClustersANNCandidateSourceModule etwittertends TwitterModule {

  val acsFlag = flag[String](
    name = "approtwitterimate_cosine_similarity",
    default = "original",
    help =
      "Select different implementations of the approtwitterimate cosine similarity algorithm, for testing optimizations",
  )
  @Singleton
  @Provides
  def provides(
    embeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding],
    cachedClusterTweetIndetwitterStore: ReadableStore[ClusterId, Seq[(TweetId, Double)]],
    statsReceiver: StatsReceiver
  ): SimClustersANNCandidateSource = {

    val approtwitterimateCosineSimilarity = acsFlag() match {
      case "original" => ApprotwitterimateCosineSimilarity
      case "optimized" => OptimizedApprotwitterimateCosineSimilarity
      case "etwitterperimental" => EtwitterperimentalApprotwitterimateCosineSimilarity
      case _ => ApprotwitterimateCosineSimilarity
    }

    new SimClustersANNCandidateSource(
      approtwitterimateCosineSimilarity = approtwitterimateCosineSimilarity,
      clusterTweetCandidatesStore = cachedClusterTweetIndetwitterStore,
      simClustersEmbeddingStore = embeddingStore,
      statsReceiver = statsReceiver.scope("simClustersANNCandidateSource")
    )
  }
}
