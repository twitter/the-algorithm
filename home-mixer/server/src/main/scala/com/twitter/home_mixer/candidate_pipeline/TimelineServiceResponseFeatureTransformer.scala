package com.ExTwitter.home_mixer.candidate_pipeline

import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.ExTwitter.timelineservice.{thriftscala => t}

object TimelineServiceResponseFeatureTransformer extends CandidateFeatureTransformer[t.Tweet] {

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
