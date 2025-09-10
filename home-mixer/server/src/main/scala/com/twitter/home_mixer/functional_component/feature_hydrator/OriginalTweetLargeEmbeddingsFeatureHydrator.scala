package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeLargeEmbeddingsFeatures.OriginalTweetLargeEmbeddingsFeature
import com.twitter.home_mixer.model.HomeLargeEmbeddingsFeatures.OriginalTweetLargeEmbeddingsKeyFeature
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableLargeEmbeddingsFeatureHydrationParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelNameParam
import com.twitter.home_mixer.util.CandidatesUtil.getOriginalTweetId
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
import com.twitter.timelines.prediction.adapters.large_embeddings.HashingFeatureParams
import com.twitter.timelines.prediction.adapters.large_embeddings.HomeMixerLargeEmbeddingsFeatureHydrator
import com.twitter.timelines.prediction.adapters.large_embeddings.LargeEmbeddingsAdapter
import com.twitter.timelines.prediction.adapters.large_embeddings.OriginalTweetLargeEmbeddingsAdapter
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OriginalTweetLargeEmbeddingsFeatureHydrator @Inject() (
  statsReceiver: StatsReceiver,
  override val homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate]
    with Conditionally[PipelineQuery]
    with HomeMixerLargeEmbeddingsFeatureHydrator {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("OriginalTweetLargeEmbeddings")

  override val features: Set[Feature[_, _]] =
    Set(OriginalTweetLargeEmbeddingsFeature, OriginalTweetLargeEmbeddingsKeyFeature)

  override val adapter: LargeEmbeddingsAdapter = OriginalTweetLargeEmbeddingsAdapter

  override val cacheType: hmf.Cache = hmf.Cache.TweetLargeEmbeddings

  override val scopedStatsReceiver: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableLargeEmbeddingsFeatureHydrationParam)

  // Hashing Features
  override val defaultHashingFeatureParams: HashingFeatureParams = HashingFeatureParams(
    scales = Seq(1131302000L, 303023026L),
    biases = Seq(799473858L, 600426834L),
    modulus = 3588720353L,
    bucketSize = 10000000L,
  )

  override val modelName2HashingFeatureParams: Map[String, HashingFeatureParams] = Map(
    "hr_video_prod__v3_realtime" -> HashingFeatureParams(
      scales = Seq(1487022661L, 1399245971L),
      biases = Seq(1372992088L, 632996194L),
      modulus = 2865175829L,
      bucketSize = 10000000L,
    ),
    "hr_video_prod__v2_lembeds" -> HashingFeatureParams(
      scales = Seq(2516541900L, 2376187492L),
      biases = Seq(3022238687L, 1571354734L),
      modulus = 3047336911L,
      bucketSize = 1000000L,
    ),
    "hr_prod__v4_embeds_230M" -> HashingFeatureParams(
      scales = Seq(2161410491L, 1754358832L),
      biases = Seq(296686044L, 1959990826L),
      modulus = 2361375383L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v5_embeds_230M_and_transformer" -> HashingFeatureParams(
      scales = Seq(2161410491L, 1754358832L),
      biases = Seq(296686044L, 1959990826L),
      modulus = 2361375383L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v5_watchtime" -> HashingFeatureParams(
      scales = Seq(407033648L, 940305868L),
      biases = Seq(494266171L, 269596788L),
      modulus = 949146421L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v6_transformer_v2" -> HashingFeatureParams(
      scales = Seq(2161410491L, 1754358832L),
      biases = Seq(296686044L, 1959990826L),
      modulus = 2361375383L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v6_mixed_training" -> HashingFeatureParams(
      scales = Seq(2161410491L, 1754358832L),
      biases = Seq(296686044L, 1959990826L),
      modulus = 2361375383L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v6_transformer_v2_kafka_merge_join" -> HashingFeatureParams(
      scales = Seq(2161410491L, 1754358832L),
      biases = Seq(296686044L, 1959990826L),
      modulus = 2361375383L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v6_transformer_v2_realtime_debias_21apr" -> HashingFeatureParams(
      scales = Seq(2161410491L, 1754358832L),
      biases = Seq(296686044L, 1959990826L),
      modulus = 2361375383L,
      bucketSize = 100000000L,
    ),
    "hr_video_prod__v4_realtime" -> HashingFeatureParams(
      scales = Seq(1487022661L, 1399245971L),
      biases = Seq(1372992088L, 632996194L),
      modulus = 2865175829L,
      bucketSize = 10000000L,
    ),
    "hr_video_prod__v4_realtime_mergehead" -> HashingFeatureParams(
      scales = Seq(1487022661L, 1399245971L),
      biases = Seq(1372992088L, 632996194L),
      modulus = 2865175829L,
      bucketSize = 10000000L,
    ),
  )

  private val batchSize = 25

  private def getBatchedFeatureMap(
    modelName: String,
    candidatesBatch: Seq[CandidateWithFeatures[TweetCandidate]],
  ): Future[Seq[FeatureMap]] = {
    val originalTweetIds =
      candidatesBatch.map { candidate =>
        getOriginalTweetId(candidate.candidate, candidate.features)
      }

    getLargeEmbeddings(originalTweetIds, modelName).map { responses =>
      responses.map { largeEmbeddingResponse =>
        FeatureMapBuilder()
          .add(OriginalTweetLargeEmbeddingsFeature, largeEmbeddingResponse.dataRecord)
          .add(OriginalTweetLargeEmbeddingsKeyFeature, largeEmbeddingResponse.hashedKeys)
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
