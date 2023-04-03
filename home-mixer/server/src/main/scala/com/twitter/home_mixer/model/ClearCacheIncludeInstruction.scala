packagelon com.twittelonr.homelon_mixelonr.modelonl

import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.DelonvicelonContelonxt.RelonquelonstContelonxt
import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasDelonvicelonContelonxt
import com.twittelonr.product_mixelonr.componelonnt_library.prelonmarshallelonr.urt.buildelonr.IncludelonInstruction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.Timelonlinelonelonntry
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.TimelonlinelonModulelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.twelonelont.TwelonelontItelonm
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.FSBoundelondParam
import com.twittelonr.timelonlinelons.configapi.FSParam

/**
 * Includelon a clelonar cachelon timelonlinelon instruction whelonn welon satisfy thelonselon critelonria:
 * - Relonquelonst Provelonnancelon is "pull to relonfrelonsh"
 * - Atlelonast N non-ad twelonelont elonntrielons in thelon relonsponselon
 *
 * This is to elonnsurelon that welon havelon sufficielonnt nelonw contelonnt to justify jumping uselonrs to thelon
 * top of thelon nelonw timelonlinelons relonsponselon and don't add unneloncelonssary load to backelonnd systelonms
 */
caselon class ClelonarCachelonIncludelonInstruction(
  elonnablelonParam: FSParam[Boolelonan],
  minelonntrielonsParam: FSBoundelondParam[Int])
    elonxtelonnds IncludelonInstruction[PipelonlinelonQuelonry with HasDelonvicelonContelonxt] {

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry with HasDelonvicelonContelonxt,
    elonntrielons: Selonq[Timelonlinelonelonntry]
  ): Boolelonan = {
    val elonnablelond = quelonry.params(elonnablelonParam)

    val ptr =
      quelonry.delonvicelonContelonxt.flatMap(_.relonquelonstContelonxtValuelon).contains(RelonquelonstContelonxt.PullToRelonfrelonsh)

    val minTwelonelonts = quelonry.params(minelonntrielonsParam) <= elonntrielons.collelonct {
      caselon itelonm: TwelonelontItelonm if itelonm.promotelondMelontadata.iselonmpty => 1
      caselon modulelon: TimelonlinelonModulelon if modulelon.itelonms.helonad.itelonm.isInstancelonOf[TwelonelontItelonm] =>
        modulelon.itelonms.sizelon
    }.sum

    elonnablelond && ptr && minTwelonelonts
  }
}
