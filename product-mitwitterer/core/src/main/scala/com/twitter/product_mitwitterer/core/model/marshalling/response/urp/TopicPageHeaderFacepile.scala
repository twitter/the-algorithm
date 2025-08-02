package com.twitter.product_mitwitterer.core.model.marshalling.response.urp

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url

case class TopicPageHeaderFacepile(
  userIds: Seq[Long],
  facepileUrl: Option[Url] = None)
