package com.X.unified_user_actions.service.module

import com.google.inject.Provides
import com.X.finagle.ThriftMux
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.mtls.client.MtlsStackClient.MtlsThriftMuxClientSyntax
import com.X.finagle.ssl.OpportunisticTls
import com.X.finagle.thrift.ClientId
import com.X.finagle.thrift.RichClientParam
import com.X.graphql.thriftscala.GraphqlExecutionService
import com.X.inject.XModule
import com.X.util.Duration
import javax.inject.Singleton

object GraphqlClientProviderModule extends XModule {
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
