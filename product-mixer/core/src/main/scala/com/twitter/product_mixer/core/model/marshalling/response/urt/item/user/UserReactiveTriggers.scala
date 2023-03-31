package com.twitter.product_mixer.core.model.marshalling.response.urt.item.user

import com.twitter.product_mixer.core.model.marshalling.response.urt.reaction.TimelineReaction

case class UserReactiveTriggers(onFollow: Option[TimelineReaction])
