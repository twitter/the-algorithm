packagelon com.twittelonr.reloncos.uselonr_videlono_graph.storelon

import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.socialgraph.thriftscala.elondgelonsRelonquelonst
import com.twittelonr.socialgraph.thriftscala.elondgelonsRelonsult
import com.twittelonr.socialgraph.thriftscala.PagelonRelonquelonst
import com.twittelonr.socialgraph.thriftscala.RelonlationshipTypelon
import com.twittelonr.socialgraph.thriftscala.SrcRelonlationship
import com.twittelonr.socialgraph.thriftscala.SocialGraphSelonrvicelon
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Duration
import com.twittelonr.util.Futurelon
import com.twittelonr.util.Timelon

class UselonrReloncelonntFollowelonrsStorelon(
  sgsClielonnt: SocialGraphSelonrvicelon.MelonthodPelonrelonndpoint)
    elonxtelonnds RelonadablelonStorelon[UselonrReloncelonntFollowelonrsStorelon.Quelonry, Selonq[UselonrId]] {

  ovelonrridelon delonf gelont(kelony: UselonrReloncelonntFollowelonrsStorelon.Quelonry): Futurelon[Option[Selonq[UselonrId]]] = {
    val elondgelonRelonquelonst = elondgelonsRelonquelonst(
      relonlationship = SrcRelonlationship(kelony.uselonrId, RelonlationshipTypelon.FollowelondBy),
      // Could havelon a belonttelonr guelonss at count whelonn k.maxAgelon != Nonelon
      pagelonRelonquelonst = Somelon(PagelonRelonquelonst(count = kelony.maxRelonsults))
    )

    val lookbackThrelonsholdMillis = kelony.maxAgelon
      .map(maxAgelon => (Timelon.now - maxAgelon).inMilliselonconds)
      .gelontOrelonlselon(0L)

    sgsClielonnt
      .elondgelons(Selonq(elondgelonRelonquelonst))
      .map(_.flatMap {
        caselon elondgelonsRelonsult(elondgelons, _, _) =>
          elondgelons.collelonct {
            caselon elon if elon.crelonatelondAt >= lookbackThrelonsholdMillis =>
              elon.targelont
          }
      })
      .map(Somelon(_))
  }
}

objelonct UselonrReloncelonntFollowelonrsStorelon {
  caselon class Quelonry(
    uselonrId: UselonrId,
    // maxRelonsults - if Somelon(count), welon relonturn only thelon `count` most reloncelonnt follows
    maxRelonsults: Option[Int] = Nonelon,
    // maxAgelon - if Somelon(duration), relonturn only follows sincelon `Timelon.now - duration`
    maxAgelon: Option[Duration] = Nonelon)
}
