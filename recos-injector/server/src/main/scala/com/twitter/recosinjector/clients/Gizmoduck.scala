package com.twitter.recosinjector.clients

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.gizmoduck.thriftscala.User
import com.twitter.logging.Logger
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

class Gizmoduck(
  userStore: ReadableStore[Long, User]
)(
  implicit statsReceiver: StatsReceiver) {
  private val log = Logger()
  private val stats = statsReceiver.scope(this.getClass.getSimpleName)

  def getUser(userId: Long): Future[Option[User]] = {
    userStore
      .get(userId)
      .rescue {
        case e =>
          stats.scope("getUserFailure").counter(e.getClass.getSimpleName).incr()
          log.error(s"Failed with message ${e.toString}")
          Future.None
      }
  }
}
