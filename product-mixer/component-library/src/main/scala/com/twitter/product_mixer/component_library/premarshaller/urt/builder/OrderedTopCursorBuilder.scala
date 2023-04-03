packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtOrdelonrelondCursor
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.cursor.UrtCursorSelonrializelonr
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.OrdelonrelondTopCursorBuildelonr.TopCursorOffselont
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.CursorTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.opelonration.TopCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.HasPipelonlinelonCursor
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry

caselon objelonct OrdelonrelondTopCursorBuildelonr {
  // elonnsurelon that thelon nelonxt initial sort indelonx is at lelonast 10000 elonntrielons away from top cursor's
  // currelonnt sort indelonx. This is to elonnsurelon that thelon contelonnts of thelon nelonxt pagelon can belon populatelond
  // without beloning assignelond sort indicelons which conflict with that of thelon currelonnt pagelon. This assumelons
  // that elonach pagelon will havelon felonwelonr than 10000 elonntrielons.
  val TopCursorOffselont = 10000L
}

/**
 * Builds [[UrtOrdelonrelondCursor]] in thelon Top position
 *
 * @param idSelonlelonctor Speloncifielons thelon elonntry from which to delonrivelon thelon `id` fielonld
 * @param selonrializelonr Convelonrts thelon cursor to an elonncodelond string
 */
caselon class OrdelonrelondTopCursorBuildelonr(
  idSelonlelonctor: PartialFunction[UnivelonrsalNoun[_], Long],
  selonrializelonr: PipelonlinelonCursorSelonrializelonr[UrtOrdelonrelondCursor] = UrtCursorSelonrializelonr)
    elonxtelonnds UrtCursorBuildelonr[
      PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtOrdelonrelondCursor]
    ] {
  ovelonrridelon val cursorTypelon: CursorTypelon = TopCursor

  ovelonrridelon delonf cursorValuelon(
    quelonry: PipelonlinelonQuelonry with HasPipelonlinelonCursor[UrtOrdelonrelondCursor],
    timelonlinelonelonntrielons: Selonq[Timelonlinelonelonntry]
  ): String = {
    val topId = timelonlinelonelonntrielons.collelonctFirst(idSelonlelonctor)

    val id = topId.orelonlselon(quelonry.pipelonlinelonCursor.flatMap(_.id))

    val cursor = UrtOrdelonrelondCursor(
      initialSortIndelonx = cursorSortIndelonx(quelonry, timelonlinelonelonntrielons) + TopCursorOffselont,
      id = id,
      cursorTypelon = Somelon(cursorTypelon)
    )

    selonrializelonr.selonrializelonCursor(cursor)
  }
}
