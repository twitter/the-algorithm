package com.X.tweetypie
package repository

import com.X.stitch.Stitch
import com.X.stitch.timelineservice.TimelineService.GetPerspectives
import com.X.timelineservice.thriftscala.TimelineEntryPerspective

object PerspectiveRepository {

  /**
   * Same type as com.X.stitch.timelineservice.TimelineService.GetPerspectives but without
   * using Arrow.
   */
  type Type = GetPerspectives.Query => Stitch[TimelineEntryPerspective]
}
