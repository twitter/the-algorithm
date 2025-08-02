package com.twitter.tweetypie.thriftscala.entities

object Implicits {
  implicit val hashtagTetwittertEntity: HashtagTetwittertEntity.type = HashtagTetwittertEntity
  implicit val cashtagTetwittertEntity: CashtagTetwittertEntity.type = CashtagTetwittertEntity
  implicit val mentionTetwittertEntity: MentionTetwittertEntity.type = MentionTetwittertEntity
  implicit val urlTetwittertEntity: UrlTetwittertEntity.type = UrlTetwittertEntity
  implicit val mediaTetwittertEntity: MediaTetwittertEntity.type = MediaTetwittertEntity
  implicit val tetwittertRangeTetwittertEntity: TetwittertRangeEntityAdapter.type = TetwittertRangeEntityAdapter
}
