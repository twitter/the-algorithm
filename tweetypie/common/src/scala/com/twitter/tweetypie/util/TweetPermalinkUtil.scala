package com.twitter.tweetypie.util

import com.twitter.tweetutil.TweetPermalink
import com.twitter.tweetypie.thriftscala._

object TweetPermalinkUtil {
  def lastQuotedTweetPermalink(tweet: Tweet): Option[(UrlEntity, TweetPermalink)] =
    lastQuotedTweetPermalink(TweetLenses.urls.get(tweet))

  def lastQuotedTweetPermalink(urls: Seq[UrlEntity]): Option[(UrlEntity, TweetPermalink)] =
    urls.flatMap(matchQuotedTweetPermalink).lastOption

  def matchQuotedTweetPermalink(entity: UrlEntity): Option[(UrlEntity, TweetPermalink)] =
    for {
      expanded <- entity.expanded
      permalink <- TweetPermalink.parse(expanded)
    } yield (entity, permalink)
}
