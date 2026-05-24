package com.twitter.tweet_mixer.candidate_source.simclusters_ann

import com.twitter.io.Buf
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.pipeline.pipeline_failure.BadRequest
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.pipeline.pipeline_failure.UnexpectedCandidateResult
import com.twitter.relevance_platform.simclustersann.multicluster.ServiceNameMapper
import com.twitter.servo.util.Transformer
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclustersann.thriftscala.SimClustersANNService
import com.twitter.simclustersann.thriftscala.{Query => SimClustersANNQuery}
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.candidate_source.cached_candidate_source.MemcachedCandidateSource
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import com.twitter.tweet_mixer.utils.Transformers
import com.twitter.tweet_mixer.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimClustersAnnCandidateSource @Inject() (
  simClustersANNServiceNameToClientMapper: Map[String, SimClustersANNService.MethodPerEndpoint],
  memcacheClient: MemcacheStitchClient)
    extends MemcachedCandidateSource[
      SANNQuery,
      SimClustersANNQuery,
      (Long, Double),
      TweetMixerCandidate
    ] {

  private def getSimClustersANNService(
    query: SimClustersANNQuery
  ): Option[SimClustersANNService.MethodPerEndpoint] = {
    ServiceNameMapper
      .getServiceName(query.sourceEmbeddingId.modelVersion, query.config.candidateEmbeddingType)
      .flatMap { serviceName =>
        simClustersANNServiceNameToClientMapper.get(serviceName)
      }
  }

  private def getSeedId(
    query: SimClustersANNQuery
  ): Long = {
    query.sourceEmbeddingId.internalId match {
      case InternalId.UserId(userId) => userId
      case InternalId.TweetId(tweetId) => tweetId
      case _ =>
        throw PipelineFailure(UnexpectedCandidateResult, "Internal Id not of the supported types")
    }
  }

  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    "SimclustersAnnCandidateSource"
  )

  override val TTL = Utils.randomizedTTL(600) //10 minutes

  override val memcache: MemcacheStitchClient = memcacheClient

  override def keyTransformer(key: SimClustersANNQuery): String = "SANN:" + key.toString

  val valueTransformer: Transformer[Seq[(Long, Double)], Buf] =
    Transformers.longDoubleSeqBufTransformer

  override def enableCache(request: SANNQuery): Boolean = request.enableCache

  override def getKeys(request: SANNQuery): Stitch[Seq[SimClustersANNQuery]] =
    Stitch.value(request.queries)

  override def getCandidatesFromStore(
    key: SimClustersANNQuery
  ): Stitch[Seq[(Long, Double)]] = {
    getSimClustersANNService(key) match {
      // Find the service
      case Some(simClustersANNService) =>
        val tweetCandidatesFuture = simClustersANNService.getTweetCandidates(key)
        Stitch
          .callFuture(tweetCandidatesFuture)
          .map {
            _.map { candidate =>
              (candidate.tweetId, candidate.score)
            }
          }
      //Throw error when service is misconfigured
      case None =>
        val errorMessage =
          s"No SANN Cluster configured to serve this query, check CandidateEmbeddingType and ModelVersion: $key"
        throw PipelineFailure(BadRequest, errorMessage)
    }
  }

  override def postProcess(
    request: SANNQuery,
    keys: Seq[SimClustersANNQuery],
    resultsSeq: Seq[Seq[(Long, Double)]]
  ): Seq[TweetMixerCandidate] = {
    val maxCandidates = request.maxCandidates
    val minScore = request.minScore
    val filteredResults = keys.zip(resultsSeq).map {
      case (key, results) =>
        val seedId = getSeedId(key)
        results
          .collect {
            case (id, score) if score > minScore => TweetMixerCandidate(id, score, seedId)
          }.take(maxCandidates)
    }
    TweetMixerCandidate.interleave(filteredResults)
  }
}
