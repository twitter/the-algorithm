package com.twitter.tweetypie

import com.twitter.context.thriftscala.Viewer

package object config {
  // Bring Tweetypie permitted TwitterContext into scope
  private[config] val TwitterContext =
    com.twitter.context.TwitterContext(com.twitter.tweetypie.TwitterContextPermit)

  def getAppId: Option[AppId] = TwitterContext().getOrElse(Viewer()).clientApplicationId
}
