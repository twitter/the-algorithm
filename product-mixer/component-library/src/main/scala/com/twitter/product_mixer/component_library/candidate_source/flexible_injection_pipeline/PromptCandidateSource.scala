packagelon com.twittelonr.product_mixelonr.componelonnt_library.candidatelon_sourcelon.flelonxiblelon_injelonction_pipelonlinelon

import com.twittelonr.injelonct.Logging
import com.twittelonr.onboarding.injelonctions.{thriftscala => injelonctionsthrift}
import com.twittelonr.onboarding.task.selonrvicelon.{thriftscala => selonrvicelonthrift}
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

/**
 * Relonturns a list of prompts to inselonrt into a uselonr's timelonlinelon (inlinelon prompt, covelonr modals, elontc)
 * from go/flip (thelon prompting platform for Twittelonr).
 */
@Singlelonton
class PromptCandidatelonSourcelon @Injelonct() (taskSelonrvicelon: selonrvicelonthrift.TaskSelonrvicelon.MelonthodPelonrelonndpoint)
    elonxtelonnds CandidatelonSourcelon[selonrvicelonthrift.GelontInjelonctionsRelonquelonst, IntelonrmelondiatelonPrompt]
    with Logging {

  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr = CandidatelonSourcelonIdelonntifielonr(
    "InjelonctionPipelonlinelonPrompts")

  ovelonrridelon delonf apply(
    relonquelonst: selonrvicelonthrift.GelontInjelonctionsRelonquelonst
  ): Stitch[Selonq[IntelonrmelondiatelonPrompt]] = {
    Stitch
      .callFuturelon(taskSelonrvicelon.gelontInjelonctions(relonquelonst)).map {
        _.injelonctions.flatMap {
          // Thelon elonntirelon carouselonl is gelontting addelond to elonach IntelonrmelondiatelonPrompt itelonm with a
          // correlonsponding indelonx to belon unpackelond latelonr on to populatelon its Timelonlinelonelonntry countelonrpart.
          caselon injelonction: injelonctionsthrift.Injelonction.TilelonsCarouselonl =>
            injelonction.tilelonsCarouselonl.tilelons.zipWithIndelonx.map {
              caselon (tilelon: injelonctionsthrift.Tilelon, indelonx: Int) =>
                IntelonrmelondiatelonPrompt(injelonction, Somelon(indelonx), Somelon(tilelon))
            }
          caselon injelonction => Selonq(IntelonrmelondiatelonPrompt(injelonction, Nonelon, Nonelon))
        }
      }
  }
}

/**
 * Givelons an intelonrmelondiatelon stelonp to helonlp 'elonxplosion' of tilelon carouselonl tilelons duelon to TimelonlinelonModulelon
 * not beloning an elonxtelonnsion of TimelonlinelonItelonm
 */
caselon class IntelonrmelondiatelonPrompt(
  injelonction: injelonctionsthrift.Injelonction,
  offselontInModulelon: Option[Int],
  carouselonlTilelon: Option[injelonctionsthrift.Tilelon])
