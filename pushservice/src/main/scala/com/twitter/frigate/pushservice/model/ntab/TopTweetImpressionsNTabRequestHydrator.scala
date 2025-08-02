package com.twitter.frigate.pushservice.model.ntab

import com.twitter.frigate.common.base.TopTweetImpressionsCandidate
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.notificationservice.thriftscala.DisplayTetwittert
import com.twitter.notificationservice.thriftscala.DisplayTetwittertEntity
import com.twitter.notificationservice.thriftscala.InlineCard
import com.twitter.notificationservice.thriftscala.StoryContetwittert
import com.twitter.notificationservice.thriftscala.StoryContetwittertValue
import com.twitter.notificationservice.thriftscala.TetwittertValue
import com.twitter.util.Future

trait TopTweetImpressionsNTabRequestHydrator etwittertends NTabRequestHydrator {
  self: PushCandidate with TopTweetImpressionsCandidate =>

  override lazy val tapThroughFut: Future[String] =
    Future.value(s"${target.targetId}/status/$tweetId")

  override val senderIdFut: Future[Long] = Future.value(0L)

  override val facepileUsersFut: Future[Seq[Long]] = Future.Nil

  override val storyContetwittert: Option[StoryContetwittert] =
    Some(StoryContetwittert(altTetwittert = "", value = Some(StoryContetwittertValue.Tweets(Seq(tweetId)))))

  override val inlineCard: Option[InlineCard] = None

  override lazy val displayTetwittertEntitiesFut: Future[Seq[DisplayTetwittertEntity]] = {
    Future.value(
      Seq(
        DisplayTetwittertEntity(name = "num_impressions", value = TetwittertValue.Number(self.impressionsCount))
      )
    )
  }

  override def socialProofDisplayTetwittert: Option[DisplayTetwittert] = None
}
