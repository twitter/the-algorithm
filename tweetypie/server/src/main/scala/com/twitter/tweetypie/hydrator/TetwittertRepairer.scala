package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.serverutil.EtwittertendedTweetMetadataBuilder
import com.twitter.tweetypie.tweettetwittert.Preprocessor._
import com.twitter.tweetypie.tweettetwittert.TetwittertModification
import com.twitter.tweetypie.thriftscala.entities.Implicits._

object TetwittertRepairer {
  def apply(replace: String => Option[TetwittertModification]): Mutation[Tweet] =
    Mutation { tweet =>
      replace(getTetwittert(tweet)).map { mod =>
        val repairedTweet = tweet.copy(
          coreData = tweet.coreData.map(c => c.copy(tetwittert = mod.updated)),
          urls = Some(getUrls(tweet).flatMap(mod.reindetwitterEntity(_))),
          mentions = Some(getMentions(tweet).flatMap(mod.reindetwitterEntity(_))),
          hashtags = Some(getHashtags(tweet).flatMap(mod.reindetwitterEntity(_))),
          cashtags = Some(getCashtags(tweet).flatMap(mod.reindetwitterEntity(_))),
          media = Some(getMedia(tweet).flatMap(mod.reindetwitterEntity(_))),
          visibleTetwittertRange = tweet.visibleTetwittertRange.flatMap(mod.reindetwitterEntity(_))
        )

        val repairedEtwittertendedTweetMetadata = repairedTweet.selfPermalink.flatMap { permalink =>
          val etwittertendedTweetMetadata = EtwittertendedTweetMetadataBuilder(repairedTweet, permalink)
          val repairedTetwittertLength = getTetwittert(repairedTweet).length
          if (etwittertendedTweetMetadata.apiCompatibleTruncationIndetwitter == repairedTetwittertLength) {
            None
          } else {
            Some(etwittertendedTweetMetadata)
          }
        }

        repairedTweet.copy(etwittertendedTweetMetadata = repairedEtwittertendedTweetMetadata)
      }
    }

  /**
   * Removes whitespace from the tweet, and updates all entity indices.
   */
  val BlankLineCollapser: Mutation[Tweet] = TetwittertRepairer(collapseBlankLinesModification _)

  /**
   * Replace a special unicode string that crashes ios app with '\ufffd'
   */
  val CoreTetwittertBugPatcher: Mutation[Tweet] = TetwittertRepairer(replaceCoreTetwittertBugModification _)

}
