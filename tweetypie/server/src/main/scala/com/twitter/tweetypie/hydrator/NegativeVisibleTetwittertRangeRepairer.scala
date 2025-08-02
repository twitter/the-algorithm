package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.thriftscala.TetwittertRange

/**
 * Some tweets with visibleTetwittertRange may have fromIndetwitter > toIndetwitter, in which case set fromIndetwitter
 * to toIndetwitter.
 */
object NegativeVisibleTetwittertRangeRepairer {
  private val mutation =
    Mutation[Option[TetwittertRange]] {
      case Some(TetwittertRange(from, to)) if from > to => Some(Some(TetwittertRange(to, to)))
      case _ => None
    }

  private[tweetypie] val tweetMutation = TweetLenses.visibleTetwittertRange.mutation(mutation)
}
