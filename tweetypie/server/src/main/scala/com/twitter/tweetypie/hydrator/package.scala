package com.twitter.tweetypie

import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository.TweetQuery
import com.twitter.tweetypie.thriftscala.FieldByPath
import org.apache.thrift.protocol.TField
import com.twitter.context.TwitterContext

package object hydrator {
  type TweetDataValueHydrator = ValueHydrator[TweetData, TweetQuery.Options]
  type TweetDataEditHydrator = EditHydrator[TweetData, TweetQuery.Options]

  def fieldByPath(fields: TField*): FieldByPath = FieldByPath(fields.map(_.id))

  val TwitterContext: TwitterContext =
    com.twitter.context.TwitterContext(com.twitter.tweetypie.TwitterContextPermit)
}
