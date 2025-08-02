package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.RosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Badge
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert

case class ForwardPivot(
  tetwittert: RichTetwittert,
  landingUrl: Url,
  displayType: ForwardPivotDisplayType,
  iconImageVariant: Option[ImageVariant],
  stateBadge: Option[Badge],
  subtetwittert: Option[RichTetwittert],
  backgroundColorName: Option[RosettaColor],
  engagementNudge: Option[Boolean],
  softInterventionDisplayType: Option[SoftInterventionDisplayType])
