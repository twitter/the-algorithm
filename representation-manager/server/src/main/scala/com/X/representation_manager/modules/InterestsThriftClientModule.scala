package com.X.representation_manager.modules

import com.google.inject.Provides
import com.X.conversions.DurationOps._
import com.X.finagle.ThriftMux
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.mtls.client.MtlsStackClient.MtlsThriftMuxClientSyntax
import com.X.finagle.mux.ClientDiscardedRequestException
import com.X.finagle.service.ReqRep
import com.X.finagle.service.ResponseClass
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.thrift.ClientId
import com.X.inject.XModule
import com.X.interests.thriftscala.InterestsThriftService
import com.X.util.Throw
import javax.inject.Singleton

object InterestsThriftClientModule extends XModule {

  @Singleton
  @Provides
  def providesInterestsThriftClient(
    clientId: ClientId,
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): InterestsThriftService.MethodPerEndpoint = {
    ThriftMux.client
      .withClientId(clientId)
      .withMutualTls(serviceIdentifier)
      .withRequestTimeout(450.milliseconds)
      .withStatsReceiver(statsReceiver.scope("InterestsThriftClient"))
      .withResponseClassifier {
        case ReqRep(_, Throw(_: ClientDiscardedRequestException)) => ResponseClass.Ignorable
      }
      .build[InterestsThriftService.MethodPerEndpoint](
        dest = "/s/interests-thrift-service/interests-thrift-service",
        label = "interests_thrift_service"
      )
  }
}
