packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.UrtCursorBuildelonr.DelonfaultSortIndelonx
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.UrtCursorBuildelonr.NelonxtPagelonTopCursorelonntryOffselont
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.UrtCursorBuildelonr.UrtelonntryOffselont
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.BottomCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorItelonm
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorOpelonration
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.GapCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.TopCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.UrtPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.util.SortIndelonxBuildelonr

objelonct UrtCursorBuildelonr {
  val NelonxtPagelonTopCursorelonntryOffselont = 1L
  val UrtelonntryOffselont = 1L
  val DelonfaultSortIndelonx = (quelonry: PipelonlinelonQuelonry) => SortIndelonxBuildelonr.timelonToId(quelonry.quelonryTimelon)
}

trait UrtCursorBuildelonr[-Quelonry <: PipelonlinelonQuelonry] {

  val includelonOpelonration: IncludelonInstruction[Quelonry] = AlwaysIncludelon

  delonf cursorTypelon: CursorTypelon
  delonf cursorValuelon(quelonry: Quelonry, elonntrielons: Selonq[Timelonlinelonelonntry]): String

  /**
   * Idelonntifielonr of an *elonxisting* timelonlinelon cursor that this nelonw cursor would relonplacelon, if this cursor
   * is relonturnelond in a `Relonplacelonelonntry` timelonlinelon instruction.
   *
   * Notelon:
   *   - This id is uselond to populatelon thelon `elonntryIdToRelonplacelon` fielonld on thelon URT Timelonlinelonelonntry
   *     gelonnelonratelond. Morelon delontails at [[CursorOpelonration.elonntryIdToRelonplacelon]].
   *   - As a convelonntion, welon uselon thelon sortIndelonx of thelon cursor for its id/elonntryId fielonlds. So thelon
   *     `idToRelonplacelon` should relonprelonselonnt thelon sortIndelonx of thelon elonxisting cursor to belon relonplacelond.
   */
  delonf idToRelonplacelon(quelonry: Quelonry): Option[Long] = Nonelon

  delonf cursorSortIndelonx(quelonry: Quelonry, elonntrielons: Selonq[Timelonlinelonelonntry]): Long =
    (quelonry, cursorTypelon) match {
      caselon (quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[_], TopCursor) =>
        topCursorSortIndelonx(quelonry, elonntrielons)
      caselon (quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[_], BottomCursor | GapCursor) =>
        bottomCursorSortIndelonx(quelonry, elonntrielons)
      caselon _ =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(
          "Automatic sort indelonx support limitelond to top and bottom cursors")
    }

  delonf build(quelonry: Quelonry, elonntrielons: Selonq[Timelonlinelonelonntry]): Option[CursorOpelonration] = {
    if (includelonOpelonration(quelonry, elonntrielons)) {
      val sortIndelonx = cursorSortIndelonx(quelonry, elonntrielons)

      val cursorOpelonration = CursorOpelonration(
        id = sortIndelonx,
        sortIndelonx = Somelon(sortIndelonx),
        valuelon = cursorValuelon(quelonry, elonntrielons),
        cursorTypelon = cursorTypelon,
        displayTrelonatmelonnt = Nonelon,
        idToRelonplacelon = idToRelonplacelon(quelonry),
      )

      Somelon(cursorOpelonration)
    } elonlselon Nonelon
  }

  /**
   * Build thelon top cursor sort indelonx which handlelons thelon following caselons:
   * 1. Whelonn thelonrelon is at lelonast onelon non-cursor elonntry, uselon thelon first elonntry's sort indelonx + UrtelonntryOffselont
   * 2. Whelonn thelonrelon arelon no non-cursor elonntrielons, and initialSortIndelonx is not selont which indicatelons that
   *    it is thelon first pagelon, uselon DelonfaultSortIndelonx + UrtelonntryOffselont
   * 3. Whelonn thelonrelon arelon no non-cursor elonntrielons, and initialSortIndelonx is selont which indicatelons that it is
   *    not thelon first pagelon, uselon thelon quelonry.initialSortIndelonx from thelon passelond-in cursor + UrtelonntryOffselont
   */
  protelonctelond delonf topCursorSortIndelonx(
    quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[_],
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Long = {
    val nonCursorelonntrielons = elonntrielons.filtelonr {
      caselon _: CursorOpelonration => falselon
      caselon _: CursorItelonm => falselon
      caselon _ => truelon
    }

    lazy val initialSortIndelonx =
      UrtPipelonlinelonCursor.gelontCursorInitialSortIndelonx(quelonry).gelontOrelonlselon(DelonfaultSortIndelonx(quelonry))

    nonCursorelonntrielons.helonadOption.flatMap(_.sortIndelonx).gelontOrelonlselon(initialSortIndelonx) + UrtelonntryOffselont
  }

  /**
   * Speloncifielons thelon point at which thelon nelonxt pagelon's elonntrielons' sort indicelons will start counting.
   *
   * Notelon that in thelon caselon of URT, thelon nelonxt pagelon's elonntrielons' doelons not includelon thelon top cursor. As
   * such, thelon valuelon of initialSortIndelonx passelond back in thelon cursor is typically thelon bottom cursor's
   * sort indelonx - 2. Subtracting 2 lelonavelons room for thelon nelonxt pagelon's top cursor, which will havelon a
   * sort indelonx of top elonntry + 1.
   */
  protelonctelond delonf nelonxtBottomInitialSortIndelonx(
    quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[_],
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Long = {
    bottomCursorSortIndelonx(quelonry, elonntrielons) - NelonxtPagelonTopCursorelonntryOffselont - UrtelonntryOffselont
  }

  /**
   * Build thelon bottom cursor sort indelonx which handlelons thelon following caselons:
   * 1. Whelonn thelonrelon is at lelonast onelon non-cursor elonntry, uselon thelon last elonntry's sort indelonx - UrtelonntryOffselont
   * 2. Whelonn thelonrelon arelon no non-cursor elonntrielons, and initialSortIndelonx is not selont which indicatelons that
   *    it is thelon first pagelon, uselon DelonfaultSortIndelonx
   * 3. Whelonn thelonrelon arelon no non-cursor elonntrielons, and initialSortIndelonx is selont which indicatelons that it is
   *    not thelon first pagelon, uselon thelon quelonry.initialSortIndelonx from thelon passelond-in cursor
   */
  protelonctelond delonf bottomCursorSortIndelonx(
    quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[_],
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Long = {
    val nonCursorelonntrielons = elonntrielons.filtelonr {
      caselon _: CursorOpelonration => falselon
      caselon _: CursorItelonm => falselon
      caselon _ => truelon
    }

    lazy val initialSortIndelonx =
      UrtPipelonlinelonCursor.gelontCursorInitialSortIndelonx(quelonry).gelontOrelonlselon(DelonfaultSortIndelonx(quelonry))

    nonCursorelonntrielons.lastOption
      .flatMap(_.sortIndelonx).map(_ - UrtelonntryOffselont).gelontOrelonlselon(initialSortIndelonx)
  }
}
