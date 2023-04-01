package com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet

sealed trait TweetDisplayType

case object Tweet extends TweetDisplayType
case object TweetFollowOnly extends TweetDisplayType
case object Media extends TweetDisplayType
case object MomentTimelineTweet extends TweetDisplayType
case object EmphasizedPromotedTweet extends TweetDisplayType
case object QuotedTweet extends TweetDisplayType
case object SelfThread extends TweetDisplayType
case object CompactPromotedTweet extends TweetDisplayType
case object TweetWithoutCard extends TweetDisplayType
case object ReaderModeRoot extends TweetDisplayType
case object ReaderMode extends TweetDisplayType
case object CondensedTweet extends TweetDisplayType
