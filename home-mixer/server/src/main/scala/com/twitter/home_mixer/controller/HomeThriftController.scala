package com.ExTwitter.home_mixer.controller

import com.ExTwitter.finatra.thrift.Controller
import com.ExTwitter.home_mixer.marshaller.request.HomeMixerRequestUnmarshaller
import com.ExTwitter.home_mixer.model.request.HomeMixerRequest
import com.ExTwitter.home_mixer.service.ScoredTweetsService
import com.ExTwitter.home_mixer.{thriftscala => t}
import com.ExTwitter.product_mixer.core.controllers.DebugExTwitterContext
import com.ExTwitter.product_mixer.core.functional_component.configapi.ParamsBuilder
import com.ExTwitter.product_mixer.core.service.debug_query.DebugQueryService
import com.ExTwitter.product_mixer.core.service.urt.UrtService
import com.ExTwitter.snowflake.id.SnowflakeId
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.configapi.Params
import javax.inject.Inject

class HomeThriftController @Inject() (
  homeRequestUnmarshaller: HomeMixerRequestUnmarshaller,
  urtService: UrtService,
  scoredTweetsService: ScoredTweetsService,
  paramsBuilder: ParamsBuilder)
    extends Controller(t.HomeMixer)
    with DebugExTwitterContext {

  handle(t.HomeMixer.GetUrtResponse) { args: t.HomeMixer.GetUrtResponse.Args =>
    val request = homeRequestUnmarshaller(args.request)
    val params = buildParams(request)
    Stitch.run(urtService.getUrtResponse[HomeMixerRequest](request, params))
  }

  handle(t.HomeMixer.GetScoredTweetsResponse) { args: t.HomeMixer.GetScoredTweetsResponse.Args =>
    val request = homeRequestUnmarshaller(args.request)
    val params = buildParams(request)
    withDebugExTwitterContext(request.clientContext) {
      Stitch.run(scoredTweetsService.getScoredTweetsResponse[HomeMixerRequest](request, params))
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
