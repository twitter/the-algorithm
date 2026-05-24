package com.twitter.tweetypie.federated.columns

import com.twitter.tweetypie.{thriftscala => thrift}

object HydrationOptions {

  def writePathHydrationOptions(
    cardsPlatformKey: Option[String]
  ) =
    thrift.WritePathHydrationOptions(
      // The GraphQL API extracts or "lifts" the ApiTweet.card reference field from the
      // ApiTweet.card.url returned by Tweetypie. Tweetypie's card hydration business logic
      // selects the single correct Card URL by first making Expandodo.getCards2 requests for
      // the Tweet's cardReference, or all of the Tweet's URL entities in cases where Tweet
      // does not have a stored cardReference, and then selecting the last of the hydrated
      // cards returned by Expandodo.
      includeCards = true,
      cardsPlatformKey = cardsPlatformKey,
      // The GraphQL API only supports quoted tweet results formatted per go/simplequotedtweet.
      simpleQuotedTweet = true,
    )
}
