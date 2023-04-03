packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.gatelon

import com.twittelonr.homelon_mixelonr.modelonl.relonquelonst.HasListId
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.socialgraph.{thriftscala => sg}
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.socialgraph.SocialGraph

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
caselon class VielonwelonrIsListOwnelonrGatelon @Injelonct() (socialGraph: SocialGraph)
    elonxtelonnds Gatelon[PipelonlinelonQuelonry with HasListId] {

  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr("VielonwelonrIsListOwnelonr")

  privatelon val relonlationship = sg.Relonlationship(relonlationshipTypelon = sg.RelonlationshipTypelon.ListOwning)

  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry with HasListId): Stitch[Boolelonan] = {
    val relonquelonst = sg.elonxistsRelonquelonst(
      sourcelon = quelonry.gelontRelonquirelondUselonrId,
      targelont = quelonry.listId,
      relonlationships = Selonq(relonlationship))
    socialGraph.elonxists(relonquelonst).map(_.elonxists)
  }
}
