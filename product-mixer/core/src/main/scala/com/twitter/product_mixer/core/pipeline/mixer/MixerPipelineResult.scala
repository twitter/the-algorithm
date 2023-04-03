packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.mixelonr

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.asyncfelonaturelonmap.AsyncFelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.HasMarshalling
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonRelonsult
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.async_felonaturelon_map_elonxeloncutor.AsyncFelonaturelonMapelonxeloncutorRelonsults
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.candidatelon_pipelonlinelon_elonxeloncutor.CandidatelonPipelonlinelonelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.domain_marshallelonr_elonxeloncutor.DomainMarshallelonrelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.gatelon_elonxeloncutor.GatelonelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.pipelonlinelon_relonsult_sidelon_elonffelonct_elonxeloncutor.PipelonlinelonRelonsultSidelonelonffelonctelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.quality_factor_elonxeloncutor.QualityFactorelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.quelonry_felonaturelon_hydrator_elonxeloncutor.QuelonryFelonaturelonHydratorelonxeloncutor
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.selonlelonctor_elonxeloncutor.SelonlelonctorelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.transport_marshallelonr_elonxeloncutor.TransportMarshallelonrelonxeloncutor

/**
 * A [[MixelonrPipelonlinelonRelonsult]] includelons both thelon uselonr-visiblelon [[PipelonlinelonRelonsult]] and all thelon
 * elonxeloncution delontails possiblelon - intelonrmelondiatelon relonsults, what componelonnts did, elontc.
 */
caselon class MixelonrPipelonlinelonRelonsult[Relonsult](
  qualityFactorRelonsult: Option[QualityFactorelonxeloncutorRelonsult],
  gatelonRelonsult: Option[GatelonelonxeloncutorRelonsult],
  quelonryFelonaturelons: Option[QuelonryFelonaturelonHydratorelonxeloncutor.Relonsult],
  quelonryFelonaturelonsPhaselon2: Option[QuelonryFelonaturelonHydratorelonxeloncutor.Relonsult],
  melonrgelondAsyncQuelonryFelonaturelons: Option[AsyncFelonaturelonMap],
  candidatelonPipelonlinelonRelonsults: Option[CandidatelonPipelonlinelonelonxeloncutorRelonsult],
  delonpelonndelonntCandidatelonPipelonlinelonRelonsults: Option[CandidatelonPipelonlinelonelonxeloncutorRelonsult],
  relonsultSelonlelonctorRelonsults: Option[SelonlelonctorelonxeloncutorRelonsult],
  domainMarshallelonrRelonsults: Option[DomainMarshallelonrelonxeloncutor.Relonsult[HasMarshalling]],
  relonsultSidelonelonffelonctRelonsults: Option[PipelonlinelonRelonsultSidelonelonffelonctelonxeloncutor.Relonsult],
  asyncFelonaturelonHydrationRelonsults: Option[AsyncFelonaturelonMapelonxeloncutorRelonsults],
  transportMarshallelonrRelonsults: Option[TransportMarshallelonrelonxeloncutor.Relonsult[Relonsult]],
  failurelon: Option[PipelonlinelonFailurelon],
  relonsult: Option[Relonsult])
    elonxtelonnds PipelonlinelonRelonsult[Relonsult] {

  ovelonrridelon delonf withFailurelon(failurelon: PipelonlinelonFailurelon): PipelonlinelonRelonsult[Relonsult] =
    copy(failurelon = Somelon(failurelon))

  ovelonrridelon delonf withRelonsult(relonsult: Relonsult): PipelonlinelonRelonsult[Relonsult] = copy(relonsult = Somelon(relonsult))

  /**
   * relonsultSizelon is calculatelond baselond on thelon selonlelonctor relonsults rathelonr than thelon marshallelond relonsults. Thelon
   * structurelon of thelon marshallelond format is unknown, making opelonrating on selonlelonctor relonsults morelon
   * convelonnielonnt. This will implicitly elonxcludelond cursors built during marshalling but cursors don't
   * contributelon to thelon relonsult sizelon anyway.
   */
  ovelonrridelon val relonsultSizelon: Int =
    relonsultSelonlelonctorRelonsults.map(_.selonlelonctelondCandidatelons).map(PipelonlinelonRelonsult.relonsultSizelon).gelontOrelonlselon(0)
}

objelonct MixelonrPipelonlinelonRelonsult {
  delonf elonmpty[A]: MixelonrPipelonlinelonRelonsult[A] = MixelonrPipelonlinelonRelonsult(
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon
  )
}
