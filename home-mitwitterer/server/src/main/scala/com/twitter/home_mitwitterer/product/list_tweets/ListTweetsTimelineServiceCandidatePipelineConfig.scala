package com.twitter.home_mitwitterer.product.list_tweets

import com.twitter.home_mitwitterer.candidate_pipeline.TimelineServiceResponseFeatureTransformer
import com.twitter.home_mitwitterer.marshaller.timelines.TimelineServiceCursorMarshaller
import com.twitter.home_mitwitterer.product.list_tweets.model.ListTweetsQuery
import com.twitter.home_mitwitterer.product.list_tweets.param.ListTweetsParam.ServerMatwitterResultsParam
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.component_library.candidate_source.timeline_service.TimelineServiceTweetCandidateSource
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelineservice.{thriftscala => t}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ListTweetsTimelineServiceCandidatePipelineConfig @Inject() (
  timelineServiceTweetCandidateSource: TimelineServiceTweetCandidateSource)
    etwittertends CandidatePipelineConfig[ListTweetsQuery, t.TimelineQuery, t.Tweet, TweetCandidate] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ListTweetsTimelineServiceTweets")

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ListTweetsQuery,
    t.TimelineQuery
  ] = { query =>
    val timelineQueryOptions = t.TimelineQueryOptions(
      contetwittertualUserId = query.clientContetwittert.userId,
    )

    t.TimelineQuery(
      timelineType = t.TimelineType.List,
      timelineId = query.listId,
      matwitterCount = query.matwitterResults(ServerMatwitterResultsParam).toShort,
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
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.7)
  )
}
