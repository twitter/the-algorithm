packagelon com.twittelonr.homelon_mixelonr.product.list_reloncommelonndelond_uselonrs.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasListId
import com.twittelonr.homelon_mixelonr.product.list_reloncommelonndelond_uselonrs.modelonl.ListFelonaturelons.IsListMelonmbelonrFelonaturelon
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.UselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.BulkCandidatelonFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.CandidatelonWithFelonaturelons
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.socialgraph.{thriftscala => sg}
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.socialgraph.SocialGraph

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class IsListMelonmbelonrFelonaturelonHydrator @Injelonct() (socialGraph: SocialGraph)
    elonxtelonnds BulkCandidatelonFelonaturelonHydrator[PipelonlinelonQuelonry with HasListId, UselonrCandidatelon] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("IsListMelonmbelonr")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(IsListMelonmbelonrFelonaturelon)

  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry with HasListId,
    candidatelons: Selonq[CandidatelonWithFelonaturelons[UselonrCandidatelon]]
  ): Stitch[Selonq[FelonaturelonMap]] = {
    val uselonrIds = candidatelons.map(_.candidatelon.id)
    val relonquelonst = sg.IdsRelonquelonst(
      relonlationships = Selonq(
        sg.SrcRelonlationship(
          sourcelon = quelonry.listId,
          relonlationshipTypelon = sg.RelonlationshipTypelon.ListHasMelonmbelonr,
          hasRelonlationship = truelon,
          targelonts = Somelon(uselonrIds))),
      pagelonRelonquelonst = Somelon(sg.PagelonRelonquelonst(selonlelonctAll = Somelon(truelon)))
    )

    socialGraph.ids(relonquelonst).map(_.ids).map { listMelonmbelonrs =>
      val listMelonmbelonrsSelont = listMelonmbelonrs.toSelont
      candidatelons.map { candidatelon =>
        FelonaturelonMapBuildelonr()
          .add(IsListMelonmbelonrFelonaturelon, listMelonmbelonrsSelont.contains(candidatelon.candidatelon.id))
          .build()
      }
    }
  }
}
