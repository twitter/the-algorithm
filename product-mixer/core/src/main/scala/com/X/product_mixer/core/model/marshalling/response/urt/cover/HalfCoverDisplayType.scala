package com.X.product_mixer.core.model.marshalling.response.urt.cover

sealed trait HalfCoverDisplayType

case object CoverHalfCoverDisplayType extends HalfCoverDisplayType
case object CenterCoverHalfCoverDisplayType extends HalfCoverDisplayType
