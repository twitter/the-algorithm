package com.twitter.product_mixer.core.model.marshalling.response.urt.button

sealed trait ButtonStyle

case object Default extends ButtonStyle
case object Primary extends ButtonStyle
case object Secondary extends ButtonStyle
case object Text extends ButtonStyle
case object Destructive extends ButtonStyle
case object Neutral extends ButtonStyle
case object DestructiveSecondary extends ButtonStyle
case object DestructiveText extends ButtonStyle
