package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

sealed trait ConfirmationDisplayType

case object Inline extends ConfirmationDisplayType
case object BottomSheet extends ConfirmationDisplayType
