package com.twitter.home_mixer.controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.home_mixer.model.request.HomeMixerRequest
import com.twitter.home_mixer.model.request.ScoredTweetsProduct
import com.twitter.home_mixer.model.request.ScoredTweetsProductContext
import com.twitter.home_mixer.service.ScoredTweetsService
import com.twitter.product_mixer.core.functional_component.configapi.ParamsBuilder
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Params
import com.twitter.util.jackson.ScalaObjectMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeHttpController @Inject() (
  scoredTweetsService: ScoredTweetsService,
  paramsBuilder: ParamsBuilder,
  mapper: ScalaObjectMapper)
    extends Controller {

  private val UserIdParam = "userId"
  private val CountryCode = "US"
  private val LanguageCode = "en"
  private val AppId = 1L
  private val DefaultUserId = 1L
  private val UserAgent = ""

  private val BaseClientContext = ClientContext(
    userId = Some(DefaultUserId),
    guestId = None,
    appId = Some(AppId),
    ipAddress = None,
    userAgent = Some(UserAgent),
    countryCode = Some(CountryCode),
    languageCode = Some(LanguageCode),
    isTwoffice = None,
    userRoles = None,
    deviceId = None,
    mobileDeviceId = None,
    mobileDeviceAdId = None,
    limitAdTracking = None,
    guestIdAds = None,
    guestIdMarketing = None,
    authenticatedUserId = None,
    isVerifiedCrawler = None
  )

  case class ScoredTweetMetadata(tweetId: Long, authorId: Long, inNetwork: Boolean, text: String)

  get("/scoredTweets") { request: Request =>
    val userId = request.getLongParam(UserIdParam)
    val hmRequest = HomeMixerRequest(
      clientContext = BaseClientContext.copy(userId = Some(userId)),
      product = ScoredTweetsProduct,
      productContext =
        Some(ScoredTweetsProductContext(None, None, None, None, None, None, None, None)),
      serializedRequestCursor = None,
      maxResults = None,
      debugParams = None,
      homeRequestParam = false
    )

    val params = buildParams(hmRequest)
    val response = scoredTweetsService.getScoredTweetsResponse[HomeMixerRequest](hmRequest, params)
    Stitch.run(response).map { scoredTweetsResponse =>
      scoredTweetsResponse.scoredTweets.map { tweet =>
        val tweetData = ScoredTweetMetadata(
          tweet.tweetId,
          tweet.authorId,
          tweet.inNetwork.getOrElse(false),
          tweet.tweetText.getOrElse("")
        )
        mapper.writeValueAsString(tweetData)
      }
    }
  }

  private def buildParams(request: HomeMixerRequest): Params = {
    val userAgeOpt = request.clientContext.userId.map { userId =>
      SnowflakeId.timeFromIdOpt(userId).map(_.untilNow.inDays).getOrElse(Int.MaxValue)
    }
    val fsCustomMapInput = userAgeOpt.map("account_age_in_days" -> _).toMap
    paramsBuilder.build(
      clientContext = request.clientContext,
      product = request.product,
      featureOverrides = request.debugParams.flatMap(_.featureOverrides).getOrElse(Map.empty),
      fsCustomMapInput = fsCustomMapInput
    )
  }
}
