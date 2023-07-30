package com.X.product_mixer.component_library.premarshaller.urt

import com.X.product_mixer.component_library.model.candidate.ArticleCandidate
import com.X.product_mixer.component_library.model.candidate.AudioSpaceCandidate
import com.X.product_mixer.component_library.model.candidate.TopicCandidate
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.component_library.model.candidate.XListCandidate
import com.X.product_mixer.component_library.model.candidate.UserCandidate
import com.X.product_mixer.component_library.premarshaller.urt.builder.AddEntriesInstructionBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.BaseUrtMetadataBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.UrtBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.UrtCursorBuilder
import com.X.product_mixer.component_library.premarshaller.urt.builder.UrtCursorUpdater
import com.X.product_mixer.component_library.premarshaller.urt.builder.UrtInstructionBuilder
import com.X.product_mixer.core.functional_component.premarshaller.DomainMarshaller
import com.X.product_mixer.core.functional_component.premarshaller.UnsupportedCandidateDomainMarshallerException
import com.X.product_mixer.core.functional_component.premarshaller.UnsupportedModuleDomainMarshallerException
import com.X.product_mixer.core.functional_component.premarshaller.UnsupportedPresentationDomainMarshallerException
import com.X.product_mixer.core.model.common.identifier.DomainMarshallerIdentifier
import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.X.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.X.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails
import com.X.product_mixer.core.model.marshalling.response.urt.Timeline
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineInstruction
import com.X.product_mixer.core.model.marshalling.response.urt.item.article.ArticleItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.article.FollowingListSeed
import com.X.product_mixer.core.model.marshalling.response.urt.item.audio_space.AudioSpaceItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.topic.TopicItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.tweet.Tweet
import com.X.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.X_list.XListItem
import com.X.product_mixer.core.model.marshalling.response.urt.item.user.User
import com.X.product_mixer.core.model.marshalling.response.urt.item.user.UserItem
import com.X.product_mixer.core.pipeline.PipelineQuery

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
  override val sortIndexStep: Int = 1,
  override val identifier: DomainMarshallerIdentifier =
    DomainMarshallerIdentifier("UndecoratedUnifiedRichTimeline"))
    extends DomainMarshaller[Query, Timeline]
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
              sortIndex = None,
              clientEventInfo = None,
              feedbackActionInfo = None,
              displayType = None,
              socialContext = None,
            )
          case candidate: AudioSpaceCandidate =>
            AudioSpaceItem(
              id = candidate.id,
              sortIndex = None,
              clientEventInfo = None,
              feedbackActionInfo = None)
          case candidate: TopicCandidate =>
            TopicItem(
              id = candidate.id,
              sortIndex = None,
              clientEventInfo = None,
              feedbackActionInfo = None,
              topicFunctionalityType = None,
              topicDisplayType = None
            )
          case candidate: TweetCandidate =>
            TweetItem(
              id = candidate.id,
              entryNamespace = TweetItem.TweetEntryNamespace,
              sortIndex = None,
              clientEventInfo = None,
              feedbackActionInfo = None,
              isPinned = None,
              entryIdToReplace = None,
              socialContext = None,
              highlights = None,
              displayType = Tweet,
              innerTombstoneInfo = None,
              timelinesScoreInfo = None,
              hasModeratedReplies = None,
              forwardPivot = None,
              innerForwardPivot = None,
              promotedMetadata = None,
              conversationAnnotation = None,
              contextualTweetRef = None,
              prerollMetadata = None,
              replyBadge = None,
              destination = None
            )
          case candidate: XListCandidate =>
            XListItem(
              id = candidate.id,
              sortIndex = None,
              clientEventInfo = None,
              feedbackActionInfo = None,
              displayType = None
            )
          case candidate: UserCandidate =>
            UserItem(
              id = candidate.id,
              sortIndex = None,
              clientEventInfo = None,
              feedbackActionInfo = None,
              isMarkUnread = None,
              displayType = User,
              promotedMetadata = None,
              socialContext = None,
              reactiveTriggers = None,
              enableReactiveBlending = None
            )
          case candidate =>
            throw new UnsupportedCandidateDomainMarshallerException(
              candidate,
              itemCandidateWithDetails.source)
        }
      case itemCandidateWithDetails @ ItemCandidateWithDetails(candidate, Some(presentation), _) =>
        throw new UnsupportedPresentationDomainMarshallerException(
          candidate,
          presentation,
          itemCandidateWithDetails.source)
      case moduleCandidateWithDetails @ ModuleCandidateWithDetails(_, presentation, _) =>
        throw new UnsupportedModuleDomainMarshallerException(
          presentation,
          moduleCandidateWithDetails.source)
    }

    buildTimeline(query, entries)
  }
}
