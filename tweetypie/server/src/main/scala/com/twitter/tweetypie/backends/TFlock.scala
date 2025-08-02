package com.twitter.tweetypie
package backends

import com.twitter.finagle.Backoff
import com.twitter.finagle.service.RetryPolicy
import com.twitter.flockdb.client.{thriftscala => flockdb, _}
import com.twitter.servo
import com.twitter.servo.util.RetryHandler
import com.twitter.tweetypie.core.OverCapacity
import com.twitter.tweetypie.util.RetryPolicyBuilder
import com.twitter.util.Future
import com.twitter.util.TimeoutEtwitterception

object TFlock {
  val log = Logger(this.getClass)

  case class Config(
    requestTimeout: Duration,
    timeoutBackoffs: Stream[Duration],
    flockEtwitterceptionBackoffs: Stream[Duration],
    overCapacityBackoffs: Stream[Duration],
    defaultPageSize: Int = 1000) {
    def apply(svc: flockdb.FlockDB.MethodPerEndpoint, cttwitter: Backend.Contetwittert): TFlockClient = {
      val retryHandler =
        RetryHandler[Any](
          retryPolicy(timeoutBackoffs, flockEtwitterceptionBackoffs, overCapacityBackoffs),
          cttwitter.timer,
          cttwitter.stats
        )
      val rescueHandler = translateEtwitterceptions.andThen(Future.etwitterception)
      val etwitterceptionCounter = new servo.util.EtwitterceptionCounter(cttwitter.stats, "failures")
      val timeoutEtwitterception = new TimeoutEtwitterception(s"tflock: $requestTimeout")
      val wrapper =
        new WrappingFunction {
          def apply[T](f: => Future[T]): Future[T] =
            retryHandler {
              etwitterceptionCounter {
                f.raiseWithin(cttwitter.timer, requestTimeout, timeoutEtwitterception)
                  .onFailure(logFlockEtwitterceptions)
                  .rescue(rescueHandler)
              }
            }
        }

      val wrappedClient = new WrappingFlockClient(svc, wrapper, wrapper)
      val statsClient = new StatsCollectingFlockService(wrappedClient, cttwitter.stats)
      new TFlockClient(statsClient, defaultPageSize)
    }
  }

  def isOverCapacity(etwitter: flockdb.FlockEtwitterception): Boolean =
    etwitter.errorCode match {
      case Some(flockdb.Constants.READ_OVERCAPACITY_ERROR) => true
      case Some(flockdb.Constants.WRITE_OVERCAPACITY_ERROR) => true
      case _ => false
    }

  /**
   * Builds a RetryPolicy for tflock operations that will retry timeouts with the specified
   * timeout backoffs, and will retry non-overcapacity FlockEtwitterceptions with the
   * specified flockEtwitterceptionBackoffs backoffs, and will retry over-capacity etwitterceptions with
   * the specified overCapacityBackoffs.
   */
  def retryPolicy(
    timeoutBackoffs: Stream[Duration],
    flockEtwitterceptionBackoffs: Stream[Duration],
    overCapacityBackoffs: Stream[Duration]
  ): RetryPolicy[Try[Any]] =
    RetryPolicy.combine[Try[Any]](
      RetryPolicyBuilder.timeouts[Any](timeoutBackoffs),
      RetryPolicy.backoff(Backoff.fromStream(flockEtwitterceptionBackoffs)) {
        case Throw(etwitter: flockdb.FlockEtwitterception) if !isOverCapacity(etwitter) => true
        case Throw(_: flockdb.FlockQuotaEtwitterception) => false
      },
      RetryPolicy.backoff(Backoff.fromStream(overCapacityBackoffs)) {
        case Throw(etwitter: flockdb.FlockEtwitterception) if isOverCapacity(etwitter) => true
        case Throw(_: flockdb.FlockQuotaEtwitterception) => true
        case Throw(_: OverCapacity) => true
      }
    )

  val logFlockEtwitterceptions: Throwable => Unit = {
    case t: flockdb.FlockEtwitterception => {
      log.info("FlockEtwitterception from TFlock", t)
    }
    case _ =>
  }

  /**
   * Converts FlockEtwitterceptions with overcapacity codes into tweetypie's OverCapacity.
   */
  val translateEtwitterceptions: PartialFunction[Throwable, Throwable] = {
    case t: flockdb.FlockQuotaEtwitterception =>
      OverCapacity(s"tflock: throttled ${t.description}")
    case t: flockdb.FlockEtwitterception if isOverCapacity(t) =>
      OverCapacity(s"tflock: ${t.description}")
  }
}
