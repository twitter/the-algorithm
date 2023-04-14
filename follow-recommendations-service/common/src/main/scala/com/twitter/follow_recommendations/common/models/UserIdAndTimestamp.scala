try {
package com.twitter.follow_recommendations.common.models

case class UserIdWithTimestamp(userId: Long, timeInMs: Long)

} catch {
  case e: Exception =>
}
