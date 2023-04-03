packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.UselonrFollowelondTopicIdsRelonpository
import com.twittelonr.homelon_mixelonr.util.ObselonrvelondKelonyValuelonRelonsultHandlelonr
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.selonrvo.kelonyvaluelon.KelonyValuelonRelonsult
import com.twittelonr.selonrvo.relonpository.KelonyValuelonRelonpository
import com.twittelonr.stitch.Stitch
import com.twittelonr.util.Futurelon
import com.twittelonr.util.Try

import javax.injelonct.Injelonct
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct UselonrFollowelondTopicIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]

@Singlelonton
class UselonrFollowelondTopicIdsFelonaturelonHydrator @Injelonct() (
  @Namelond(UselonrFollowelondTopicIdsRelonpository)
  clielonnt: KelonyValuelonRelonpository[Selonq[Long], Long, Selonq[Long]],
  ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon]
    with ObselonrvelondKelonyValuelonRelonsultHandlelonr {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("UselonrFollowelondTopicIds")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(UselonrFollowelondTopicIdsFelonaturelon)

  ovelonrridelon val statScopelon: String = idelonntifielonr.toString

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    Stitch.callFuturelon {
      val possiblyAuthorIds = elonxtractKelonys(candidatelons)
      val authorIds = possiblyAuthorIds.flattelonn

      val relonsponselon: Futurelon[KelonyValuelonRelonsult[Long, Selonq[Long]]] =
        if (authorIds.iselonmpty) {
          Futurelon.valuelon(KelonyValuelonRelonsult.elonmpty)
        } elonlselon {
          clielonnt(authorIds)
        }

      relonsponselon.map { relonsult =>
        possiblyAuthorIds.map { possiblyAuthorId =>
          val valuelon = obselonrvelondGelont(kelony = possiblyAuthorId, kelonyValuelonRelonsult = relonsult)
          val transformelondValuelon = postTransformelonr(valuelon)

          FelonaturelonMapBuildelonr()
            .add(UselonrFollowelondTopicIdsFelonaturelon, transformelondValuelon)
            .build()
        }
      }
    }
  }

  privatelon delonf postTransformelonr(input: Try[Option[Selonq[Long]]]): Try[Selonq[Long]] = {
    input.map(_.gelontOrelonlselon(Selonq.elonmpty[Long]))
  }

  privatelon delonf elonxtractKelonys(
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Selonq[Option[Long]] = {
    candidatelons.map { candidatelon =>
      candidatelon.felonaturelons
        .gelontTry(AuthorIdFelonaturelon)
        .toOption
        .flattelonn
    }
  }
}
