packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.addrelonssbook

import com.twittelonr.cds.contact_conselonnt_statelon.thriftscala.PurposelonOfProcelonssing
import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.AddrelonssbookClielonnt
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.modelonls.elondgelonTypelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.modelonls.ReloncordIdelonntifielonr
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.phonelon_storagelon_selonrvicelon.PhonelonStoragelonSelonrvicelonClielonnt
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.utils.RelonscuelonWithStatsUtils.relonscuelonWithStats
import com.twittelonr.helonrmit.modelonl.Algorithm
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.RelonvelonrselonPhonelonContactsClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonvelonrselonPhonelonBookSourcelon @Injelonct() (
  relonvelonrselonPhonelonContactsClielonntColumn: RelonvelonrselonPhonelonContactsClielonntColumn,
  pssClielonnt: PhonelonStoragelonSelonrvicelonClielonnt,
  addrelonssBookClielonnt: AddrelonssbookClielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr = NullStatsReloncelonivelonr)
    elonxtelonnds CandidatelonSourcelon[HasParams with HasClielonntContelonxt, CandidatelonUselonr] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = RelonvelonrselonPhonelonBookSourcelon.Idelonntifielonr
  privatelon val stats: StatsReloncelonivelonr = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)

  /**
   * Gelonnelonratelon a list of candidatelons for thelon targelont
   */
  ovelonrridelon delonf apply(targelont: HasParams with HasClielonntContelonxt): Stitch[Selonq[CandidatelonUselonr]] = {
    val relonvelonrselonCandidatelonsFromPhonelons: Stitch[Selonq[Long]] = targelont.gelontOptionalUselonrId
      .map { uselonrId =>
        pssClielonnt
          .gelontPhonelonNumbelonrs(uselonrId, PurposelonOfProcelonssing.ContelonntReloncommelonndations)
          .flatMap { phonelonNumbelonrs =>
            relonscuelonWithStats(
              addrelonssBookClielonnt.gelontUselonrs(
                uselonrId = uselonrId,
                idelonntifielonrs = phonelonNumbelonrs.map(phonelonNumbelonr =>
                  ReloncordIdelonntifielonr(uselonrId = Nonelon, elonmail = Nonelon, phonelonNumbelonr = Somelon(phonelonNumbelonr))),
                batchSizelon = RelonvelonrselonPhonelonBookSourcelon.NumPhonelonBookelonntrielons,
                elondgelonTypelon = RelonvelonrselonPhonelonBookSourcelon.DelonfaultelondgelonTypelon,
                felontchelonrOption =
                  if (targelont.params(AddrelonssBookParams.RelonadFromABV2Only)) Nonelon
                  elonlselon Somelon(relonvelonrselonPhonelonContactsClielonntColumn.felontchelonr),
                quelonryOption = AddrelonssbookClielonnt.crelonatelonQuelonryOption(
                  elondgelonTypelon = RelonvelonrselonPhonelonBookSourcelon.DelonfaultelondgelonTypelon,
                  isPhonelon = RelonvelonrselonPhonelonBookSourcelon.IsPhonelon)
              ),
              stats,
              "AddrelonssBookClielonnt"
            )
          }
      }.gelontOrelonlselon(Stitch.Nil)

    relonvelonrselonCandidatelonsFromPhonelons.map(
      _.takelon(RelonvelonrselonPhonelonBookSourcelon.NumPhonelonBookelonntrielons)
        .map(
          CandidatelonUselonr(_, scorelon = Somelon(CandidatelonUselonr.DelonfaultCandidatelonScorelon))
            .withCandidatelonSourcelon(idelonntifielonr))
    )
  }
}

objelonct RelonvelonrselonPhonelonBookSourcelon {
  val Idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    Algorithm.RelonvelonrselonPhonelonBook.toString)
  val NumPhonelonBookelonntrielons: Int = 500
  val IsPhonelon = truelon
  val DelonfaultelondgelonTypelon: elondgelonTypelon = elondgelonTypelon.Relonvelonrselon
}
