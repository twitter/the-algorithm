package com.X.visibility.interfaces.search

case class BatchSearchVisibilityResponse(
  visibilityResults: Map[Long, CombinedVisibilityResult],
  failedTweetIds: Seq[Long])
