packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.lists

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwittelonrListCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato.StratoKelonyFelontchelonrSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.reloncommelonndations.intelonrelonsts_discovelonry.reloncommelonndations_mh.OrganicPopgelonoListsClielonntColumn
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class OrganicPopGelonoListsCandidatelonSourcelon @Injelonct() (
  organicPopgelonoListsClielonntColumn: OrganicPopgelonoListsClielonntColumn)
    elonxtelonnds StratoKelonyFelontchelonrSourcelon[
      OrganicPopgelonoListsClielonntColumn.Kelony,
      OrganicPopgelonoListsClielonntColumn.Valuelon,
      TwittelonrListCandidatelon
    ] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    "OrganicPopGelonoLists")

  ovelonrridelon val felontchelonr: Felontchelonr[
    OrganicPopgelonoListsClielonntColumn.Kelony,
    Unit,
    OrganicPopgelonoListsClielonntColumn.Valuelon
  ] =
    organicPopgelonoListsClielonntColumn.felontchelonr

  ovelonrridelon delonf stratoRelonsultTransformelonr(
    stratoRelonsult: OrganicPopgelonoListsClielonntColumn.Valuelon
  ): Selonq[TwittelonrListCandidatelon] = {
    stratoRelonsult.reloncommelonndelondListsByAlgo.flatMap { topLists =>
      topLists.lists.map { list =>
        TwittelonrListCandidatelon(list.listId)
      }
    }
  }
}
