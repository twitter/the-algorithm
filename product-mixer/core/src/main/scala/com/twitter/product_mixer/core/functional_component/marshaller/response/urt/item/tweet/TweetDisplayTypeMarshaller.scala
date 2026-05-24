package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.tweet

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet._
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetDisplayTypeMarshaller @Inject() () {

  def apply(tweetDisplayType: TweetDisplayType): urt.TweetDisplayType = tweetDisplayType match {
    case Tweet => urt.TweetDisplayType.Tweet
    case TweetFollowOnly => urt.TweetDisplayType.TweetFollowOnly
    case Media => urt.TweetDisplayType.Media
    case MomentTimelineTweet => urt.TweetDisplayType.MomentTimelineTweet
    case EmphasizedPromotedTweet => urt.TweetDisplayType.EmphasizedPromotedTweet
    case QuotedTweet => urt.TweetDisplayType.QuotedTweet
    case SelfThread => urt.TweetDisplayType.SelfThread
    case CompactPromotedTweet => urt.TweetDisplayType.CompactPromotedTweet
    case TweetWithoutCard => urt.TweetDisplayType.TweetWithoutCard
    case ReaderModeRoot => urt.TweetDisplayType.ReaderModeRoot
    case ReaderMode => urt.TweetDisplayType.ReaderMode
    case CondensedTweet => urt.TweetDisplayType.CondensedTweet
  }
}
