package com.X.tweetypie
package hydrator

import com.X.stitch.Stitch
import com.X.tweetypie.core.TweetData
import com.X.tweetypie.core.ValueState
import com.X.tweetypie.repository.TweetQuery
import com.X.tweetypie.thriftscala.entities.Implicits._
import com.X.tweetypie.thriftscala.TextRange
import com.X.tweetypie.tweettext.Offset
import com.X.tweetypie.tweettext.TextModification
import com.X.tweetypie.tweettext.TweetText
import com.X.tweetypie.util.TweetLenses

object NoteTweetSuffixHydrator {

  val ELLIPSIS: String = "\u2026"

  private def addTextSuffix(tweet: Tweet): Tweet = {
    val originalText = TweetLenses.text(tweet)
    val originalTextLength = TweetText.codePointLength(originalText)

    val visibleTextRange: TextRange =
      TweetLenses
        .visibleTextRange(tweet)
        .getOrElse(TextRange(0, originalTextLength))

    val insertAtCodePoint = Offset.CodePoint(visibleTextRange.toIndex)

    val textModification = TextModification.insertAt(
      originalText,
      insertAtCodePoint,
      ELLIPSIS
    )

    val mediaEntities = TweetLenses.media(tweet)
    val urlEntities = TweetLenses.urls(tweet)

    val modifiedText = textModification.updated
    val modifiedMediaEntities = textModification.reindexEntities(mediaEntities)
    val modifiedUrlEntities = textModification.reindexEntities(urlEntities)
    val modifiedVisibleTextRange = visibleTextRange.copy(toIndex =
      visibleTextRange.toIndex + TweetText.codePointLength(ELLIPSIS))

    val updatedTweet =
      Lens.setAll(
        tweet,
        TweetLenses.text -> modifiedText,
        TweetLenses.urls -> modifiedUrlEntities.sortBy(_.fromIndex),
        TweetLenses.media -> modifiedMediaEntities.sortBy(_.fromIndex),
        TweetLenses.visibleTextRange -> Some(modifiedVisibleTextRange)
      )

    updatedTweet
  }

  def apply(): TweetDataValueHydrator = {
    ValueHydrator[TweetData, TweetQuery.Options] { (td, _) =>
      val updatedTweet = addTextSuffix(td.tweet)
      Stitch.value(ValueState.delta(td, td.copy(tweet = updatedTweet)))
    }.onlyIf { (td, _) =>
      td.tweet.noteTweet.isDefined &&
      td.tweet.noteTweet.flatMap(_.isExpandable).getOrElse(true)
    }
  }
}
