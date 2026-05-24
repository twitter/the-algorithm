package com.twitter.product_mixer.core.model.marshalling.response.urt.item.forward_pivot

sealed trait ForwardPivotDisplayType

case object LiveEvent extends ForwardPivotDisplayType
case object SoftIntervention extends ForwardPivotDisplayType
case object CommunityNotes extends ForwardPivotDisplayType
