package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.metadata.WhoToFollowFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import javatwitter.inject.Inject
import javatwitter.inject.Provider
import javatwitter.inject.Singleton
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.service.{thriftscala => tl}
import com.twitter.timelines.util.FeedbackRequestSerializer
import com.twitter.timelineservice.suggests.thriftscala.SuggestType
import com.twitter.timelineservice.thriftscala.FeedbackType

object HomeWhoToFollowFeedbackActionInfoBuilder {
  private val FeedbackMetadata = tl.FeedbackMetadata(
    injectionType = Some(SuggestType.WhoToFollow),
    engagementType = None,
    entityIds = Seq.empty,
    ttlMs = None
  )
  private val FeedbackRequest =
    tl.DefaultFeedbackRequest2(FeedbackType.SeeFewer, FeedbackMetadata)
  private val EncodedFeedbackRequest =
    FeedbackRequestSerializer.serialize(tl.FeedbackRequest.DefaultFeedbackRequest2(FeedbackRequest))
}

@Singleton
case class HomeWhoToFollowFeedbackActionInfoBuilder @Inject() (
  feedbackStrings: FeedbackStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    etwittertends BaseFeedbackActionInfoBuilder[PipelineQuery, UserCandidate] {

  private val whoToFollowFeedbackActionInfoBuilder = WhoToFollowFeedbackActionInfoBuilder(
    seeLessOftenFeedbackString = feedbackStrings.seeLessOftenFeedbackString,
    seeLessOftenConfirmationFeedbackString = feedbackStrings.seeLessOftenConfirmationFeedbackString,
    stringCenter = stringCenterProvider.get(),
    encodedFeedbackRequest = Some(HomeWhoToFollowFeedbackActionInfoBuilder.EncodedFeedbackRequest)
  )

  override def apply(
    query: PipelineQuery,
    candidate: UserCandidate,
    candidateFeatures: FeatureMap
  ): Option[FeedbackActionInfo] =
    whoToFollowFeedbackActionInfoBuilder.apply(query, candidate, candidateFeatures)
}
