package com.twitter.product_mixer.core.model.marshalling.response.urt.item.tweet_composer

sealed trait TweetComposerDisplayType

case object TweetComposerSelfThread extends TweetComposerDisplayType
case object Reply extends TweetComposerDisplayType
