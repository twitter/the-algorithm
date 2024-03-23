package com.ExTwitter.home_mixer.marshaller.timelines

import com.ExTwitter.product_mixer.component_library.model.cursor.UrtUnorderedExcludeIdsCursor
import com.ExTwitter.timelines.service.{thriftscala => t}
import com.ExTwitter.util.Time

object RecommendedUsersCursorUnmarshaller {

  def apply(requestCursor: t.RequestCursor): Option[UrtUnorderedExcludeIdsCursor] = {
    requestCursor match {
      case t.RequestCursor.RecommendedUsersCursor(cursor) =>
        Some(
          UrtUnorderedExcludeIdsCursor(
            initialSortIndex = cursor.minSortIndex.getOrElse(Time.now.inMilliseconds),
            excludedIds = cursor.previouslyRecommendedUserIds
          ))
      case _ => None
    }
  }
}
