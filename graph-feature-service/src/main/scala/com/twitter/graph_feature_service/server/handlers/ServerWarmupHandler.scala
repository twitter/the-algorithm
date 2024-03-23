package com.ExTwitter.graph_feature_service.server.handlers

import com.ExTwitter.finatra.thrift.routing.ThriftWarmup
import com.ExTwitter.graph_feature_service.thriftscala.EdgeType.FavoritedBy
import com.ExTwitter.graph_feature_service.thriftscala.EdgeType.FollowedBy
import com.ExTwitter.graph_feature_service.thriftscala.EdgeType.Following
import com.ExTwitter.graph_feature_service.thriftscala.Server.GetIntersection
import com.ExTwitter.graph_feature_service.thriftscala.FeatureType
import com.ExTwitter.graph_feature_service.thriftscala.GfsIntersectionRequest
import com.ExTwitter.inject.utils.Handler
import com.ExTwitter.scrooge.Request
import com.ExTwitter.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random

@Singleton
class ServerWarmupHandler @Inject() (warmup: ThriftWarmup) extends Handler {

  val logger: Logger = Logger("WarmupHandler")

  // TODO: Add the testing accounts to warm-up the service.
  private val testingAccounts: Array[Long] = Seq.empty.toArray

  private def getRandomRequest: GfsIntersectionRequest = {
    GfsIntersectionRequest(
      testingAccounts(Random.nextInt(testingAccounts.length)),
      testingAccounts,
      Seq(FeatureType(Following, FollowedBy), FeatureType(Following, FavoritedBy))
    )
  }

  override def handle(): Unit = {
    warmup.sendRequest(
      GetIntersection,
      Request(
        GetIntersection.Args(
          getRandomRequest
        )),
      10
    )()

    logger.info("Warmup Done!")
  }
}
