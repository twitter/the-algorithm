package com.twitter.product_mixer.core.model.marshalling.response.urt.item.card

sealed trait CardDisplayType

case object HeroDisplayType extends CardDisplayType
case object CellDisplayType extends CardDisplayType
case object TweetCardDisplayType extends CardDisplayType
