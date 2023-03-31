package com.twitter.home_mixer.marshaller.timeline_logging

import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.SocialContextFeature
import com.twitter.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet.TweetItem
import com.twitter.timelines.service.{thriftscala => tst}
import com.twitter.timelines.timeline_logging.{thriftscala => thriftlog}

object TweetEntryMarshaller {

  def apply(entry: TweetItem, candidate: CandidateWithDetails): thriftlog.TweetEntry = {
    val socialContextType = candidate.features.getOrElse(SocialContextFeature, None) match {
      case Some(tst.SocialContext.GeneralContext(tst.GeneralContext(contextType, _, _, _, _))) =>
        Some(contextType.value.toShort)
      case Some(tst.SocialContext.TopicContext(_)) =>
        Some(tst.ContextType.Topic.value.toShort)
      case _ => None
    }
    thriftlog.TweetEntry(
      id = candidate.candidateIdLong,
      sourceTweetId = candidate.features.getOrElse(SourceTweetIdFeature, None),
      displayType = Some(entry.displayType.toString),
      score = candidate.features.getOrElse(ScoreFeature, None),
      socialContextType = socialContextType
    )
  }
}
