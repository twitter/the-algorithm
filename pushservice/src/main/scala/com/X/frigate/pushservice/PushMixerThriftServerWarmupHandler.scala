package com.X.frigate.pushservice

import com.google.inject.Inject
import com.google.inject.Singleton
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.thrift.ClientId
import com.X.finatra.thrift.routing.ThriftWarmup
import com.X.util.logging.Logging
import com.X.inject.utils.Handler
import com.X.frigate.pushservice.{thriftscala => t}
import com.X.frigate.thriftscala.NotificationDisplayLocation
import com.X.util.Stopwatch
import com.X.scrooge.Request
import com.X.scrooge.Response
import com.X.util.Return
import com.X.util.Throw
import com.X.util.Try

/**
 * Warms up the refresh request path.
 * If service is running as pushservice-send then the warmup does nothing.
 *
 * When making the warmup refresh requests we
 *  - Set skipFilters to true to execute as much of the request path as possible
 *  - Set darkWrite to true to prevent sending a push
 */
@Singleton
class PushMixerThriftServerWarmupHandler @Inject() (
  warmup: ThriftWarmup,
  serviceIdentifier: ServiceIdentifier)
    extends Handler
    with Logging {

  private val clientId = ClientId("thrift-warmup-client")

  def handle(): Unit = {
    val refreshServices = Set(
      "frigate-pushservice",
      "frigate-pushservice-canary",
      "frigate-pushservice-canary-control",
      "frigate-pushservice-canary-treatment"
    )
    val isRefresh = refreshServices.contains(serviceIdentifier.service)
    if (isRefresh && !serviceIdentifier.isLocal) refreshWarmup()
  }

  def refreshWarmup(): Unit = {
    val elapsed = Stopwatch.start()
    val testIds = Seq(
      1,
      2,
      3
    )
    try {
      clientId.asCurrent {
        testIds.foreach { id =>
          val warmupReq = warmupQuery(id)
          info(s"Sending warm-up request to service with query: $warmupReq")
          warmup.sendRequest(
            method = t.PushService.Refresh,
            req = Request(t.PushService.Refresh.Args(warmupReq)))(assertWarmupResponse)
        }
      }
    } catch {
      case e: Throwable =>
        error(e.getMessage, e)
    }
    info(s"Warm up complete. Time taken: ${elapsed().toString}")
  }

  private def warmupQuery(userId: Long): t.RefreshRequest = {
    t.RefreshRequest(
      userId = userId,
      notificationDisplayLocation = NotificationDisplayLocation.PushToMobileDevice,
      context = Some(
        t.PushContext(
          skipFilters = Some(true),
          darkWrite = Some(true)
        ))
    )
  }

  private def assertWarmupResponse(
    result: Try[Response[t.PushService.Refresh.SuccessType]]
  ): Unit = {
    result match {
      case Return(_) => // ok
      case Throw(exception) =>
        warn("Error performing warm-up request.")
        error(exception.getMessage, exception)
    }
  }
}
