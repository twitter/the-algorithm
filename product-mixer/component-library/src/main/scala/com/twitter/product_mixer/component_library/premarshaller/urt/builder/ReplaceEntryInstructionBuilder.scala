packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.RelonplacelonelonntryTimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorOpelonration
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

/**
 * Selonleloncts onelon or morelon [[Timelonlinelonelonntry]] instancelon from thelon input timelonlinelon elonntrielons.
 *
 * @tparam Quelonry Thelon domain modelonl for thelon [[PipelonlinelonQuelonry]] uselond as input.
 */
trait elonntrielonsToRelonplacelon[-Quelonry <: PipelonlinelonQuelonry] {
  delonf apply(quelonry: Quelonry, elonntrielons: Selonq[Timelonlinelonelonntry]): Selonq[Timelonlinelonelonntry]
}

/**
 * Selonleloncts all elonntrielons with a non-elonmpty valid elonntryIdToRelonplacelon.
 *
 * @notelon this will relonsult in multiplelon [[RelonplacelonelonntryTimelonlinelonInstruction]]s
 */
caselon objelonct RelonplacelonAllelonntrielons elonxtelonnds elonntrielonsToRelonplacelon[PipelonlinelonQuelonry] {
  delonf apply(quelonry: PipelonlinelonQuelonry, elonntrielons: Selonq[Timelonlinelonelonntry]): Selonq[Timelonlinelonelonntry] =
    elonntrielons.filtelonr(_.elonntryIdToRelonplacelon.isDelonfinelond)
}

/**
 * Selonleloncts a relonplacelonablelon URT [[CursorOpelonration]] from thelon timelonlinelon elonntrielons, that matchelons thelon
 * input cursorTypelon.
 */
caselon class RelonplacelonUrtCursor(cursorTypelon: CursorTypelon) elonxtelonnds elonntrielonsToRelonplacelon[PipelonlinelonQuelonry] {
  ovelonrridelon delonf apply(quelonry: PipelonlinelonQuelonry, elonntrielons: Selonq[Timelonlinelonelonntry]): Selonq[Timelonlinelonelonntry] =
    elonntrielons.collelonctFirst {
      caselon cursorOpelonration: CursorOpelonration
          if cursorOpelonration.cursorTypelon == cursorTypelon && cursorOpelonration.elonntryIdToRelonplacelon.isDelonfinelond =>
        cursorOpelonration
    }.toSelonq
}

/**
 * Crelonatelon a Relonplacelonelonntry instruction
 *
 * @param elonntrielonsToRelonplacelon   elonach relonplacelon instruction can contain only onelon elonntry. Uselonrs speloncify which
 *                           elonntry to relonplacelon using [[elonntrielonsToRelonplacelon]]. If multiplelon elonntrielons arelon
 *                           speloncifielond, multiplelon [[RelonplacelonelonntryTimelonlinelonInstruction]]s will belon crelonatelond.
 * @param includelonInstruction whelonthelonr thelon instruction should belon includelond in thelon relonsponselon
 */
caselon class RelonplacelonelonntryInstructionBuildelonr[Quelonry <: PipelonlinelonQuelonry](
  elonntrielonsToRelonplacelon: elonntrielonsToRelonplacelon[Quelonry],
  ovelonrridelon val includelonInstruction: IncludelonInstruction[Quelonry] = AlwaysIncludelon)
    elonxtelonnds UrtInstructionBuildelonr[Quelonry, RelonplacelonelonntryTimelonlinelonInstruction] {

  ovelonrridelon delonf build(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[RelonplacelonelonntryTimelonlinelonInstruction] = {
    if (includelonInstruction(quelonry, elonntrielons))
      elonntrielonsToRelonplacelon(quelonry, elonntrielons).map(RelonplacelonelonntryTimelonlinelonInstruction)
    elonlselon
      Selonq.elonmpty
  }
}
