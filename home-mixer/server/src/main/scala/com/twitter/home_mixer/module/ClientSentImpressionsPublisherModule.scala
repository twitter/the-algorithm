package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.ExTwitter.conversions.DurationOps._
import com.ExTwitter.eventbus.client.EventBusPublisher
import com.ExTwitter.eventbus.client.EventBusPublisherBuilder
import com.ExTwitter.finagle.mtls.authentication.ServiceIdentifier
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.timelines.config.ConfigUtils
import com.ExTwitter.timelines.config.Env
import com.ExTwitter.timelines.impressionstore.thriftscala.PublishedImpressionList
import javax.inject.Singleton

object ClientSentImpressionsPublisherModule extends ExTwitterModule with ConfigUtils {
  private val serviceName = "home-mixer"

  @Singleton
  @Provides
  def providesClientSentImpressionsPublisher(
    serviceIdentifier: ServiceIdentifier,
    statsReceiver: StatsReceiver
  ): EventBusPublisher[PublishedImpressionList] = {
    val env = serviceIdentifier.environment.toLowerCase match {
      case "prod" => Env.prod
      case "staging" => Env.staging
      case "local" => Env.local
      case _ => Env.devel
    }

    val streamName = env match {
      case Env.prod => "timelinemixer_client_sent_impressions_prod"
      case _ => "timelinemixer_client_sent_impressions_devel"
    }

    EventBusPublisherBuilder()
      .clientId(clientIdWithScopeOpt(serviceName, env))
      .serviceIdentifier(serviceIdentifier)
      .streamName(streamName)
      .statsReceiver(statsReceiver.scope("eventbus"))
      .thriftStruct(PublishedImpressionList)
      .tcpConnectTimeout(20.milliseconds)
      .connectTimeout(100.milliseconds)
      .requestTimeout(1.second)
      .publishTimeout(1.second)
      .build()
  }
}
