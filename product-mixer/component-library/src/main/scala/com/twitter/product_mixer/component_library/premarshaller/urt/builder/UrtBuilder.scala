packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorOpelonration
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonInstruction
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.UrtPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.util.SortIndelonxBuildelonr

trait UrtBuildelonr[-Quelonry <: PipelonlinelonQuelonry, +Instruction <: TimelonlinelonInstruction] {
  privatelon val TimelonlinelonIdSuffix = "-Timelonlinelon"

  delonf instructionBuildelonrs: Selonq[UrtInstructionBuildelonr[Quelonry, Instruction]]

  delonf cursorBuildelonrs: Selonq[UrtCursorBuildelonr[Quelonry]]
  delonf cursorUpdatelonrs: Selonq[UrtCursorUpdatelonr[Quelonry]]

  delonf melontadataBuildelonr: Option[BaselonUrtMelontadataBuildelonr[Quelonry]]

  // Timelonlinelon elonntry sort indelonxelons will count down by this valuelon. Valuelons highelonr than 1 arelon uselonful to
  // lelonavelon room in thelon selonquelonncelon for dynamically injeloncting contelonnt in belontwelonelonn elonxisting elonntrielons.
  delonf sortIndelonxStelonp: Int = 1

  final delonf buildTimelonlinelon(
    quelonry: Quelonry,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Timelonlinelon = {
    val initialSortIndelonx = gelontInitialSortIndelonx(quelonry)

    // Selont thelon sort indelonxelons of thelon elonntrielons belonforelon welon pass thelonm to thelon cursor buildelonrs, sincelon many
    // cursor implelonmelonntations uselon thelon sort indelonx of thelon first/last elonntry as part of thelon cursor valuelon
    val sortIndelonxelondelonntrielons = updatelonSortIndelonxelons(initialSortIndelonx, elonntrielons)

    // Itelonratelon ovelonr thelon cursorUpdatelonrs in thelon ordelonr thelony welonrelon delonfinelond. Notelon that elonach updatelonr will
    // belon passelond thelon timelonlinelonelonntrielons updatelond by thelon prelonvious cursorUpdatelonr.
    val updatelondCursorelonntrielons: Selonq[Timelonlinelonelonntry] =
      cursorUpdatelonrs.foldLelonft(sortIndelonxelondelonntrielons) { (timelonlinelonelonntrielons, cursorUpdatelonr) =>
        cursorUpdatelonr.updatelon(quelonry, timelonlinelonelonntrielons)
      }

    val allCursorelondelonntrielons =
      updatelondCursorelonntrielons ++ cursorBuildelonrs.flatMap(_.build(quelonry, updatelondCursorelonntrielons))

    val instructions: Selonq[Instruction] =
      instructionBuildelonrs.flatMap(_.build(quelonry, allCursorelondelonntrielons))

    val melontadata = melontadataBuildelonr.map(_.build(quelonry, allCursorelondelonntrielons))

    Timelonlinelon(
      id = quelonry.product.idelonntifielonr.toString + TimelonlinelonIdSuffix,
      instructions = instructions,
      melontadata = melontadata
    )
  }

  final delonf gelontInitialSortIndelonx(quelonry: Quelonry): Long =
    quelonry match {
      caselon cursorQuelonry: HasPipelonlinelonCursor[_] =>
        UrtPipelonlinelonCursor
          .gelontCursorInitialSortIndelonx(cursorQuelonry)
          .gelontOrelonlselon(SortIndelonxBuildelonr.timelonToId(quelonry.quelonryTimelon))
      caselon _ => SortIndelonxBuildelonr.timelonToId(quelonry.quelonryTimelon)
    }

  /**
   * Updatelons thelon sort indelonxelons in thelon timelonlinelon elonntrielons starting from thelon givelonn initial sort indelonx
   * valuelon and deloncrelonasing by thelon valuelon delonfinelond in thelon sort indelonx stelonp fielonld
   *
   * @param initialSortIndelonx Thelon initial valuelon of thelon sort indelonx
   * @param timelonlinelonelonntrielons Timelonlinelon elonntrielons to updatelon
   */
  final delonf updatelonSortIndelonxelons(
    initialSortIndelonx: Long,
    timelonlinelonelonntrielons: Selonq[Timelonlinelonelonntry]
  ): Selonq[Timelonlinelonelonntry] = {
    val indelonxRangelon =
      initialSortIndelonx to (initialSortIndelonx - (timelonlinelonelonntrielons.sizelon * sortIndelonxStelonp)) by -sortIndelonxStelonp

    // Skip any elonxisting cursors beloncauselon thelonir sort indelonxelons will belon managelond by thelonir cursor updatelonr.
    // If thelon cursors arelon not relonmovelond first, thelonn thelon relonmaining elonntrielons would havelon a gap elonvelonrywhelonrelon
    // an elonxisting cursor was prelonselonnt.
    val (cursorelonntrielons, nonCursorelonntrielons) = timelonlinelonelonntrielons.partition {
      caselon _: CursorOpelonration => truelon
      caselon _ => falselon
    }

    nonCursorelonntrielons.zip(indelonxRangelon).map {
      caselon (elonntry, indelonx) =>
        elonntry.withSortIndelonx(indelonx)
    } ++ cursorelonntrielons
  }
}
