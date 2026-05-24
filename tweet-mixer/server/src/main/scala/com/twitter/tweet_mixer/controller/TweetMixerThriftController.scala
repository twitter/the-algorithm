package com.twitter.tweet_mixer.controller

import com.twitter.core_workflows.user_model.thriftscala.UserState
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.thrift.Controller
import com.twitter.product_mixer.core.controllers.DebugTwitterContext
import com.twitter.product_mixer.core.functional_component.configapi.ParamsBuilder
import com.twitter.product_mixer.core.service.debug_query.DebugQueryService
import com.twitter.simclusters_v2.common.UserId
import com.twitter.timelines.configapi.Params
import com.twitter.tweet_mixer.service.TweetMixerService
import com.twitter.tweet_mixer.marshaller.request.TweetMixerRequestUnmarshaller
import com.twitter.tweet_mixer.model.request.TweetMixerRequest
import com.twitter.tweet_mixer.{thriftscala => t}
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.tweet_mixer.product.imv_related_tweets.model.request.IMVRelatedTweetsProductContext
import com.twitter.util.Future
import javax.inject.Inject

class TweetMixerThriftController @Inject() (
  tweetMixerRequestUnmarshaller: TweetMixerRequestUnmarshaller,
  tweetMixerService: TweetMixerService,
  debugQueryService: DebugQueryService,
  userStateStore: ReadableStore[UserId, UserState],
  statsReceiver: StatsReceiver,
  paramsBuilder: ParamsBuilder)
    extends Controller(t.TweetMixer)
    with DebugTwitterContext {

  handle(t.TweetMixer.GetRecommendationResponse) {
    args: t.TweetMixer.GetRecommendationResponse.Args =>
      val request = tweetMixerRequestUnmarshaller(args.request)
      val paramsFuture = buildParams(request)
      paramsFuture.flatMap { params =>
        Stitch.run(
          tweetMixerService
            .getTweetMixerRecommendationResponse[TweetMixerRequest](request, params))
      }
  }

  // Handle debug requests from Turntable
  handle(t.TweetMixer.ExecutePipeline)
    .withService(debugQueryService(tweetMixerRequestUnmarshaller.apply))

  private def buildParams(request: TweetMixerRequest): Future[Params] = {
    val userStateOptFut =
      userStateStore.get(request.getUserIdLoggedOutSupport).handle { case _ => None }
    userStateOptFut.map { userStateOpt =>
      val userState = userStateOpt.getOrElse(UserState.EnumUnknownUserState(100))
      statsReceiver.scope("UserState").counter(userState.toString).incr()

      val userAgeOpt: Option[Int] = request.clientContext.userId.map { userId =>
        SnowflakeId.timeFromIdOpt(userId).map(_.untilNow.inDays).getOrElse(Int.MaxValue)
      }

      val imvRequestType: String = request.productContext
        .flatMap {
          case context: IMVRelatedTweetsProductContext =>
            context.requestType.map(_.name.toLowerCase)
          case _ => None
        }.getOrElse("")

      val fsCustomMapInput: Map[String, Any] =
        Map(
          "user_state" -> userState.toString,
          "account_age_in_days" -> userAgeOpt.getOrElse(Int.MaxValue),
          "imv_request_type" -> imvRequestType)

      paramsBuilder.build(
        clientContext = request.clientContext,
        product = request.product,
        featureOverrides = request.debugParams.flatMap(_.featureOverrides).getOrElse(Map.empty),
        fsCustomMapInput = fsCustomMapInput
      )
    }
  }
}
