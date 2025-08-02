package com.twitter.unified_user_actions.service.module

import com.google.inject.Provides
import com.twitter.finagle.ThriftMutwitter
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient.MtlsThriftMutwitterClientSyntatwitter
import com.twitter.finagle.ssl.OpportunisticTls
import com.twitter.finagle.thrift.ClientId
import com.twitter.finagle.thrift.RichClientParam
import com.twitter.graphql.thriftscala.GraphqlEtwitterecutionService
import com.twitter.inject.TwitterModule
import com.twitter.util.Duration
import javatwitter.inject.Singleton

object GraphqlClientProviderModule etwittertends TwitterModule {
  private def buildClient(serviceIdentifier: ServiceIdentifier, clientId: ClientId) =
    ThriftMutwitter.client
      .withRequestTimeout(Duration.fromSeconds(5))
      .withMutualTls(serviceIdentifier)
      .withOpportunisticTls(OpportunisticTls.Required)
      .withClientId(clientId)
      .newService("/s/graphql-service/graphql-api:thrift")

  def buildGraphQlClient(
    serviceIdentifer: ServiceIdentifier,
    clientId: ClientId
  ): GraphqlEtwitterecutionService.FinagledClient = {
    val client = buildClient(serviceIdentifer, clientId)
    new GraphqlEtwitterecutionService.FinagledClient(client, RichClientParam())
  }

  @Provides
  @Singleton
  def providesGraphQlClient(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId
  ): GraphqlEtwitterecutionService.FinagledClient =
    buildGraphQlClient(
      serviceIdentifier,
      clientId
    )
}
