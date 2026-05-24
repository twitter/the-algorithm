package com.twitter.unified_user_actions.service.module

import com.google.inject.Provides
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.mtls.client.MtlsStackClient.MtlsThriftMuxClientSyntax
import com.twitter.finagle.ssl.OpportunisticTls
import com.twitter.finagle.thrift.ClientId
import com.twitter.finagle.thrift.RichClientParam
import com.twitter.graphql.thriftscala.GraphqlExecutionService
import com.twitter.inject.TwitterModule
import com.twitter.util.Duration
import javax.inject.Singleton

object GraphqlClientProviderModule extends TwitterModule {
  private def buildClient(serviceIdentifier: ServiceIdentifier, clientId: ClientId) =
    ThriftMux.client
      .withRequestTimeout(Duration.fromSeconds(5))
      .withMutualTls(serviceIdentifier)
      .withOpportunisticTls(OpportunisticTls.Required)
      .withClientId(clientId)
      .newService("/s/graphql-service/graphql-api:thrift")

  def buildGraphQlClient(
    serviceIdentifer: ServiceIdentifier,
    clientId: ClientId
  ): GraphqlExecutionService.FinagledClient = {
    val client = buildClient(serviceIdentifer, clientId)
    new GraphqlExecutionService.FinagledClient(client, RichClientParam())
  }

  @Provides
  @Singleton
  def providesGraphQlClient(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId
  ): GraphqlExecutionService.FinagledClient =
    buildGraphQlClient(
      serviceIdentifier,
      clientId
    )
}
