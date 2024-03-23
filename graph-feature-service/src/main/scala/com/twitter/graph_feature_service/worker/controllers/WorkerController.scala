package com.ExTwitter.graph_feature_service.worker.controllers

import com.ExTwitter.discovery.common.stats.DiscoveryStatsFilter
import com.ExTwitter.finagle.Service
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finatra.thrift.Controller
import com.ExTwitter.graph_feature_service.thriftscala
import com.ExTwitter.graph_feature_service.thriftscala.Worker.GetIntersection
import com.ExTwitter.graph_feature_service.thriftscala._
import com.ExTwitter.graph_feature_service.worker.handlers._
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
