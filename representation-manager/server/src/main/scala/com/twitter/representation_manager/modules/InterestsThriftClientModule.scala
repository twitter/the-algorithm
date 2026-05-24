package com.twitter.representation_manager.modules

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient.MtlsThriftMuxClientSyntax
import com.twitter.finagle.mux.ClientDiscardedRequestException
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.inject.TwitterModule
import com.twitter.interests.thriftscala.InterestsThriftService
import com.twitter.util.Throw
import javax.inject.Singleton

object InterestsThriftClientModule extends TwitterModule {

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
