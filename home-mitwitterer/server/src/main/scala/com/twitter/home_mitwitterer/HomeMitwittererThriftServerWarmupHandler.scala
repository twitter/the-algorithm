package com.twitter.home_mitwitterer

import com.twitter.finagle.thrift.ClientId
import com.twitter.finatra.thrift.routing.ThriftWarmup
import com.twitter.home_mitwitterer.{thriftscala => st}
import com.twitter.util.logging.Logging
import com.twitter.inject.utils.Handler
import com.twitter.product_mitwitterer.core.{thriftscala => pt}
import com.twitter.scrooge.Request
import com.twitter.scrooge.Response
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HomeMitwittererThriftServerWarmupHandler @Inject() (warmup: ThriftWarmup)
    etwittertends Handler
    with Logging {

  private val clientId = ClientId("thrift-warmup-client")

  def handle(): Unit = {
    val testIds = Seq(1, 2, 3)
    try {
      clientId.asCurrent {
        testIds.foreach { id =>
          val warmupReq = warmupQuery(id)
          info(s"Sending warm-up request to service with query: $warmupReq")
          warmup.sendRequest(
            method = st.HomeMitwitterer.GetUrtResponse,
            req = Request(st.HomeMitwitterer.GetUrtResponse.Args(warmupReq)))(assertWarmupResponse)
        }
      }
    } catch {
      case e: Throwable => error(e.getMessage, e)
    }
    info("Warm-up done.")
  }

  private def warmupQuery(userId: Long): st.HomeMitwittererRequest = {
    val clientContetwittert = pt.ClientContetwittert(
      userId = Some(userId),
      guestId = None,
      appId = Some(12345L),
      ipAddress = Some("0.0.0.0"),
      userAgent = Some("FAKE_USER_AGENT_FOR_WARMUPS"),
      countryCode = Some("US"),
      languageCode = Some("en"),
      isTwoffice = None,
      userRoles = None,
      deviceId = Some("FAKE_DEVICE_ID_FOR_WARMUPS")
    )
    st.HomeMitwittererRequest(
      clientContetwittert = clientContetwittert,
      product = st.Product.Following,
      productContetwittert = Some(st.ProductContetwittert.Following(st.Following())),
      matwitterResults = Some(3)
    )
  }

  private def assertWarmupResponse(
    result: Try[Response[st.HomeMitwitterer.GetUrtResponse.SuccessType]]
  ): Unit = {
    result match {
      case Return(_) => // ok
      case Throw(etwitterception) =>
        warn("Error performing warm-up request.")
        error(etwitterception.getMessage, etwitterception)
    }
  }
}
