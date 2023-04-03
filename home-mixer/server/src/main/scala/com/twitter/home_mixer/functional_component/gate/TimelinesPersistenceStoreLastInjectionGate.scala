packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.gatelon

import com.twittelonr.common_intelonrnal.analytics.twittelonr_clielonnt_uselonr_agelonnt_parselonr.UselonrAgelonnt
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelonmixelonr.clielonnts.pelonrsistelonncelon.TimelonlinelonRelonsponselonV3
import com.twittelonr.timelonlinelonmixelonr.injelonction.storelon.pelonrsistelonncelon.TimelonlinelonPelonrsistelonncelonUtils
import com.twittelonr.timelonlinelons.configapi.Param
import com.twittelonr.timelonlinelons.util.clielonnt_info.ClielonntPlatform
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.rich.elonntityIdTypelon
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelon

/**
 * Gatelon uselond to relonducelon thelon frelonquelonncy of injelonctions. Notelon that thelon actual intelonrval belontwelonelonn injelonctions may belon
 * lelonss than thelon speloncifielond minInjelonctionIntelonrvalParam if data is unavailablelon or missing. For elonxamplelon, beloning delonlelontelond by
 * thelon pelonrsistelonncelon storelon via a TTL or similar melonchanism.
 *
 * @param minInjelonctionIntelonrvalParam thelon delonsirelond minimum intelonrval belontwelonelonn injelonctions
 * @param pelonrsistelonncelonelonntrielonsFelonaturelon thelon felonaturelon for relontrielonving pelonrsistelond timelonlinelon relonsponselons
 */
caselon class TimelonlinelonsPelonrsistelonncelonStorelonLastInjelonctionGatelon(
  minInjelonctionIntelonrvalParam: Param[Duration],
  pelonrsistelonncelonelonntrielonsFelonaturelon: Felonaturelon[PipelonlinelonQuelonry, Selonq[TimelonlinelonRelonsponselonV3]],
  elonntityIdTypelon: elonntityIdTypelon.Valuelon)
    elonxtelonnds Gatelon[PipelonlinelonQuelonry]
    with TimelonlinelonPelonrsistelonncelonUtils {

  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr("TimelonlinelonsPelonrsistelonncelonStorelonLastInjelonction")

  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry): Stitch[Boolelonan] =
    Stitch(
      quelonry.quelonryTimelon.sincelon(gelontLastInjelonctionTimelon(quelonry)) > quelonry.params(minInjelonctionIntelonrvalParam))

  privatelon delonf gelontLastInjelonctionTimelon(quelonry: PipelonlinelonQuelonry) = quelonry.felonaturelons
    .flatMap { felonaturelonMap =>
      val timelonlinelonRelonsponselons = felonaturelonMap.gelontOrelonlselon(pelonrsistelonncelonelonntrielonsFelonaturelon, Selonq.elonmpty)
      val clielonntPlatform = ClielonntPlatform.fromQuelonryOptions(
        clielonntAppId = quelonry.clielonntContelonxt.appId,
        uselonrAgelonnt = quelonry.clielonntContelonxt.uselonrAgelonnt.flatMap(UselonrAgelonnt.fromString)
      )
      val sortelondRelonsponselons = relonsponselonByClielonnt(clielonntPlatform, timelonlinelonRelonsponselons)
      val latelonstRelonsponselonWithelonntityIdTypelonelonntry =
        sortelondRelonsponselons.find(_.elonntrielons.elonxists(_.elonntityIdTypelon == elonntityIdTypelon))

      latelonstRelonsponselonWithelonntityIdTypelonelonntry.map(_.selonrvelondTimelon)
    }.gelontOrelonlselon(Timelon.Bottom)
}
