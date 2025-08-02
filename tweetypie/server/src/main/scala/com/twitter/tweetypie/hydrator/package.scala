package com.twitter.tweetypie

import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository.TweetQuery
import com.twitter.tweetypie.thriftscala.FieldByPath
import org.apache.thrift.protocol.TField
import com.twitter.contetwittert.TwitterContetwittert

package object hydrator {
  type TweetDataValueHydrator = ValueHydrator[TweetData, TweetQuery.Options]
  type TweetDataEditHydrator = EditHydrator[TweetData, TweetQuery.Options]

  def fieldByPath(fields: TField*): FieldByPath = FieldByPath(fields.map(_.id))

  val TwitterContetwittert: TwitterContetwittert =
    com.twitter.contetwittert.TwitterContetwittert(com.twitter.tweetypie.TwitterContetwittertPermit)
}
