package com.twitter.home_mitwitterer.marshaller.timelines

import com.twitter.product_mitwitterer.component_library.model.cursor.UrtUnorderedEtwittercludeIdsCursor
import com.twitter.timelines.service.{thriftscala => t}
import com.twitter.util.Time

object RecommendedUsersCursorUnmarshaller {

  def apply(requestCursor: t.RequestCursor): Option[UrtUnorderedEtwittercludeIdsCursor] = {
    requestCursor match {
      case t.RequestCursor.RecommendedUsersCursor(cursor) =>
        Some(
          UrtUnorderedEtwittercludeIdsCursor(
            initialSortIndetwitter = cursor.minSortIndetwitter.getOrElse(Time.now.inMilliseconds),
            etwittercludedIds = cursor.previouslyRecommendedUserIds
          ))
      case _ => None
    }
  }
}
