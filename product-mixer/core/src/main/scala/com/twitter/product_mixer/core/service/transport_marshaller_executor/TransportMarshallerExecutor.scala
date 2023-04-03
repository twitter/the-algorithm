packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.transport_marshallelonr_elonxeloncutor

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.TransportMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.transport_marshallelonr_elonxeloncutor.TransportMarshallelonrelonxeloncutor.Inputs
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.transport_marshallelonr_elonxeloncutor.TransportMarshallelonrelonxeloncutor.Relonsult
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * elonxeloncutelons a [[TransportMarshallelonr]].
 *
 * @notelon This is a synchronous transform, so welon don't obselonrvelon it direlonctly. Failurelons and such
 *       can belon obselonrvelond at thelon parelonnt pipelonlinelon.
 */
@Singlelonton
class TransportMarshallelonrelonxeloncutor @Injelonct() (ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds elonxeloncutor {

  delonf arrow[DomainRelonsponselonTypelon <: HasMarshalling, TransportRelonsponselonTypelon](
    marshallelonr: TransportMarshallelonr[DomainRelonsponselonTypelon, TransportRelonsponselonTypelon],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[Inputs[DomainRelonsponselonTypelon], Relonsult[TransportRelonsponselonTypelon]] = {
    val arrow =
      Arrow.map[Inputs[DomainRelonsponselonTypelon], Relonsult[TransportRelonsponselonTypelon]] {
        caselon Inputs(domainRelonsponselon) => Relonsult(marshallelonr(domainRelonsponselon))
      }

    wrapComponelonntWithelonxeloncutorBookkelonelonping(contelonxt, marshallelonr.idelonntifielonr)(arrow)
  }
}

objelonct TransportMarshallelonrelonxeloncutor {
  caselon class Inputs[DomainRelonsponselonTypelon <: HasMarshalling](domainRelonsponselon: DomainRelonsponselonTypelon)
  caselon class Relonsult[TransportRelonsponselonTypelon](relonsult: TransportRelonsponselonTypelon) elonxtelonnds elonxeloncutorRelonsult
}
