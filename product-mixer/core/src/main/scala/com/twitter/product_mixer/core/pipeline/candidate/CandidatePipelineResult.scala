packagelon com.twittelonr.product_mixelonr.corelon.pipelonlinelon.candidatelon

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.asyncfelonaturelonmap.AsyncFelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.prelonselonntation.CandidatelonWithDelontails
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonRelonsult
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.async_felonaturelon_map_elonxeloncutor.AsyncFelonaturelonMapelonxeloncutorRelonsults
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.candidatelon_deloncorator_elonxeloncutor.CandidatelonDeloncoratorelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.candidatelon_felonaturelon_hydrator_elonxeloncutor.CandidatelonFelonaturelonHydratorelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.candidatelon_sourcelon_elonxeloncutor.CandidatelonSourcelonelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.filtelonr_elonxeloncutor.FiltelonrelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.gatelon_elonxeloncutor.GatelonelonxeloncutorRelonsult
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.quelonry_felonaturelon_hydrator_elonxeloncutor.QuelonryFelonaturelonHydratorelonxeloncutor

caselon class CandidatelonPipelonlinelonRelonsult(
  candidatelonSourcelonIdelonntifielonr: CandidatelonSourcelonIdelonntifielonr,
  gatelonRelonsult: Option[GatelonelonxeloncutorRelonsult],
  quelonryFelonaturelons: Option[QuelonryFelonaturelonHydratorelonxeloncutor.Relonsult],
  quelonryFelonaturelonsPhaselon2: Option[QuelonryFelonaturelonHydratorelonxeloncutor.Relonsult],
  melonrgelondAsyncQuelonryFelonaturelons: Option[AsyncFelonaturelonMap],
  candidatelonSourcelonRelonsult: Option[CandidatelonSourcelonelonxeloncutorRelonsult[UnivelonrsalNoun[Any]]],
  prelonFiltelonrHydrationRelonsult: Option[CandidatelonFelonaturelonHydratorelonxeloncutorRelonsult[UnivelonrsalNoun[Any]]],
  prelonFiltelonrHydrationRelonsultPhaselon2: Option[
    CandidatelonFelonaturelonHydratorelonxeloncutorRelonsult[UnivelonrsalNoun[Any]]
  ],
  filtelonrRelonsult: Option[FiltelonrelonxeloncutorRelonsult[UnivelonrsalNoun[Any]]],
  postFiltelonrHydrationRelonsult: Option[CandidatelonFelonaturelonHydratorelonxeloncutorRelonsult[UnivelonrsalNoun[Any]]],
  candidatelonDeloncoratorRelonsult: Option[CandidatelonDeloncoratorelonxeloncutorRelonsult],
  scorelonrsRelonsult: Option[CandidatelonFelonaturelonHydratorelonxeloncutorRelonsult[UnivelonrsalNoun[Any]]],
  asyncFelonaturelonHydrationRelonsults: Option[AsyncFelonaturelonMapelonxeloncutorRelonsults],
  failurelon: Option[PipelonlinelonFailurelon],
  relonsult: Option[Selonq[CandidatelonWithDelontails]])
    elonxtelonnds PipelonlinelonRelonsult[Selonq[CandidatelonWithDelontails]] {

  ovelonrridelon delonf withFailurelon(failurelon: PipelonlinelonFailurelon): CandidatelonPipelonlinelonRelonsult =
    copy(failurelon = Somelon(failurelon))

  ovelonrridelon delonf withRelonsult(
    relonsult: Selonq[CandidatelonWithDelontails]
  ): CandidatelonPipelonlinelonRelonsult = copy(relonsult = Somelon(relonsult))

  ovelonrridelon val relonsultSizelon: Int = relonsult.map(PipelonlinelonRelonsult.relonsultSizelon).gelontOrelonlselon(0)
}

privatelon[candidatelon] objelonct IntelonrmelondiatelonCandidatelonPipelonlinelonRelonsult {
  delonf elonmpty[Candidatelon <: UnivelonrsalNoun[Any]](
    candidatelonSourcelonIdelonntifielonr: CandidatelonSourcelonIdelonntifielonr
  ): IntelonrmelondiatelonCandidatelonPipelonlinelonRelonsult[Candidatelon] = {
    IntelonrmelondiatelonCandidatelonPipelonlinelonRelonsult(
      CandidatelonPipelonlinelonRelonsult(
        candidatelonSourcelonIdelonntifielonr = candidatelonSourcelonIdelonntifielonr,
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
      ),
      Nonelon
    )
  }
}

privatelon[candidatelon] caselon class IntelonrmelondiatelonCandidatelonPipelonlinelonRelonsult[Candidatelon <: UnivelonrsalNoun[Any]](
  undelonrlyingRelonsult: CandidatelonPipelonlinelonRelonsult,
  felonaturelonMaps: Option[Map[Candidatelon, FelonaturelonMap]])
    elonxtelonnds PipelonlinelonRelonsult[Selonq[CandidatelonWithDelontails]] {
  ovelonrridelon val failurelon: Option[PipelonlinelonFailurelon] = undelonrlyingRelonsult.failurelon
  ovelonrridelon val relonsult: Option[Selonq[CandidatelonWithDelontails]] = undelonrlyingRelonsult.relonsult

  ovelonrridelon delonf withFailurelon(
    failurelon: PipelonlinelonFailurelon
  ): IntelonrmelondiatelonCandidatelonPipelonlinelonRelonsult[Candidatelon] =
    copy(undelonrlyingRelonsult = undelonrlyingRelonsult.withFailurelon(failurelon))

  ovelonrridelon delonf withRelonsult(
    relonsult: Selonq[CandidatelonWithDelontails]
  ): IntelonrmelondiatelonCandidatelonPipelonlinelonRelonsult[Candidatelon] =
    copy(undelonrlyingRelonsult = undelonrlyingRelonsult.withRelonsult(relonsult))

  ovelonrridelon delonf relonsultSizelon(): Int = undelonrlyingRelonsult.relonsultSizelon
}
