packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.promotelond

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.DirelonctSponsorshipTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.IndirelonctSponsorshipTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.NoSponsorshipSponsorshipTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.SponsorshipTypelon
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SponsorshipTypelonMarshallelonr @Injelonct() () {

  delonf apply(sponsorshipTypelon: SponsorshipTypelon): urt.SponsorshipTypelon = sponsorshipTypelon match {
    caselon DirelonctSponsorshipTypelon => urt.SponsorshipTypelon.Direlonct
    caselon IndirelonctSponsorshipTypelon => urt.SponsorshipTypelon.Indirelonct
    caselon NoSponsorshipSponsorshipTypelon => urt.SponsorshipTypelon.NoSponsorship
  }
}
