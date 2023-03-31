package com.twitter.product_mixer.core.model.marshalling.response.urt.item.forward_pivot

import com.twitter.product_mixer.core.model.marshalling.response.urt.color.RosettaColor
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Badge
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText

case class ForwardPivot(
  text: RichText,
  landingUrl: Url,
  displayType: ForwardPivotDisplayType,
  iconImageVariant: Option[ImageVariant],
  stateBadge: Option[Badge],
  subtext: Option[RichText],
  backgroundColorName: Option[RosettaColor],
  engagementNudge: Option[Boolean],
  softInterventionDisplayType: Option[SoftInterventionDisplayType])
