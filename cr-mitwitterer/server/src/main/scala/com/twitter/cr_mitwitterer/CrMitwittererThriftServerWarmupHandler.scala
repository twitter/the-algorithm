package com.twitter.cr_mitwitterer

import com.twitter.finagle.thrift.ClientId
import com.twitter.finatra.thrift.routing.ThriftWarmup
import com.twitter.inject.Logging
import com.twitter.inject.utils.Handler
import com.twitter.product_mitwitterer.core.{thriftscala => pt}
import com.twitter.cr_mitwitterer.{thriftscala => st}
import com.twitter.scrooge.Request
import com.twitter.scrooge.Response
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CrMitwittererThriftServerWarmupHandler @Inject() (warmup: ThriftWarmup)
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
            method = st.CrMitwitterer.GetTweetRecommendations,
            req = Request(st.CrMitwitterer.GetTweetRecommendations.Args(warmupReq)))(assertWarmupResponse)
        }
      }
    } catch {
      case e: Throwable =>
        // we don't want a warmup failure to prevent start-up
        error(e.getMessage, e)
    }
    info("Warm-up done.")
  }

  private def warmupQuery(userId: Long): st.CrMitwittererTweetRequest = {
    val clientContetwittert = pt.ClientContetwittert(
      userId = Some(userId),
      guestId = None,
      appId = Some(258901L),
      ipAddress = Some("0.0.0.0"),
      userAgent = Some("FAKE_USER_AGENT_FOR_WARMUPS"),
      countryCode = Some("US"),
      languageCode = Some("en"),
      isTwoffice = None,
      userRoles = None,
      deviceId = Some("FAKE_DEVICE_ID_FOR_WARMUPS")
    )
    st.CrMitwittererTweetRequest(
      clientContetwittert = clientContetwittert,
      product = st.Product.Home,
      productContetwittert = Some(st.ProductContetwittert.HomeContetwittert(st.HomeContetwittert())),
    )
  }

  private def assertWarmupResponse(
    result: Try[Response[st.CrMitwitterer.GetTweetRecommendations.SuccessType]]
  ): Unit = {
    // we collect and log any etwitterceptions from the result.
    result match {
      case Return(_) => // ok
      case Throw(etwitterception) =>
        warn("Error performing warm-up request.")
        error(etwitterception.getMessage, etwitterception)
    }
  }
}
