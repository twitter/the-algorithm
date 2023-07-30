package com.X.graph_feature_service.server.controllers

import com.X.discovery.common.stats.DiscoveryStatsFilter
import com.X.finagle.Service
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.thrift.Controller
import com.X.graph_feature_service.server.handlers.ServerGetIntersectionHandler.GetIntersectionRequest
import com.X.graph_feature_service.server.handlers.ServerGetIntersectionHandler
import com.X.graph_feature_service.thriftscala
import com.X.graph_feature_service.thriftscala.Server.GetIntersection
import com.X.graph_feature_service.thriftscala.Server.GetPresetIntersection
import com.X.graph_feature_service.thriftscala._
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServerController @Inject() (
  serverGetIntersectionHandler: ServerGetIntersectionHandler
)(
  implicit statsReceiver: StatsReceiver)
    extends Controller(thriftscala.Server) {

  private val getIntersectionService: Service[GetIntersectionRequest, GfsIntersectionResponse] =
    new DiscoveryStatsFilter(statsReceiver.scope("srv").scope("get_intersection"))
      .andThen(Service.mk(serverGetIntersectionHandler))

  val getIntersection: Service[GetIntersection.Args, GfsIntersectionResponse] = { args =>
    // TODO: Disable updateCache after HTL switch to use PresetIntersection endpoint.
    getIntersectionService(
      GetIntersectionRequest.fromGfsIntersectionRequest(args.request, cacheable = true))
  }
  handle(GetIntersection) { getIntersection }

  def getPresetIntersection: Service[
    GetPresetIntersection.Args,
    GfsIntersectionResponse
  ] = { args =>
    // TODO: Refactor after HTL switch to PresetIntersection
    val cacheable = args.request.presetFeatureTypes == PresetFeatureTypes.HtlTwoHop
    getIntersectionService(
      GetIntersectionRequest.fromGfsPresetIntersectionRequest(args.request, cacheable))
  }

  handle(GetPresetIntersection) { getPresetIntersection }

}
