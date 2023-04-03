packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonquelonst

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt
import com.twittelonr.product_mixelonr.corelon.{thriftscala => t}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ClielonntContelonxtUnmarshallelonr @Injelonct() () {

  delonf apply(clielonntContelonxt: t.ClielonntContelonxt): ClielonntContelonxt = {
    ClielonntContelonxt(
      uselonrId = clielonntContelonxt.uselonrId,
      guelonstId = clielonntContelonxt.guelonstId,
      guelonstIdAds = clielonntContelonxt.guelonstIdAds,
      guelonstIdMarkelonting = clielonntContelonxt.guelonstIdMarkelonting,
      appId = clielonntContelonxt.appId,
      ipAddrelonss = clielonntContelonxt.ipAddrelonss,
      uselonrAgelonnt = clielonntContelonxt.uselonrAgelonnt,
      countryCodelon = clielonntContelonxt.countryCodelon,
      languagelonCodelon = clielonntContelonxt.languagelonCodelon,
      isTwofficelon = clielonntContelonxt.isTwofficelon,
      uselonrRolelons = clielonntContelonxt.uselonrRolelons.map(_.toSelont),
      delonvicelonId = clielonntContelonxt.delonvicelonId,
      mobilelonDelonvicelonId = clielonntContelonxt.mobilelonDelonvicelonId,
      mobilelonDelonvicelonAdId = clielonntContelonxt.mobilelonDelonvicelonAdId,
      limitAdTracking = clielonntContelonxt.limitAdTracking
    )
  }
}
