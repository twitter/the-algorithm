package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

sealed trait ImageDisplayType

case object Icon extends ImageDisplayType
case object FullWidth extends ImageDisplayType
case object IconSmall extends ImageDisplayType
