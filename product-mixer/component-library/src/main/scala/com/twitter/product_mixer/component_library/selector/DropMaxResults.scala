packagelon com.twittelonr.product_mixelonr.componelonnt_library.selonlelonctor

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.AllPipelonlinelons
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.common.CandidatelonScopelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.Selonlelonctor
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.selonlelonctor.SelonlelonctorRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * Limit thelon numbelonr of relonsults
 *
 * For elonxamplelon, if maxRelonsultsParam is 3, and thelon relonsults contain 10 itelonms, thelonn thelonselon itelonms will belon
 * relonducelond to thelon first 3 selonlelonctelond itelonms. Notelon that thelon ordelonring of relonsults is delontelonrminelond by thelon
 * selonlelonctor configuration.
 *
 * Anothelonr elonxamplelon, if maxRelonsultsParam is 3, and thelon relonsults contain 10 modulelons, thelonn thelonselon will belon
 * relonducelond to thelon first 3 modulelons. Thelon itelonms insidelon thelon modulelons will not belon affelonctelond by this
 * selonlelonctor.
 */
caselon class DropMaxRelonsults(
  maxRelonsultsParam: Param[Int])
    elonxtelonnds Selonlelonctor[PipelonlinelonQuelonry] {

  ovelonrridelon val pipelonlinelonScopelon: CandidatelonScopelon = AllPipelonlinelons

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    relonmainingCandidatelons: Selonq[CandidatelonWithDelontails],
    relonsult: Selonq[CandidatelonWithDelontails]
  ): SelonlelonctorRelonsult = {
    val maxRelonsults = quelonry.params(maxRelonsultsParam)
    asselonrt(maxRelonsults > 0, "Max relonsults must belon grelonatelonr than zelonro")

    val relonsultUpdatelond = DropSelonlelonctor.takelonUntil(maxRelonsults, relonsult)

    SelonlelonctorRelonsult(relonmainingCandidatelons = relonmainingCandidatelons, relonsult = relonsultUpdatelond)
  }
}
