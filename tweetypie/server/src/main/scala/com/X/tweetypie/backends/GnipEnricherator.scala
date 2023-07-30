package com.X.tweetypie
package backends

import com.X.conversions.PercentOps._
import com.X.conversions.DurationOps._
import com.X.dataproducts.enrichments.thriftscala._
import com.X.dataproducts.enrichments.thriftscala.Enricherator
import com.X.finagle.thriftmux.MethodBuilder
import com.X.servo.util.FutureArrow

object GnipEnricherator {

  type HydrateProfileGeo = FutureArrow[ProfileGeoRequest, Seq[ProfileGeoResponse]]

  private def methodPerEndpoint(methodBuilder: MethodBuilder) =
    Enricherator.MethodPerEndpoint(
      methodBuilder
        .servicePerEndpoint[Enricherator.ServicePerEndpoint]
        .withHydrateProfileGeo(
          methodBuilder
            .withTimeoutTotal(300.milliseconds)
            .withTimeoutPerRequest(100.milliseconds)
            .idempotent(maxExtraLoad = 1.percent)
            .servicePerEndpoint[Enricherator.ServicePerEndpoint](methodName = "hydrateProfileGeo")
            .hydrateProfileGeo
        )
    )

  def fromMethod(methodBuilder: MethodBuilder): GnipEnricherator = {
    val mpe = methodPerEndpoint(methodBuilder)

    new GnipEnricherator {
      override val hydrateProfileGeo: HydrateProfileGeo =
        FutureArrow(mpe.hydrateProfileGeo)
    }
  }
}

trait GnipEnricherator {
  import GnipEnricherator._
  val hydrateProfileGeo: HydrateProfileGeo
}
