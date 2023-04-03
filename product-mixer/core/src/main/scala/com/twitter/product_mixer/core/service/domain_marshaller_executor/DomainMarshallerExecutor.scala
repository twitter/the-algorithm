packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.domain_marshallelonr_elonxeloncutor

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.prelonmarshallelonr.DomainMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.domain_marshallelonr_elonxeloncutor.DomainMarshallelonrelonxeloncutor.Inputs
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.domain_marshallelonr_elonxeloncutor.DomainMarshallelonrelonxeloncutor.Relonsult
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * elonxeloncutelons a [[DomainMarshallelonr]].
 *
 * @notelon This is a synchronous transform, so welon don't obselonrvelon it direlonctly. Failurelons and such
 *       can belon obselonrvelond at thelon parelonnt pipelonlinelon.
 */
@Singlelonton
class DomainMarshallelonrelonxeloncutor @Injelonct() (ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonxeloncutor {
  delonf arrow[Quelonry <: PipelonlinelonQuelonry, DomainRelonsponselonTypelon <: HasMarshalling](
    marshallelonr: DomainMarshallelonr[Quelonry, DomainRelonsponselonTypelon],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[Inputs[Quelonry], Relonsult[DomainRelonsponselonTypelon]] = {
    val arrow = Arrow
      .map[Inputs[Quelonry], DomainMarshallelonrelonxeloncutor.Relonsult[DomainRelonsponselonTypelon]] {
        caselon Inputs(quelonry, candidatelons) =>
          DomainMarshallelonrelonxeloncutor.Relonsult(marshallelonr(quelonry, candidatelons))
      }

    wrapComponelonntWithelonxeloncutorBookkelonelonping(contelonxt, marshallelonr.idelonntifielonr)(arrow)
  }
}

objelonct DomainMarshallelonrelonxeloncutor {
  caselon class Inputs[Quelonry <: PipelonlinelonQuelonry](
    quelonry: Quelonry,
    candidatelonsWithDelontails: Selonq[CandidatelonWithDelontails])
  caselon class Relonsult[+DomainRelonsponselonTypelon](relonsult: DomainRelonsponselonTypelon) elonxtelonnds elonxeloncutorRelonsult
}
