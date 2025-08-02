package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.conversions.DurationOps._
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.home_mitwitterer.param.HomeGlobalParams.EnableNahFeedbackInfoParam
import com.twitter.home_mitwitterer.product.following.model.HomeMitwittererEtwitterternalStrings
import com.twitter.home_mitwitterer.util.CandidatesUtil
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.icon.Frown
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.DontLike
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.FeedbackAction
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelines.common.{thriftscala => tlc}
import com.twitter.timelineservice.model.FeedbackInfo
import com.twitter.timelineservice.model.FeedbackMetadata
import com.twitter.timelineservice.{thriftscala => tls}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class DontLikeFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  etwitterternalStrings: HomeMitwittererEtwitterternalStrings,
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
        prompt = Some(stringCenter.prepare(etwitterternalStrings.dontLikeString)),
        confirmation = Some(stringCenter.prepare(etwitterternalStrings.dontLikeConfirmationString)),
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
