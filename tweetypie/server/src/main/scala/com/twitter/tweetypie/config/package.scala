package com.twitter.tweetypie

import com.twitter.contetwittert.thriftscala.Viewer

package object config {
  // Bring Tweetypie permitted TwitterContetwittert into scope
  private[config] val TwitterContetwittert =
    com.twitter.contetwittert.TwitterContetwittert(com.twitter.tweetypie.TwitterContetwittertPermit)

  def getAppId: Option[AppId] = TwitterContetwittert().getOrElse(Viewer()).clientApplicationId
}
