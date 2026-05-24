package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

sealed trait DisclosureType

case object NoDisclosure extends DisclosureType
case object Political extends DisclosureType
case object Earned extends DisclosureType
case object Issue extends DisclosureType
