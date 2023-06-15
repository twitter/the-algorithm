package com.twitter.tweetypie.serverutil

import com.twitter.finagle.Service
import com.twitter.util.Activity
import com.twitter.util.Future

/**
 * Transforms an `Activity` that contains a `Service` into a `Service`.
 * The implementation guarantees that the service is rebuilt only when the
 * activity changes, not on every request.
 */
object ActivityService {

  def apply[Req, Rep](activity: Activity[Service[Req, Rep]]): Service[Req, Rep] = {

    val serviceEvent =
      ActivityUtil.strict(activity).values.map(_.get)

    new Service[Req, Rep] {

      def apply(req: Req): Future[Rep] =
        serviceEvent.toFuture.flatMap(_.apply(req))
    }
  }
}
