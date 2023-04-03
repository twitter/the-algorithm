packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.candidatelon_sourcelon

import com.twittelonr.helonrmit.candidatelon.{thriftscala => t}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.gelonnelonratelond.clielonnt.reloncommelonndations.similarity.SimilarUselonrsBySimsOnUselonrClielonntColumn

import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SimilarityBaselondUselonrsCandidatelonSourcelon @Injelonct() (
  similarUselonrsBySimsOnUselonrClielonntColumn: SimilarUselonrsBySimsOnUselonrClielonntColumn)
    elonxtelonnds CandidatelonSourcelon[Selonq[Long], t.Candidatelon] {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr =
    CandidatelonSourcelonIdelonntifielonr("SimilarityBaselondUselonrs")

  privatelon val felontchelonr: Felontchelonr[Long, Unit, t.Candidatelons] =
    similarUselonrsBySimsOnUselonrClielonntColumn.felontchelonr

  ovelonrridelon delonf apply(relonquelonst: Selonq[Long]): Stitch[Selonq[t.Candidatelon]] = {
    Stitch
      .collelonct {
        relonquelonst.map { uselonrId =>
          felontchelonr.felontch(uselonrId, Unit).map { relonsult =>
            relonsult.v.map(_.candidatelons).gelontOrelonlselon(Selonq.elonmpty)
          }
        }
      }.map(_.flattelonn)
  }
}
