package com.X.frigate.pushservice.model.ntab

import com.X.frigate.common.base.TopTweetImpressionsCandidate
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.notificationservice.thriftscala.DisplayText
import com.X.notificationservice.thriftscala.DisplayTextEntity
import com.X.notificationservice.thriftscala.InlineCard
import com.X.notificationservice.thriftscala.StoryContext
import com.X.notificationservice.thriftscala.StoryContextValue
import com.X.notificationservice.thriftscala.TextValue
import com.X.util.Future

trait TopTweetImpressionsNTabRequestHydrator extends NTabRequestHydrator {
  self: PushCandidate with TopTweetImpressionsCandidate =>

  override lazy val tapThroughFut: Future[String] =
    Future.value(s"${target.targetId}/status/$tweetId")

  override val senderIdFut: Future[Long] = Future.value(0L)

  override val facepileUsersFut: Future[Seq[Long]] = Future.Nil

  override val storyContext: Option[StoryContext] =
    Some(StoryContext(altText = "", value = Some(StoryContextValue.Tweets(Seq(tweetId)))))

  override val inlineCard: Option[InlineCard] = None

  override lazy val displayTextEntitiesFut: Future[Seq[DisplayTextEntity]] = {
    Future.value(
      Seq(
        DisplayTextEntity(name = "num_impressions", value = TextValue.Number(self.impressionsCount))
      )
    )
  }

  override def socialProofDisplayText: Option[DisplayText] = None
}
