package com.twitter.product_mixer.core.model.marshalling.response.urt.item.label

sealed trait LabelDisplayType

case object InlineHeaderLabelDisplayType extends LabelDisplayType
case object OtherRepliesSectionHeaderLabelDisplayType extends LabelDisplayType
