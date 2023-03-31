package com.twitter.simclusters_v2.stores
import com.twitter.bijection.Bufferable
import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.simclusters_v2.common.Language
import com.twitter.simclusters_v2.thriftscala.ClustersUserIsInterestedIn
import com.twitter.simclusters_v2.thriftscala.LeftNode
import com.twitter.simclusters_v2.thriftscala.NounWithFrequencyList
import com.twitter.simclusters_v2.thriftscala.RightNode
import com.twitter.simclusters_v2.thriftscala.RightNodeTypeStruct
import com.twitter.simclusters_v2.thriftscala.RightNodeWithEdgeWeightList
import com.twitter.simclusters_v2.thriftscala.SimilarRightNodes
import com.twitter.simclusters_v2.thriftscala.CandidateTweetsList
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.Apollo
import com.twitter.storehaus_internal.manhattan.ManhattanRO
import com.twitter.storehaus_internal.manhattan.ManhattanROConfig
import com.twitter.storehaus_internal.util.ApplicationID
import com.twitter.storehaus_internal.util.DatasetName
import com.twitter.storehaus_internal.util.HDFSPath
import com.twitter.scalding_internal.multiformat.format.keyval.KeyValInjection.Long2BigEndian
import com.twitter.simclusters_v2.thriftscala.FullClusterId
import com.twitter.simclusters_v2.thriftscala.TopKTweetsWithScores

object MultiTypeGraphStore {

  implicit val leftNodesInject: Injection[LeftNode, Array[Byte]] =
    CompactScalaCodec(LeftNode)
  implicit val truncatedMultiTypeGraphInject: Injection[RightNodeWithEdgeWeightList, Array[Byte]] =
    CompactScalaCodec(RightNodeWithEdgeWeightList)
  implicit val topKNounsListInject: Injection[NounWithFrequencyList, Array[Byte]] =
    CompactScalaCodec(NounWithFrequencyList)
  implicit val rightNodesStructInject: Injection[RightNodeTypeStruct, Array[Byte]] =
    CompactScalaCodec(RightNodeTypeStruct)
  implicit val similarRightNodesStructInject: Injection[SimilarRightNodes, Array[Byte]] =
    CompactScalaCodec(SimilarRightNodes)
  implicit val rightNodesInject: Injection[RightNode, Array[Byte]] =
    CompactScalaCodec(RightNode)
  implicit val tweetCandidatesInject: Injection[CandidateTweetsList, Array[Byte]] =
    CompactScalaCodec(CandidateTweetsList)
  implicit val fullClusterIdInject: Injection[FullClusterId, Array[Byte]] =
    CompactScalaCodec(FullClusterId)
  implicit val topKTweetsWithScoresInject: Injection[TopKTweetsWithScores, Array[Byte]] =
    CompactScalaCodec(TopKTweetsWithScores)
  implicit val clustersUserIsInterestedInInjection: Injection[ClustersUserIsInterestedIn, Array[
    Byte
  ]] =
    CompactScalaCodec(ClustersUserIsInterestedIn)

  private val appId = "multi_type_simclusters"

  def getTruncatedMultiTypeGraphRightNodesForUser(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[LeftNode, RightNodeWithEdgeWeightList] = {
    ManhattanRO.getReadableStoreWithMtls[LeftNode, RightNodeWithEdgeWeightList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName("mts_user_truncated_graph"),
        Apollo
      ),
      mhMtlsParams
    )
  }

  def getTopKNounsForRightNodeType(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[RightNodeTypeStruct, NounWithFrequencyList] = {
    ManhattanRO.getReadableStoreWithMtls[RightNodeTypeStruct, NounWithFrequencyList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName("mts_topk_frequent_nouns"),
        Apollo
      ),
      mhMtlsParams
    )
  }

  def getTopKSimilarRightNodes(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[RightNode, SimilarRightNodes] = {
    ManhattanRO.getReadableStoreWithMtls[RightNode, SimilarRightNodes](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName("mts_topk_similar_right_nodes_scio"),
        Apollo
      ),
      mhMtlsParams
    )
  }

  def getOfflineTweetMTSCandidateStore(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[Long, CandidateTweetsList] = {
    ManhattanRO.getReadableStoreWithMtls[Long, CandidateTweetsList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName("offline_tweet_recommendations_from_mts_consumer_embeddings"),
        Apollo
      ),
      mhMtlsParams
    )
  }

