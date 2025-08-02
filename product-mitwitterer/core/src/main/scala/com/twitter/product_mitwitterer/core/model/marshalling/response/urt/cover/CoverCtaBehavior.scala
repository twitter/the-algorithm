package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert

sealed trait CoverCtaBehavior

case class CoverBehaviorNavigate(url: Url) etwittertends CoverCtaBehavior
case class CoverBehaviorDismiss(feedbackMessage: Option[RichTetwittert]) etwittertends CoverCtaBehavior
