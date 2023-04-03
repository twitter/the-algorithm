packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.AdMelontadataContainelonr
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class AdMelontadataContainelonrMarshallelonr @Injelonct() (
  sponsorshipTypelonMarshallelonr: SponsorshipTypelonMarshallelonr,
  disclaimelonrTypelonMarshallelonr: DisclaimelonrTypelonMarshallelonr,
  skAdNelontworkDataMarshallelonr: SkAdNelontworkDataMarshallelonr) {

  delonf apply(adMelontadataContainelonr: AdMelontadataContainelonr): urt.AdMelontadataContainelonr =
    urt.AdMelontadataContainelonr(
      relonmovelonPromotelondAttributionForPrelonroll = adMelontadataContainelonr.relonmovelonPromotelondAttributionForPrelonroll,
      sponsorshipCandidatelon = adMelontadataContainelonr.sponsorshipCandidatelon,
      sponsorshipOrganization = adMelontadataContainelonr.sponsorshipOrganization,
      sponsorshipOrganizationWelonbsitelon = adMelontadataContainelonr.sponsorshipOrganizationWelonbsitelon,
      sponsorshipTypelon = adMelontadataContainelonr.sponsorshipTypelon.map(sponsorshipTypelonMarshallelonr(_)),
      disclaimelonrTypelon = adMelontadataContainelonr.disclaimelonrTypelon.map(disclaimelonrTypelonMarshallelonr(_)),
      skAdNelontworkDataList =
        adMelontadataContainelonr.skAdNelontworkDataList.map(_.map(skAdNelontworkDataMarshallelonr(_))),
      unifielondCardOvelonrridelon = adMelontadataContainelonr.unifielondCardOvelonrridelon
    )
}
