package com.X.home_mixer.product.list_tweets

import com.X.home_mixer.candidate_pipeline.TimelineServiceResponseFeatureTransformer
import com.X.home_mixer.marshaller.timelines.TimelineServiceCursorMarshaller
import com.X.home_mixer.product.list_tweets.model.ListTweetsQuery
import com.X.home_mixer.product.list_tweets.param.ListTweetsParam.ServerMaxResultsParam
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.product_mixer.component_library.candidate_source.timeline_service.TimelineServiceTweetCandidateSource
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.X.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.X.timelineservice.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListTweetsTimelineServiceCandidatePipelineConfig @Inject() (
  timelineServiceTweetCandidateSource: TimelineServiceTweetCandidateSource)
    extends CandidatePipelineConfig[ListTweetsQuery, t.TimelineQuery, t.Tweet, TweetCandidate] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ListTweetsTimelineServiceTweets")

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ListTweetsQuery,
    t.TimelineQuery
  ] = { query =>
    val timelineQueryOptions = t.TimelineQueryOptions(
      contextualUserId = query.clientContext.userId,
    )

    t.TimelineQuery(
      timelineType = t.TimelineType.List,
      timelineId = query.listId,
      maxCount = query.maxResults(ServerMaxResultsParam).toShort,
      cursor2 = query.pipelineCursor.flatMap(TimelineServiceCursorMarshaller(_)),
      options = Some(timelineQueryOptions),
      timelineId2 = Some(t.TimelineId(t.TimelineType.List, query.listId, None))
    )
  }

  override def candidateSource: BaseCandidateSource[t.TimelineQuery, t.Tweet] =
    timelineServiceTweetCandidateSource

  override val resultTransformer: CandidatePipelineResultsTransformer[t.Tweet, TweetCandidate] = {
    sourceResult => TweetCandidate(id = sourceResult.statusId)
  }

  override val featuresFromCandidateSourceTransformers: Seq[CandidateFeatureTransformer[t.Tweet]] =
    Seq(TimelineServiceResponseFeatureTransformer)

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.7)
  )
}
