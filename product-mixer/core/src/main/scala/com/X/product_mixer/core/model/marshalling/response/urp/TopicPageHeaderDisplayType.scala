package com.X.product_mixer.core.model.marshalling.response.urp

sealed trait TopicPageHeaderDisplayType

case object BasicTopicPageHeaderDisplayType extends TopicPageHeaderDisplayType
case object PersonalizedTopicPageHeaderDisplayType extends TopicPageHeaderDisplayType
