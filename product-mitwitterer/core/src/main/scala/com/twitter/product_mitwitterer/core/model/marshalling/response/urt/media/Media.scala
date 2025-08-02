package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.media

case class Media(
  mediaEntity: Option[MediaEntity],
  mediaKey: Option[MediaKey],
  imagePossibleCropping: Option[List[Rect]],
  aspectRatio: Option[AspectRatio])
