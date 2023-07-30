package com.X.graph_feature_service.worker.controllers

import com.X.discovery.common.stats.DiscoveryStatsFilter
import com.X.finagle.Service
import com.X.finagle.stats.StatsReceiver
import com.X.finatra.thrift.Controller
import com.X.graph_feature_service.thriftscala
import com.X.graph_feature_service.thriftscala.Worker.GetIntersection
import com.X.graph_feature_service.thriftscala._
import com.X.graph_feature_service.worker.handlers._
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerController @Inject() (
  workerGetIntersectionHandler: WorkerGetIntersectionHandler
)(
  implicit statsReceiver: StatsReceiver)
    extends Controller(thriftscala.Worker) {

  // use DiscoveryStatsFilter to filter out exceptions out of our control
  private val getIntersectionService: Service[
    WorkerIntersectionRequest,
    WorkerIntersectionResponse
  ] =
    new DiscoveryStatsFilter[WorkerIntersectionRequest, WorkerIntersectionResponse](
      statsReceiver.scope("srv").scope("get_intersection")
    ).andThen(Service.mk(workerGetIntersectionHandler))

  val getIntersection: Service[GetIntersection.Args, WorkerIntersectionResponse] = { args =>
    getIntersectionService(args.request).onFailure { throwable =>
      logger.error(s"Failure to get intersection for request $args.", throwable)
    }
  }

  handle(GetIntersection) { getIntersection }

}
