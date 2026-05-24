package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.Frown
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.BottomSheet
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackAction
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.NotRelevant
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
case class PostDetailsNegativeFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings) {

  private val PostDetailsNegativeClientEventInfo =
    ClientEventInfo(None, Some("feedback_notrelevant"), None, Some("click"), None)

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
        feedbackType = tls.FeedbackType.NotRelevant,
        feedbackMetadata = feedbackMetadata,
        injectionType = None
      )

      FeedbackAction(
        feedbackType = NotRelevant,
        prompt = Some(stringCenter.prepare(externalStrings.notRelevantString)),
        confirmation = Some(stringCenter.prepare(externalStrings.notRelevantConfirmationString)),
        childFeedbackActions = None,
        feedbackUrl = Some(feedbackUrl),
        hasUndoAction = Some(false),
        confirmationDisplayType = Some(BottomSheet),
        clientEventInfo = Some(PostDetailsNegativeClientEventInfo),
        icon = Some(Frown),
        richBehavior = None,
        subprompt = None,
        encodedFeedbackRequest = None
      )
    }
  }
}
