package com.twitter.product_mixer.core.model.marshalling.response.urp

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.HasClientEventInfo

sealed trait PageNavBar

case class TopicPageNavBar(
  topicId: String,
  override val clientEventInfo: Option[ClientEventInfo] = None)
    extends PageNavBar
    with HasClientEventInfo

case class TitleNavBar(
  title: String,
  subtitle: Option[String] = None,
  override val clientEventInfo: Option[ClientEventInfo] = None)
    extends PageNavBar
    with HasClientEventInfo
