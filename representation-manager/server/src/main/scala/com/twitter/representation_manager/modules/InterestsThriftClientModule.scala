package com.twitter.representation_manager.modules

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient.MtlsThriftMutwitterClientSyntatwitter
import com.twitter.finagle.mutwitter.ClientDiscardedRequestEtwitterception
import com.twitter.finagle.service.ReqRep
import com.twitter.finagle.service.ResponseClass
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.inject.TwitterModule
import com.twitter.interests.thriftscala.InterestsThriftService
import com.twitter.util.Throw
import javatwitter.inject.Singleton

object InterestsThriftClientModule etwittertends TwitterModule {

  @Singleton
  @Provides
  def providesInterestsThriftClient(
    clientId: ClientId,
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): InterestsThriftService.MethodPerEndpoint = {
    ThriftMutwitter.client
      .withClientId(clientId)
      .withMutualTls(serviceIdentifier)
      .withRequestTimeout(450.milliseconds)
      .withStatsReceiver(statsReceiver.scope("InterestsThriftClient"))
      .withResponseClassifier {
        case ReqRep(_, Throw(_: ClientDiscardedRequestEtwitterception)) => ResponseClass.Ignorable
      }
      .build[InterestsThriftService.MethodPerEndpoint](
        dest = "/s/interests-thrift-service/interests-thrift-service",
        label = "interests_thrift_service"
      )
  }
}
