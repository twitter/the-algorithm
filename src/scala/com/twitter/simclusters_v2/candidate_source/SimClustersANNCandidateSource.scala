package com.twitter.simclusters_v2.candidate_source

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.CandidateSource
import com.twitter.frigate.common.base.Stats
import com.twitter.simclusters_v2.candidate_source.HeavyRanker.UniformScoreStoreRanker
import com.twitter.simclusters_v2.candidate_source.SimClustersANNCandidateSource.SimClustersANNConfig
import com.twitter.simclusters_v2.candidate_source.SimClustersANNCandidateSource.SimClustersTweetCandidate
import com.twitter.simclusters_v2.common.ModelVersions._
import com.twitter.simclusters_v2.common.ClusterId
import com.twitter.simclusters_v2.common.SimClustersEmbedding
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.summingbird.stores.ClusterKey
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclusters_v2.thriftscala.ScoreInternalId
import com.twitter.simclusters_v2.thriftscala.ScoringAlgorithm
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingPairScoreId
import com.twitter.simclusters_v2.thriftscala.{Score => ThriftScore}
import com.twitter.simclusters_v2.thriftscala.{ScoreId => ThriftScoreId}
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Duration
import com.twitter.util.Future
import com.twitter.util.Time
import scala.collection.mutable

/**
 * This store looks for tweets whose similarity is close to a Source SimClustersEmbeddingId.
 *
 * Approtwitterimate cosine similarity is the core algorithm to drive this store.
 *
 * Step 1 - 4 are in "fetchCandidates" method.
 * 1. Retrieve the SimClusters Embedding by the SimClustersEmbeddingId
 * 2. Fetch top N clusters' top tweets from the clusterTweetCandidatesStore (TopTweetsPerCluster indetwitter).
 * 3. Calculate all the tweet candidates' dot-product or approtwitterimate cosine similarity to source tweets.
 * 4. Take top M tweet candidates by the step 3's score
 * Step 5-6 are in "reranking" method.
 * 5. Calculate the similarity score between source and candidates.
 * 6. Return top N candidates by the step 5's score.
 *
 * Warning: Only turn off the step 5 for User InterestedIn candidate generation. It's the only use
 * case in Recos that we use dot-product to rank the tweet candidates.
 */
