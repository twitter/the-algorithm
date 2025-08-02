package com.twitter.home_mitwitterer.marshaller.timeline_logging

import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SourceUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.product_mitwitterer.component_library.model.presentation.urt.UrtItemPresentation
import com.twitter.product_mitwitterer.component_library.model.presentation.urt.UrtModulePresentation
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.GeneralContetwittertTypeMarshaller
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ConversationGeneralContetwittertType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.GeneralContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.TopicContetwittert
import com.twitter.timelines.service.{thriftscala => tst}
import com.twitter.timelines.timeline_logging.{thriftscala => thriftlog}

object TweetDetailsMarshaller {

  private val generalContetwittertTypeMarshaller = new GeneralContetwittertTypeMarshaller()

  def apply(entry: TweetItem, candidate: CandidateWithDetails): thriftlog.TweetDetails = {
    val socialContetwittert = candidate.presentation.flatMap {
      case _ @UrtItemPresentation(timelineItem: TweetItem, _) => timelineItem.socialContetwittert
      case _ @UrtModulePresentation(timelineModule) =>
        timelineModule.items.head.item match {
          case timelineItem: TweetItem => timelineItem.socialContetwittert
          case _ => Some(ConversationGeneralContetwittertType)
        }
    }

    val socialContetwittertType = socialContetwittert match {
      case Some(GeneralContetwittert(contetwittertType, _, _, _, _)) =>
        Some(generalContetwittertTypeMarshaller(contetwittertType).value.toShort)
      case Some(TopicContetwittert(_, _)) => Some(tst.ContetwittertType.Topic.value.toShort)
      case _ => None
    }

    thriftlog.TweetDetails(
      sourceTweetId = candidate.features.getOrElse(SourceTweetIdFeature, None),
      socialContetwittertType = socialContetwittertType,
      suggestType = candidate.features.getOrElse(SuggestTypeFeature, None).map(_.name),
      authorId = candidate.features.getOrElse(AuthorIdFeature, None),
      sourceAuthorId = candidate.features.getOrElse(SourceUserIdFeature, None)
    )
  }
}
