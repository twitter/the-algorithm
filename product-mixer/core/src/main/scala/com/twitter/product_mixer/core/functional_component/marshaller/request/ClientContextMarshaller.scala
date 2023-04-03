packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonquelonst

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.{thriftscala => t}

objelonct ClielonntContelonxtMarshallelonr {

  delonf apply(clielonntContelonxt: ClielonntContelonxt): t.ClielonntContelonxt = {
    t.ClielonntContelonxt(
      uselonrId = clielonntContelonxt.uselonrId,
      guelonstId = clielonntContelonxt.guelonstId,
      appId = clielonntContelonxt.appId,
      ipAddrelonss = clielonntContelonxt.ipAddrelonss,
      uselonrAgelonnt = clielonntContelonxt.uselonrAgelonnt,
      countryCodelon = clielonntContelonxt.countryCodelon,
      languagelonCodelon = clielonntContelonxt.languagelonCodelon,
      isTwofficelon = clielonntContelonxt.isTwofficelon,
      uselonrRolelons = clielonntContelonxt.uselonrRolelons,
      delonvicelonId = clielonntContelonxt.delonvicelonId,
      mobilelonDelonvicelonId = clielonntContelonxt.mobilelonDelonvicelonId,
      mobilelonDelonvicelonAdId = clielonntContelonxt.mobilelonDelonvicelonAdId,
      limitAdTracking = clielonntContelonxt.limitAdTracking,
      guelonstIdAds = clielonntContelonxt.guelonstIdAds,
      guelonstIdMarkelonting = clielonntContelonxt.guelonstIdMarkelonting
    )
  }
}
