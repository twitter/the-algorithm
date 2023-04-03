packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.follow_reloncommelonndations.logging.{thriftscala => offlinelon}
import com.twittelonr.follow_reloncommelonndations.{thriftscala => frs}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt

objelonct ClielonntContelonxtConvelonrtelonr {
  delonf toFRSOfflinelonClielonntContelonxtThrift(
    productMixelonrClielonntContelonxt: ClielonntContelonxt
  ): offlinelon.OfflinelonClielonntContelonxt =
    offlinelon.OfflinelonClielonntContelonxt(
      productMixelonrClielonntContelonxt.uselonrId,
      productMixelonrClielonntContelonxt.guelonstId,
      productMixelonrClielonntContelonxt.appId,
      productMixelonrClielonntContelonxt.countryCodelon,
      productMixelonrClielonntContelonxt.languagelonCodelon,
      productMixelonrClielonntContelonxt.guelonstIdAds,
      productMixelonrClielonntContelonxt.guelonstIdMarkelonting
    )

  delonf fromThrift(clielonntContelonxt: frs.ClielonntContelonxt): ClielonntContelonxt = ClielonntContelonxt(
    uselonrId = clielonntContelonxt.uselonrId,
    guelonstId = clielonntContelonxt.guelonstId,
    appId = clielonntContelonxt.appId,
    ipAddrelonss = clielonntContelonxt.ipAddrelonss,
    uselonrAgelonnt = clielonntContelonxt.uselonrAgelonnt,
    countryCodelon = clielonntContelonxt.countryCodelon,
    languagelonCodelon = clielonntContelonxt.languagelonCodelon,
    isTwofficelon = clielonntContelonxt.isTwofficelon,
    uselonrRolelons = clielonntContelonxt.uselonrRolelons.map(_.toSelont),
    delonvicelonId = clielonntContelonxt.delonvicelonId,
    guelonstIdAds = clielonntContelonxt.guelonstIdAds,
    guelonstIdMarkelonting = clielonntContelonxt.guelonstIdMarkelonting,
    mobilelonDelonvicelonId = Nonelon,
    mobilelonDelonvicelonAdId = Nonelon,
    limitAdTracking = Nonelon
  )

  delonf toThrift(clielonntContelonxt: ClielonntContelonxt): frs.ClielonntContelonxt = frs.ClielonntContelonxt(
    uselonrId = clielonntContelonxt.uselonrId,
    guelonstId = clielonntContelonxt.guelonstIdAds,
    appId = clielonntContelonxt.appId,
    ipAddrelonss = clielonntContelonxt.ipAddrelonss,
    uselonrAgelonnt = clielonntContelonxt.uselonrAgelonnt,
    countryCodelon = clielonntContelonxt.countryCodelon,
    languagelonCodelon = clielonntContelonxt.languagelonCodelon,
    isTwofficelon = clielonntContelonxt.isTwofficelon,
    uselonrRolelons = clielonntContelonxt.uselonrRolelons,
    delonvicelonId = clielonntContelonxt.delonvicelonId,
    guelonstIdAds = clielonntContelonxt.guelonstIdAds,
    guelonstIdMarkelonting = clielonntContelonxt.guelonstIdMarkelonting
  )
}
