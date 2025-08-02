package com.twitter.tweetypie
package hydrator

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.core.TweetData
import com.twitter.tweetypie.core.ValueState
import com.twitter.tweetypie.repository.TweetQuery
import com.twitter.tweetypie.thriftscala.entities.Implicits._
import com.twitter.tweetypie.thriftscala.TetwittertRange
import com.twitter.tweetypie.tweettetwittert.Offset
import com.twitter.tweetypie.tweettetwittert.TetwittertModification
import com.twitter.tweetypie.tweettetwittert.TweetTetwittert
import com.twitter.tweetypie.util.TweetLenses

object NoteTweetSuffitwitterHydrator {

  val ELLIPSIS: String = "\u2026"

  private def addTetwittertSuffitwitter(tweet: Tweet): Tweet = {
    val originalTetwittert = TweetLenses.tetwittert(tweet)
    val originalTetwittertLength = TweetTetwittert.codePointLength(originalTetwittert)

    val visibleTetwittertRange: TetwittertRange =
      TweetLenses
        .visibleTetwittertRange(tweet)
        .getOrElse(TetwittertRange(0, originalTetwittertLength))

    val insertAtCodePoint = Offset.CodePoint(visibleTetwittertRange.toIndetwitter)

    val tetwittertModification = TetwittertModification.insertAt(
      originalTetwittert,
      insertAtCodePoint,
      ELLIPSIS
    )

    val mediaEntities = TweetLenses.media(tweet)
    val urlEntities = TweetLenses.urls(tweet)

    val modifiedTetwittert = tetwittertModification.updated
    val modifiedMediaEntities = tetwittertModification.reindetwitterEntities(mediaEntities)
    val modifiedUrlEntities = tetwittertModification.reindetwitterEntities(urlEntities)
    val modifiedVisibleTetwittertRange = visibleTetwittertRange.copy(toIndetwitter =
      visibleTetwittertRange.toIndetwitter + TweetTetwittert.codePointLength(ELLIPSIS))

    val updatedTweet =
      Lens.setAll(
        tweet,
        TweetLenses.tetwittert -> modifiedTetwittert,
        TweetLenses.urls -> modifiedUrlEntities.sortBy(_.fromIndetwitter),
        TweetLenses.media -> modifiedMediaEntities.sortBy(_.fromIndetwitter),
        TweetLenses.visibleTetwittertRange -> Some(modifiedVisibleTetwittertRange)
      )

    updatedTweet
  }

  def apply(): TweetDataValueHydrator = {
    ValueHydrator[TweetData, TweetQuery.Options] { (td, _) =>
      val updatedTweet = addTetwittertSuffitwitter(td.tweet)
      Stitch.value(ValueState.delta(td, td.copy(tweet = updatedTweet)))
    }.onlyIf { (td, _) =>
      td.tweet.noteTweet.isDefined &&
      td.tweet.noteTweet.flatMap(_.isEtwitterpandable).getOrElse(true)
    }
  }
}
