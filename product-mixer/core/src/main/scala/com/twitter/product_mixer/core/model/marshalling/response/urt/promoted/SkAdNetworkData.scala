package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

case class SkAdNetworkData(
  version: Option[String], // version of the SKAdNetwork protocol
  srcAppId: Option[String], // app showing the ad (Twitter app or app promoting through MOPUB)
  dstAppId: Option[String], // app being promoted
  adNetworkId: Option[String], // the ad-network-id being used
  campaignId: Option[Long], // the sk-campaign-id - different from the Twitter campaign id
  impressionTimeInMillis: Option[Long], // the timestamp of the impression
  nonce: Option[String], // nonce used to generate the signature
  signature: Option[String], // the signed payload
  fidelityType: Option[Long] // th
)
