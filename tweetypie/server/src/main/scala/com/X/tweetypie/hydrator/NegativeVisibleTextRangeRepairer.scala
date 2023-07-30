package com.X.tweetypie
package hydrator

import com.X.tweetypie.thriftscala.TextRange

/**
 * Some tweets with visibleTextRange may have fromIndex > toIndex, in which case set fromIndex
 * to toIndex.
 */
object NegativeVisibleTextRangeRepairer {
  private val mutation =
    Mutation[Option[TextRange]] {
      case Some(TextRange(from, to)) if from > to => Some(Some(TextRange(to, to)))
      case _ => None
    }

  private[tweetypie] val tweetMutation = TweetLenses.visibleTextRange.mutation(mutation)
}
