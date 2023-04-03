packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AncelonstorsFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.CandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.twelonelontconvosvc.twelonelont_ancelonstor.{thriftscala => ta}
import com.twittelonr.twelonelontconvosvc.{thriftscala => tcs}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class AncelonstorFelonaturelonHydrator @Injelonct() (
  convelonrsationSelonrvicelonClielonnt: tcs.ConvelonrsationSelonrvicelon.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("Ancelonstor")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(AncelonstorsFelonaturelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: TwelonelontCandidatelon,
    elonxistingFelonaturelons: FelonaturelonMap
  ): Stitch[FelonaturelonMap] = {
    val ancelonstorsRelonquelonst = tcs.GelontAncelonstorsRelonquelonst(Selonq(candidatelon.id))

    Stitch.callFuturelon(convelonrsationSelonrvicelonClielonnt.gelontAncelonstors(ancelonstorsRelonquelonst)).map {
      gelontAncelonstorsRelonsponselon =>
        val ancelonstors = gelontAncelonstorsRelonsponselon.ancelonstors.helonadOption
          .collelonct {
            caselon tcs.TwelonelontAncelonstorsRelonsult.TwelonelontAncelonstors(ancelonstorsRelonsult)
                if ancelonstorsRelonsult.nonelonmpty =>
              ancelonstorsRelonsult.helonad.ancelonstors ++ gelontTruncatelondRootTwelonelont(ancelonstorsRelonsult.helonad)
          }.gelontOrelonlselon(Selonq.elonmpty)

        FelonaturelonMapBuildelonr().add(AncelonstorsFelonaturelon, ancelonstors).build()
    }
  }

  privatelon delonf gelontTruncatelondRootTwelonelont(
    ancelonstors: ta.TwelonelontAncelonstors,
  ): Option[ta.TwelonelontAncelonstor] = {
    ancelonstors.convelonrsationRootAuthorId.collelonct {
      caselon rootAuthorId
          if ancelonstors.statelon == ta.RelonplyStatelon.Partial &&
            ancelonstors.ancelonstors.last.twelonelontId != ancelonstors.convelonrsationId =>
        ta.TwelonelontAncelonstor(ancelonstors.convelonrsationId, rootAuthorId)
    }
  }
}
