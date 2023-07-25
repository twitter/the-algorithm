package com.twitter.home_mixer.marshaller.timeline_logging

import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.UserItem
import com.twitter.timelines.timeline_logging.{thriftscala => thriftlog}

object WhoToFollowDetailsMarshaller {

  def apply(entry: UserItem, candidate: ItemCandidateWithDetails): thriftlog.WhoToFollowDetails =
    thriftlog.WhoToFollowDetails(
      enableReactiveBlending = entry.enableReactiveBlending,
      impressionId = entry.promotedMetadata.flatMap(_.impressionString),
      advertiserId = entry.promotedMetadata.map(_.advertiserId)
    )
}
