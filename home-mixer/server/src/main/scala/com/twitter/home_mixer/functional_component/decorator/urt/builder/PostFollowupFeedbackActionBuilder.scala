package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.param.HomeGlobalParams.PostFeedbackPromptTitleParam
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.BottomSheet
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackAction
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.GiveFeedback
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelines.common.{thriftscala => tlc}
import com.twitter.timelineservice.model.FeedbackInfo
import com.twitter.timelineservice.model.FeedbackMetadata
import com.twitter.timelineservice.{thriftscala => tls}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class PostFollowupFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings,
  seeMoreChildFeedbackActionBuilder: SeeMoreChildFeedbackActionBuilder,
  seeLessChildFeedbackActionBuilder: SeeLessChildFeedbackActionBuilder) {

  val ClientEventInfoComponent: String = "for_you_post_followup"
  val ClientEventInfoElement: String = "followup"

  def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    candidateFeatures: FeatureMap
  ): Option[FeedbackAction] = {
    CandidatesUtil.getOriginalAuthorId(candidateFeatures).map { authorId =>
      val feedbackEntities = Seq(
        tlc.FeedbackEntity.TweetId(candidate.id),
        tlc.FeedbackEntity.UserId(authorId)
      )
      val feedbackMetadata = FeedbackMetadata(
        engagementType = None,
        entityIds = feedbackEntities,
        ttl = Some(30.days)
      )
      val feedbackUrl = FeedbackInfo.feedbackUrl(
        feedbackType = tls.FeedbackType.Generic,
        feedbackMetadata = feedbackMetadata,
        injectionType = None
      )

      val childFeedbackActions: Seq[ChildFeedbackAction] = {
        Seq(
          seeLessChildFeedbackActionBuilder(query, candidate, candidateFeatures),
          seeMoreChildFeedbackActionBuilder(query, candidate, candidateFeatures)
        ).flatten
      }

      FeedbackAction(
        feedbackType = GiveFeedback,
        prompt = Some(query.params(PostFeedbackPromptTitleParam)),
        confirmation = Some(
          stringCenter.prepare(externalStrings.genericConfirmationString)
        ),
        childFeedbackActions = Some(childFeedbackActions),
        feedbackUrl = Some(feedbackUrl),
        hasUndoAction = None,
        confirmationDisplayType = Some(BottomSheet),
        clientEventInfo = Some(
          ClientEventInfo(
            component = Some(ClientEventInfoComponent),
            element = Some(ClientEventInfoElement),
            details = None,
            action = None,
            entityToken = None
          )),
        icon = None,
        richBehavior = None,
        subprompt = None,
        encodedFeedbackRequest = None
      )
    }
  }
}
