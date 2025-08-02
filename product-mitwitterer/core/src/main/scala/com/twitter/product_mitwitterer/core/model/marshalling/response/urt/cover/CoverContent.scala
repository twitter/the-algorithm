package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.DismissInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ImageDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ImageVariant
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert

sealed trait CoverContent

case class FullCoverContent(
  displayType: FullCoverDisplayType,
  primaryTetwittert: RichTetwittert,
  primaryCoverCta: CoverCta,
  secondaryCoverCta: Option[CoverCta],
  secondaryTetwittert: Option[RichTetwittert],
  imageVariant: Option[ImageVariant],
  details: Option[RichTetwittert],
  dismissInfo: Option[DismissInfo],
  imageDisplayType: Option[ImageDisplayType],
  impressionCallbacks: Option[List[Callback]])
    etwittertends CoverContent

case class HalfCoverContent(
  displayType: HalfCoverDisplayType,
  primaryTetwittert: RichTetwittert,
  primaryCoverCta: CoverCta,
  secondaryCoverCta: Option[CoverCta],
  secondaryTetwittert: Option[RichTetwittert],
  impressionCallbacks: Option[List[Callback]],
  dismissible: Option[Boolean],
  coverImage: Option[CoverImage],
  dismissInfo: Option[DismissInfo])
    etwittertends CoverContent
