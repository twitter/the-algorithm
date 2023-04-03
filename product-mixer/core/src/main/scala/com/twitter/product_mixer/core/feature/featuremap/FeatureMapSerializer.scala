packagelon com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap

import com.fastelonrxml.jackson.corelon.JsonGelonnelonrator
import com.fastelonrxml.jackson.databind.JsonSelonrializelonr
import com.fastelonrxml.jackson.databind.SelonrializelonrProvidelonr
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.felonaturelonvaluelon.FelonaturelonStorelonV1Relonsponselon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonstorelonv1.felonaturelonvaluelon.FelonaturelonStorelonV1RelonsponselonFelonaturelon
import com.twittelonr.util.Relonturn

/**
 * Relonndelonring felonaturelon maps is dangelonrous beloncauselon welon don't control all thelon data that's storelond in thelonm.
 * This can relonsult failelond relonquelonsts, as welon might try to relonndelonr a reloncursivelon structurelon, velonry largelon
 * structurelon, elontc. Crelonatelon a simplelon map using toString, this mostly works and is belonttelonr than failing
 * thelon relonquelonst.
 *
 * @notelon changelons to selonrialization logic can havelon selonrious pelonrformancelon implications givelonn how hot thelon
 *       selonrialization path is. Considelonr belonnchmarking changelons with [[com.twittelonr.product_mixelonr.corelon.belonnchmark.CandidatelonPipelonlinelonRelonsultSelonrializationBelonnchmark]]
 */
privatelon[felonaturelonmap] class FelonaturelonMapSelonrializelonr() elonxtelonnds JsonSelonrializelonr[FelonaturelonMap] {
  ovelonrridelon delonf selonrializelon(
    felonaturelonMap: FelonaturelonMap,
    gelonn: JsonGelonnelonrator,
    selonrializelonrs: SelonrializelonrProvidelonr
  ): Unit = {
    gelonn.writelonStartObjelonct()

    felonaturelonMap.undelonrlyingMap.forelonach {
      caselon (FelonaturelonStorelonV1RelonsponselonFelonaturelon, Relonturn(valuelon)) =>
        // Welon know that valuelon has to belon [[FelonaturelonStorelonV1Relonsponselon]] but its typelon has belonelonn elonraselond,
        // prelonvelonnting us from pattelonrn-matching.
        val felonaturelonStorelonRelonsponselon = valuelon.asInstancelonOf[FelonaturelonStorelonV1Relonsponselon]

        val felonaturelonsItelonrator = felonaturelonStorelonRelonsponselon.richDataReloncord.allFelonaturelonsItelonrator()
        whilelon (felonaturelonsItelonrator.movelonNelonxt()) {
          gelonn.writelonStringFielonld(
            felonaturelonsItelonrator.gelontFelonaturelon.gelontFelonaturelonNamelon,
            s"${felonaturelonsItelonrator.gelontFelonaturelonTypelon.namelon}(${truncatelonString(
              felonaturelonsItelonrator.gelontFelonaturelonValuelon.toString)})")
        }

        felonaturelonStorelonRelonsponselon.failelondFelonaturelons.forelonach {
          caselon (failelondFelonaturelon, failurelonRelonasons) =>
            gelonn.writelonStringFielonld(
              failelondFelonaturelon.toString,
              s"Failelond(${truncatelonString(failurelonRelonasons.toString)})")
        }
      caselon (namelon, Relonturn(valuelon)) =>
        gelonn.writelonStringFielonld(namelon.toString, truncatelonString(valuelon.toString))
      caselon (namelon, elonrror) =>
        // Notelon: welon don't match on Throw(elonrror) beloncauselon welon want to kelonelonp it for thelon toString
        gelonn.writelonStringFielonld(namelon.toString, truncatelonString(elonrror.toString))

    }

    gelonn.writelonelonndObjelonct()
  }

  // Somelon felonaturelons can belon velonry largelon whelonn stringifielond, for elonxamplelon whelonn a delonpelonndant candidatelon
  // pipelonlinelon is uselond, thelon elonntirelon prelonvious candidatelon pipelonlinelon relonsult is selonrializelond into a felonaturelon.
  // This causelons significant pelonrformancelon issuelons whelonn thelon relonsult is latelonr selonnt ovelonr thelon wirelon.
  privatelon delonf truncatelonString(input: String): String =
    if (input.lelonngth > 1000) input.takelon(1000) + "..." elonlselon input
}
