package com.twitter.product_mixer.core.model.marshalling.response.urt.cover

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mixer.core.model.marshalling.response.urt.richtext.RichText

sealed trait CoverCtaBehavior

case class CoverBehaviorNavigate(url: Url) extends CoverCtaBehavior
case class CoverBehaviorDismiss(feedbackMessage: Option[RichText]) extends CoverCtaBehavior
