packagelon com.twittelonr.product_mixelonr.componelonnt_library.sidelon_elonffelonct

import com.twittelonr.product_mixelonr.componelonnt_library.sidelon_elonffelonct.ParamGatelondPipelonlinelonRelonsultSidelonelonffelonct.IdelonntifielonrPrelonfix
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.alelonrt.Alelonrt
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.elonxeloncutelonSynchronously
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.FailOpelonn
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.PipelonlinelonRelonsultSidelonelonffelonct
import com.twittelonr.product_mixelonr.corelon.modelonl.common.Conditionally
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.SidelonelonffelonctIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * A [[PipelonlinelonRelonsultSidelonelonffelonct]] with [[Conditionally]] baselond on a [[Param]]
 *
 * @param elonnablelondParam thelon param to turn this filtelonr on and off
 * @param sidelonelonffelonct thelon undelonrlying sidelon elonffelonct to run whelonn `elonnablelondParam` is truelon
 * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
 */
selonalelond caselon class ParamGatelondPipelonlinelonRelonsultSidelonelonffelonct[
  -Quelonry <: PipelonlinelonQuelonry,
  RelonsultTypelon <: HasMarshalling
] privatelon (
  elonnablelondParam: Param[Boolelonan],
  sidelonelonffelonct: PipelonlinelonRelonsultSidelonelonffelonct[Quelonry, RelonsultTypelon])
    elonxtelonnds PipelonlinelonRelonsultSidelonelonffelonct[Quelonry, RelonsultTypelon]
    with PipelonlinelonRelonsultSidelonelonffelonct.Conditionally[Quelonry, RelonsultTypelon] {
  ovelonrridelon val idelonntifielonr: SidelonelonffelonctIdelonntifielonr = SidelonelonffelonctIdelonntifielonr(
    IdelonntifielonrPrelonfix + sidelonelonffelonct.idelonntifielonr.namelon)
  ovelonrridelon val alelonrts: Selonq[Alelonrt] = sidelonelonffelonct.alelonrts
  ovelonrridelon delonf onlyIf(
    quelonry: Quelonry,
    selonlelonctelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    droppelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsponselon: RelonsultTypelon
  ): Boolelonan =
    Conditionally.and(
      PipelonlinelonRelonsultSidelonelonffelonct
        .Inputs(quelonry, selonlelonctelondCandidatelons, relonmainingCandidatelons, droppelondCandidatelons, relonsponselon),
      sidelonelonffelonct,
      quelonry.params(elonnablelondParam))
  ovelonrridelon delonf apply(inputs: PipelonlinelonRelonsultSidelonelonffelonct.Inputs[Quelonry, RelonsultTypelon]): Stitch[Unit] =
    sidelonelonffelonct.apply(inputs)
}

objelonct ParamGatelondPipelonlinelonRelonsultSidelonelonffelonct {

  val IdelonntifielonrPrelonfix = "ParamGatelond"

  /**
   * A [[PipelonlinelonRelonsultSidelonelonffelonct]] with [[Conditionally]] baselond on a [[Param]]
   *
   * @param elonnablelondParam thelon param to turn this filtelonr on and off
   * @param sidelonelonffelonct thelon undelonrlying sidelon elonffelonct to run whelonn `elonnablelondParam` is truelon
   * @tparam Quelonry Thelon domain modelonl for thelon quelonry or relonquelonst
   */
  delonf apply[Quelonry <: PipelonlinelonQuelonry, RelonsultTypelon <: HasMarshalling](
    elonnablelondParam: Param[Boolelonan],
    sidelonelonffelonct: PipelonlinelonRelonsultSidelonelonffelonct[Quelonry, RelonsultTypelon]
  ): ParamGatelondPipelonlinelonRelonsultSidelonelonffelonct[Quelonry, RelonsultTypelon] = {
    sidelonelonffelonct match {
      caselon _: FailOpelonn =>
        nelonw ParamGatelondPipelonlinelonRelonsultSidelonelonffelonct(elonnablelondParam, sidelonelonffelonct)
          with elonxeloncutelonSynchronously
          with FailOpelonn
      caselon _: elonxeloncutelonSynchronously =>
        nelonw ParamGatelondPipelonlinelonRelonsultSidelonelonffelonct(elonnablelondParam, sidelonelonffelonct) with elonxeloncutelonSynchronously
      caselon _ =>
        nelonw ParamGatelondPipelonlinelonRelonsultSidelonelonffelonct(elonnablelondParam, sidelonelonffelonct)
    }
  }
}