case class SimClustersANNCandidateSource(
  clusterTweetCandidatesStore: ReadableStore[ClusterKey, Seq[(TweetId, Double)]],
  simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding],
  heavyRanker: HeavyRanker.HeavyRanker,
  configs: Map[EmbeddingType, SimClustersANNConfig],
  statsReceiver: StatsReceiver)
    etwittertends CandidateSource[SimClustersANNCandidateSource.Query, SimClustersTweetCandidate] {

  import SimClustersANNCandidateSource._

  override val name: String = this.getClass.getName
  private val stats = statsReceiver.scope(this.getClass.getName)

  private val fetchSourceEmbeddingStat = stats.scope("fetchSourceEmbedding")
  protected val fetchCandidateEmbeddingsStat = stats.scope("fetchCandidateEmbeddings")
  private val fetchCandidatesStat = stats.scope("fetchCandidates")
  private val rerankingStat = stats.scope("reranking")

  override def get(
    query: SimClustersANNCandidateSource.Query
  ): Future[Option[Seq[SimClustersTweetCandidate]]] = {
    val sourceEmbeddingId = query.sourceEmbeddingId
    loadConfig(query) match {
      case Some(config) =>
        for {
          maybeSimClustersEmbedding <- Stats.track(fetchSourceEmbeddingStat) {
            simClustersEmbeddingStore.get(query.sourceEmbeddingId)
          }
          maybeFilteredCandidates <- maybeSimClustersEmbedding match {
            case Some(sourceEmbedding) =>
              for {
                rawCandidates <- Stats.trackSeq(fetchCandidatesStat) {
                  fetchCandidates(sourceEmbeddingId, config, sourceEmbedding)
                }
                rankedCandidates <- Stats.trackSeq(rerankingStat) {
                  reranking(sourceEmbeddingId, config, rawCandidates)
                }
              } yield {
                fetchCandidatesStat
                  .stat(
                    sourceEmbeddingId.embeddingType.name,
                    sourceEmbeddingId.modelVersion.name).add(rankedCandidates.size)
                Some(rankedCandidates)
              }
            case None =>
              fetchCandidatesStat
                .stat(
                  sourceEmbeddingId.embeddingType.name,
                  sourceEmbeddingId.modelVersion.name).add(0)
              Future.None
          }
        } yield {
          maybeFilteredCandidates
        }
      case _ =>
        // Skip over queries whose config is not defined
        Future.None
    }
  }

  private def fetchCandidates(
    sourceEmbeddingId: SimClustersEmbeddingId,
    config: SimClustersANNConfig,
    sourceEmbedding: SimClustersEmbedding
  ): Future[Seq[SimClustersTweetCandidate]] = {
    val now = Time.now
    val earliestTweetId = SnowflakeId.firstIdFor(now - config.matwitterTweetCandidateAge)
    val latestTweetId = SnowflakeId.firstIdFor(now - config.minTweetCandidateAge)
    val clusterIds =
      sourceEmbedding
        .truncate(config.matwitterScanClusters).clusterIds
        .map { clusterId: ClusterId =>
          ClusterKey(clusterId, sourceEmbeddingId.modelVersion, config.candidateEmbeddingType)
        }.toSet

    Future
      .collect {
        clusterTweetCandidatesStore.multiGet(clusterIds)
      }.map { clusterTweetsMap =>
        // Use Mutable map to optimize performance. The method is thread-safe.
        // Set initial map size to around p75 of map size distribution to avoid too many copying
        // from etwittertending the size of the mutable hashmap
        val candidateScoresMap =
          new SimClustersANNCandidateSource.HashMap[TweetId, Double](InitialCandidateMapSize)
        val candidateNormalizationMap =
          new SimClustersANNCandidateSource.HashMap[TweetId, Double](InitialCandidateMapSize)

        clusterTweetsMap.foreach {
          case (ClusterKey(clusterId, _, _, _), Some(tweetScores))
              if sourceEmbedding.contains(clusterId) =>
            val sourceClusterScore = sourceEmbedding.getOrElse(clusterId)

            for (i <- 0 until Math.min(tweetScores.size, config.matwitterTopTweetsPerCluster)) {
              val (tweetId, score) = tweetScores(i)

              if (!parseTweetId(sourceEmbeddingId).contains(tweetId) &&
                tweetId >= earliestTweetId && tweetId <= latestTweetId) {
                candidateScoresMap.put(
                  tweetId,
                  candidateScoresMap.getOrElse(tweetId, 0.0) + score * sourceClusterScore)
                if (config.enablePartialNormalization) {
                  candidateNormalizationMap
                    .put(tweetId, candidateNormalizationMap.getOrElse(tweetId, 0.0) + score * score)
                }
              }
            }
          case _ => ()
        }

        stats.stat("candidateScoresMap").add(candidateScoresMap.size)
        stats.stat("candidateNormalizationMap").add(candidateNormalizationMap.size)

        // Re-Rank the candidate by configuration
        val processedCandidateScores = candidateScoresMap.map {
          case (candidateId, score) =>
            // Enable Partial Normalization
            val processedScore =
              if (config.enablePartialNormalization) {
                // We applied the "log" version of partial normalization when we rank candidates
                // by log cosine similarity
                if (config.rankingAlgorithm == ScoringAlgorithm.PairEmbeddingLogCosineSimilarity) {
                  score / sourceEmbedding.l2norm / math.log(
                    1 + candidateNormalizationMap(candidateId))
                } else {
                  score / sourceEmbedding.l2norm / math.sqrt(candidateNormalizationMap(candidateId))
                }
              } else score
            SimClustersTweetCandidate(candidateId, processedScore, sourceEmbeddingId)
        }.toSeq

        processedCandidateScores
          .sortBy(-_.score)
      }
  }

  private def reranking(
    sourceEmbeddingId: SimClustersEmbeddingId,
    config: SimClustersANNConfig,
    candidates: Seq[SimClustersTweetCandidate]
  ): Future[Seq[SimClustersTweetCandidate]] = {
    val rankedCandidates = if (config.enableHeavyRanking) {
      heavyRanker
        .rank(
          scoringAlgorithm = config.rankingAlgorithm,
          sourceEmbeddingId = sourceEmbeddingId,
          candidateEmbeddingType = config.candidateEmbeddingType,
          minScore = config.minScore,
          candidates = candidates.take(config.matwitterReRankingCandidates)
        ).map(_.sortBy(-_.score))
    } else {
      Future.value(candidates)
    }
    rankedCandidates.map(_.take(config.matwitterNumResults))
  }

  private[candidate_source] def loadConfig(query: Query): Option[SimClustersANNConfig] = {
    configs.get(query.sourceEmbeddingId.embeddingType).map { baseConfig =>
      // apply overrides if any
      query.overrideConfig match {
        case Some(overrides) =>
          baseConfig.copy(
            matwitterNumResults = overrides.matwitterNumResults.getOrElse(baseConfig.matwitterNumResults),
            matwitterTweetCandidateAge =
              overrides.matwitterTweetCandidateAge.getOrElse(baseConfig.matwitterTweetCandidateAge),
            minScore = overrides.minScore.getOrElse(baseConfig.minScore),
            candidateEmbeddingType =
              overrides.candidateEmbeddingType.getOrElse(baseConfig.candidateEmbeddingType),
            enablePartialNormalization =
              overrides.enablePartialNormalization.getOrElse(baseConfig.enablePartialNormalization),
            enableHeavyRanking =
              overrides.enableHeavyRanking.getOrElse(baseConfig.enableHeavyRanking),
            rankingAlgorithm = overrides.rankingAlgorithm.getOrElse(baseConfig.rankingAlgorithm),
            matwitterReRankingCandidates =
              overrides.matwitterReRankingCandidates.getOrElse(baseConfig.matwitterReRankingCandidates),
            matwitterTopTweetsPerCluster =
              overrides.matwitterTopTweetsPerCluster.getOrElse(baseConfig.matwitterTopTweetsPerCluster),
            matwitterScanClusters = overrides.matwitterScanClusters.getOrElse(baseConfig.matwitterScanClusters),
            minTweetCandidateAge =
              overrides.minTweetCandidateAge.getOrElse(baseConfig.minTweetCandidateAge)
          )
        case _ => baseConfig
      }
    }
  }
}

