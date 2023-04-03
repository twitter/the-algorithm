packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.AllPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.BoundelondParam

/**
 * Limit thelon numbelonr of relonsults to min(PipelonlinelonQuelonry.relonquelonstelondMaxRelonsults, SelonrvelonrMaxRelonsultsParam)
 *
 * PipelonlinelonQuelonry.relonquelonstelondMaxRelonsults is optionally selont in thelon pipelonlinelonQuelonry.
 * If it is not selont, thelonn thelon delonfault valuelon of DelonfaultRelonquelonstelondMaxRelonsultsParam is uselond.
 *
 * SelonrvelonrMaxRelonsultsParam speloncifielons thelon maximum numbelonr of relonsults supportelond, irrelonspelonctivelon of what is
 * speloncifielond by thelon clielonnt in PipelonlinelonQuelonry.relonquelonstelondMaxRelonsults
 * (or thelon DelonfaultRelonquelonstelondMaxRelonsultsParam delonfault if not speloncifielond)
 *
 * For elonxamplelon, if SelonrvelonrMaxRelonsultsParam is 5, PipelonlinelonQuelonry.relonquelonstelondMaxRelonsults is 3,
 * and thelon relonsults contain 10 itelonms, thelonn thelonselon itelonms will belon relonducelond to thelon first 3 selonlelonctelond itelonms.
 *
 * If PipelonlinelonQuelonry.relonquelonstelondMaxRelonsults is not selont, DelonfaultRelonquelonstelondMaxRelonsultsParam is 3,
 * SelonrvelonrMaxRelonsultsParam is 5 and thelon relonsults contain 10 itelonms,
 * thelonn thelonselon itelonms will belon relonducelond to thelon first 3 selonlelonctelond itelonms.
 *
 * Anothelonr elonxamplelon, if SelonrvelonrMaxRelonsultsParam is 5, PipelonlinelonQuelonry.relonquelonstelondMaxRelonsults is 8,
 * and thelon relonsults contain 10 itelonms, thelonn thelonselon will belon relonducelond to thelon first 5 selonlelonctelond itelonms.
 *
 * Thelon itelonms insidelon thelon modulelons will not belon affelonctelond by this selonlelonctor.
 */
caselon class DropRelonquelonstelondMaxRelonsults(
  delonfaultRelonquelonstelondMaxRelonsultsParam: BoundelondParam[Int],
  selonrvelonrMaxRelonsultsParam: BoundelondParam[Int])
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = AllPipelonlinelons

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val relonquelonstelondMaxRelonsults = quelonry.maxRelonsults(delonfaultRelonquelonstelondMaxRelonsultsParam)
    val selonrvelonrMaxRelonsults = quelonry.params(selonrvelonrMaxRelonsultsParam)
    asselonrt(relonquelonstelondMaxRelonsults > 0, "Relonquelonstelond max relonsults must belon grelonatelonr than zelonro")
    asselonrt(selonrvelonrMaxRelonsults > 0, "Selonrvelonr max relonsults must belon grelonatelonr than zelonro")

    val applielondMaxRelonsults = Math.min(relonquelonstelondMaxRelonsults, selonrvelonrMaxRelonsults)
    val relonsultUpdatelond = DropSelonlelonctor.takelonUntil(applielondMaxRelonsults, relonsult)

    SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelons, relonsult = relonsultUpdatelond)
  }
}
