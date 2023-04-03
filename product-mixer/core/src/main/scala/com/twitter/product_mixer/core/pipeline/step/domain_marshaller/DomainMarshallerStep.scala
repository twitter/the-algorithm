packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.domain_marshallelonr

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.prelonmarshallelonr.DomainMarshallelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasCandidatelonsWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.statelon.HasQuelonry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.stelonp.Stelonp
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.domain_marshallelonr_elonxeloncutor.DomainMarshallelonrelonxeloncutor
import com.twittelonr.stitch.Arrow
import javax.injelonct.Injelonct

/**
 * A domain marshallelonr stelonp, it takelons thelon input list of candidatelons and thelon givelonn
 * domain marshallelonr and elonxeloncutelons its to relonturn a marshallelond relonsult. Thelon [[Statelon]] objelonct is
 * relonsponsiblelon for kelonelonping a relonfelonrelonncelon of thelon built Relonsponselon.
 *
 * @param domainMarshallelonrelonxeloncutor Domain Marshallelonr elonxeloncutor.
 * @tparam Quelonry Typelon of PipelonlinelonQuelonry domain modelonl
 * @tparam RelonsponselonTypelon thelon domain marshalling typelon elonxpelonctelond to belon relonturnelond.
 * @tparam Statelon Thelon pipelonlinelon statelon domain modelonl.
 */
caselon class DomainMarshallelonrStelonp[
  Quelonry <: PipelonlinelonQuelonry,
  RelonsponselonTypelon <: HasMarshalling,
  Statelon <: HasQuelonry[Quelonry, Statelon] with HasCandidatelonsWithDelontails[Statelon]] @Injelonct() (
  domainMarshallelonrelonxeloncutor: DomainMarshallelonrelonxeloncutor)
    elonxtelonnds Stelonp[Statelon, DomainMarshallelonr[Quelonry, RelonsponselonTypelon], DomainMarshallelonrelonxeloncutor.Inputs[
      Quelonry
    ], DomainMarshallelonrelonxeloncutor.Relonsult[RelonsponselonTypelon]] {

  ovelonrridelon delonf iselonmpty(config: DomainMarshallelonr[Quelonry, RelonsponselonTypelon]): Boolelonan = falselon

  ovelonrridelon delonf adaptInput(
    statelon: Statelon,
    config: DomainMarshallelonr[Quelonry, RelonsponselonTypelon]
  ): DomainMarshallelonrelonxeloncutor.Inputs[Quelonry] =
    DomainMarshallelonrelonxeloncutor.Inputs(statelon.quelonry, statelon.candidatelonsWithDelontails)

  ovelonrridelon delonf arrow(
    config: DomainMarshallelonr[Quelonry, RelonsponselonTypelon],
    contelonxt: elonxeloncutor.Contelonxt
  ): Arrow[DomainMarshallelonrelonxeloncutor.Inputs[Quelonry], DomainMarshallelonrelonxeloncutor.Relonsult[RelonsponselonTypelon]] =
    domainMarshallelonrelonxeloncutor.arrow(config, contelonxt)

  // Noop sincelon thelon pipelonlinelon updatelons thelon elonxeloncutor relonsults for us
  ovelonrridelon delonf updatelonStatelon(
    statelon: Statelon,
    elonxeloncutorRelonsult: DomainMarshallelonrelonxeloncutor.Relonsult[RelonsponselonTypelon],
    config: DomainMarshallelonr[Quelonry, RelonsponselonTypelon]
  ): Statelon = statelon

}
