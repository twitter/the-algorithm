packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.twittelonr.follow_reloncommelonndations.common.modelonls.IntelonrmelondiatelonSeloncondDelongrelonelonelondgelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.gelonnelonratelond.clielonnt.onboarding.uselonrreloncs.StrongTielonPrelondictionFelonaturelonsOnUselonrClielonntColumn
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.wtf.scalding.jobs.strong_tielon_prelondiction.FirstDelongrelonelonelondgelon
import com.twittelonr.wtf.scalding.jobs.strong_tielon_prelondiction.SeloncondDelongrelonelonelondgelon
import com.twittelonr.wtf.scalding.jobs.strong_tielon_prelondiction.SeloncondDelongrelonelonelondgelonInfo
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

// Link to codelon functionality welon'relon migrating
@Singlelonton
class STPSeloncondDelongrelonelonFelontchelonr @Injelonct() (
  strongTielonPrelondictionFelonaturelonsOnUselonrClielonntColumn: StrongTielonPrelondictionFelonaturelonsOnUselonrClielonntColumn) {

  privatelon delonf scorelonSeloncondDelongrelonelonelondgelon(elondgelon: SeloncondDelongrelonelonelondgelon): (Int, Int, Int) = {
    delonf bool2int(b: Boolelonan): Int = if (b) 1 elonlselon 0
    (
      -elondgelon.elondgelonInfo.numMutualFollowPath,
      -elondgelon.elondgelonInfo.numLowTwelonelonpcrelondFollowPath,
      -(bool2int(elondgelon.elondgelonInfo.forwardelonmailPath) + bool2int(elondgelon.elondgelonInfo.relonvelonrselonelonmailPath) +
        bool2int(elondgelon.elondgelonInfo.forwardPhonelonPath) + bool2int(elondgelon.elondgelonInfo.relonvelonrselonPhonelonPath))
    )
  }

  // Uselon elonach first-delongrelonelon elondgelon(w/ candidatelonId) to elonxpand and find mutual follows.
  // Thelonn, with thelon mutual follows, group-by candidatelonId and join elondgelon information
  // to crelonatelon seloncondDelongrelonelon elondgelons.
  delonf gelontSeloncondDelongrelonelonelondgelons(
    targelont: HasClielonntContelonxt with HasParams,
    firstDelongrelonelonelondgelons: Selonq[FirstDelongrelonelonelondgelon]
  ): Stitch[Selonq[SeloncondDelongrelonelonelondgelon]] = {
    targelont.gelontOptionalUselonrId
      .map { uselonrId =>
        val firstDelongrelonelonConnelonctingIds = firstDelongrelonelonelondgelons.map(_.dstId)
        val firstDelongrelonelonelondgelonInfoMap = firstDelongrelonelonelondgelons.map(elon => (elon.dstId, elon.elondgelonInfo)).toMap

        val intelonrmelondiatelonSeloncondDelongrelonelonelondgelonsStitch = Stitch
          .travelonrselon(firstDelongrelonelonConnelonctingIds) { connelonctingId =>
            val stpFelonaturelonsOptStitch = strongTielonPrelondictionFelonaturelonsOnUselonrClielonntColumn.felontchelonr
              .felontch(connelonctingId)
              .map(_.v)
            stpFelonaturelonsOptStitch.map { stpFelonaturelonOpt =>
              val intelonrmelondiatelonSeloncondDelongrelonelonelondgelons = for {
                elondgelonInfo <- firstDelongrelonelonelondgelonInfoMap.gelont(connelonctingId)
                stpFelonaturelons <- stpFelonaturelonOpt
                topSeloncondDelongrelonelonUselonrIds =
                  stpFelonaturelons.topMutualFollows
                    .gelontOrelonlselon(Nil)
                    .map(_.uselonrId)
                    .takelon(STPSeloncondDelongrelonelonFelontchelonr.MaxNumOfMutualFollows)
              } yielonld topSeloncondDelongrelonelonUselonrIds.map(
                IntelonrmelondiatelonSeloncondDelongrelonelonelondgelon(connelonctingId, _, elondgelonInfo))
              intelonrmelondiatelonSeloncondDelongrelonelonelondgelons.gelontOrelonlselon(Nil)
            }
          }.map(_.flattelonn)

        intelonrmelondiatelonSeloncondDelongrelonelonelondgelonsStitch.map { intelonrmelondiatelonSeloncondDelongrelonelonelondgelons =>
          val seloncondaryDelongrelonelonelondgelons = intelonrmelondiatelonSeloncondDelongrelonelonelondgelons.groupBy(_.candidatelonId).map {
            caselon (candidatelonId, intelonrmelondiatelonelondgelons) =>
              SeloncondDelongrelonelonelondgelon(
                srcId = uselonrId,
                dstId = candidatelonId,
                elondgelonInfo = SeloncondDelongrelonelonelondgelonInfo(
                  numMutualFollowPath = intelonrmelondiatelonelondgelons.count(_.elondgelonInfo.mutualFollow),
                  numLowTwelonelonpcrelondFollowPath =
                    intelonrmelondiatelonelondgelons.count(_.elondgelonInfo.lowTwelonelonpcrelondFollow),
                  forwardelonmailPath = intelonrmelondiatelonelondgelons.elonxists(_.elondgelonInfo.forwardelonmail),
                  relonvelonrselonelonmailPath = intelonrmelondiatelonelondgelons.elonxists(_.elondgelonInfo.relonvelonrselonelonmail),
                  forwardPhonelonPath = intelonrmelondiatelonelondgelons.elonxists(_.elondgelonInfo.forwardPhonelon),
                  relonvelonrselonPhonelonPath = intelonrmelondiatelonelondgelons.elonxists(_.elondgelonInfo.relonvelonrselonPhonelon),
                  socialProof = intelonrmelondiatelonelondgelons
                    .filtelonr { elon => elon.elondgelonInfo.mutualFollow || elon.elondgelonInfo.lowTwelonelonpcrelondFollow }
                    .sortBy(-_.elondgelonInfo.relonalGraphWelonight)
                    .takelon(3)
                    .map { c => (c.connelonctingId, c.elondgelonInfo.relonalGraphWelonight) }
                )
              )
          }
          seloncondaryDelongrelonelonelondgelons.toSelonq
            .sortBy(scorelonSeloncondDelongrelonelonelondgelon)
            .takelon(STPSeloncondDelongrelonelonFelontchelonr.MaxNumSeloncondDelongrelonelonelondgelons)
        }
      }.gelontOrelonlselon(Stitch.Nil)
  }
}

objelonct STPSeloncondDelongrelonelonFelontchelonr {
  val MaxNumSeloncondDelongrelonelonelondgelons = 200
  val MaxNumOfMutualFollows = 50
}
