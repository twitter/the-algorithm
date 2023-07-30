package com.X.follow_recommendations.common.clients.adserver

import com.X.adserver.thriftscala.NewAdServer
import com.X.adserver.{thriftscala => t}
import com.X.stitch.Stitch
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
