package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.AdMetadataContainer
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdMetadataContainerMarshaller @Inject() (
  sponsorshipTypeMarshaller: SponsorshipTypeMarshaller,
  disclaimerTypeMarshaller: DisclaimerTypeMarshaller,
  skAdNetworkDataMarshaller: SkAdNetworkDataMarshaller) {

  def apply(adMetadataContainer: AdMetadataContainer): urt.AdMetadataContainer =
    urt.AdMetadataContainer(
      removePromotedAttributionForPreroll = adMetadataContainer.removePromotedAttributionForPreroll,
      sponsorshipCandidate = adMetadataContainer.sponsorshipCandidate,
      sponsorshipOrganization = adMetadataContainer.sponsorshipOrganization,
      sponsorshipOrganizationWebsite = adMetadataContainer.sponsorshipOrganizationWebsite,
      sponsorshipType = adMetadataContainer.sponsorshipType.map(sponsorshipTypeMarshaller(_)),
      disclaimerType = adMetadataContainer.disclaimerType.map(disclaimerTypeMarshaller(_)),
      skAdNetworkDataList =
        adMetadataContainer.skAdNetworkDataList.map(_.map(skAdNetworkDataMarshaller(_))),
      unifiedCardOverride = adMetadataContainer.unifiedCardOverride
    )
}
