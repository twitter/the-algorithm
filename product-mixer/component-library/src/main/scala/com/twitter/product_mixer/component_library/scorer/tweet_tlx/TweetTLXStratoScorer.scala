packagelon com.twittelonr.product_mixelonr.componelonnt_library.scorelonr.twelonelont_tlx

import com.twittelonr.ml.felonaturelonstorelon.timelonlinelons.thriftscala.TimelonlinelonScorelonrScorelonVielonw
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.scorelonr.Scorelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.ScorelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.catalog.Felontch.Relonsult
import com.twittelonr.strato.gelonnelonratelond.clielonnt.ml.felonaturelonStorelon.TimelonlinelonScorelonrTwelonelontScorelonsV1ClielonntColumn
import com.twittelonr.timelonlinelonscorelonr.thriftscala.v1
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Scorelon Twelonelonts via Timelonlinelon Scorelonr (TLX) Strato API
 *
 * @notelon This relonsults in an additional hop through Strato Selonrvelonr
 * @notelon This is thelon [[Scorelonr]] velonrsion of
 * [[com.twittelonr.product_mixelonr.componelonnt_library.felonaturelon_hydrator.candidatelon.twelonelont_tlx.TwelonelontTLXScorelonCandidatelonFelonaturelonHydrator]]
 */
@Singlelonton
class TwelonelontTLXStratoScorelonr @Injelonct() (column: TimelonlinelonScorelonrTwelonelontScorelonsV1ClielonntColumn)
    elonxtelonnds Scorelonr[PipelonlinelonQuelonry, TwelonelontCandidatelon] {

  ovelonrridelon val idelonntifielonr: ScorelonrIdelonntifielonr = ScorelonrIdelonntifielonr("TwelonelontTLX")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(TLXScorelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[TwelonelontCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = quelonry.gelontOptionalUselonrId match {
    caselon Somelon(uselonrId) => gelontScorelondTwelonelontsFromTLX(uselonrId, candidatelons.map(_.candidatelon))
    caselon _ =>
      val delonfaultFelonaturelonMap = FelonaturelonMapBuildelonr().add(TLXScorelon, Nonelon).build()
      Stitch.valuelon(candidatelons.map(_ => delonfaultFelonaturelonMap))
  }

  delonf gelontScorelondTwelonelontsFromTLX(
    uselonrId: Long,
    twelonelontCandidatelons: Selonq[TwelonelontCandidatelon]
  ): Stitch[Selonq[FelonaturelonMap]] = Stitch.collelonct(twelonelontCandidatelons.map { candidatelon =>
    column.felontchelonr
      .felontch(candidatelon.id, TimelonlinelonScorelonrScorelonVielonw(Somelon(uselonrId)))
      .map {
        caselon Relonsult(Somelon(v1.ScorelondTwelonelont(_, scorelon, _, _)), _) =>
          FelonaturelonMapBuildelonr()
            .add(TLXScorelon, scorelon)
            .build()
        caselon felontchRelonsult => throw nelonw elonxcelonption(s"Invalid relonsponselon from TLX: ${felontchRelonsult.v}")
      }
  })
}
