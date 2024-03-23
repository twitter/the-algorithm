package com.ExTwitter.cr_mixer

import com.ExTwitter.finagle.thrift.ClientId
import com.ExTwitter.finatra.thrift.routing.ThriftWarmup
import com.ExTwitter.inject.Logging
import com.ExTwitter.inject.utils.Handler
import com.ExTwitter.product_mixer.core.{thriftscala => pt}
import com.ExTwitter.cr_mixer.{thriftscala => st}
import com.ExTwitter.scrooge.Request
import com.ExTwitter.scrooge.Response
import com.ExTwitter.util.Return
import com.ExTwitter.util.Throw
import com.ExTwitter.util.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrMixerThriftServerWarmupHandler @Inject() (warmup: ThriftWarmup)
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
            method = st.CrMixer.GetTweetRecommendations,
            req = Request(st.CrMixer.GetTweetRecommendations.Args(warmupReq)))(assertWarmupResponse)
        }
      }
    } catch {
      case e: Throwable =>
        // we don't want a warmup failure to prevent start-up
        error(e.getMessage, e)
    }
    info("Warm-up done.")
  }

  private def warmupQuery(userId: Long): st.CrMixerTweetRequest = {
    val clientContext = pt.ClientContext(
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
    st.CrMixerTweetRequest(
      clientContext = clientContext,
      product = st.Product.Home,
      productContext = Some(st.ProductContext.HomeContext(st.HomeContext())),
    )
  }

  private def assertWarmupResponse(
    result: Try[Response[st.CrMixer.GetTweetRecommendations.SuccessType]]
  ): Unit = {
    // we collect and log any exceptions from the result.
    result match {
      case Return(_) => // ok
      case Throw(exception) =>
        warn("Error performing warm-up request.")
        error(exception.getMessage, exception)
    }
  }
}
