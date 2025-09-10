package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.tweet_mixer.candidate_source.qig_service.QigTweetCandidate
import com.twitter.tweet_mixer.feature.LanguageCodeFeature
import com.twitter.tweet_mixer.feature.SearcherRealtimeHistorySourceSignalFeature

object QigQueryFeature extends Feature[PipelineQuery, String]

object QigTweetCandidateFeatureTransformer extends CandidateFeatureTransformer[QigTweetCandidate] {
  override val identifier: TransformerIdentifier = TransformerIdentifier("QigTweetCandidate")
  override def features: Set[Feature[_, _]] =
    Set(QigQueryFeature, LanguageCodeFeature, SearcherRealtimeHistorySourceSignalFeature)

  override def transform(input: QigTweetCandidate): FeatureMap = {
    FeatureMapBuilder()
      .add(QigQueryFeature, input.query)
      .add(LanguageCodeFeature, input.tweetCandidate.qigTweetFeatures.flatMap(_.language))
      .add(SearcherRealtimeHistorySourceSignalFeature, input.query)
      .build()
  }
}
