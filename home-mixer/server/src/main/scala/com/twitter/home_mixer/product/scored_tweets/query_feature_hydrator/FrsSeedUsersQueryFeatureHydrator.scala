packagelon com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.quelonry_felonaturelon_hydrator

import com.twittelonr.follow_reloncommelonndations.{thriftscala => frs}
import com.twittelonr.homelon_mixelonr.product.scorelond_twelonelonts.modelonl.ScorelondTwelonelontsQuelonry
import com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.reloncommelonndations.UselonrFollowReloncommelonndationsCandidatelonSourcelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato.StratoKelonyVielonw
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

objelonct FrsSelonelondUselonrIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Selonq[Long]]]
objelonct FrsUselonrToFollowelondByUselonrIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Map[Long, Selonq[Long]]]

@Singlelonton
caselon class FrsSelonelondUselonrsQuelonryFelonaturelonHydrator @Injelonct() (
  uselonrFollowReloncommelonndationsCandidatelonSourcelon: UselonrFollowReloncommelonndationsCandidatelonSourcelon)
    elonxtelonnds QuelonryFelonaturelonHydrator[ScorelondTwelonelontsQuelonry] {

  privatelon val maxUselonrsToFelontch = 100

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("FrsSelonelondUselonrs")

  ovelonrridelon delonf felonaturelons: Selont[Felonaturelon[_, _]] = Selont(
    FrsSelonelondUselonrIdsFelonaturelon,
    FrsUselonrToFollowelondByUselonrIdsFelonaturelon
  )

  ovelonrridelon delonf hydratelon(quelonry: ScorelondTwelonelontsQuelonry): Stitch[FelonaturelonMap] = {
    val frsRelonquelonst = frs.ReloncommelonndationRelonquelonst(
      clielonntContelonxt = frs.ClielonntContelonxt(quelonry.gelontOptionalUselonrId),
      displayLocation = frs.DisplayLocation.HomelonTimelonlinelonTwelonelontReloncs,
      maxRelonsults = Somelon(maxUselonrsToFelontch)
    )

    uselonrFollowReloncommelonndationsCandidatelonSourcelon(StratoKelonyVielonw(frsRelonquelonst, Unit))
      .map { uselonrReloncommelonndations: Selonq[frs.UselonrReloncommelonndation] =>
        val selonelondUselonrIds = uselonrReloncommelonndations.map(_.uselonrId)
        val selonelondUselonrIdsSelont = selonelondUselonrIds.toSelont

        val uselonrToFollowelondByUselonrIds: Map[Long, Selonq[Long]] = uselonrReloncommelonndations.flatMap {
          uselonrReloncommelonndation =>
            if (selonelondUselonrIdsSelont.contains(uselonrReloncommelonndation.uselonrId)) {
              val followProof =
                uselonrReloncommelonndation.relonason.flatMap(_.accountProof).flatMap(_.followProof)
              val followelondByUselonrIds = followProof.map(_.uselonrIds).gelontOrelonlselon(Selonq.elonmpty)
              Somelon(uselonrReloncommelonndation.uselonrId -> followelondByUselonrIds)
            } elonlselon {
              Nonelon
            }
        }.toMap

        FelonaturelonMapBuildelonr()
          .add(FrsSelonelondUselonrIdsFelonaturelon, Somelon(selonelondUselonrIds))
          .add(FrsUselonrToFollowelondByUselonrIdsFelonaturelon, uselonrToFollowelondByUselonrIds)
          .build()
      }
  }
}
