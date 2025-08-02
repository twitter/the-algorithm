package com.twitter.product_mitwitterer.component_library.premarshaller.urt

import com.twitter.product_mitwitterer.component_library.model.candidate.ArticleCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.AudioSpaceCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.TopicCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.TwitterListCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.AddEntriesInstructionBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.BaseUrtMetadataBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.UrtBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.UrtCursorBuilder
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.UrtCursorUpdater
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.UrtInstructionBuilder
import com.twitter.product_mitwitterer.core.functional_component.premarshaller.DomainMarshaller
import com.twitter.product_mitwitterer.core.functional_component.premarshaller.UnsupportedCandidateDomainMarshallerEtwitterception
import com.twitter.product_mitwitterer.core.functional_component.premarshaller.UnsupportedModuleDomainMarshallerEtwitterception
import com.twitter.product_mitwitterer.core.functional_component.premarshaller.UnsupportedPresentationDomainMarshallerEtwitterception
import com.twitter.product_mitwitterer.core.model.common.identifier.DomainMarshallerIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mitwitterer.core.model.common.presentation.ModuleCandidateWithDetails
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineInstruction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.article.ArticleItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.article.FollowingListSeed
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.audio_space.AudioSpaceItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.topic.TopicItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.Tweet
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.twitter_list.TwitterListItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.user.User
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.user.UserItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

/**
 * Decorator that is useful for fast prototyping, as it will generate URT entries from only
 * candidate IDs (no ItemPresentations or ModulePresentations from candidate pipeline decorators
 * are required).
 */
case class UndecoratedUrtDomainMarshaller[Query <: PipelineQuery](
  override val instructionBuilders: Seq[UrtInstructionBuilder[Query, TimelineInstruction]] =
    Seq(AddEntriesInstructionBuilder()),
  override val cursorBuilders: Seq[UrtCursorBuilder[Query]] = Seq.empty,
  override val cursorUpdaters: Seq[UrtCursorUpdater[Query]] = Seq.empty,
  override val metadataBuilder: Option[BaseUrtMetadataBuilder[Query]] = None,
  override val sortIndetwitterStep: Int = 1,
  override val identifier: DomainMarshallerIdentifier =
    DomainMarshallerIdentifier("UndecoratedUnifiedRichTimeline"))
    etwittertends DomainMarshaller[Query, Timeline]
    with UrtBuilder[Query, TimelineInstruction] {

  override def apply(
    query: Query,
    selections: Seq[CandidateWithDetails]
  ): Timeline = {
    val entries = selections.map {
      case itemCandidateWithDetails @ ItemCandidateWithDetails(candidate, None, _) =>
        candidate match {
          case candidate: ArticleCandidate =>
            ArticleItem(
              id = candidate.id,
              articleSeedType = FollowingListSeed,
              sortIndetwitter = None,
              clientEventInfo = None,
              feedbackActionInfo = None,
              displayType = None,
              socialContetwittert = None,
            )
          case candidate: AudioSpaceCandidate =>
            AudioSpaceItem(
              id = candidate.id,
              sortIndetwitter = None,
              clientEventInfo = None,
              feedbackActionInfo = None)
          case candidate: TopicCandidate =>
            TopicItem(
              id = candidate.id,
              sortIndetwitter = None,
              clientEventInfo = None,
              feedbackActionInfo = None,
              topicFunctionalityType = None,
              topicDisplayType = None
            )
          case candidate: TweetCandidate =>
            TweetItem(
              id = candidate.id,
              entryNamespace = TweetItem.TweetEntryNamespace,
              sortIndetwitter = None,
              clientEventInfo = None,
              feedbackActionInfo = None,
              isPinned = None,
              entryIdToReplace = None,
              socialContetwittert = None,
              highlights = None,
              displayType = Tweet,
              innerTombstoneInfo = None,
              timelinesScoreInfo = None,
              hasModeratedReplies = None,
              forwardPivot = None,
              innerForwardPivot = None,
              promotedMetadata = None,
              conversationAnnotation = None,
              contetwittertualTweetRef = None,
              prerollMetadata = None,
              replyBadge = None,
              destination = None
            )
          case candidate: TwitterListCandidate =>
            TwitterListItem(
              id = candidate.id,
              sortIndetwitter = None,
              clientEventInfo = None,
              feedbackActionInfo = None,
              displayType = None
            )
          case candidate: UserCandidate =>
            UserItem(
              id = candidate.id,
              sortIndetwitter = None,
              clientEventInfo = None,
              feedbackActionInfo = None,
              isMarkUnread = None,
              displayType = User,
              promotedMetadata = None,
              socialContetwittert = None,
              reactiveTriggers = None,
              enableReactiveBlending = None
            )
          case candidate =>
            throw new UnsupportedCandidateDomainMarshallerEtwitterception(
              candidate,
              itemCandidateWithDetails.source)
        }
      case itemCandidateWithDetails @ ItemCandidateWithDetails(candidate, Some(presentation), _) =>
        throw new UnsupportedPresentationDomainMarshallerEtwitterception(
          candidate,
          presentation,
          itemCandidateWithDetails.source)
      case moduleCandidateWithDetails @ ModuleCandidateWithDetails(_, presentation, _) =>
        throw new UnsupportedModuleDomainMarshallerEtwitterception(
          presentation,
          moduleCandidateWithDetails.source)
    }

    buildTimeline(query, entries)
  }
}
