package com.ExTwitter.home_mixer.functional_component.decorator.urt.builder

import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.metadata.WhoToFollowFeedbackActionInfoBuilder
import com.ExTwitter.product_mixer.component_library.model.candidate.UserCandidate
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.ExTwitter.product_mixer.core.product.guice.scope.ProductScoped
import com.ExTwitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackActionInfo
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.timelines.service.{thriftscala => tl}
import com.ExTwitter.timelines.util.FeedbackRequestSerializer
import com.ExTwitter.timelineservice.suggests.thriftscala.SuggestType
import com.ExTwitter.timelineservice.thriftscala.FeedbackType

object HomeWhoToSubscribeFeedbackActionInfoBuilder {
  private val FeedbackMetadata = tl.FeedbackMetadata(
    injectionType = Some(SuggestType.WhoToSubscribe),
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
case class HomeWhoToSubscribeFeedbackActionInfoBuilder @Inject() (
  feedbackStrings: FeedbackStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    extends BaseFeedbackActionInfoBuilder[PipelineQuery, UserCandidate] {

  private val whoToSubscribeFeedbackActionInfoBuilder = WhoToFollowFeedbackActionInfoBuilder(
    seeLessOftenFeedbackString = feedbackStrings.seeLessOftenFeedbackString,
    seeLessOftenConfirmationFeedbackString = feedbackStrings.seeLessOftenConfirmationFeedbackString,
    stringCenter = stringCenterProvider.get(),
    encodedFeedbackRequest =
      Some(HomeWhoToSubscribeFeedbackActionInfoBuilder.EncodedFeedbackRequest)
  )

  override def apply(
    query: PipelineQuery,
    candidate: UserCandidate,
    candidateFeatures: FeatureMap
  ): Option[FeedbackActionInfo] =
    whoToSubscribeFeedbackActionInfoBuilder.apply(query, candidate, candidateFeatures)
}
