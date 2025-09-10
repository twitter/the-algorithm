package com.twitter.tweet_mixer.candidate_pipeline

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.common.alert.Alert
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.component_library.gate.DefinedCountryCodeGate
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.strato.generated.client.trendsai.media.TopCountryVideosClientColumn
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.trends.TrendsVideoCandidateSource
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.CandidateSourceParams.TrendsVideoEnabled
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.BusinessHours
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.CoreProductGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrendsVideoCandidatePipelineConfig @Inject() (
  trendsVideoCandidateSource: TrendsVideoCandidateSource)
    extends CandidatePipelineConfig[
      PipelineQuery,
      TopCountryVideosClientColumn.Key,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier(CandidatePipelineConstants.TrendsVideo)

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(TrendsVideoEnabled)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    DefinedCountryCodeGate
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    PipelineQuery,
    TopCountryVideosClientColumn.Key,
  ] = { query => query.getCountryCode.get }

  override def candidateSource: CandidateSource[
    TopCountryVideosClientColumn.Key,
    TweetMixerCandidate
  ] = trendsVideoCandidateSource

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetMixerCandidate]
  ] = Seq(TweetMixerCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { candidate => TweetCandidate(id = candidate.tweetId) }

  override val alerts: Seq[Alert] = Seq(
    defaultSuccessRateAlert(threshold = 95, notificationType = BusinessHours)(CoreProductGroupMap),
  )
}
