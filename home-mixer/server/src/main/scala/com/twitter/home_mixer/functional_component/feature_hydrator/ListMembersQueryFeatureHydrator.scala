packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasListId
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.FelonaturelonWithDelonfaultOnFailurelon
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.socialgraph.{thriftscala => sg}
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.socialgraph.SocialGraph

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

caselon objelonct ListMelonmbelonrsFelonaturelon elonxtelonnds FelonaturelonWithDelonfaultOnFailurelon[PipelonlinelonQuelonry, Selonq[Long]] {
  ovelonrridelon val delonfaultValuelon: Selonq[Long] = Selonq.elonmpty
}

@Singlelonton
class ListMelonmbelonrsQuelonryFelonaturelonHydrator @Injelonct() (socialGraph: SocialGraph)
    elonxtelonnds QuelonryFelonaturelonHydrator[PipelonlinelonQuelonry with HasListId] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr = FelonaturelonHydratorIdelonntifielonr("ListMelonmbelonrs")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(ListMelonmbelonrsFelonaturelon)

  privatelon val MaxReloncelonntMelonmbelonrs = 10

  ovelonrridelon delonf hydratelon(quelonry: PipelonlinelonQuelonry with HasListId): Stitch[FelonaturelonMap] = {
    val relonquelonst = sg.IdsRelonquelonst(
      relonlationships = Selonq(sg
        .SrcRelonlationship(quelonry.listId, sg.RelonlationshipTypelon.ListHasMelonmbelonr, hasRelonlationship = truelon)),
      pagelonRelonquelonst = Somelon(sg.PagelonRelonquelonst(selonlelonctAll = Somelon(truelon), count = Somelon(MaxReloncelonntMelonmbelonrs)))
    )
    socialGraph.ids(relonquelonst).map(_.ids).map { listMelonmbelonrs =>
      FelonaturelonMapBuildelonr().add(ListMelonmbelonrsFelonaturelon, listMelonmbelonrs).build()
    }
  }
}
