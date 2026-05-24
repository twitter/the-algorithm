package com.twitter.tweetypie.thriftscala.entities

object Implicits {
  implicit val hashtagTextEntity: HashtagTextEntity.type = HashtagTextEntity
  implicit val cashtagTextEntity: CashtagTextEntity.type = CashtagTextEntity
  implicit val mentionTextEntity: MentionTextEntity.type = MentionTextEntity
  implicit val urlTextEntity: UrlTextEntity.type = UrlTextEntity
  implicit val mediaTextEntity: MediaTextEntity.type = MediaTextEntity
  implicit val textRangeTextEntity: TextRangeEntityAdapter.type = TextRangeEntityAdapter
}
