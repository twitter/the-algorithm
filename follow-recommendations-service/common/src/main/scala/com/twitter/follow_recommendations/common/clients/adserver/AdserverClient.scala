package com.twitter.follow_recommendations.common.clients.adserver

import com.twitter.adserver.thriftscala.NewAdServer
import com.twitter.adserver.{thriftscala => t}
import com.twitter.stitch.Stitch
import javax.inject.{Inject, Singleton}

@Singleton
class AdserverClient @Inject() (adserverService: NewAdServer.MethodPerEndpoint) {
  def getAdImpressions(adRequest: AdRequest): Stitch[Seq[t.AdImpression]] = {
    Stitch
      .callFuture(
        adserverService.makeAdRequest(adRequest.toThrift)
      ).map(_.impressions)
  }
}
