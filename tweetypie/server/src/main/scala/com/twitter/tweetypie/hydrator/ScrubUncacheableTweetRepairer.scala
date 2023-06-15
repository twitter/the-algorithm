package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.thriftscala._

object ScrubUncacheable {

  // A mutation to use for scrubbing tweets for cache
  val tweetMutation: Mutation[Tweet] =
    Mutation { tweet =>
      if (tweet.place != None ||
        tweet.counts != None ||
        tweet.deviceSource != None ||
        tweet.perspective != None ||
        tweet.cards != None ||
        tweet.card2 != None ||
        tweet.spamLabels != None ||
        tweet.conversationMuted != None)
        Some(
          tweet.copy(
            place = None,
            counts = None,
            deviceSource = None,
            perspective = None,
            cards = None,
            card2 = None,
            spamLabels = None,
            conversationMuted = None
          )
        )
      else
        None
    }

  // throws an AssertionError if a tweet when a tweet is scrubbed
  def assertNotScrubbed(message: String): Mutation[Tweet] =
    tweetMutation.withEffect(Effect(update => assert(update.isEmpty, message)))
}
