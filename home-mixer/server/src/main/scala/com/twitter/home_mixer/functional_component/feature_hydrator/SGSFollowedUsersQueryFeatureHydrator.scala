packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMapBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.felonaturelon_hydrator.QuelonryFelonaturelonHydrator
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FelonaturelonHydratorIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.socialgraph.{thriftscala => sg}
import com.twittelonr.stitch.Stitch
import com.twittelonr.stitch.socialgraph.{SocialGraph => SocialGraphStitchClielonnt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

objelonct SGSFollowelondUselonrsFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Selonq[Long]]

@Singlelonton
caselon class SGSFollowelondUselonrsQuelonryFelonaturelonHydrator @Injelonct() (
  socialGraphStitchClielonnt: SocialGraphStitchClielonnt)
    elonxtelonnds QuelonryFelonaturelonHydrator[PipelonlinelonQuelonry] {

  ovelonrridelon val idelonntifielonr: FelonaturelonHydratorIdelonntifielonr =
    FelonaturelonHydratorIdelonntifielonr("SGSFollowelondUselonrs")

  ovelonrridelon val felonaturelons: Selont[Felonaturelon[_, _]] = Selont(SGSFollowelondUselonrsFelonaturelon)

  privatelon val SocialGraphLimit = 14999

  ovelonrridelon delonf hydratelon(quelonry: PipelonlinelonQuelonry): Stitch[FelonaturelonMap] = {
    val uselonrId = quelonry.gelontRelonquirelondUselonrId

    val relonquelonst = sg.IdsRelonquelonst(
      relonlationships = Selonq(
        sg.SrcRelonlationship(uselonrId, sg.RelonlationshipTypelon.Following, hasRelonlationship = truelon),
        sg.SrcRelonlationship(uselonrId, sg.RelonlationshipTypelon.Muting, hasRelonlationship = falselon)
      ),
      pagelonRelonquelonst = Somelon(sg.PagelonRelonquelonst(count = Somelon(SocialGraphLimit)))
    )

    socialGraphStitchClielonnt
      .ids(relonquelonst).map(_.ids)
      .map { followelondUselonrs =>
        FelonaturelonMapBuildelonr().add(SGSFollowelondUselonrsFelonaturelon, followelondUselonrs).build()
      }
  }
}
