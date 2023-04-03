packagelon com.twittelonr.follow_reloncommelonndations.common.transforms.modify_social_proof

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.GatelondTransform
import com.twittelonr.follow_reloncommelonndations.common.modelonls.CandidatelonUselonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.HasClielonntContelonxt
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class RelonmovelonAccountProofTransform @Injelonct() (statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds GatelondTransform[HasClielonntContelonxt with HasParams, CandidatelonUselonr] {

  privatelon val stats = statsReloncelonivelonr.scopelon(this.gelontClass.gelontSimplelonNamelon)
  privatelon val relonmovelondProofsCountelonr = stats.countelonr("num_relonmovelond_proofs")

  ovelonrridelon delonf transform(
    targelont: HasClielonntContelonxt with HasParams,
    itelonms: Selonq[CandidatelonUselonr]
  ): Stitch[Selonq[CandidatelonUselonr]] =
    Stitch.valuelon(itelonms.map { candidatelon =>
      relonmovelondProofsCountelonr.incr()
      candidatelon.copy(relonason = Nonelon)
    })
}
