package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.forward_pivot

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.color.RosettaColorMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.BadgeMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.ImageVariantMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.forward_pivot.ForwardPivot
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForwardPivotMarshaller @Inject() (
  urlMarshaller: UrlMarshaller,
  richTextMarshaller: RichTextMarshaller,
  forwardPivotDisplayTypeMarshaller: ForwardPivotDisplayTypeMarshaller,
  softInterventionDisplayTypeMarshaller: SoftInterventionDisplayTypeMarshaller,
  imageVariantMarshaller: ImageVariantMarshaller,
  badgeMarshaller: BadgeMarshaller,
  rosettaColorMarshaller: RosettaColorMarshaller) {

  def apply(forwardPivot: ForwardPivot): urt.ForwardPivot = urt.ForwardPivot(
    text = richTextMarshaller(forwardPivot.text),
    landingUrl = urlMarshaller(forwardPivot.landingUrl),
    displayType = forwardPivotDisplayTypeMarshaller(forwardPivot.displayType),
    iconImageVariant = forwardPivot.iconImageVariant.map(imageVariantMarshaller(_)),
    stateBadge = forwardPivot.stateBadge.map(badgeMarshaller(_)),
    subtext = forwardPivot.subtext.map(richTextMarshaller(_)),
    backgroundColorName = forwardPivot.backgroundColorName.map(rosettaColorMarshaller(_)),
    engagementNudge = forwardPivot.engagementNudge,
    softInterventionDisplayType =
      forwardPivot.softInterventionDisplayType.map(softInterventionDisplayTypeMarshaller(_)),
  )
}
