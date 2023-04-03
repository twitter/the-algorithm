packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtUnordelonrelondelonxcludelonIdsCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.BottomCursor
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

trait BaselonUnordelonrelondelonxcludelonIdsBottomCursorBuildelonr
    elonxtelonnds UrtCursorBuildelonr[
      PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtUnordelonrelondelonxcludelonIdsCursor]
    ] {

  delonf elonxcludelondIdsMaxLelonngthParam: Param[Int]

  delonf elonxcludelonelonntrielonsCollelonctor(elonntrielons: Selonq[Timelonlinelonelonntry]): Selonq[Long]

  delonf selonrializelonr: PipelonlinelonCursorSelonrializelonr[UrtUnordelonrelondelonxcludelonIdsCursor]

  ovelonrridelon val cursorTypelon: CursorTypelon = BottomCursor

  ovelonrridelon delonf cursorValuelon(
    quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtUnordelonrelondelonxcludelonIdsCursor],
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): String = {
    val elonxcludelondIdsMaxLelonngth = quelonry.params(elonxcludelondIdsMaxLelonngthParam)
    asselonrt(elonxcludelondIdsMaxLelonngth > 0, "elonxcludelond IDs max lelonngth must belon grelonatelonr than zelonro")

    val nelonwelonntryIds = elonxcludelonelonntrielonsCollelonctor(elonntrielons)
    asselonrt(
      nelonwelonntryIds.lelonngth < elonxcludelondIdsMaxLelonngth,
      "Nelonw elonntry IDs lelonngth must belon smallelonr than elonxcludelond IDs max lelonngth")

    val elonxcludelondIds = quelonry.pipelonlinelonCursor
      .map(_.elonxcludelondIds ++ nelonwelonntryIds)
      .gelontOrelonlselon(nelonwelonntryIds)
      .takelonRight(elonxcludelondIdsMaxLelonngth)

    val cursor = UrtUnordelonrelondelonxcludelonIdsCursor(
      initialSortIndelonx = nelonxtBottomInitialSortIndelonx(quelonry, elonntrielons),
      elonxcludelondIds = elonxcludelondIds
    )

    selonrializelonr.selonrializelonCursor(cursor)
  }
}
