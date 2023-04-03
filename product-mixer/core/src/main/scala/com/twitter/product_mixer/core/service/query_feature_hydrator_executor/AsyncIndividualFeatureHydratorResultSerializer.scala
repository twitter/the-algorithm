packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.quelonry_felonaturelon_hydrator_elonxeloncutor

import com.fastelonrxml.jackson.corelon.JsonGelonnelonrator
import com.fastelonrxml.jackson.databind.JsonSelonrializelonr
import com.fastelonrxml.jackson.databind.SelonrializelonrProvidelonr
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.quelonry_felonaturelon_hydrator_elonxeloncutor.QuelonryFelonaturelonHydratorelonxeloncutor.AsyncIndividualFelonaturelonHydratorRelonsult

/** A [[JsonSelonrializelonr]] that skips thelon `Stitch` valuelons */
privatelon[quelonry_felonaturelon_hydrator_elonxeloncutor] class AsyncIndividualFelonaturelonHydratorRelonsultSelonrializelonr()
    elonxtelonnds JsonSelonrializelonr[AsyncIndividualFelonaturelonHydratorRelonsult] {

  ovelonrridelon delonf selonrializelon(
    asyncIndividualFelonaturelonHydratorRelonsult: AsyncIndividualFelonaturelonHydratorRelonsult,
    gelonn: JsonGelonnelonrator,
    selonrializelonrs: SelonrializelonrProvidelonr
  ): Unit =
    selonrializelonrs.delonfaultSelonrializelonValuelon(
      // implicitly calls `toString` on thelon idelonntifielonr beloncauselon thelony arelon kelonys in thelon Map
      Map(
        asyncIndividualFelonaturelonHydratorRelonsult.hydratelonBelonforelon ->
          asyncIndividualFelonaturelonHydratorRelonsult.felonaturelons.map(_.toString)),
      gelonn
    )
}
