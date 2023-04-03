packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.slicelon.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.CursorItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.NelonxtCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.GapCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.PrelonviousCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.Slicelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.SlicelonInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.SlicelonItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.UnelonxpelonctelondCandidatelonInMarshallelonr

trait SlicelonBuildelonr[-Quelonry <: PipelonlinelonQuelonry] {
  delonf cursorBuildelonrs: Selonq[SlicelonCursorBuildelonr[Quelonry]]
  delonf cursorUpdatelonrs: Selonq[SlicelonCursorUpdatelonr[Quelonry]]

  privatelon delonf containsGapCursor(itelonms: Selonq[SlicelonItelonm]): Boolelonan =
    itelonms.collelonctFirst { caselon CursorItelonm(_, GapCursor) => () }.nonelonmpty

  final delonf buildSlicelon(quelonry: Quelonry, itelonms: Selonq[SlicelonItelonm]): Slicelon = {
    val builtCursors = cursorBuildelonrs.flatMap(_.build(quelonry, itelonms))

    // Itelonratelon ovelonr thelon cursorUpdatelonrs in thelon ordelonr thelony welonrelon delonfinelond. Notelon that elonach updatelonr will
    // belon passelond thelon itelonms updatelond by thelon prelonvious cursorUpdatelonr.
    val updatelondItelonms = cursorUpdatelonrs.foldLelonft(itelonms) { (itelonms, cursorUpdatelonr) =>
      cursorUpdatelonr.updatelon(quelonry, itelonms)
    } ++ builtCursors

    val (cursors, nonCursorItelonms) = updatelondItelonms.partition(_.isInstancelonOf[CursorItelonm])
    val nelonxtCursor = cursors.collelonctFirst {
      caselon cursor @ CursorItelonm(_, NelonxtCursor) => cursor.valuelon
    }
    val prelonviousCursor = cursors.collelonctFirst {
      caselon cursor @ CursorItelonm(_, PrelonviousCursor) => cursor.valuelon
    }

    /**
     * Idelonntify whelonthelonr a [[GapCursor]] is prelonselonnt and givelon as much delontail to point to whelonrelon it camelon from
     * Sincelon this is alrelonady a fatal elonrror caselon for thelon relonquelonst, its okay to belon a littlelon elonxpelonnsivelon to gelont
     * thelon belonst elonrror melonssagelon possiblelon for delonbug purposelons.
     */
    if (containsGapCursor(cursors)) {
      val elonrrorDelontails =
        if (containsGapCursor(builtCursors)) {
          "This melonans onelon of your `cursorBuildelonrs` relonturnelond a GapCursor."
        } elonlselon if (containsGapCursor(itelonms)) {
          "This melonans onelon of your `CandidatelonDeloncorator`s deloncoratelond a Candidatelon with a GapCursor."
        } elonlselon {
          "This melonans onelon of your `cursorUpdatelonrs` relonturnelond a GapCursor."
        }
      throw PipelonlinelonFailurelon(
        UnelonxpelonctelondCandidatelonInMarshallelonr,
        s"SlicelonBuildelonr doelons not support GapCursors but onelon was givelonn. $elonrrorDelontails"
      )
    }

    Slicelon(
      itelonms = nonCursorItelonms,
      slicelonInfo = SlicelonInfo(prelonviousCursor = prelonviousCursor, nelonxtCursor = nelonxtCursor))
  }
}
