packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.addrelonssbook

import com.twittelonr.cds.contact_conselonnt_statelon.thriftscala.PurposelonOfProcelonssing
import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.AddrelonssbookClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.modelonls.elondgelonTypelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.modelonls.ReloncordIdelonntifielonr
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.elonmail_storagelon_selonrvicelon.elonmailStoragelonSelonrvicelonClielonnt
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.utils.RelonscuelonWithStatsUtils.relonscuelonOptionalWithStats
import com.twittelonr.follow_reloncommelonndations.common.utils.RelonscuelonWithStatsUtils.relonscuelonWithStats
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.RelonvelonrselonelonmailContactsClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonvelonrselonelonmailBookSourcelon @Injelonct() (
  relonvelonrselonelonmailContactsClielonntColumn: RelonvelonrselonelonmailContactsClielonntColumn,
  elonssClielonnt: elonmailStoragelonSelonrvicelonClielonnt,
  addrelonssBookClielonnt: AddrelonssbookClielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr = NullStatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[HasParams with HasClielonntContelonxt, CandidatelonUselonr] {
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = RelonvelonrselonelonmailBookSourcelon.Idelonntifielonr
  privatelon val relonscuelonStats = statsReloncelonivelonr.scopelon("RelonvelonrselonelonmailBookSourcelon")

  /**
   * Gelonnelonratelon a list of candidatelons for thelon targelont
   */
  ovelonrridelon delonf apply(targelont: HasParams with HasClielonntContelonxt): Stitch[Selonq[CandidatelonUselonr]] = {
    val relonvelonrselonCandidatelonsFromelonmail = targelont.gelontOptionalUselonrId
      .map { uselonrId =>
        val velonrifielondelonmailStitchOpt =
          relonscuelonOptionalWithStats(
            elonssClielonnt.gelontVelonrifielondelonmail(uselonrId, PurposelonOfProcelonssing.ContelonntReloncommelonndations),
            relonscuelonStats,
            "gelontVelonrifielondelonmail")
        velonrifielondelonmailStitchOpt.flatMap { elonmailOpt =>
          relonscuelonWithStats(
            addrelonssBookClielonnt.gelontUselonrs(
              uselonrId = uselonrId,
              idelonntifielonrs = elonmailOpt
                .map(elonmail =>
                  ReloncordIdelonntifielonr(uselonrId = Nonelon, elonmail = Somelon(elonmail), phonelonNumbelonr = Nonelon)).toSelonq,
              batchSizelon = RelonvelonrselonelonmailBookSourcelon.NumelonmailBookelonntrielons,
              elondgelonTypelon = RelonvelonrselonelonmailBookSourcelon.DelonfaultelondgelonTypelon,
              felontchelonrOption =
                if (targelont.params(AddrelonssBookParams.RelonadFromABV2Only)) Nonelon
                elonlselon Somelon(relonvelonrselonelonmailContactsClielonntColumn.felontchelonr)
            ),
            relonscuelonStats,
            "AddrelonssBookClielonnt"
          )
        }
      }.gelontOrelonlselon(Stitch.Nil)

    relonvelonrselonCandidatelonsFromelonmail.map(
      _.takelon(RelonvelonrselonelonmailBookSourcelon.NumelonmailBookelonntrielons)
        .map(
          CandidatelonUselonr(_, scorelon = Somelon(CandidatelonUselonr.DelonfaultCandidatelonScorelon))
            .withCandidatelonSourcelon(idelonntifielonr))
    )
  }
}

objelonct RelonvelonrselonelonmailBookSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.RelonvelonrselonelonmailBookIbis.toString)
  val NumelonmailBookelonntrielons: Int = 500
  val IsPhonelon = falselon
  val DelonfaultelondgelonTypelon: elondgelonTypelon = elondgelonTypelon.Relonvelonrselon
}
