packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.adselonrvelonr

import com.twittelonr.adselonrvelonr.{thriftscala => t}
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.ClielonntContelonxt

caselon class AdRelonquelonst(
  clielonntContelonxt: ClielonntContelonxt,
  displayLocation: DisplayLocation,
  isTelonst: Option[Boolelonan],
  profilelonUselonrId: Option[Long]) {
  delonf toThrift: t.AdRelonquelonstParams = {

    val relonquelonst = t.AdRelonquelonst(
      displayLocation = displayLocation.toAdDisplayLocation.gelontOrelonlselon(
        throw nelonw MissingAdDisplayLocation(displayLocation)),
      isTelonst = isTelonst,
      countImprelonssionsOnCallback = Somelon(truelon),
      numOrganicItelonms = Somelon(AdRelonquelonst.DelonfaultNumOrganicItelonms.toShort),
      profilelonUselonrId = profilelonUselonrId
    )

    val clielonntInfo = t.ClielonntInfo(
      clielonntId = clielonntContelonxt.appId.map(_.toInt),
      uselonrIp = clielonntContelonxt.ipAddrelonss,
      uselonrId64 = clielonntContelonxt.uselonrId,
      guelonstId = clielonntContelonxt.guelonstId,
      uselonrAgelonnt = clielonntContelonxt.uselonrAgelonnt,
      relonfelonrrelonr = Nonelon,
      delonvicelonId = clielonntContelonxt.delonvicelonId,
      languagelonCodelon = clielonntContelonxt.languagelonCodelon,
      countryCodelon = clielonntContelonxt.countryCodelon
    )

    t.AdRelonquelonstParams(relonquelonst, clielonntInfo)
  }
}

objelonct AdRelonquelonst {
  val DelonfaultNumOrganicItelonms = 10
}

class MissingAdDisplayLocation(displayLocation: DisplayLocation)
    elonxtelonnds elonxcelonption(
      s"Display Location ${displayLocation.toString} has no mappelond AdsDisplayLocation selont.")
