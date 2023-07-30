package com.X.recosinjector.clients

import com.X.finagle.stats.StatsReceiver
import com.X.gizmoduck.thriftscala.User
import com.X.logging.Logger
import com.X.storehaus.ReadableStore
import com.X.util.Future

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