  def getOfflineTweet2020CandidateStore(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[Long, CandidateTweetsList] = {
    ManhattanRO.getReadableStoreWithMtls[Long, CandidateTweetsList](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName("offline_tweet_recommendations_from_interestedin_2020"),
        Apollo
      ),
      mhMtlsParams
    )
  }

  def getVideoViewBasedClusterTopKTweets(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[FullClusterId, TopKTweetsWithScores] = {
    ManhattanRO
      .getReadableStoreWithMtls[FullClusterId, TopKTweetsWithScores](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("video_view_based_cluster_to_tweet_index"),
          Apollo
        ),
        mhMtlsParams
      )
  }

  def getRetweetBasedClusterTopKTweets(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[FullClusterId, TopKTweetsWithScores] = {
    ManhattanRO
      .getReadableStoreWithMtls[FullClusterId, TopKTweetsWithScores](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("retweet_based_simclusters_cluster_to_tweet_index"),
          Apollo
        ),
        mhMtlsParams
      )
  }

  def getReplyBasedClusterTopKTweets(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[FullClusterId, TopKTweetsWithScores] = {
    ManhattanRO
      .getReadableStoreWithMtls[FullClusterId, TopKTweetsWithScores](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("reply_based_simclusters_cluster_to_tweet_index"),
          Apollo
        ),
        mhMtlsParams
      )
  }

  def getPushOpenBasedClusterTopKTweets(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[FullClusterId, TopKTweetsWithScores] = {
    ManhattanRO
      .getReadableStoreWithMtls[FullClusterId, TopKTweetsWithScores](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("push_open_based_simclusters_cluster_to_tweet_index"),
          Apollo
        ),
        mhMtlsParams
      )
  }

  def getAdsFavBasedClusterTopKTweets(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[FullClusterId, TopKTweetsWithScores] = {
    ManhattanRO
      .getReadableStoreWithMtls[FullClusterId, TopKTweetsWithScores](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("ads_fav_based_simclusters_cluster_to_tweet_index"),
          Apollo
        ),
        mhMtlsParams
      )
  }

  def getAdsFavClickBasedClusterTopKTweets(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[FullClusterId, TopKTweetsWithScores] = {
    ManhattanRO
      .getReadableStoreWithMtls[FullClusterId, TopKTweetsWithScores](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("ads_fav_click_based_simclusters_cluster_to_tweet_index"),
          Apollo
        ),
        mhMtlsParams
      )
  }

  def getFTRPop1000BasedClusterTopKTweets(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[FullClusterId, TopKTweetsWithScores] = {
    ManhattanRO
      .getReadableStoreWithMtls[FullClusterId, TopKTweetsWithScores](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("ftr_pop1000_rank_decay_1_1_cluster_to_tweet_index"),
          Apollo
        ),
        mhMtlsParams
      )
  }

  def getFTRPop10000BasedClusterTopKTweets(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[FullClusterId, TopKTweetsWithScores] = {
    ManhattanRO
      .getReadableStoreWithMtls[FullClusterId, TopKTweetsWithScores](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("ftr_pop10000_rank_decay_1_1_cluster_to_tweet_index"),
          Apollo
        ),
        mhMtlsParams
      )
  }

  def getOONFTRPop1000BasedClusterTopKTweets(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[FullClusterId, TopKTweetsWithScores] = {
    ManhattanRO
      .getReadableStoreWithMtls[FullClusterId, TopKTweetsWithScores](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("oon_ftr_pop1000_rnkdecay_cluster_to_tweet_index"),
          Apollo
        ),
        mhMtlsParams
      )
  }

  def getOfflineLogFavBasedTweetBasedClusterTopKTweets(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[FullClusterId, TopKTweetsWithScores] = {
    ManhattanRO
      .getReadableStoreWithMtls[FullClusterId, TopKTweetsWithScores](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("decayed_sum_cluster_to_tweet_index"),
          Apollo
        ),
        mhMtlsParams
      )
  }

  def getGlobalSimClustersLanguageEmbeddings(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[Language, ClustersUserIsInterestedIn] = {
    ManhattanRO
      .getReadableStoreWithMtls[Language, ClustersUserIsInterestedIn](
        ManhattanROConfig(
          HDFSPath(""),
          ApplicationID(appId),
          DatasetName("global_simclusters_language_embeddings"),
          Apollo
        ),
        mhMtlsParams
      )
  }
}
