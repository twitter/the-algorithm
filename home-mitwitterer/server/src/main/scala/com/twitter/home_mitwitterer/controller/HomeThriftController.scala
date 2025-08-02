package com.twitter.home_mitwitterer.controller

import com.twitter.finatra.thrift.Controller
import com.twitter.home_mitwitterer.marshaller.request.HomeMitwittererRequestUnmarshaller
import com.twitter.home_mitwitterer.model.request.HomeMitwittererRequest
import com.twitter.home_mitwitterer.service.ScoredTweetsService
import com.twitter.home_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.core.controllers.DebugTwitterContetwittert
import com.twitter.product_mitwitterer.core.functional_component.configapi.ParamsBuilder
import com.twitter.product_mitwitterer.core.service.debug_query.DebugQueryService
import com.twitter.product_mitwitterer.core.service.urt.UrtService
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Params
import javatwitter.inject.Inject

class HomeThriftController @Inject() (
  homeRequestUnmarshaller: HomeMitwittererRequestUnmarshaller,
  urtService: UrtService,
  scoredTweetsService: ScoredTweetsService,
  paramsBuilder: ParamsBuilder)
    etwittertends Controller(t.HomeMitwitterer)
    with DebugTwitterContetwittert {

  handle(t.HomeMitwitterer.GetUrtResponse) { args: t.HomeMitwitterer.GetUrtResponse.Args =>
    val request = homeRequestUnmarshaller(args.request)
    val params = buildParams(request)
    Stitch.run(urtService.getUrtResponse[HomeMitwittererRequest](request, params))
  }

  handle(t.HomeMitwitterer.GetScoredTweetsResponse) { args: t.HomeMitwitterer.GetScoredTweetsResponse.Args =>
    val request = homeRequestUnmarshaller(args.request)
    val params = buildParams(request)
    withDebugTwitterContetwittert(request.clientContetwittert) {
      Stitch.run(scoredTweetsService.getScoredTweetsResponse[HomeMitwittererRequest](request, params))
    }
  }

  private def buildParams(request: HomeMitwittererRequest): Params = {
    val userAgeOpt = request.clientContetwittert.userId.map { userId =>
      SnowflakeId.timeFromIdOpt(userId).map(_.untilNow.inDays).getOrElse(Int.MatwitterValue)
    }
    val fsCustomMapInput = userAgeOpt.map("account_age_in_days" -> _).toMap
    paramsBuilder.build(
      clientContetwittert = request.clientContetwittert,
      product = request.product,
      featureOverrides = request.debugParams.flatMap(_.featureOverrides).getOrElse(Map.empty),
      fsCustomMapInput = fsCustomMapInput
    )
  }
}
