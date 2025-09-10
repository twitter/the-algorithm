package com.twitter.tweet_mixer

import com.twitter.finagle.thrift.ClientId
import com.twitter.finatra.thrift.routing.ThriftWarmup
import com.twitter.inject.utils.Handler
import com.twitter.product_mixer.core.{thriftscala => pt}
import com.twitter.tweet_mixer.{thriftscala => st}
import com.twitter.scrooge.Request
import com.twitter.scrooge.Response
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetMixerThriftServerWarmupHandler @Inject() (warmup: ThriftWarmup)
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
            method = st.TweetMixer.GetRecommendationResponse,
            req = Request(st.TweetMixer.GetRecommendationResponse.Args(warmupReq)))(
            assertWarmupResponse)
        }
      }
    } catch {
      case e: Throwable =>
        error(e.getMessage, e)
    }
    info("Warm-up done.")
  }

  private def warmupQuery(userId: Long): st.TweetMixerRequest = {
    val clientContext = pt.ClientContext(
      userId = Some(userId),
      guestId = None,
      appId = Some(1L),
      ipAddress = Some("0.0.0.0"),
      userAgent = Some("FAKE_USER_AGENT_FOR_WARMUPS"),
      countryCode = Some("US"),
      languageCode = Some("en"),
      isTwoffice = None,
      userRoles = None,
      deviceId = Some("FAKE_DEVICE_ID_FOR_WARMUPS")
    )
    st.TweetMixerRequest(
      clientContext = clientContext,
      product = st.Product.HomeRecommendedTweets,
      productContext = Some(
        st.ProductContext.HomeRecommendedTweetsProductContext(
          st.HomeRecommendedTweetsProductContext())),
      maxResults = Some(3)
    )
  }

  private def assertWarmupResponse(
    result: Try[Response[st.TweetMixer.GetRecommendationResponse.SuccessType]]
  ): Unit = {
    result match {
      case Return(_) => // ok
      case Throw(exception) =>
        warn("Error performing warm-up request.")
        error(exception.getMessage, exception)
    }
  }
}
