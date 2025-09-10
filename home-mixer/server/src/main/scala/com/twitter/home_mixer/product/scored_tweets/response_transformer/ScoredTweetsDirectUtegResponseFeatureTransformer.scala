package com.twitter.home_mixer.product.scored_tweets.response_transformer

import com.twitter.home_mixer.model.HomeFeatures.DebugStringFeature
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.recos.recos_common.thriftscala.SocialProofType
import com.twitter.recos.user_tweet_entity_graph.{thriftscala => uteg}

object UtegFavListFeature extends Feature[TweetCandidate, Seq[Long]]
object UtegScoreFeature extends Feature[TweetCandidate, Double]

object ScoredTweetsDirectUtegResponseFeatureTransformer
    extends CandidateFeatureTransformer[uteg.TweetRecommendation] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsDirectUtegResponse")

  override val features: Set[Feature[_, _]] = Set(
    FromInNetworkSourceFeature,
    ServedTypeFeature,
    DebugStringFeature,
    UtegFavListFeature,
    UtegScoreFeature
  )

  override def transform(input: uteg.TweetRecommendation): FeatureMap =
    FeatureMapBuilder()
      .add(FromInNetworkSourceFeature, false)
      .add(ServedTypeFeature, hmt.ServedType.ForYouUteg)
      .add(DebugStringFeature, Some("Uteg"))
      .add(
        UtegFavListFeature,
        input.socialProofByType.getOrElse(SocialProofType.Favorite, Seq.empty)
      )
      .add(UtegScoreFeature, input.score)
      .build()
}
