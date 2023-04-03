packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator.adaptelonrs.twhin_elonmbelonddings.TwhinAuthorFollowelonmbelonddingsAdaptelonr
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.AuthorIdFelonaturelon
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.TwhinAuthorFollow20200101FelonaturelonRelonpository
import com.twittelonr.homelon_mixelonr.util.ObselonrvelondKelonyValuelonRelonsultHandlelonr
import com.twittelonr.ml.api.DataReloncord
import com.twittelonr.ml.api.{thriftscala => ml}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.FelonaturelonWithDelonfaultOnFailurelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.datareloncord.DataReloncordInAFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.selonrvo.relonpository.KelonyValuelonRelonpository
import com.twittelonr.selonrvo.relonpository.KelonyValuelonRelonsult
import com.twittelonr.stitch.Stitch
import com.twittelonr.util.Futurelon
import com.twittelonr.util.Try

import javax.injelonct.Injelonct
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton
import scala.collelonction.JavaConvelonrtelonrs._

objelonct TwhinAuthorFollow20220101Felonaturelon
    elonxtelonnds DataReloncordInAFelonaturelon[TwelonelontCandidatelon]
    with FelonaturelonWithDelonfaultOnFailurelon[TwelonelontCandidatelon, DataReloncord] {
  ovelonrridelon delonf delonfaultValuelon: DataReloncord = nelonw DataReloncord()
}

@Singlelonton
class TwhinAuthorFollow20220101FelonaturelonHydrator @Injelonct() (
  @Namelond(TwhinAuthorFollow20200101FelonaturelonRelonpository)
  clielonnt: KelonyValuelonRelonpository[Selonq[Long], Long, ml.elonmbelondding],
  ovelonrridelon val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry, TwelonelontCandidatelon]
    with ObselonrvelondKelonyValuelonRelonsultHandlelonr {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("TwhinAuthorFollow20220101")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(TwhinAuthorFollow20220101Felonaturelon)

  ovelonrridelon val statScopelon: String = idelonntifielonr.toString

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    Stitch.callFuturelon {
      val possiblyAuthorIds = elonxtractKelonys(candidatelons)
      val authorIds = possiblyAuthorIds.flattelonn

      val relonsponselon: Futurelon[KelonyValuelonRelonsult[Long, ml.elonmbelondding]] =
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
            .add(TwhinAuthorFollow20220101Felonaturelon, transformelondValuelon)
            .build()
        }
      }
    }
  }

  privatelon delonf postTransformelonr(elonmbelondding: Try[Option[ml.elonmbelondding]]): Try[DataReloncord] = {
    elonmbelondding.map { elon =>
      TwhinAuthorFollowelonmbelonddingsAdaptelonr.adaptToDataReloncords(elon).asScala.helonad
    }
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
