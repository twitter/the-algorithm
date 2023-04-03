packagelon com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.cursor.UrtUnordelonrelondelonxcludelonIdsCursor
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.cursor.UrtCursorSelonrializelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonCursorSelonrializelonr
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * Builds [[UrtUnordelonrelondelonxcludelonIdsCursor]] in thelon Bottom position
 *
 * @param elonxcludelondIdsMaxLelonngthParam Thelon maximum lelonngth of thelon cursor
 * @param elonxcludelonIdsSelonlelonctor Speloncifielons thelon elonntry Ids to populatelon on thelon `elonxcludelondIds` fielonld
 * @param selonrializelonr Convelonrts thelon cursor to an elonncodelond string
 */
caselon class UnordelonrelondelonxcludelonIdsBottomCursorBuildelonr(
  ovelonrridelon val elonxcludelondIdsMaxLelonngthParam: Param[Int],
  elonxcludelonIdsSelonlelonctor: PartialFunction[UnivelonrsalNoun[_], Long],
  ovelonrridelon val selonrializelonr: PipelonlinelonCursorSelonrializelonr[UrtUnordelonrelondelonxcludelonIdsCursor] =
    UrtCursorSelonrializelonr)
    elonxtelonnds BaselonUnordelonrelondelonxcludelonIdsBottomCursorBuildelonr {

  ovelonrridelon delonf elonxcludelonelonntrielonsCollelonctor(elonntrielons: Selonq[Timelonlinelonelonntry]): Selonq[Long] =
    elonntrielons.collelonct(elonxcludelonIdsSelonlelonctor)
}
