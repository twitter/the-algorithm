packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.addrelonssbook

import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.addrelonssbook.AddrelonssBookParams.RelonadFromABV2Only
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.AddrelonssbookClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.modelonls.elondgelonTypelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.modelonls.ReloncordIdelonntifielonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.utils.RelonscuelonWithStatsUtils.relonscuelonWithStats
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.ForwardPhonelonContactsClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ForwardPhonelonBookSourcelon @Injelonct() (
  forwardPhonelonContactsClielonntColumn: ForwardPhonelonContactsClielonntColumn,
  addrelonssBookClielonnt: AddrelonssbookClielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr = NullStatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[HasParams with HasClielonntContelonxt, CandidatelonUselonr] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    ForwardPhonelonBookSourcelon.Idelonntifielonr
  privatelon val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)

  /**
   * Gelonnelonratelon a list of candidatelons for thelon targelont
   */
  ovelonrridelon delonf apply(targelont: HasParams with HasClielonntContelonxt): Stitch[Selonq[CandidatelonUselonr]] = {
    val candidatelonUselonrs: Stitch[Selonq[Long]] = targelont.gelontOptionalUselonrId
      .map { uselonrId =>
        relonscuelonWithStats(
          addrelonssBookClielonnt.gelontUselonrs(
            uselonrId,
            idelonntifielonrs =
              Selonq(ReloncordIdelonntifielonr(uselonrId = Somelon(uselonrId), elonmail = Nonelon, phonelonNumbelonr = Nonelon)),
            batchSizelon = AddrelonssbookClielonnt.AddrelonssBook2BatchSizelon,
            elondgelonTypelon = ForwardPhonelonBookSourcelon.DelonfaultelondgelonTypelon,
            felontchelonrOption =
              if (targelont.params.apply(RelonadFromABV2Only)) Nonelon
              elonlselon Somelon(forwardPhonelonContactsClielonntColumn.felontchelonr),
            quelonryOption = AddrelonssbookClielonnt
              .crelonatelonQuelonryOption(
                elondgelonTypelon = ForwardPhonelonBookSourcelon.DelonfaultelondgelonTypelon,
                isPhonelon = ForwardPhonelonBookSourcelon.IsPhonelon)
          ),
          stats,
          "AddrelonssBookClielonnt"
        )
      }.gelontOrelonlselon(Stitch.Nil)

    candidatelonUselonrs
      .map(
        _.takelon(ForwardPhonelonBookSourcelon.NumPhonelonBookelonntrielons)
          .map(CandidatelonUselonr(_, scorelon = Somelon(CandidatelonUselonr.DelonfaultCandidatelonScorelon))
            .withCandidatelonSourcelon(idelonntifielonr)))
  }
}

objelonct ForwardPhonelonBookSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.ForwardPhonelonBook.toString)
  val NumPhonelonBookelonntrielons: Int = 1000
  val IsPhonelon = truelon
  val DelonfaultelondgelonTypelon: elondgelonTypelon = elondgelonTypelon.Forward
}
