package com.twitter.home_mixer

import com.twitter.finagle.thrift.ClientId
import com.twitter.finatra.thrift.routing.ThriftWarmup
import com.twitter.home_mixer.{thriftscala => st}
import com.twitter.inject.utils.Handler
import com.twitter.product_mixer.core.{thriftscala => pt}
import com.twitter.scrooge.Request
import com.twitter.scrooge.Response
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeMixerThriftServerWarmupHandler @Inject() (warmup: ThriftWarmup)
    extends Handler
    with Logging {

  private val CurrentClientId = ClientId("thrift-warmup-client")
  private val SleepThreshold = 10000 // millis

  private val TestIds = Seq.empty

  private val BaseClientContext = pt.ClientContext(
    userId = TestIds.headOption,
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

  def handle(): Unit = {
    try {
      CurrentClientId.asCurrent {
        TestIds.foreach { id =>
          val warmupReqs = warmupQuery(id)
          warmupReqs.foreach { warmupReq =>
            info(s"Sending warm-up request to service with query: $warmupReq")
            warmup.sendRequest(
              method = st.HomeMixer.GetUrtResponse,
              req = Request(st.HomeMixer.GetUrtResponse.Args(warmupReq)),
            )(assertWarmupResponse)
          }
        }
      }
    } catch {
      case e: Throwable =>
        error(e.getMessage, e)
    }
    info("Warm-up done.")
  }

  private def warmupQuery(userId: Long): Seq[st.HomeMixerRequest] = {
    val clientContext = BaseClientContext.copy(userId = Some(userId))

    val scoredTweets = st.HomeMixerRequest(
      clientContext = clientContext,
      product = st.Product.ScoredTweets,
      productContext = Some(st.ProductContext.ScoredTweets(st.ScoredTweets())),
    )

    val scoredVideoTweets = st.HomeMixerRequest(
      clientContext = clientContext,
      product = st.Product.ScoredVideoTweets,
      productContext = Some(st.ProductContext.ScoredVideoTweets(st.ScoredVideoTweets())),
    )

    val forYou = st.HomeMixerRequest(
      clientContext = clientContext,
      product = st.Product.ForYou,
      productContext = Some(st.ProductContext.ForYou(st.ForYou())),
    )

    val following = st.HomeMixerRequest(
      clientContext = clientContext,
      product = st.Product.Following,
      productContext = Some(st.ProductContext.Following(st.Following())),
    )

    Seq(scoredTweets, scoredVideoTweets, forYou, following)
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
