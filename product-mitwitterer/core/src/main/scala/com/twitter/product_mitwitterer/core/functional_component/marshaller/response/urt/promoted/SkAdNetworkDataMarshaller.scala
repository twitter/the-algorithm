package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.promoted.SkAdNetworkData
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.timelines.render.{thriftscala => urt}

@Singleton
class SkAdNetworkDataMarshaller @Inject() () {

  def apply(skAdNetworkData: SkAdNetworkData): urt.SkAdNetworkData =
    urt.SkAdNetworkData(
      version = skAdNetworkData.version,
      srcAppId = skAdNetworkData.srcAppId,
      dstAppId = skAdNetworkData.dstAppId,
      adNetworkId = skAdNetworkData.adNetworkId,
      campaignId = skAdNetworkData.campaignId,
      impressionTimeInMillis = skAdNetworkData.impressionTimeInMillis,
      nonce = skAdNetworkData.nonce,
      signature = skAdNetworkData.signature,
      fidelityType = skAdNetworkData.fidelityType
    )
}
