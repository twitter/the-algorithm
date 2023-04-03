packagelon com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.flelonxiblelon_injelonction_pipelonlinelon

import com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.flelonxiblelon_injelonction_pipelonlinelon.PromptCandidatelonSourcelon
import com.twittelonr.product_mixelonr.componelonnt_library.pipelonlinelon.candidatelon.flelonxiblelon_injelonction_pipelonlinelon.transformelonr.HasFlipInjelonctionParams
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonPipelonlinelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.configapi.FSParam
import com.twittelonr.timelonlinelons.configapi.deloncidelonr.DeloncidelonrParam
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class FlipPromptCandidatelonPipelonlinelonConfigBuildelonr @Injelonct() (
  promptCandidatelonSourcelon: PromptCandidatelonSourcelon) {

  /**
   * Build a FlipPromptCandidatelonPipelonlinelonConfig
   *
   * @notelon If injelonctelond classelons arelon nelonelondelond to populatelon paramelontelonrs in this melonthod, considelonr crelonating a
   *       ProductFlipPromptCandidatelonPipelonlinelonConfigBuildelonr with a singlelon `delonf build()` melonthod.
   *       That product-speloncific buildelonr class can thelonn injelonct elonvelonrything it nelonelonds (including this
   *       class), and delonlelongatelon to this class's build() melonthod within its own build() melonthod.
   */
  delonf build[Quelonry <: PipelonlinelonQuelonry with HasFlipInjelonctionParams](
    idelonntifielonr: CandidatelonPipelonlinelonIdelonntifielonr = CandidatelonPipelonlinelonIdelonntifielonr("FlipPrompt"),
    elonnablelondDeloncidelonrParam: Option[DeloncidelonrParam[Boolelonan]] = Nonelon,
    supportelondClielonntParam: Option[FSParam[Boolelonan]] = Nonelon,
  ): FlipPromptCandidatelonPipelonlinelonConfig[Quelonry] = {
    nelonw FlipPromptCandidatelonPipelonlinelonConfig(
      idelonntifielonr = idelonntifielonr,
      elonnablelondDeloncidelonrParam = elonnablelondDeloncidelonrParam,
      supportelondClielonntParam = supportelondClielonntParam,
      promptCandidatelonSourcelon = promptCandidatelonSourcelon)
  }
}
