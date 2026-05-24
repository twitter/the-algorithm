package com.twitter.product_mixer.core.model.marshalling.response.urp

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.HasClientEventInfo

sealed trait PageHeader

case class TopicPageHeader(
  topicId: String,
  facepile: Option[TopicPageHeaderFacepile] = None,
  override val clientEventInfo: Option[ClientEventInfo] = None,
  landingContext: Option[String] = None,
  displayType: Option[TopicPageHeaderDisplayType] = Some(BasicTopicPageHeaderDisplayType))
    extends PageHeader
    with HasClientEventInfo
