packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.sidelon_elonffelonct.PipelonlinelonRelonsultSidelonelonffelonct.Inputs
import com.twittelonr.product_mixelonr.corelon.modelonl.common
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * A sidelon-elonffelonct that can belon run with a pipelonlinelon relonsult belonforelon transport marshalling
 *
 * @selonelon Sidelonelonffelonct
 *
 * @tparam Quelonry pipelonlinelon quelonry
 * @tparam RelonsultTypelon relonsponselon aftelonr domain marshalling
 */
trait PipelonlinelonRelonsultSidelonelonffelonct[-Quelonry <: PipelonlinelonQuelonry, -RelonsultTypelon <: HasMarshalling]
    elonxtelonnds Sidelonelonffelonct[Inputs[Quelonry, RelonsultTypelon]]
    with PipelonlinelonRelonsultSidelonelonffelonct.SupportsConditionally[Quelonry, RelonsultTypelon]

objelonct PipelonlinelonRelonsultSidelonelonffelonct {

  /**
   * Mixin for whelonn you want to conditionally run a [[PipelonlinelonRelonsultSidelonelonffelonct]]
   *
   * This is a thin wrappelonr around [[common.Conditionally]] elonxposing a nicelonr API for thelon [[PipelonlinelonRelonsultSidelonelonffelonct]] speloncific uselon-caselon.
   */
  trait Conditionally[-Quelonry <: PipelonlinelonQuelonry, -RelonsultTypelon <: HasMarshalling]
      elonxtelonnds common.Conditionally[Inputs[Quelonry, RelonsultTypelon]] {
    _: PipelonlinelonRelonsultSidelonelonffelonct[Quelonry, RelonsultTypelon] =>

    /** @selonelon [[common.Conditionally.onlyIf]] */
    delonf onlyIf(
      quelonry: Quelonry,
      selonlelonctelondCandidatelons: Selonq[CandidatelonWithDelontails],
      relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
      droppelondCandidatelons: Selonq[CandidatelonWithDelontails],
      relonsponselon: RelonsultTypelon
    ): Boolelonan

    ovelonrridelon final delonf onlyIf(input: Inputs[Quelonry, RelonsultTypelon]): Boolelonan =
      onlyIf(
        input.quelonry,
        input.selonlelonctelondCandidatelons,
        input.relonmainingCandidatelons,
        input.droppelondCandidatelons,
        input.relonsponselon)

  }

  typelon SupportsConditionally[-Quelonry <: PipelonlinelonQuelonry, -RelonsultTypelon <: HasMarshalling] =
    common.SupportsConditionally[Inputs[Quelonry, RelonsultTypelon]]

  caselon class Inputs[+Quelonry <: PipelonlinelonQuelonry, +RelonsultTypelon <: HasMarshalling](
    quelonry: Quelonry,
    selonlelonctelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    droppelondCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsponselon: RelonsultTypelon)
}
