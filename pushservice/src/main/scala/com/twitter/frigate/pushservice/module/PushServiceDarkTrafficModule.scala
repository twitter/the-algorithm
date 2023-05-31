package com.twitter.frigate.pushservice.module

import com.google.inject.Singleton
import com.twitter.decider.Decider
import com.twitter.decider.RandomRecipient
import com.twitter.finatra.mtls.thriftmux.modules.MtlsClient
import com.twitter.frigate.pushservice.thriftscala.PushService
import com.twitter.inject.Injector
import com.twitter.inject.thrift.modules.ReqRepDarkTrafficFilterModule

/**
 * The darkTraffic filter sample all requests by default
  and set the diffy dest to nil for non prod environments
 */
@Singleton
object PushServiceDarkTrafficModule
    extends ReqRepDarkTrafficFilterModule[PushService.ReqRepServicePerEndpoint]
    with MtlsClient {

  override def label: String = "frigate-pushservice-diffy-proxy"

  /**
   * Function to determine if the request should be "sampled", e.g.
   * sent to the dark service.
   *
   * @param injector the [[com.twitter.inject.Injector]] for use in determining if a given request
   *                 should be forwarded or not.
   */
  override protected def enableSampling(injector: Injector): Any => Boolean = {
    val decider = injector.instance[Decider]
    _ => decider.isAvailable("frigate_pushservice_dark_traffic_percent", Some(RandomRecipient))
  }
}
