package com.X.follow_recommendations.common.clients.deepbirdv2

import com.google.inject.Provides
import com.google.inject.name.Named
import com.X.bijection.scrooge.TBinaryProtocol
import com.X.conversions.DurationOps._
import com.X.cortex.deepbird.thriftjava.DeepbirdPredictionService
import com.X.finagle.ThriftMux
import com.X.finagle.builder.ClientBuilder
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.mtls.client.MtlsStackClient._
import com.X.finagle.stats.NullStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.thrift.ClientId
import com.X.finagle.thrift.RichClientParam
import com.X.follow_recommendations.common.constants.GuiceNamedConstants
import com.X.inject.XModule

/**
 * Module that provides multiple deepbirdv2 prediction service clients
 * We use the java api since data records are native java objects and we want to reduce overhead
 * while serializing/deserializing data.
 */
object DeepBirdV2PredictionServiceClientModule extends XModule {

  val RequestTimeout = 300.millis

  private def getDeepbirdPredictionServiceClient(
    clientId: ClientId,
    label: String,
    dest: String,
    statsReceiver: StatsReceiver,
    serviceIdentifier: ServiceIdentifier
  ): DeepbirdPredictionService.ServiceToClient = {
    val clientStatsReceiver = statsReceiver.scope("clnt")
    val mTlsClient = ThriftMux.client.withClientId(clientId).withMutualTls(serviceIdentifier)
    new DeepbirdPredictionService.ServiceToClient(
      ClientBuilder()
        .name(label)
        .stack(mTlsClient)
        .dest(dest)
        .requestTimeout(RequestTimeout)
        .reportHostStats(NullStatsReceiver)
        .build(),
      RichClientParam(
        new TBinaryProtocol.Factory(),
        clientStats = clientStatsReceiver
      )
    )
  }

  @Provides
  @Named(GuiceNamedConstants.WTF_PROD_DEEPBIRDV2_CLIENT)
  def providesWtfProdDeepbirdV2PredictionService(
    clientId: ClientId,
    statsReceiver: StatsReceiver,
    serviceIdentifier: ServiceIdentifier
  ): DeepbirdPredictionService.ServiceToClient = {
    getDeepbirdPredictionServiceClient(
      clientId = clientId,
      label = "WtfProdDeepbirdV2PredictionService",
      dest = "/s/cassowary/deepbirdv2-hermit-wtf",
      statsReceiver = statsReceiver,
      serviceIdentifier = serviceIdentifier
    )
  }
}
