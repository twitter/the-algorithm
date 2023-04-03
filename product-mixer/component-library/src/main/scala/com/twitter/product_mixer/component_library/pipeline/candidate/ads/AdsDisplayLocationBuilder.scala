packagelon com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.ads

import com.twittelonr.adselonrvelonr.{thriftscala => ads}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.quelonry.ads.AdsQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

trait AdsDisplayLocationBuildelonr[-Quelonry <: PipelonlinelonQuelonry with AdsQuelonry] {

  delonf apply(quelonry: Quelonry): ads.DisplayLocation
}

caselon class StaticAdsDisplayLocationBuildelonr(displayLocation: ads.DisplayLocation)
    elonxtelonnds AdsDisplayLocationBuildelonr[PipelonlinelonQuelonry with AdsQuelonry] {

  delonf apply(quelonry: PipelonlinelonQuelonry with AdsQuelonry): ads.DisplayLocation = displayLocation
}
