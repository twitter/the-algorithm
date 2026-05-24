package com.twitter.home_mixer.controller

import com.twitter.finatra.thrift.Controller
import com.twitter.home_mixer.marshaller.request.HomeMixerRequestUnmarshaller
import com.twitter.home_mixer.model.request.HomeMixerRequest
import com.twitter.home_mixer.service.ScoredTweetsService
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.core.controllers.DebugTwitterContext
import com.twitter.product_mixer.core.functional_component.configapi.ParamsBuilder
import com.twitter.product_mixer.core.service.debug_query.DebugQueryService
import com.twitter.product_mixer.core.service.urt.UrtService
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Params
import javax.inject.Inject
import server.src.main.scala.com.twitter.home_mixer.service.HeavyRankerScoresService

class HomeThriftController @Inject() (
  homeRequestUnmarshaller: HomeMixerRequestUnmarshaller,
  debugQueryService: DebugQueryService,
  urtService: UrtService,
  scoredTweetsService: ScoredTweetsService,
  heavyRankerScoresService: HeavyRankerScoresService,
  paramsBuilder: ParamsBuilder)
    extends Controller(t.HomeMixer)
    with DebugTwitterContext {

  handle(t.HomeMixer.GetUrtResponse) { args: t.HomeMixer.GetUrtResponse.Args =>
    val request = homeRequestUnmarshaller(args.request)
    val params = buildParams(request)
    Stitch.run(urtService.getUrtResponse[HomeMixerRequest](request, params))
  }

  handle(t.HomeMixer.DebugGetUrtResponse) { args: t.HomeMixer.DebugGetUrtResponse.Args =>
    val request = homeRequestUnmarshaller(args.request)
    val params = buildParams(request)
    withDebugTwitterContext(request.clientContext) {
      Stitch.run(urtService.getUrtResponse[HomeMixerRequest](request, params))
    }
  }

  // Handle debug requests
  handle(t.HomeMixer.ExecutePipeline)
    .withService(debugQueryService(homeRequestUnmarshaller.apply))

  handle(t.HomeMixer.GetScoredTweetsResponse) { args: t.HomeMixer.GetScoredTweetsResponse.Args =>
    val request = homeRequestUnmarshaller(args.request)
    val params = buildParams(request)
    withDebugTwitterContext(request.clientContext) {
      Stitch.run(scoredTweetsService.getScoredTweetsResponse[HomeMixerRequest](request, params))
    }
  }

  handle(t.HomeMixer.GetHeavyRankerScoresResponse) {
    args: t.HomeMixer.GetHeavyRankerScoresResponse.Args =>
      val request = homeRequestUnmarshaller(args.request)
      val params = buildParams(request)
      withDebugTwitterContext(request.clientContext) {
        Stitch.run(
          heavyRankerScoresService.getHeavyRankerScoresResponse[HomeMixerRequest](request, params))
      }
  }

  private def buildParams(request: HomeMixerRequest): Params = {
    val userAgeOpt = request.clientContext.userId.map { userId =>
      // Setting to Int.MaxValue for cases where id is not snowflake id as they are pretty old accounts
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
