package com.twitter.graph_feature_service.server.controllers

import com.twitter.discovery.common.stats.DiscoveryStatsFilter
import com.twitter.finagle.Service
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.thrift.Controller
import com.twitter.graph_feature_service.server.handlers.ServerGetIntersectionHandler
import com.twitter.graph_feature_service.server.handlers.ServerGetIntersectionHandler.{fromGfsIntersectionRequest, fromGfsPresetIntersectionRequest}
import com.twitter.graph_feature_service.thriftscala._
import javax.inject.{Inject, Singleton}

@Singleton
class ServerController @Inject() (
  serverGetIntersectionHandler: ServerGetIntersectionHandler
)(implicit statsReceiver: StatsReceiver) extends Controller(thriftscala.Server) {

  private val getIntersectionService: Service[GetIntersectionRequest, GfsIntersectionResponse] =
    new DiscoveryStatsFilter(statsReceiver.scope("srv").scope("get_intersection"))
      .andThen(Service.mk(serverGetIntersectionHandler))

  val getIntersection: Service[GetIntersection.Args, GfsIntersectionResponse] = { args =>
    getIntersectionService(fromGfsIntersectionRequest(args.request, cacheable = true))
  }

  handle(GetIntersection) { getIntersection }

  def getPresetIntersection: Service[GetPresetIntersection.Args, GfsIntersectionResponse] = { args =>
    getIntersectionService(fromGfsPresetIntersectionRequest(args.request, cacheable = args.request.presetFeatureTypes == PresetFeatureTypes.HtlTwoHop))
  }

  handle(GetPresetIntersection) { getPresetIntersection }

}
