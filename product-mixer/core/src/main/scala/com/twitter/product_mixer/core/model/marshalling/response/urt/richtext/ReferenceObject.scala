package com.twitter.product_mixer.core.model.marshalling.response.urt.richtext

trait ReferenceObject

case class RichTextUser(id: Long) extends ReferenceObject
case class RichTextMention(id: Long, screenName: String) extends ReferenceObject
case class RichTextHashtag(text: String) extends ReferenceObject
case class RichTextCashtag(text: String) extends ReferenceObject
case class RichTextList(id: Long, url: String) extends ReferenceObject
