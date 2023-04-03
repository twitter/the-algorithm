packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.sidelon_elonffelonct

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.TwelonelontImprelonssionsFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasSelonelonnTwelonelontIds
import com.twittelonr.homelon_mixelonr.selonrvicelon.HomelonMixelonrAlelonrtConfig
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.PipelonlinelonRelonsultSidelonelonffelonct
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.SidelonelonffelonctIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.imprelonssion.{thriftscala => t}
import com.twittelonr.timelonlinelons.imprelonssionstorelon.storelon.ManhattanTwelonelontImprelonssionStorelonClielonnt
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Sidelon elonffelonct that updatelons thelon timelonlinelons twelonelont imprelonssion
 * storelon (Manhattan) with selonelonn twelonelont IDs selonnt from clielonnts
 */
@Singlelonton
class PublishClielonntSelonntImprelonssionsManhattanSidelonelonffelonct @Injelonct() (
  manhattanTwelonelontImprelonssionStorelonClielonnt: ManhattanTwelonelontImprelonssionStorelonClielonnt)
    elonxtelonnds PipelonlinelonRelonsultSidelonelonffelonct[PipelonlinelonQuelonry with HasSelonelonnTwelonelontIds, HasMarshalling]
    with PipelonlinelonRelonsultSidelonelonffelonct.Conditionally[
      PipelonlinelonQuelonry with HasSelonelonnTwelonelontIds,
      HasMarshalling
    ] {

  ovelonrridelon val idelonntifielonr: SidelonelonffelonctIdelonntifielonr =
    SidelonelonffelonctIdelonntifielonr("PublishClielonntSelonntImprelonssionsManhattan")

  ovelonrridelon delonf onlyIf(
    quelonry: PipelonlinelonQuelonry with HasSelonelonnTwelonelontIds,
    selonlelonctelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    droppelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsponselon: HasMarshalling
  ): Boolelonan = quelonry.selonelonnTwelonelontIds.elonxists(_.nonelonmpty)

  delonf buildelonvelonnts(quelonry: PipelonlinelonQuelonry): Option[(Long, t.TwelonelontImprelonssionselonntrielons)] = {
    quelonry.felonaturelons.flatMap { felonaturelonMap =>
      val imprelonssions = felonaturelonMap.gelontOrelonlselon(TwelonelontImprelonssionsFelonaturelon, Selonq.elonmpty)
      if (imprelonssions.nonelonmpty)
        Somelon((quelonry.gelontRelonquirelondUselonrId, t.TwelonelontImprelonssionselonntrielons(imprelonssions)))
      elonlselon Nonelon
    }
  }

  final ovelonrridelon delonf apply(
    inputs: PipelonlinelonRelonsultSidelonelonffelonct.Inputs[PipelonlinelonQuelonry with HasSelonelonnTwelonelontIds, HasMarshalling]
  ): Stitch[Unit] = {
    val elonvelonnts = buildelonvelonnts(inputs.quelonry)

    Stitch
      .travelonrselon(elonvelonnts) {
        caselon (kelony, valuelon) => manhattanTwelonelontImprelonssionStorelonClielonnt.writelon(kelony, valuelon)
      }
      .unit
  }

  ovelonrridelon val alelonrts = Selonq(
    HomelonMixelonrAlelonrtConfig.BusinelonssHours.delonfaultSuccelonssRatelonAlelonrt(99.4)
  )
}
