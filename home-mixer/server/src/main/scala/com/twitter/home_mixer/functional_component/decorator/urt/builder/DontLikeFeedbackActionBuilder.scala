package com.ExTwitter.home_mixer.functional_component.decorator.urt.builder

import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.home_mixer.param.HomeGlobalParams.EnableNahFeedbackInfoParam
import com.ExTwitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.ExTwitter.home_mixer.util.CandidatesUtil
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.icon.Frown
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.DontLike
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackAction
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.product.guice.scope.ProductScoped
import com.ExTwitter.stringcenter.client.StringCenter
import com.ExTwitter.timelines.common.{thriftscala => tlc}
import com.ExTwitter.timelineservice.model.FeedbackInfo
import com.ExTwitter.timelineservice.model.FeedbackMetadata
import com.ExTwitter.timelineservice.{thriftscala => tls}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class DontLikeFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings,
  authorChildFeedbackActionBuilder: AuthorChildFeedbackActionBuilder,
  retweeterChildFeedbackActionBuilder: RetweeterChildFeedbackActionBuilder,
  notRelevantChildFeedbackActionBuilder: NotRelevantChildFeedbackActionBuilder,
  unfollowUserChildFeedbackActionBuilder: UnfollowUserChildFeedbackActionBuilder,
  muteUserChildFeedbackActionBuilder: MuteUserChildFeedbackActionBuilder,
  blockUserChildFeedbackActionBuilder: BlockUserChildFeedbackActionBuilder,
  reportTweetChildFeedbackActionBuilder: ReportTweetChildFeedbackActionBuilder) {

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
        feedbackType = tls.FeedbackType.DontLike,
        feedbackMetadata = feedbackMetadata,
        injectionType = candidateFeatures.getOrElse(SuggestTypeFeature, None)
      )
      val childFeedbackActions = if (query.params(EnableNahFeedbackInfoParam)) {
        Seq(
          unfollowUserChildFeedbackActionBuilder(candidateFeatures),
          muteUserChildFeedbackActionBuilder(candidateFeatures),
          blockUserChildFeedbackActionBuilder(candidateFeatures),
          reportTweetChildFeedbackActionBuilder(candidate)
        ).flatten
      } else {
        Seq(
          authorChildFeedbackActionBuilder(candidateFeatures),
          retweeterChildFeedbackActionBuilder(candidateFeatures),
          notRelevantChildFeedbackActionBuilder(candidate, candidateFeatures)
        ).flatten
      }

      FeedbackAction(
        feedbackType = DontLike,
        prompt = Some(stringCenter.prepare(externalStrings.dontLikeString)),
        confirmation = Some(stringCenter.prepare(externalStrings.dontLikeConfirmationString)),
        childFeedbackActions =
          if (childFeedbackActions.nonEmpty) Some(childFeedbackActions) else None,
        feedbackUrl = Some(feedbackUrl),
        hasUndoAction = Some(true),
        confirmationDisplayType = None,
        clientEventInfo = None,
        icon = Some(Frown),
        richBehavior = None,
        subprompt = None,
        encodedFeedbackRequest = None
      )
    }
  }
}
