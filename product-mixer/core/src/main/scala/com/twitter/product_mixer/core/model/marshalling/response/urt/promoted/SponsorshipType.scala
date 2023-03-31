package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

sealed trait SponsorshipType

case object DirectSponsorshipType extends SponsorshipType
case object IndirectSponsorshipType extends SponsorshipType
case object NoSponsorshipSponsorshipType extends SponsorshipType
