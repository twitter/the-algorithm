package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.feature.location.LocationSharedFeatures.LocationFeature
import com.twitter.product_mixer.component_library.gate.DefinedLocationGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyView
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.strato.generated.client.timelines.local.FetchLocalPostsClientColumn
import com.twitter.tweet_mixer.candidate_source.user_location.UserLocationCandidateSource
import com.twitter.tweet_mixer.functional_component.gate.AllowLowSignalUserGate
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.ForYouGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocationCandidatePipelineConfig @Inject() (
  userLocationCandidateSource: UserLocationCandidateSource)
    extends CandidatePipelineConfig[
      PipelineQuery,
      StratoKeyView[
        FetchLocalPostsClientColumn.Key,
        Unit
      ],
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier(CandidatePipelineConstants.UserLocation)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(DefinedLocationGate, AllowLowSignalUserGate)

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    StratoKeyView[
      FetchLocalPostsClientColumn.Key,
      Unit
    ]
  ] = { query =>
    val location = query.features.flatMap(_.get(LocationFeature)).head

    val locationString = Seq(location.city, location.metro, location.region)
      .map { place => place.map { l => l.toString }.getOrElse("") }.mkString(",")

    StratoKeyView(key = locationString, Unit)
  }

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { candidate => TweetCandidate(id = candidate.tweetId) }

  override def candidateSource: CandidateSource[
    StratoKeyView[FetchLocalPostsClientColumn.Key, Unit],
    TweetMixerCandidate,
  ] = userLocationCandidateSource

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetMixerCandidate]
  ] = Seq(TweetMixerCandidateFeatureTransformer)

  override val alerts: Seq[Alert] = Seq(
    defaultSuccessRateAlert()(ForYouGroupMap),
    defaultEmptyResponseRateAlert()(ForYouGroupMap)
  )
}
