package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

case class AdMetadataContainer(
  removePromotedAttributionForPreroll: Option[Boolean],
  sponsorshipCandidate: Option[String],
  sponsorshipOrganization: Option[String],
  sponsorshipOrganizationWebsite: Option[String],
  sponsorshipType: Option[SponsorshipType],
  disclaimerType: Option[DisclaimerType],
  skAdNetworkDataList: Option[Seq[SkAdNetworkData]],
  unifiedCardOverride: Option[String])
