package com.X.product_mixer.core.model.marshalling.response.urp

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Url

case class TopicPageHeaderFacepile(
  userIds: Seq[Long],
  facepileUrl: Option[Url] = None)
