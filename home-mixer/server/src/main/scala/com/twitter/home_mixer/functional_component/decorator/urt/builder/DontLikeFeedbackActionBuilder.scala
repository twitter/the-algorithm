package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.param.HomeGlobalParams.EnableAdditionalChildFeedbackParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnableBlockMuteReportChildFeedbackParam
import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.home_mixer.util.CandidatesUtil
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.marshalling.response.urt.icon.Frown
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ChildFeedbackAction
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.DontLike
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.FeedbackAction
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.StringCenter
import com.twitter.timelines.common.{thriftscala => tlc}
import com.twitter.timelineservice.model.FeedbackMetadata
import com.twitter.timelineservice.model.FeedbackInfo
import com.twitter.timelineservice.{thriftscala => tls}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class DontLikeFeedbackActionBuilder @Inject() (
  @ProductScoped stringCenter: StringCenter,
  externalStrings: HomeMixerExternalStrings,
  authorChildFeedbackActionBuilder: AuthorChildFeedbackActionBuilder,
  retweeterChildFeedbackActionBuilder: RetweeterChildFeedbackActionBuilder,
  notRelevantChildFeedbackActionBuilder: DontlikeNotRelevantChildFeedbackActionBuilder,
  hatefulChildFeedbackActionBuilder: HatefulChildFeedbackActionBuilder,
  boringChildFeedbackActionBuilder: BoringChildFeedbackActionBuilder,
  confusingChildFeedbackActionBuilder: ConfusingChildFeedbackActionBuilder,
  clickbaitChildFeedbackActionBuilder: ClickbaitChildFeedbackActionBuilder,
  ragebaitChildFeedbackActionBuilder: RagebaitChildFeedbackActionBuilder,
  regretChildFeedbackActionBuilder: RegretChildFeedbackActionBuilder,
  blockUserChildFeedbackActionBuilder: BlockUserChildFeedbackActionBuilder,
  muteUserChildFeedbackActionBuilder: MuteUserChildFeedbackActionBuilder) {

  private val DontLikeClientEventInfo =
    ClientEventInfo(None, Some("feedback_dontlike"), None, Some("click"), None)

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
        injectionType = None
      )

      val additionalChildFeedbackActions: Seq[ChildFeedbackAction] =
        if (query.params(EnableAdditionalChildFeedbackParam)) {
          Seq(
            hatefulChildFeedbackActionBuilder(query, candidate, candidateFeatures),
            boringChildFeedbackActionBuilder(query, candidate, candidateFeatures),
            confusingChildFeedbackActionBuilder(query, candidate, candidateFeatures),
            clickbaitChildFeedbackActionBuilder(query, candidate, candidateFeatures),
            ragebaitChildFeedbackActionBuilder(query, candidate, candidateFeatures),
            regretChildFeedbackActionBuilder(query, candidate, candidateFeatures)
          ).flatten
        } else Seq.empty

      val blockMuteReportChildFeedbackActions: Seq[ChildFeedbackAction] =
        if (query.params(EnableBlockMuteReportChildFeedbackParam)) {
          Seq(
            blockUserChildFeedbackActionBuilder(candidateFeatures),
            muteUserChildFeedbackActionBuilder(candidateFeatures)
          ).flatten
        } else Seq.empty

      val childFeedbackActions = Seq(
        authorChildFeedbackActionBuilder(candidateFeatures),
        retweeterChildFeedbackActionBuilder(candidateFeatures),
        notRelevantChildFeedbackActionBuilder(query, candidate, candidateFeatures),
      ).flatten ++ additionalChildFeedbackActions ++ blockMuteReportChildFeedbackActions

      FeedbackAction(
        feedbackType = DontLike,
        prompt = Some(stringCenter.prepare(externalStrings.dontLikeString)),
        confirmation = Some(stringCenter.prepare(externalStrings.dontLikeConfirmationString)),
        childFeedbackActions =
          if (childFeedbackActions.nonEmpty) Some(childFeedbackActions) else None,
        feedbackUrl = Some(feedbackUrl),
        hasUndoAction = Some(true),
        confirmationDisplayType = None,
        clientEventInfo = Some(DontLikeClientEventInfo),
        icon = Some(Frown),
        richBehavior = None,
        subprompt = None,
        encodedFeedbackRequest = None
      )
    }
  }
}
