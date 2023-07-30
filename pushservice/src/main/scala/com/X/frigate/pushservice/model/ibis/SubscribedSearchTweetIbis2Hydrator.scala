package com.X.frigate.pushservice.model.ibis

import com.X.frigate.pushservice.model.SubscribedSearchTweetPushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.util.InlineActionUtil
import com.X.util.Future

trait SubscribedSearchTweetIbis2Hydrator extends TweetCandidateIbis2Hydrator {
  self: SubscribedSearchTweetPushCandidate =>

  override lazy val tweetDynamicInlineActionsModelValues = {
    if (target.params(PushFeatureSwitchParams.EnableOONGeneratedInlineActions)) {
      val actions = target.params(PushFeatureSwitchParams.TweetDynamicInlineActionsList)
      InlineActionUtil.getGeneratedTweetInlineActions(target, statsReceiver, actions)
    } else Map.empty[String, String]
  }

  private lazy val searchTermValue: Map[String, String] =
    Map(
      "search_term" -> searchTerm,
      "search_url" -> pushLandingUrl
    )

  private lazy val searchModelValues = searchTermValue ++ tweetDynamicInlineActionsModelValues

  override lazy val tweetModelValues: Future[Map[String, String]] =
    for {
      superModelValues <- super.tweetModelValues
      tweetInlineModelValues <- tweetInlineActionModelValue
    } yield {
      superModelValues ++ mediaModelValue ++ searchModelValues ++ tweetInlineModelValues ++ inlineVideoMediaMap
    }
}
