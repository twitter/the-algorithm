package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

/*
 * As per discussion with #revenue-serving on 9/22/2017, `impressionId` should be set from `impressionString`.
 * impressionId often returns None from adserver, as it's been replaced with impressionString.
 *
 * However, Android (at least) crashes without impressionId filled out in the response.
 *
 * So, we've removed `impressionId` from this case class, and our marshaller will set both `impressionId`
 * and `impressionString` in the render thrift from `impressionString`.
 */

case class PromotedMetadata(
  advertiserId: Long,
  disclosureType: Option[DisclosureType],
  experimentValues: Option[Map[String, String]],
  promotedTrendId: Option[Long],
  promotedTrendName: Option[String],
  promotedTrendQueryTerm: Option[String],
  adMetadataContainer: Option[AdMetadataContainer],
  promotedTrendDescription: Option[String],
  impressionString: Option[String],
  clickTrackingInfo: Option[ClickTrackingInfo])
