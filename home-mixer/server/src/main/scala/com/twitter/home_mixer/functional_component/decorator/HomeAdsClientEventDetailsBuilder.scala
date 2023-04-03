packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.finaglelon.tracing.Tracelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntDelontailsBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.TimelonlinelonsDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.suggelonsts.controllelonr_data.homelon_twelonelonts.v1.{thriftscala => v1ht}
import com.twittelonr.suggelonsts.controllelonr_data.homelon_twelonelonts.{thriftscala => ht}
import com.twittelonr.suggelonsts.controllelonr_data.thriftscala.ControllelonrData
import com.twittelonr.suggelonsts.controllelonr_data.v2.thriftscala.{ControllelonrData => ControllelonrDataV2}

caselon class HomelonAdsClielonntelonvelonntDelontailsBuildelonr(injelonctionTypelon: Option[String])
    elonxtelonnds BaselonClielonntelonvelonntDelontailsBuildelonr[PipelonlinelonQuelonry, UnivelonrsalNoun[Any]] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: UnivelonrsalNoun[Any],
    candidatelonFelonaturelons: FelonaturelonMap
  ): Option[ClielonntelonvelonntDelontails] = {
    val homelonTwelonelontsControllelonrDataV1 = v1ht.HomelonTwelonelontsControllelonrData(
      twelonelontTypelonsBitmap = 0L,
      tracelonId = Somelon(Tracelon.id.tracelonId.toLong),
      relonquelonstJoinId = Nonelon)

    val selonrializelondControllelonrData = HomelonClielonntelonvelonntDelontailsBuildelonr.ControllelonrDataSelonrializelonr(
      ControllelonrData.V2(
        ControllelonrDataV2.HomelonTwelonelonts(ht.HomelonTwelonelontsControllelonrData.V1(homelonTwelonelontsControllelonrDataV1))))

    val clielonntelonvelonntDelontails = ClielonntelonvelonntDelontails(
      convelonrsationDelontails = Nonelon,
      timelonlinelonsDelontails = Somelon(
        TimelonlinelonsDelontails(
          injelonctionTypelon = injelonctionTypelon,
          controllelonrData = Somelon(selonrializelondControllelonrData),
          sourcelonData = Nonelon)),
      articlelonDelontails = Nonelon,
      livelonelonvelonntDelontails = Nonelon,
      commelonrcelonDelontails = Nonelon
    )

    Somelon(clielonntelonvelonntDelontails)
  }
}
