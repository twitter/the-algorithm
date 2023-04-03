packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.sidelon_elonffelonct

import com.twittelonr.elonvelonntbus.clielonnt.elonvelonntBusPublishelonr
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.FollowingProduct
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.ForYouProduct
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasSelonelonnTwelonelontIds
import com.twittelonr.homelon_mixelonr.selonrvicelon.HomelonMixelonrAlelonrtConfig
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.PipelonlinelonRelonsultSidelonelonffelonct
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.SidelonelonffelonctIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.imprelonssionstorelon.thriftscala.Imprelonssion
import com.twittelonr.timelonlinelons.imprelonssionstorelon.thriftscala.ImprelonssionList
import com.twittelonr.timelonlinelons.imprelonssionstorelon.thriftscala.PublishelondImprelonssionList
import com.twittelonr.timelonlinelons.imprelonssionstorelon.thriftscala.SurfacelonArelona
import com.twittelonr.util.Timelon
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

objelonct PublishClielonntSelonntImprelonssionselonvelonntBusSidelonelonffelonct {
  val HomelonSurfacelonArelona: Option[Selont[SurfacelonArelona]] = Somelon(Selont(SurfacelonArelona.HomelonTimelonlinelon))
  val HomelonLatelonstSurfacelonArelona: Option[Selont[SurfacelonArelona]] = Somelon(Selont(SurfacelonArelona.HomelonLatelonstTimelonlinelon))
}

/**
 * Sidelon elonffelonct that publishelons selonelonn twelonelont IDs selonnt from clielonnts. Thelon selonelonn twelonelont IDs arelon selonnt to a
 * helonron topology which writelons to a melonmcachelon dataselont.
 */
@Singlelonton
class PublishClielonntSelonntImprelonssionselonvelonntBusSidelonelonffelonct @Injelonct() (
  elonvelonntBusPublishelonr: elonvelonntBusPublishelonr[PublishelondImprelonssionList])
    elonxtelonnds PipelonlinelonRelonsultSidelonelonffelonct[PipelonlinelonQuelonry with HasSelonelonnTwelonelontIds, HasMarshalling]
    with PipelonlinelonRelonsultSidelonelonffelonct.Conditionally[
      PipelonlinelonQuelonry with HasSelonelonnTwelonelontIds,
      HasMarshalling
    ] {
  import PublishClielonntSelonntImprelonssionselonvelonntBusSidelonelonffelonct._

  ovelonrridelon val idelonntifielonr: SidelonelonffelonctIdelonntifielonr =
    SidelonelonffelonctIdelonntifielonr("PublishClielonntSelonntImprelonssionselonvelonntBus")

  ovelonrridelon delonf onlyIf(
    quelonry: PipelonlinelonQuelonry with HasSelonelonnTwelonelontIds,
    selonlelonctelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    droppelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsponselon: HasMarshalling
  ): Boolelonan = quelonry.selonelonnTwelonelontIds.elonxists(_.nonelonmpty)

  delonf buildelonvelonnts(
    quelonry: PipelonlinelonQuelonry with HasSelonelonnTwelonelontIds,
    currelonntTimelon: Long
  ): Option[Selonq[Imprelonssion]] = {
    val surfacelonArelona = quelonry.product match {
      caselon ForYouProduct => HomelonSurfacelonArelona
      caselon FollowingProduct => HomelonLatelonstSurfacelonArelona
      caselon _ => Nonelon
    }
    quelonry.selonelonnTwelonelontIds.map { selonelonnTwelonelontIds =>
      selonelonnTwelonelontIds.map { twelonelontId =>
        Imprelonssion(
          twelonelontId = twelonelontId,
          imprelonssionTimelon = Somelon(currelonntTimelon),
          surfacelonArelonas = surfacelonArelona
        )
      }
    }
  }

  final ovelonrridelon delonf apply(
    inputs: PipelonlinelonRelonsultSidelonelonffelonct.Inputs[PipelonlinelonQuelonry with HasSelonelonnTwelonelontIds, HasMarshalling]
  ): Stitch[Unit] = {
    val currelonntTimelon = Timelon.now.inMilliselonconds
    val imprelonssions = buildelonvelonnts(inputs.quelonry, currelonntTimelon)

    Stitch.callFuturelon(
      elonvelonntBusPublishelonr.publish(
        PublishelondImprelonssionList(
          inputs.quelonry.gelontRelonquirelondUselonrId,
          ImprelonssionList(imprelonssions),
          currelonntTimelon
        )
      )
    )
  }

  ovelonrridelon val alelonrts = Selonq(
    HomelonMixelonrAlelonrtConfig.BusinelonssHours.delonfaultSuccelonssRatelonAlelonrt(99.4)
  )
}
