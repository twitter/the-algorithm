package com.twitter.product_mixer.core.model.marshalling.response.urt.item.conversation_annotation

sealed trait ConversationAnnotationType

case object Political extends ConversationAnnotationType
case object Large extends ConversationAnnotationType