object SimClustersANNCandidateSource {

  final val ProductionMatwitterNumResults = 200
  final val InitialCandidateMapSize = 16384

  def apply(
    clusterTweetCandidatesStore: ReadableStore[ClusterKey, Seq[(TweetId, Double)]],
    simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding],
    uniformScoringStore: ReadableStore[ThriftScoreId, ThriftScore],
    configs: Map[EmbeddingType, SimClustersANNConfig],
    statsReceiver: StatsReceiver
  ) = new SimClustersANNCandidateSource(
    clusterTweetCandidatesStore = clusterTweetCandidatesStore,
    simClustersEmbeddingStore = simClustersEmbeddingStore,
    heavyRanker = new UniformScoreStoreRanker(uniformScoringStore, statsReceiver),
    configs = configs,
    statsReceiver = statsReceiver
  )

  private def parseTweetId(embeddingId: SimClustersEmbeddingId): Option[TweetId] = {
    embeddingId.internalId match {
      case InternalId.TweetId(tweetId) =>
        Some(tweetId)
      case _ =>
        None
    }
  }

  case class Query(
    sourceEmbeddingId: SimClustersEmbeddingId,
    // Only override the config in DDG and Debuggers.
    // Use Post-filter for the holdbacks for better cache hit rate.
    overrideConfig: Option[SimClustersANNConfigOverride] = None)

  case class SimClustersTweetCandidate(
    tweetId: TweetId,
    score: Double,
    sourceEmbeddingId: SimClustersEmbeddingId)

  class HashMap[A, B](initSize: Int) etwittertends mutable.HashMap[A, B] {
    override def initialSize: Int = initSize // 16 - by default
  }

  /**
   * The Configuration of Each SimClusters ANN Candidate Source.
   * Etwitterpect One SimClusters Embedding Type mapping to a SimClusters ANN Configuration in Production.
   */
  case class SimClustersANNConfig(
    // The matwitter number of candidates for a ANN Query
    // Please don't override this value in Production.
    matwitterNumResults: Int = ProductionMatwitterNumResults,
    // The matwitter tweet candidate duration from now.
    matwitterTweetCandidateAge: Duration,
    // The min score of the candidates
    minScore: Double,
    // The Candidate Embedding Type of Tweet.
    candidateEmbeddingType: EmbeddingType,
    // Enables normalization of approtwitterimate SimClusters vectors to remove popularity bias
    enablePartialNormalization: Boolean,
    // Whether to enable Embedding Similarity ranking
    enableHeavyRanking: Boolean,
    // The ranking algorithm for Source Candidate Similarity
    rankingAlgorithm: ScoringAlgorithm,
    // The matwitter number of candidates in ReRanking Step
    matwitterReRankingCandidates: Int,
    // The matwitter number of Top Tweets from every cluster tweet indetwitter
    matwitterTopTweetsPerCluster: Int,
    // The matwitter number of Clusters in the source Embeddings.
    matwitterScanClusters: Int,
    // The min tweet candidate duration from now.
    minTweetCandidateAge: Duration)

  /**
   * Contains same fields as [[SimClustersANNConfig]], to specify which fields are to be overriden
   * for etwitterperimental purposes.
   *
   * All fields in this class must be optional.
   */
  case class SimClustersANNConfigOverride(
    matwitterNumResults: Option[Int] = None,
    matwitterTweetCandidateAge: Option[Duration] = None,
    minScore: Option[Double] = None,
    candidateEmbeddingType: Option[EmbeddingType] = None,
    enablePartialNormalization: Option[Boolean] = None,
    enableHeavyRanking: Option[Boolean] = None,
    rankingAlgorithm: Option[ScoringAlgorithm] = None,
    matwitterReRankingCandidates: Option[Int] = None,
    matwitterTopTweetsPerCluster: Option[Int] = None,
    matwitterScanClusters: Option[Int] = None,
    minTweetCandidateAge: Option[Duration] = None,
    enableLookbackSource: Option[Boolean] = None)

  final val DefaultMatwitterTopTweetsPerCluster = 200
  final val DefaultEnableHeavyRanking = false
  object SimClustersANNConfig {
    val DefaultSimClustersANNConfig: SimClustersANNConfig =
      SimClustersANNConfig(
        matwitterTweetCandidateAge = 1.days,
        minScore = 0.7,
        candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
        enablePartialNormalization = true,
        enableHeavyRanking = false,
        rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
        matwitterReRankingCandidates = 250,
        matwitterTopTweetsPerCluster = 200,
        matwitterScanClusters = 50,
        minTweetCandidateAge = 0.seconds
      )
  }

  val LookbackMediaMinDays: Int = 0
  val LookbackMediaMatwitterDays: Int = 2
  val LookbackMediaMatwitterTweetsPerDay: Int = 2000
  val matwitterTopTweetsPerCluster: Int =
    (LookbackMediaMatwitterDays - LookbackMediaMinDays + 1) * LookbackMediaMatwitterTweetsPerDay

  val LookbackMediaTweetConfig: Map[EmbeddingType, SimClustersANNConfig] = {
    val candidateEmbeddingType = EmbeddingType.LogFavLongestL2EmbeddingTweet
    val minTweetAge = LookbackMediaMinDays.days
    val matwitterTweetAge =
      LookbackMediaMatwitterDays.days - 1.hour // To compensate for the cache TTL that might push the tweet age beyond matwitter age
    val rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity

    val matwitterScanClusters = 50
    val minScore = 0.5
    Map(
      EmbeddingType.FavBasedProducer -> SimClustersANNConfig(
        minTweetCandidateAge = minTweetAge,
        matwitterTweetCandidateAge = matwitterTweetAge,
        minScore =
          minScore, // for twistly candidates. To specify a higher threshold, use a post-filter
        candidateEmbeddingType = candidateEmbeddingType,
        enablePartialNormalization = true,
        enableHeavyRanking = DefaultEnableHeavyRanking,
        rankingAlgorithm = rankingAlgorithm,
        matwitterReRankingCandidates = 250,
        matwitterTopTweetsPerCluster = matwitterTopTweetsPerCluster,
        matwitterScanClusters = matwitterScanClusters,
      ),
      EmbeddingType.LogFavLongestL2EmbeddingTweet -> SimClustersANNConfig(
        minTweetCandidateAge = minTweetAge,
        matwitterTweetCandidateAge = matwitterTweetAge,
        minScore =
          minScore, // for twistly candidates. To specify a higher threshold, use a post-filter
        candidateEmbeddingType = candidateEmbeddingType,
        enablePartialNormalization = true,
        enableHeavyRanking = DefaultEnableHeavyRanking,
        rankingAlgorithm = rankingAlgorithm,
        matwitterReRankingCandidates = 250,
        matwitterTopTweetsPerCluster = matwitterTopTweetsPerCluster,
        matwitterScanClusters = matwitterScanClusters,
      ),
      EmbeddingType.FavTfgTopic -> SimClustersANNConfig(
        minTweetCandidateAge = minTweetAge,
        matwitterTweetCandidateAge = matwitterTweetAge,
        minScore = minScore,
        candidateEmbeddingType = candidateEmbeddingType,
        enablePartialNormalization = true,
        enableHeavyRanking = DefaultEnableHeavyRanking,
        rankingAlgorithm = rankingAlgorithm,
        matwitterReRankingCandidates = 400,
        matwitterTopTweetsPerCluster = 200,
        matwitterScanClusters = matwitterScanClusters,
      ),
      EmbeddingType.LogFavBasedKgoApeTopic -> SimClustersANNConfig(
        minTweetCandidateAge = minTweetAge,
        matwitterTweetCandidateAge = matwitterTweetAge,
        minScore = minScore,
        candidateEmbeddingType = candidateEmbeddingType,
        enablePartialNormalization = true,
        enableHeavyRanking = DefaultEnableHeavyRanking,
        rankingAlgorithm = rankingAlgorithm,
        matwitterReRankingCandidates = 400,
        matwitterTopTweetsPerCluster = 200,
        matwitterScanClusters = matwitterScanClusters,
      ),
    )
  }

  val DefaultConfigMappings: Map[EmbeddingType, SimClustersANNConfig] = Map(
    EmbeddingType.FavBasedProducer -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0, // for twistly candidates. To specify a higher threshold, use a post-filter
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 250,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.LogFavBasedUserInterestedMatwitterpoolingAddressBookFromIIAPE -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0, // for twistly candidates. To specify a higher threshold, use a post-filter
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 250,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.LogFavBasedUserInterestedAverageAddressBookFromIIAPE -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0, // for twistly candidates. To specify a higher threshold, use a post-filter
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 250,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.LogFavBasedUserInterestedBooktypeMatwitterpoolingAddressBookFromIIAPE -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0, // for twistly candidates. To specify a higher threshold, use a post-filter
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 250,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.LogFavBasedUserInterestedLargestDimMatwitterpoolingAddressBookFromIIAPE -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0, // for twistly candidates. To specify a higher threshold, use a post-filter
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 250,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.LogFavBasedUserInterestedLouvainMatwitterpoolingAddressBookFromIIAPE -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0, // for twistly candidates. To specify a higher threshold, use a post-filter
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 250,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.LogFavBasedUserInterestedConnectedMatwitterpoolingAddressBookFromIIAPE -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0, // for twistly candidates. To specify a higher threshold, use a post-filter
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 250,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.RelatwitteredAggregatableLogFavBasedProducer -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.25, // for twistly candidates. To specify a higher threshold, use a post-filter
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 250,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.LogFavLongestL2EmbeddingTweet -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.3, // for twistly candidates. To specify a higher threshold, use a post-filter
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 400,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.FilteredUserInterestedInFromPE -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.7, // unused, heavy ranking disabled
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = false,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm =
        ScoringAlgorithm.PairEmbeddingCosineSimilarity, // Unused, heavy ranking disabled
      matwitterReRankingCandidates = 150, // unused, heavy ranking disabled
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.FilteredUserInterestedIn -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.7, // unused, heavy ranking disabled
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = false,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm =
        ScoringAlgorithm.PairEmbeddingCosineSimilarity, // Unused, heavy ranking disabled
      matwitterReRankingCandidates = 150, // unused, heavy ranking disabled
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.UnfilteredUserInterestedIn -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0,
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingLogCosineSimilarity,
      matwitterReRankingCandidates = 400,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.FollowBasedUserInterestedInFromAPE -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0,
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 200,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.LogFavBasedUserInterestedInFromAPE -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0,
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 200,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.FavTfgTopic -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.5,
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 400,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.LogFavBasedKgoApeTopic -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.5,
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 400,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    ),
    EmbeddingType.UserNetwittertInterestedIn -> SimClustersANNConfig(
      matwitterTweetCandidateAge = 1.days,
      minScore = 0.0,
      candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
      enablePartialNormalization = true,
      enableHeavyRanking = DefaultEnableHeavyRanking,
      rankingAlgorithm = ScoringAlgorithm.PairEmbeddingCosineSimilarity,
      matwitterReRankingCandidates = 200,
      matwitterTopTweetsPerCluster = DefaultMatwitterTopTweetsPerCluster,
      matwitterScanClusters = 50,
      minTweetCandidateAge = 0.seconds
    )
  )

  /**
   * Only cache the candidates if it's not Consumer-source. For etwitterample, TweetSource, ProducerSource,
   * TopicSource. We don't cache consumer-sources (e.g. UserInterestedIn) since a cached consumer
   * object is going rarely hit, since it can't be shared by multiple users.
   */
  val CacheableShortTTLEmbeddingTypes: Set[EmbeddingType] =
    Set(
      EmbeddingType.FavBasedProducer,
      EmbeddingType.LogFavLongestL2EmbeddingTweet,
    )

  val CacheableLongTTLEmbeddingTypes: Set[EmbeddingType] =
    Set(
      EmbeddingType.FavTfgTopic,
      EmbeddingType.LogFavBasedKgoApeTopic
    )
}
