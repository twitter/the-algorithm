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
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.strato.generated.client.recommendations.simclusters_v2.TopPostsPerEventClientColumn
import com.twitter.timelines.configapi.FSParam
import com.twitter.tweet_mixer.candidate_source.events.EventsCandidateSource
import com.twitter.tweet_mixer.candidate_source.events.EventsRequest
import com.twitter.tweet_mixer.functional_component.gate.AllowLowSignalUserGate
import com.twitter.tweet_mixer.functional_component.transformer.TweetMixerCandidateFeatureTransformer
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.tweet_mixer.param.CandidateSourceParams.EventsEnabled
import com.twitter.tweet_mixer.param.CandidateSourceParams.EventsIrrelevanceDownrank
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.BusinessHours
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.ForYouGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import com.twitter.tweet_mixer.utils.CandidatePipelineConstants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsCandidatePipelineConfig @Inject() (
  eventsCandidateSource: EventsCandidateSource)
    extends CandidatePipelineConfig[
      PipelineQuery,
      EventsRequest,
      TweetMixerCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier(CandidatePipelineConstants.Events)

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(EventsEnabled)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(AllowLowSignalUserGate)

  override val queryTransformer: CandidatePipelineQueryTransformer[PipelineQuery, EventsRequest] = {
    query =>
      EventsRequest(TopPostsPerEventClientColumn.Nba, query.params(EventsIrrelevanceDownrank))
  }

  override def candidateSource: CandidateSource[EventsRequest, TweetMixerCandidate] =
    eventsCandidateSource

  override def featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetMixerCandidate]
  ] = Seq(TweetMixerCandidateFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetMixerCandidate,
    TweetCandidate
  ] = { candidate => TweetCandidate(id = candidate.tweetId) }

  override val alerts: Seq[Alert] = Seq(
    defaultSuccessRateAlert(threshold = 95, notificationType = BusinessHours)(ForYouGroupMap),
    defaultEmptyResponseRateAlert()(ForYouGroupMap)
  )
}
