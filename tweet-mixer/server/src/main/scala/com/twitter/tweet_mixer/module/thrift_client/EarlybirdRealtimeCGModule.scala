package com.twitter.tweet_mixer.module.thrift_client

import com.google.inject.Provides
import com.twitter.conversions.DurationOps._
import com.twitter.conversions.PercentOps._
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finagle.thrift.ClientId
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.shared_library.thrift_client.FinagleThriftClientBuilder
import com.twitter.product_mixer.shared_library.thrift_client.Idempotent
import com.twitter.search.earlybird.{thriftscala => t}
import com.twitter.tweet_mixer.model.ModuleNames.EarlybirdRealtimeCGEndpoint

import javax.inject.Named
import javax.inject.Singleton
import org.apache.thrift.protocol.TCompactProtocol

object EarlybirdRealtimeCGModule extends TwitterModule {

  val Label: String = "earlybird-rootrealtimecg"
  val Dest: String = "/s/earlybird-rootrealtimecg/root-realtime_cg"

  @Provides
  @Singleton
  @Named(EarlybirdRealtimeCGEndpoint)
  def providesEarlybirdRealtimeCGService(
    serviceIdentifier: ServiceIdentifier,
    clientId: ClientId,
    statsReceiver: StatsReceiver
  ): t.EarlybirdService.MethodPerEndpoint = {

    FinagleThriftClientBuilder.buildFinagleMethodPerEndpoint[
      t.EarlybirdService.ServicePerEndpoint,
      t.EarlybirdService.MethodPerEndpoint
    ](
      serviceIdentifier = serviceIdentifier,
      clientId = clientId,
      dest = Dest,
      label = Label,
      statsReceiver = statsReceiver,
      protocolFactoryOverride = Some(new TCompactProtocol.Factory),
      idempotency = Idempotent(1.percent),
      timeoutPerRequest = 200.milliseconds,
      timeoutTotal = 400.milliseconds,
      acquisitionTimeout = 1.seconds
    )
  }
}
