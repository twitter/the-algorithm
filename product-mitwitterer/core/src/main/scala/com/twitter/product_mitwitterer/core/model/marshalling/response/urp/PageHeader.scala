package com.twitter.product_mitwitterer.core.model.marshalling.response.urp

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.HasClientEventInfo

sealed trait PageHeader

case class TopicPageHeader(
  topicId: String,
  facepile: Option[TopicPageHeaderFacepile] = None,
  override val clientEventInfo: Option[ClientEventInfo] = None,
  landingContetwittert: Option[String] = None,
  displayType: Option[TopicPageHeaderDisplayType] = Some(BasicTopicPageHeaderDisplayType))
    etwittertends PageHeader
    with HasClientEventInfo
