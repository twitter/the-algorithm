package com.X.graph_feature_service.server.modules

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.mtls.client.MtlsStackClient._
import com.X.finagle.ThriftMux
import com.X.finagle.service.RetryBudget
import com.X.graph_feature_service.thriftscala
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.util.{Await, Duration}
import javax.inject.Singleton

case class GraphFeatureServiceWorkerClients(
  workers: Seq[thriftscala.Worker.MethodPerEndpoint])

object GraphFeatureServiceWorkerClientsModule extends XModule {
  private[this] val closeableGracePeriod: Duration = 1.second
  private[this] val requestTimeout: Duration = 25.millis

  @Provides
  @Singleton
  def provideGraphFeatureServiceWorkerClient(
    @Flag(ServerFlagNames.NumWorkers) numWorkers: Int,
    @Flag(ServerFlagNames.ServiceRole) serviceRole: String,
    @Flag(ServerFlagNames.ServiceEnv) serviceEnv: String,
    serviceIdentifier: ServiceIdentifier
  ): GraphFeatureServiceWorkerClients = {

    val workers: Seq[thriftscala.Worker.MethodPerEndpoint] =
      (0 until numWorkers).map { id =>
        val dest = s"/srv#/$serviceEnv/local/$serviceRole/graph_feature_service-worker-$id"

        val client = ThriftMux.client
          .withRequestTimeout(requestTimeout)
          .withRetryBudget(RetryBudget.Empty)
          .withMutualTls(serviceIdentifier)
          .build[thriftscala.Worker.MethodPerEndpoint](dest, s"worker-$id")

        onExit {
          val closeable = client.asClosable
          Await.result(closeable.close(closeableGracePeriod), closeableGracePeriod)
        }

        client
      }

    GraphFeatureServiceWorkerClients(workers)
  }
}
