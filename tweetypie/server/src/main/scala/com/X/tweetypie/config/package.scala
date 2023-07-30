package com.X.tweetypie

import com.X.context.thriftscala.Viewer

package object config {
  // Bring Tweetypie permitted XContext into scope
  private[config] val XContext =
    com.X.context.XContext(com.X.tweetypie.XContextPermit)

  def getAppId: Option[AppId] = XContext().getOrElse(Viewer()).clientApplicationId
}
