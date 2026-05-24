package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeLargeEmbeddingsFeatures.AuthorLargeEmbeddingsFeature
import com.twitter.home_mixer.model.HomeLargeEmbeddingsFeatures.AuthorLargeEmbeddingsKeyFeature
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableLargeEmbeddingsFeatureHydrationParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelNameParam
import com.twitter.home_mixer_features.{thriftscala => hmf}
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.adapters.large_embeddings.AuthorLargeEmbeddingsAdapter
import com.twitter.timelines.prediction.adapters.large_embeddings.HashingFeatureParams
import com.twitter.timelines.prediction.adapters.large_embeddings.HomeMixerLargeEmbeddingsFeatureHydrator
import com.twitter.timelines.prediction.adapters.large_embeddings.LargeEmbeddingsAdapter
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorLargeEmbeddingsFeatureHydrator @Inject() (
  statsReceiver: StatsReceiver,
  override val homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery]
    with HomeMixerLargeEmbeddingsFeatureHydrator {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("AuthorLargeEmbeddings")

  override val features: Set[Feature[_, _]] =
    Set(AuthorLargeEmbeddingsFeature, AuthorLargeEmbeddingsKeyFeature)

  override val adapter: LargeEmbeddingsAdapter = AuthorLargeEmbeddingsAdapter

  override val cacheType: hmf.Cache = hmf.Cache.AuthorLargeEmbeddings

  override val scopedStatsReceiver: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableLargeEmbeddingsFeatureHydrationParam)

  // Hashing Features
  override val defaultHashingFeatureParams: HashingFeatureParams = HashingFeatureParams(
    scales = Seq(3384241453L, 3372414709L),
    biases = Seq(1649585795L, 3131243219L),
    modulus = 3957384397L,
    bucketSize = 3000000L,
  )

  override val modelName2HashingFeatureParams: Map[String, HashingFeatureParams] = Map(
    "hr_video_prod__v3_realtime" -> HashingFeatureParams(
      scales = Seq(113294449L, 601841083L),
      biases = Seq(2231299001L, 841367196L),
      modulus = 2343760591L,
      bucketSize = 3000000L,
    ),
    "hr_video_prod__v2_lembeds" -> HashingFeatureParams(
      scales = Seq(787140070L, 633713480L),
      biases = Seq(427768658L, 911091889L),
      modulus = 2888480981L,
      bucketSize = 300000L,
    ),
    "hr_prod__v4_embeds_230M" -> HashingFeatureParams(
      scales = Seq(371965780L, 328930218L),
      biases = Seq(139686260L, 37755056L),
      modulus = 631860353L,
      bucketSize = 30000000L,
    ),
    "hr_prod__v5_embeds_230M_and_transformer" -> HashingFeatureParams(
      scales = Seq(371965780L, 328930218L),
      biases = Seq(139686260L, 37755056L),
      modulus = 631860353L,
      bucketSize = 30000000L,
    ),
    "hr_prod__v5_watchtime" -> HashingFeatureParams(
      scales = Seq(2328530078L, 2844016377L),
      biases = Seq(1352496802L, 3011003330L),
      modulus = 3979826519L,
      bucketSize = 30000000L,
    ),
    "hr_prod__v6_transformer_v2" -> HashingFeatureParams(
      scales = Seq(371965780L, 328930218L),
      biases = Seq(139686260L, 37755056L),
      modulus = 631860353L,
      bucketSize = 30000000L,
    ),
    "hr_prod__v6_mixed_training" -> HashingFeatureParams(
      scales = Seq(371965780L, 328930218L),
      biases = Seq(139686260L, 37755056L),
      modulus = 631860353L,
      bucketSize = 30000000L,
    ),
    "hr_prod__v6_transformer_v2_kafka_merge_join" -> HashingFeatureParams(
      scales = Seq(371965780L, 328930218L),
      biases = Seq(139686260L, 37755056L),
      modulus = 631860353L,
      bucketSize = 30000000L,
    ),
    "hr_prod__v6_transformer_v2_realtime_debias_21apr" -> HashingFeatureParams(
      scales = Seq(371965780L, 328930218L),
      biases = Seq(139686260L, 37755056L),
      modulus = 631860353L,
      bucketSize = 30000000L,
    ),
  )
  private val batchSize = 25

  private def getBatchedFeatureMap(
    modelName: String,
    candidatesBatch: Seq[CandidateWithFeatures[TweetCandidate]],
  ): Future[Seq[FeatureMap]] = {
    val authorIds =
      candidatesBatch.map { candidate =>
        candidate.features.getOrElse(AuthorIdFeature, None).getOrElse(0L)
      }

    getLargeEmbeddings(authorIds, modelName).map { responses =>
      responses.map { largeEmbeddingResponse =>
        FeatureMapBuilder()
          .add(AuthorLargeEmbeddingsFeature, largeEmbeddingResponse.dataRecord)
          .add(AuthorLargeEmbeddingsKeyFeature, largeEmbeddingResponse.hashedKeys)
          .build()
      }
    }
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val modelName = query.params(ModelNameParam)
    OffloadFuturePools.offloadBatchSeqToFutureSeq(
      candidates,
      getBatchedFeatureMap(modelName, _),
      batchSize
    )
  }
}
