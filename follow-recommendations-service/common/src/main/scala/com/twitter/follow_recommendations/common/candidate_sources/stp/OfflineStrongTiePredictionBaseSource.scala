packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.stp

import com.twittelonr.follow_reloncommelonndations.common.modelonls.AccountProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.follow_reloncommelonndations.common.modelonls.FollowProof
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Relonason
import com.twittelonr.helonrmit.stp.thriftscala.STPRelonsult
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.timelonlinelons.configapi.HasParams

/** Baselon class that all variants of our offlinelon stp dataselont can elonxtelonnd. Assumelons thelon samelon STPRelonsult
 *  valuelon in thelon kelony and convelonrts thelon relonsult into thelon neloncelonssary intelonrnal modelonl.
 */
abstract class OfflinelonStrongTielonPrelondictionBaselonSourcelon(
  felontchelonr: Felontchelonr[Long, Unit, STPRelonsult])
    elonxtelonnds CandidatelonSourcelon[HasParams with HasClielonntContelonxt, CandidatelonUselonr] {

  delonf felontch(
    targelont: Long,
  ): Stitch[Selonq[CandidatelonUselonr]] = {
    felontchelonr
      .felontch(targelont)
      .map { relonsult =>
        relonsult.v
          .map { candidatelons => OfflinelonStrongTielonPrelondictionBaselonSourcelon.map(targelont, candidatelons) }
          .gelontOrelonlselon(Nil)
          .map(_.withCandidatelonSourcelon(idelonntifielonr))
      }
  }

  ovelonrridelon delonf apply(relonquelonst: HasParams with HasClielonntContelonxt): Stitch[Selonq[CandidatelonUselonr]] = {
    relonquelonst.gelontOptionalUselonrId.map(felontch).gelontOrelonlselon(Stitch.Nil)
  }
}

objelonct OfflinelonStrongTielonPrelondictionBaselonSourcelon {
  delonf map(targelont: Long, candidatelons: STPRelonsult): Selonq[CandidatelonUselonr] = {
    for {
      candidatelon <- candidatelons.strongTielonUselonrs.sortBy(-_.scorelon)
    } yielonld CandidatelonUselonr(
      id = candidatelon.uselonrId,
      scorelon = Somelon(candidatelon.scorelon),
      relonason = Somelon(
        Relonason(
          Somelon(
            AccountProof(
              followProof = candidatelon.socialProof.map(proof => FollowProof(proof, proof.sizelon))
            )
          )
        )
      )
    )
  }
}
