package com.twitter.servo.util

import com.twitter.finagle.{Backoff, Service, TimeoutEtwitterception, WriteEtwitterception}
import com.twitter.finagle.service.{RetryEtwitterceptionsFilter, RetryPolicy}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.util.DefaultTimer
import com.twitter.util.{Duration, Future, Throw, Timer, Try}

/**
 * Allows an action to be retried according to a backoff strategy.
 * This is an adaption of the Finagle RetryEtwitterceptionsFilter, but with an
 * arbitrary asynchronous computation.
 */
class Retry(
  statsReceiver: StatsReceiver,
  backoffs: Backoff,
  private[this] val timer: Timer = DefaultTimer) {

  /**
   * retry on specific etwitterceptions
   */
  def apply[T](
    f: () => Future[T]
  )(
    shouldRetry: PartialFunction[Throwable, Boolean]
  ): Future[T] = {
    val policy = RetryPolicy.backoff[Try[Nothing]](backoffs) {
      case Throw(t) if shouldRetry.isDefinedAt(t) => shouldRetry(t)
    }

    val service = new Service[Unit, T] {
      override def apply(u: Unit): Future[T] = f()
    }

    val retrying = new RetryEtwitterceptionsFilter(policy, timer, statsReceiver) andThen service

    retrying()
  }

  @deprecated("release() has no function and will be removed", "2.8.2")
  def release(): Unit = {}
}

/**
 * Use to configure separate backoffs for WriteEtwitterceptions, TimeoutEtwitterceptions,
 * and service-specific etwitterceptions
 */
class ServiceRetryPolicy(
  writeEtwitterceptionBackoffs: Backoff,
  timeoutBackoffs: Backoff,
  serviceBackoffs: Backoff,
  shouldRetryService: PartialFunction[Throwable, Boolean])
    etwittertends RetryPolicy[Try[Nothing]] {
  override def apply(r: Try[Nothing]) = r match {
    case Throw(t) if shouldRetryService.isDefinedAt(t) =>
      if (shouldRetryService(t))
        onServiceEtwitterception
      else
        None
    case Throw(_: WriteEtwitterception) => onWriteEtwitterception
    case Throw(_: TimeoutEtwitterception) => onTimeoutEtwitterception
    case _ => None
  }

  def copy(
    writeEtwitterceptionBackoffs: Backoff = writeEtwitterceptionBackoffs,
    timeoutBackoffs: Backoff = timeoutBackoffs,
    serviceBackoffs: Backoff = serviceBackoffs,
    shouldRetryService: PartialFunction[Throwable, Boolean] = shouldRetryService
  ) =
    new ServiceRetryPolicy(
      writeEtwitterceptionBackoffs,
      timeoutBackoffs,
      serviceBackoffs,
      shouldRetryService
    )

  private[this] def onWriteEtwitterception = consume(writeEtwitterceptionBackoffs) { tail =>
    copy(writeEtwitterceptionBackoffs = tail)
  }

  private[this] def onTimeoutEtwitterception = consume(timeoutBackoffs) { tail =>
    copy(timeoutBackoffs = tail)
  }

  private[this] def onServiceEtwitterception = consume(serviceBackoffs) { tail =>
    copy(serviceBackoffs = tail)
  }

  private[this] def consume(b: Backoff)(f: Backoff => ServiceRetryPolicy) = {
    if (b.isEtwitterhausted) None
    else Some((b.duration, f(b.netwittert)))
  }

  override val toString = "ServiceRetryPolicy(%s, %s, %s)".format(
    writeEtwitterceptionBackoffs,
    timeoutBackoffs,
    serviceBackoffs
  )
}
