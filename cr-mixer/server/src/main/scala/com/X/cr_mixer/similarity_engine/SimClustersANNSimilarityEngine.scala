package com.X.cr_mixer.similarity_engine

import com.X.cr_mixer.config.SimClustersANNConfig
import com.X.cr_mixer.model.SimilarityEngineInfo
import com.X.cr_mixer.model.TweetWithScore
import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.util.StatsUtil
import com.X.simclusters_v2.thriftscala.EmbeddingType
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.simclusters_v2.thriftscala.ModelVersion
import com.X.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.X.simclustersann.thriftscala.SimClustersANNService
import com.X.simclustersann.thriftscala.{Query => SimClustersANNQuery}
import com.X.storehaus.ReadableStore
import com.X.timelines.configapi
import com.X.util.Future
import javax.inject.Singleton
import com.X.cr_mixer.exception.InvalidSANNConfigException
import com.X.relevance_platform.simclustersann.multicluster.ServiceNameMapper

@Singleton
case class SimClustersANNSimilarityEngine(
  simClustersANNServiceNameToClientMapper: Map[String, SimClustersANNService.MethodPerEndpoint],
  statsReceiver: StatsReceiver)
    extends ReadableStore[
      SimClustersANNSimilarityEngine.Query,
      Seq[TweetWithScore]
    ] {

  private val name: String = this.getClass.getSimpleName
  private val stats = statsReceiver.scope(name)
  private val fetchCandidatesStat = stats.scope("fetchCandidates")

  private def getSimClustersANNService(
    query: SimClustersANNQuery
  ): Option[SimClustersANNService.MethodPerEndpoint] = {
    ServiceNameMapper
      .getServiceName(
        query.sourceEmbeddingId.modelVersion,
        query.config.candidateEmbeddingType).flatMap(serviceName =>
        simClustersANNServiceNameToClientMapper.get(serviceName))
  }

  override def get(
    query: SimClustersANNSimilarityEngine.Query
  ): Future[Option[Seq[TweetWithScore]]] = {
    StatsUtil.trackOptionItemsStats(fetchCandidatesStat) {

      getSimClustersANNService(query.simClustersANNQuery) match {
        case Some(simClustersANNService) =>
          simClustersANNService.getTweetCandidates(query.simClustersANNQuery).map {
            simClustersANNTweetCandidates =>
              val tweetWithScores = simClustersANNTweetCandidates.map { candidate =>
                TweetWithScore(candidate.tweetId, candidate.score)
              }
              Some(tweetWithScores)
          }
        case None =>
          throw InvalidSANNConfigException(
            "No SANN Cluster configured to serve this query, check CandidateEmbeddingType and ModelVersion")
      }
    }
  }
}

object SimClustersANNSimilarityEngine {
  case class Query(
    simClustersANNQuery: SimClustersANNQuery,
    simClustersANNConfigId: String)

  def toSimilarityEngineInfo(
    query: EngineQuery[Query],
    score: Double
  ): SimilarityEngineInfo = {
    SimilarityEngineInfo(
      similarityEngineType = SimilarityEngineType.SimClustersANN,
      modelId = Some(
        s"SimClustersANN_${query.storeQuery.simClustersANNQuery.sourceEmbeddingId.embeddingType.toString}_" +
          s"${query.storeQuery.simClustersANNQuery.sourceEmbeddingId.modelVersion.toString}_" +
          s"${query.storeQuery.simClustersANNConfigId}"),
      score = Some(score)
    )
  }

  def fromParams(
    internalId: InternalId,
    embeddingType: EmbeddingType,
    modelVersion: ModelVersion,
    simClustersANNConfigId: String,
    params: configapi.Params,
  ): EngineQuery[Query] = {

    // SimClusters EmbeddingId and ANNConfig
    val simClustersEmbeddingId =
      SimClustersEmbeddingId(embeddingType, modelVersion, internalId)
    val simClustersANNConfig =
      SimClustersANNConfig
        .getConfig(embeddingType.toString, modelVersion.toString, simClustersANNConfigId)

    EngineQuery(
      Query(
        SimClustersANNQuery(
          sourceEmbeddingId = simClustersEmbeddingId,
          config = simClustersANNConfig.toSANNConfigThrift
        ),
        simClustersANNConfigId
      ),
      params
    )
  }

}
