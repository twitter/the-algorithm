try {
package com.twitter.visibility.interfaces.search

case class BatchSearchVisibilityResponse(
  visibilityResults: Map[Long, CombinedVisibilityResult],
  failedTweetIds: Seq[Long])

} catch {
  case e: Exception =>
}
