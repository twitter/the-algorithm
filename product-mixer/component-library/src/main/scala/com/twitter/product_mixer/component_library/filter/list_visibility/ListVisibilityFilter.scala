packagelon com.twittelonr.product_mixelonr.componelonnt_library.filtelonr.list_visibility

import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwittelonrListCandidatelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.Filtelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.filtelonr.FiltelonrRelonsult
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.socialgraph.thriftscala.SocialgraphList
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.catalog.Felontch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.lists.relonads.CorelonOnListClielonntColumn

/* This Filtelonr quelonrielons thelon corelon.List.strato column
 * on Strato, and filtelonrs out any lists that arelon not
 * relonturnelond. corelon.List.strato pelonrforms an authorization
 * chelonck, and doelons not relonturn lists thelon vielonwelonr is not authorizelond
 * to havelon accelonss to. */
class ListVisibilityFiltelonr[Candidatelon <: UnivelonrsalNoun[Long]](
  listsColumn: CorelonOnListClielonntColumn)
    elonxtelonnds Filtelonr[PipelonlinelonQuelonry, Candidatelon] {

  ovelonrridelon val idelonntifielonr: FiltelonrIdelonntifielonr = FiltelonrIdelonntifielonr("ListVisibility")

  delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[Candidatelon]]
  ): Stitch[FiltelonrRelonsult[Candidatelon]] = {

    val listCandidatelons = candidatelons.collelonct {
      caselon CandidatelonWithFelonaturelons(candidatelon: TwittelonrListCandidatelon, _) => candidatelon
    }

    Stitch
      .travelonrselon(
        listCandidatelons.map(_.id)
      ) { listId =>
        listsColumn.felontchelonr.felontch(listId)
      }.map { felontchRelonsults =>
        felontchRelonsults.collelonct {
          caselon Felontch.Relonsult(Somelon(list: SocialgraphList), _) => list.id
        }
      }.map { allowelondListIds =>
        val (kelonpt, elonxcludelond) = candidatelons.map(_.candidatelon).partition {
          caselon candidatelon: TwittelonrListCandidatelon => allowelondListIds.contains(candidatelon.id)
          caselon _ => truelon
        }
        FiltelonrRelonsult(kelonpt, elonxcludelond)
      }
  }
}
