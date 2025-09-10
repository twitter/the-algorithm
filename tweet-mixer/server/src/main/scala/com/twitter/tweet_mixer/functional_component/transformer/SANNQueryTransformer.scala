package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.simclusters_ann.SANNQuery
import com.twitter.tweet_mixer.config.SimClustersANNConfig
import com.twitter.tweet_mixer.feature.USSFeatures
import com.twitter.tweet_mixer.param.SimClustersANNParams.EnableSANNCacheParam
import com.twitter.tweet_mixer.param.SimClustersANNParams.ModelVersionParam

case class SANNQueryTransformer(
  override val identifier: TransformerIdentifier,
  clusterParamMap: Map[_ <: FSParam[Boolean], _ <: FSParam[String]],
  signalsFn: PipelineQuery => Seq[InternalId],
  embeddingTypes: Seq[EmbeddingType],
  minScoreParam: FSBoundedParam[Double],
  maxInterestedInCandidatesParam: Option[FSBoundedParam[Int]] = None)
    extends CandidatePipelineQueryTransformer[PipelineQuery, SANNQuery] {

  /* This determines the total number of candidates from Simclusters ANN
   */
  val maxSANNCandidates: Int = 10000

  override def transform(inputQuery: PipelineQuery): SANNQuery = {
    val signalLength =
      USSFeatures
        .getSignals(inputQuery, USSFeatures.TweetFeatures ++ USSFeatures.ProducerFeatures)
        .length
    val numCandidates = maxInterestedInCandidatesParam match {
      case Some(param) => inputQuery.params(param)
      case None => if (signalLength == 0) 200 else maxSANNCandidates / signalLength
    }
    val configIds: Seq[String] = clusterParamMap.collect {
      case (enableParam, configParam) if inputQuery.params(enableParam) =>
        inputQuery.params(configParam)
    }.toSeq
    val modelVersion = {
      ModelVersions.Enum.enumToSimClustersModelVersionMap(inputQuery.params(ModelVersionParam))
    }
    val minScore = inputQuery.params(minScoreParam)
    val queries = signalsFn(inputQuery).flatMap { signal =>
      configIds.flatMap { configId =>
        embeddingTypes.map { embeddingType =>
          SimClustersANNConfig.getQuery(
            signal,
            embeddingType,
            modelVersion,
            configId
          )
        }
      }
    }
    SANNQuery(queries, numCandidates, minScore, inputQuery.params(EnableSANNCacheParam))
  }
}
