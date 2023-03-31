package com.twitter.home_mixer.marshaller.timeline_logging

import com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module.ScoreFeature
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.UserItem
import com.twitter.timelines.timeline_logging.{thriftscala => thriftlog}

object WhoToFollowEntryMarshaller {

  def apply(entry: UserItem, candidate: ItemCandidateWithDetails): thriftlog.WhoToFollowEntry =
    thriftlog.WhoToFollowEntry(
      userId = entry.id,
      displayType = Some(entry.displayType.toString),
      score = candidate.features.getOrElse(ScoreFeature, None),
      enableReactiveBlending = entry.enableReactiveBlending,
      impressionId = entry.promotedMetadata.flatMap(_.impressionString),
      advertiserId = entry.promotedMetadata.map(_.advertiserId)
    )
}
