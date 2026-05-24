package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeLargeEmbeddingsFeatures.UserLargeEmbeddingsFeature
import com.twitter.home_mixer.model.HomeLargeEmbeddingsFeatures.UserLargeEmbeddingsKeyFeature
import com.twitter.home_mixer.param.HomeGlobalParams.FeatureHydration.EnableLargeEmbeddingsFeatureHydrationParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelNameParam
import com.twitter.home_mixer_features.{thriftscala => hmf}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.prediction.adapters.large_embeddings.HashingFeatureParams
import com.twitter.timelines.prediction.adapters.large_embeddings.HomeMixerLargeEmbeddingsFeatureHydrator
import com.twitter.timelines.prediction.adapters.large_embeddings.LargeEmbeddingsAdapter
import com.twitter.timelines.prediction.adapters.large_embeddings.UserLargeEmbeddingsAdapter
import com.twitter.util.Future
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLargeEmbeddingsFeatureHydrator @Inject() (
  statsReceiver: StatsReceiver,
  override val homeMixerFeatureService: hmf.HomeMixerFeatures.MethodPerEndpoint)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery]
    with HomeMixerLargeEmbeddingsFeatureHydrator {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserLargeEmbeddings")

  override val features: Set[Feature[_, _]] =
    Set(UserLargeEmbeddingsFeature, UserLargeEmbeddingsKeyFeature)

  override val adapter: LargeEmbeddingsAdapter = UserLargeEmbeddingsAdapter

  override val cacheType: hmf.Cache = hmf.Cache.UserLargeEmbeddings

  override val scopedStatsReceiver: StatsReceiver = statsReceiver.scope(getClass.getSimpleName)

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableLargeEmbeddingsFeatureHydrationParam)

  // Hashing Features
  override val defaultHashingFeatureParams: HashingFeatureParams = HashingFeatureParams(
    scales = Seq(1681734645L, 1546314972L),
    biases = Seq(2701200313L, 1873259806L),
    modulus = 3055559939L,
    bucketSize = 10000000L,
  )

  override val modelName2HashingFeatureParams: Map[String, HashingFeatureParams] = Map(
    "hr_video_prod__v3_realtime" -> HashingFeatureParams(
      scales = Seq(1341131000L, 519927459L),
      biases = Seq(2924993425L, 294422133L),
      modulus = 3109207999L,
      bucketSize = 10000000L,
    ),
    "hr_video_prod__v2_lembeds" -> HashingFeatureParams(
      scales = Seq(214226227L, 561611689L),
      biases = Seq(182790211L, 330327483L),
      modulus = 816016163L,
      bucketSize = 1000000L,
    ),
    "hr_prod__v4_embeds_230M" -> HashingFeatureParams(
      scales = Seq(196742702L, 1852108266L),
      biases = Seq(1935840681L, 167407236L),
      modulus = 2859568897L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v5_embeds_230M_and_transformer" -> HashingFeatureParams(
      scales = Seq(196742702L, 1852108266L),
      biases = Seq(1935840681L, 167407236L),
      modulus = 2859568897L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v5_watchtime" -> HashingFeatureParams(
      scales = Seq(45230244L, 676046872L),
      biases = Seq(866394657L, 1019127517L),
      modulus = 1047809363L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v6_transformer_v2" -> HashingFeatureParams(
      scales = Seq(196742702L, 1852108266L),
      biases = Seq(1935840681L, 167407236L),
      modulus = 2859568897L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v6_mixed_training" -> HashingFeatureParams(
      scales = Seq(196742702L, 1852108266L),
      biases = Seq(1935840681L, 167407236L),
      modulus = 2859568897L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v6_transformer_v2_kafka_merge_join" -> HashingFeatureParams(
      scales = Seq(196742702L, 1852108266L),
      biases = Seq(1935840681L, 167407236L),
      modulus = 2859568897L,
      bucketSize = 100000000L,
    ),
    "hr_prod__v6_transformer_v2_realtime_debias_21apr" -> HashingFeatureParams(
      scales = Seq(196742702L, 1852108266L),
      biases = Seq(1935840681L, 167407236L),
      modulus = 2859568897L,
      bucketSize = 100000000L,
    ),
    "hr_video_prod__v4_realtime" -> HashingFeatureParams(
      scales = Seq(1341131000L, 519927459L),
      biases = Seq(2924993425L, 294422133L),
      modulus = 3109207999L,
      bucketSize = 10000000L,
    ),
    "hr_video_prod__v4_realtime_mergehead" -> HashingFeatureParams(
      scales = Seq(1341131000L, 519927459L),
      biases = Seq(2924993425L, 294422133L),
      modulus = 3109207999L,
      bucketSize = 10000000L,
    ),
  )

  private def getFeatureMap(
    pipelineQuery: PipelineQuery
  ): Future[FeatureMap] = {
    val userId = pipelineQuery.getRequiredUserId
    val modelName = pipelineQuery.params(ModelNameParam)
    val responseMap = getLargeEmbeddings(userId, modelName)
    responseMap.map { response =>
      FeatureMapBuilder()
        .add(UserLargeEmbeddingsFeature, response.dataRecord)
        .add(UserLargeEmbeddingsKeyFeature, response.hashedKeys)
        .build()
    }
  }

  override def hydrate(
    query: PipelineQuery
  ): Stitch[FeatureMap] = Stitch.callFuture(getFeatureMap(query))
}
