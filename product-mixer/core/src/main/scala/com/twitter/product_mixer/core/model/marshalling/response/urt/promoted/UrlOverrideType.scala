package com.twitter.product_mixer.core.model.marshalling.response.urt.promoted

sealed trait UrlOverrideType

object UnknownUrlOverrideType extends UrlOverrideType
object DcmUrlOverrideType extends UrlOverrideType
