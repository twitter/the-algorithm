package com.ExTwitter.home_mixer

import com.ExTwitter.finagle.thrift.ClientId
import com.ExTwitter.finatra.thrift.routing.ThriftWarmup
import com.ExTwitter.home_mixer.{thriftscala => st}
import com.ExTwitter.util.logging.Logging
import com.ExTwitter.inject.utils.Handler
import com.ExTwitter.product_mixer.core.{thriftscala => pt}
import com.ExTwitter.scrooge.Request
import com.ExTwitter.scrooge.Response
import com.ExTwitter.util.Return
import com.ExTwitter.util.Throw
import com.ExTwitter.util.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeMixerThriftServerWarmupHandler @Inject() (warmup: ThriftWarmup)
    extends Handler
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
            method = st.HomeMixer.GetUrtResponse,
            req = Request(st.HomeMixer.GetUrtResponse.Args(warmupReq)))(assertWarmupResponse)
        }
      }
    } catch {
      case e: Throwable => error(e.getMessage, e)
    }
    info("Warm-up done.")
  }

  private def warmupQuery(userId: Long): st.HomeMixerRequest = {
    val clientContext = pt.ClientContext(
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
    st.HomeMixerRequest(
      clientContext = clientContext,
      product = st.Product.Following,
      productContext = Some(st.ProductContext.Following(st.Following())),
      maxResults = Some(3)
    )
  }

  private def assertWarmupResponse(
    result: Try[Response[st.HomeMixer.GetUrtResponse.SuccessType]]
  ): Unit = {
    result match {
      case Return(_) => // ok
      case Throw(exception) =>
        warn("Error performing warm-up request.")
        error(exception.getMessage, exception)
    }
  }
}
