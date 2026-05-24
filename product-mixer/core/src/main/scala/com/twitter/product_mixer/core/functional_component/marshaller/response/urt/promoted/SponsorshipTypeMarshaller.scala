package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.DirectSponsorshipType
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.IndirectSponsorshipType
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.NoSponsorshipSponsorshipType
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.SponsorshipType
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SponsorshipTypeMarshaller @Inject() () {

  def apply(sponsorshipType: SponsorshipType): urt.SponsorshipType = sponsorshipType match {
    case DirectSponsorshipType => urt.SponsorshipType.Direct
    case IndirectSponsorshipType => urt.SponsorshipType.Indirect
    case NoSponsorshipSponsorshipType => urt.SponsorshipType.NoSponsorship
  }
}
