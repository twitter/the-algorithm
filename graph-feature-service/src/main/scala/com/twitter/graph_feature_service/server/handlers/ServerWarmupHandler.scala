package com.twitter.graph_feature_service.server.handlers

import com.twitter.finatra.thrift.routing.ThriftWarmup
import com.twitter.graph_feature_service.thriftscala.EdgeType.{FavoritedBy, FollowedBy, Following}
import com.twitter.graph_feature_service.thriftscala.Server.GetIntersection
import com.twitter.graph_feature_service.thriftscala.{FeatureType, GfsIntersectionRequest}
import com.twitter.inject.utils.Handler
import com.twitter.scrooge.Request
import com.twitter.util.logging.Logger
import javax.inject.{Inject, Singleton}
import scala.util.Random

@Singleton
class ServerWarmupHandler @Inject() (warmup: ThriftWarmup) extends Handler {

  val logger: Logger = Logger("WarmupHandler")

  private val testingAccounts: Array[Long] = {
    Seq(
      12L, //jack
      21447363L, // KATY PERRY
      42562446L, // Stephen Curry
      813286L // Barack Obama
    ).toArray
  }

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
