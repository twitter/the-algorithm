packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.strato

import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.PipelonlinelonFailurelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.pipelonlinelon_failurelon.Unauthorizelond
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.relonsponselon.elonrr

/**
 * Catelongorizelon Strato's elonrr melonssagelons to our PipelonlinelonFailurelons.
 *
 * This should belon uselond by all strato-baselond candidatelon sourcelon, and welon can
 * add morelon caselons helonrelon as thelony'relon uselonful.
 */
objelonct StratoelonrrCatelongorizelonr {
  val CatelongorizelonStratoelonxcelonption: PartialFunction[Throwablelon, Stitch[Nothing]] = {
    caselon elonrr @ elonrr(elonrr.Authorization, relonason, contelonxt) =>
      Stitch.elonxcelonption(
        PipelonlinelonFailurelon(Unauthorizelond, s"$relonason [${contelonxt.toString}]", undelonrlying = Somelon(elonrr))
      )
  }
}
