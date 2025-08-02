package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.forward_pivot

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.color.RosettaColorMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.BadgeMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.ImageVariantMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.forward_pivot.ForwardPivot
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ForwardPivotMarshaller @Inject() (
  urlMarshaller: UrlMarshaller,
  richTetwittertMarshaller: RichTetwittertMarshaller,
  forwardPivotDisplayTypeMarshaller: ForwardPivotDisplayTypeMarshaller,
  softInterventionDisplayTypeMarshaller: SoftInterventionDisplayTypeMarshaller,
  imageVariantMarshaller: ImageVariantMarshaller,
  badgeMarshaller: BadgeMarshaller,
  rosettaColorMarshaller: RosettaColorMarshaller) {

  def apply(forwardPivot: ForwardPivot): urt.ForwardPivot = urt.ForwardPivot(
    tetwittert = richTetwittertMarshaller(forwardPivot.tetwittert),
    landingUrl = urlMarshaller(forwardPivot.landingUrl),
    displayType = forwardPivotDisplayTypeMarshaller(forwardPivot.displayType),
    iconImageVariant = forwardPivot.iconImageVariant.map(imageVariantMarshaller(_)),
    stateBadge = forwardPivot.stateBadge.map(badgeMarshaller(_)),
    subtetwittert = forwardPivot.subtetwittert.map(richTetwittertMarshaller(_)),
    backgroundColorName = forwardPivot.backgroundColorName.map(rosettaColorMarshaller(_)),
    engagementNudge = forwardPivot.engagementNudge,
    softInterventionDisplayType =
      forwardPivot.softInterventionDisplayType.map(softInterventionDisplayTypeMarshaller(_)),
  )
}
