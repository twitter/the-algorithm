package com.X.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.X.product_mixer.core.model.marshalling.response.urt.promoted.SkAdNetworkData
import javax.inject.Inject
import javax.inject.Singleton
import com.X.timelines.render.{thriftscala => urt}

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
