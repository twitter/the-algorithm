package com.twitter.home_mixer.marshaller.timeline_logging

import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.timelines.timeline_logging.{thriftscala => thriftlog}

object ConversationEntryMarshaller {

  def apply(entry: TweetItem, candidate: ItemCandidateWithDetails): thriftlog.ConversationEntry =
    thriftlog.ConversationEntry(
      displayedTweetId = entry.id,
      displayType = Some(entry.displayType.toString),
      score = candidate.features.getOrElse(ScoreFeature, None)
    )
}
