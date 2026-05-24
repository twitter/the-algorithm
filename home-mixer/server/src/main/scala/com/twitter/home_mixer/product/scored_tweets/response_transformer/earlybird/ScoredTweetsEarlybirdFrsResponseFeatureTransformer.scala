package com.twitter.home_mixer.product.scored_tweets.response_transformer.earlybird

import com.twitter.home_mixer.model.HomeFeatures.DebugStringFeature
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.search.earlybird.{thriftscala => eb}

object ScoredTweetsEarlybirdFrsResponseFeatureTransformer
    extends CandidateFeatureTransformer[eb.ThriftSearchResult] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsEarlybirdFrsResponse")

  private val candidateSourceFeatures = Set(
    FromInNetworkSourceFeature,
    ServedTypeFeature,
    DebugStringFeature
  )

  override val features: Set[Feature[_, _]] =
    EarlybirdResponseTransformer.features ++ candidateSourceFeatures

  override def transform(candidate: eb.ThriftSearchResult): FeatureMap = {
    val baseFeatures = EarlybirdResponseTransformer.transform(candidate)

    val features = FeatureMapBuilder()
      .add(FromInNetworkSourceFeature, false)
      .add(ServedTypeFeature, hmt.ServedType.ForYouFrs)
      .add(DebugStringFeature, Some("FRS"))
      .build()

    baseFeatures ++ features
  }
}
