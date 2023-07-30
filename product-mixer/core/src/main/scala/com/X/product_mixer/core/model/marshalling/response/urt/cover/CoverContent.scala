package com.X.product_mixer.core.model.marshalling.response.urt.cover

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Callback
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.DismissInfo
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ImageDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.X.product_mixer.core.model.marshalling.response.urt.richtext.RichText

sealed trait CoverContent

case class FullCoverContent(
  displayType: FullCoverDisplayType,
  primaryText: RichText,
  primaryCoverCta: CoverCta,
  secondaryCoverCta: Option[CoverCta],
  secondaryText: Option[RichText],
  imageVariant: Option[ImageVariant],
  details: Option[RichText],
  dismissInfo: Option[DismissInfo],
  imageDisplayType: Option[ImageDisplayType],
  impressionCallbacks: Option[List[Callback]])
    extends CoverContent

case class HalfCoverContent(
  displayType: HalfCoverDisplayType,
  primaryText: RichText,
  primaryCoverCta: CoverCta,
  secondaryCoverCta: Option[CoverCta],
  secondaryText: Option[RichText],
  impressionCallbacks: Option[List[Callback]],
  dismissible: Option[Boolean],
  coverImage: Option[CoverImage],
  dismissInfo: Option[DismissInfo])
    extends CoverContent
