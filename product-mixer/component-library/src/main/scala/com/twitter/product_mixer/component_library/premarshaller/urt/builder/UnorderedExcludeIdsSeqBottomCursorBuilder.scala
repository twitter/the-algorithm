packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtUnordelonrelondelonxcludelonIdsCursor
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.cursor.UrtCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursorSelonrializelonr
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * Builds [[UrtUnordelonrelondelonxcludelonIdsCursor]] in thelon Bottom position whelonn welon want to also elonxcludelon ids
 * of itelonms insidelon a modulelon. Thelon relonason welon cannot uselon [[UnordelonrelondelonxcludelonIdsBottomCursorBuildelonr]] in
 * such caselon is that thelon elonxcludelonIdsSelonlelonctor of [[UnordelonrelondelonxcludelonIdsBottomCursorBuildelonr]] is doing a
 * onelon to onelon mapping belontwelonelonn elonntrielons and elonxcludelond ids, but in caselon of having a modulelon, a modulelon
 * elonntry can relonsult in elonxcluding a selonquelonncelon of elonntrielons.
 *
 * @param elonxcludelondIdsMaxLelonngthParam Thelon maximum lelonngth of thelon cursor
 * @param elonxcludelonIdsSelonlelonctor Speloncifielons thelon elonntry Ids to populatelon on thelon `elonxcludelondIds` fielonld
 * @param selonrializelonr Convelonrts thelon cursor to an elonncodelond string
 */
caselon class UnordelonrelondelonxcludelonIdsSelonqBottomCursorBuildelonr(
  ovelonrridelon val elonxcludelondIdsMaxLelonngthParam: Param[Int],
  elonxcludelonIdsSelonlelonctor: PartialFunction[UnivelonrsalNoun[_], Selonq[Long]],
  ovelonrridelon val selonrializelonr: PipelonlinelonCursorSelonrializelonr[UrtUnordelonrelondelonxcludelonIdsCursor] =
    UrtCursorSelonrializelonr)
    elonxtelonnds BaselonUnordelonrelondelonxcludelonIdsBottomCursorBuildelonr {

  ovelonrridelon delonf elonxcludelonelonntrielonsCollelonctor(elonntrielons: Selonq[Timelonlinelonelonntry]): Selonq[Long] =
    elonntrielons.collelonct(elonxcludelonIdsSelonlelonctor).flattelonn
}
