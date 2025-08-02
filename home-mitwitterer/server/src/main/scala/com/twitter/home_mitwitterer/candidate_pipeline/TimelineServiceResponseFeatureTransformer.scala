package com.twitter.home_mitwitterer.candidate_pipeline

import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsRetweetFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier
import com.twitter.timelineservice.{thriftscala => t}

object TimelineServiceResponseFeatureTransformer etwittertends CandidateFeatureTransformer[t.Tweet] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("TimelineServiceResponse")

  override val features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    InReplyToTweetIdFeature,
    IsRetweetFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
  )

  override def transform(candidate: t.Tweet): FeatureMap = FeatureMapBuilder()
    .add(AuthorIdFeature, candidate.userId)
    .add(InReplyToTweetIdFeature, candidate.inReplyToStatusId)
    .add(IsRetweetFeature, candidate.sourceStatusId.isDefined)
    .add(SourceTweetIdFeature, candidate.sourceStatusId)
    .add(SourceUserIdFeature, candidate.sourceUserId)
    .build()
}
