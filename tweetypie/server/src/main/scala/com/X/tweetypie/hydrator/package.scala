package com.X.tweetypie

import com.X.tweetypie.core._
import com.X.tweetypie.repository.TweetQuery
import com.X.tweetypie.thriftscala.FieldByPath
import org.apache.thrift.protocol.TField
import com.X.context.XContext

package object hydrator {
  type TweetDataValueHydrator = ValueHydrator[TweetData, TweetQuery.Options]
  type TweetDataEditHydrator = EditHydrator[TweetData, TweetQuery.Options]

  def fieldByPath(fields: TField*): FieldByPath = FieldByPath(fields.map(_.id))

  val XContext: XContext =
    com.X.context.XContext(com.X.tweetypie.XContextPermit)
}
