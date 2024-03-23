package com.ExTwitter.follow_recommendations.common.clients.adserver

import com.ExTwitter.adserver.thriftscala.NewAdServer
import com.ExTwitter.adserver.{thriftscala => t}
import com.ExTwitter.stitch.Stitch
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
