package com.X.home_mixer.product.scored_tweets.query_transformer.earlybird

import com.X.conversions.DurationOps._
import com.X.home_mixer.model.request.HasDeviceContext
import com.X.home_mixer.product.scored_tweets.feature_hydrator.FrsSeedUserIdsFeature
import com.X.home_mixer.product.scored_tweets.query_transformer.earlybird.EarlybirdFrsQueryTransformer._
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.quality_factor.HasQualityFactorStatus
import com.X.search.earlybird.{thriftscala => eb}
import com.X.timelines.common.model.TweetKindOption

object EarlybirdFrsQueryTransformer {
  private val SinceDuration = 24.hours
  private val MaxTweetsToFetch = 100
  private val TensorflowModel = Some("timelines_rectweet_replica")

  private val TweetKindOptions: TweetKindOption.ValueSet =
    TweetKindOption(includeOriginalTweetsAndQuotes = true)
}

case class EarlybirdFrsQueryTransformer[
  Query <: PipelineQuery with HasQualityFactorStatus with HasDeviceContext
](
  candidatePipelineIdentifier: CandidatePipelineIdentifier,
  override val clientId: Option[String])
    extends CandidatePipelineQueryTransformer[Query, eb.EarlybirdRequest]
    with EarlybirdQueryTransformer[Query] {

  override val tweetKindOptions: TweetKindOption.ValueSet = TweetKindOptions
  override val maxTweetsToFetch: Int = MaxTweetsToFetch
  override val tensorflowModel: Option[String] = TensorflowModel

  override def transform(query: Query): eb.EarlybirdRequest = {
    val seedUserIds = query.features
      .flatMap(_.getOrElse(FrsSeedUserIdsFeature, None))
      .getOrElse(Seq.empty).toSet
    buildEarlybirdQuery(query, SinceDuration, seedUserIds)
  }
}
