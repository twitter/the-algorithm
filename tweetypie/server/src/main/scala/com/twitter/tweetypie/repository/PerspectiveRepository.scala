package com.twitter.tweetypie
package repository

import com.twitter.stitch.Stitch
import com.twitter.stitch.timelineservice.TimelineService.GetPerspectives
import com.twitter.timelineservice.thriftscala.TimelineEntryPerspective

object PerspectiveRepository {

  /**
   * Same type as com.twitter.stitch.timelineservice.TimelineService.GetPerspectives but without
   * using Arrow.
   */
  type Type = GetPerspectives.Query => Stitch[TimelineEntryPerspective]
}
