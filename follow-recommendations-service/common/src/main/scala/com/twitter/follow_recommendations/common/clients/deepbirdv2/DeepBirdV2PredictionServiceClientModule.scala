package com.ExTwitter.follow_recommendations.common.clients.deepbirdv2

import com.google.inject.Provides
import com.google.inject.name.Named
import com.ExTwitter.bijection.scrooge.TBinaryProtocol
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.cortex.deepbird.thriftjava.DeepbirdPredictionService
import com.ExTwitter.finagle.ThriftMux
import com.ExTwitter.finagle.builder.ClientBuilder
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.mtls.client.MtlsStackClient._
import com.ExTwitter.finagle.stats.NullStatsReceiver
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.finagle.thrift.ClientId
import com.ExTwitter.finagle.thrift.RichClientParam
import com.ExTwitter.follow_recommendations.common.constants.GuiceNamedConstants
import com.ExTwitter.inject.ExTwitterModule

/**
 * Module that provides multiple deepbirdv2 prediction service clients
 * We use the java api since data records are native java objects and we want to reduce overhead
 * while serializing/deserializing data.
 */
object DeepBirdV2PredictionServiceClientModule extends ExTwitterModule {

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
