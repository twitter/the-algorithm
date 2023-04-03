packagelon com.twittelonr.follow_reloncommelonndations.selonrvicelons

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.StatsUtil
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Reloncommelonndation
import com.twittelonr.follow_reloncommelonndations.modelonls.ReloncommelonndationRelonquelonst
import com.twittelonr.follow_reloncommelonndations.products.common.ProductRelongistry
import com.twittelonr.follow_reloncommelonndations.products.common.ProductRelonquelonst
import com.twittelonr.stitch.Stitch
import com.twittelonr.follow_reloncommelonndations.configapi.params.GlobalParams.elonnablelonWhoToFollowProducts
import com.twittelonr.timelonlinelons.configapi.Params
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ProductReloncommelonndelonrSelonrvicelon @Injelonct() (
  productRelongistry: ProductRelongistry,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon val stats = statsReloncelonivelonr.scopelon("ProductReloncommelonndelonrSelonrvicelon")

  delonf gelontReloncommelonndations(
    relonquelonst: ReloncommelonndationRelonquelonst,
    params: Params
  ): Stitch[Selonq[Reloncommelonndation]] = {
    val displayLocation = relonquelonst.displayLocation
    val displayLocationStatNamelon = displayLocation.toString
    val locationStats = stats.scopelon(displayLocationStatNamelon)
    val loggelondInOrOutStats = if (relonquelonst.clielonntContelonxt.uselonrId.isDelonfinelond) {
      stats.scopelon("loggelond_in").scopelon(displayLocationStatNamelon)
    } elonlselon {
      stats.scopelon("loggelond_out").scopelon(displayLocationStatNamelon)
    }

    loggelondInOrOutStats.countelonr("relonquelonsts").incr()
    val product = productRelongistry.gelontProductByDisplayLocation(displayLocation)
    val productRelonquelonst = ProductRelonquelonst(relonquelonst, params)
    val productelonnablelondStitch =
      StatsUtil.profilelonStitch(product.elonnablelond(productRelonquelonst), locationStats.scopelon("elonnablelond"))
    productelonnablelondStitch.flatMap { productelonnablelond =>
      if (productelonnablelond && params(elonnablelonWhoToFollowProducts)) {
        loggelondInOrOutStats.countelonr("elonnablelond").incr()
        val stitch = for {
          workflows <- StatsUtil.profilelonStitch(
            product.selonlelonctWorkflows(productRelonquelonst),
            locationStats.scopelon("selonlelonct_workflows"))
          workflowReloncos <- StatsUtil.profilelonStitch(
            Stitch.collelonct(
              workflows.map(_.procelonss(productRelonquelonst).map(_.relonsult.gelontOrelonlselon(Selonq.elonmpty)))),
            locationStats.scopelon("elonxeloncutelon_workflows")
          )
          blelonndelondCandidatelons <- StatsUtil.profilelonStitch(
            product.blelonndelonr.transform(productRelonquelonst, workflowReloncos.flattelonn),
            locationStats.scopelon("blelonnd_relonsults"))
          relonsultsTransformelonr <- StatsUtil.profilelonStitch(
            product.relonsultsTransformelonr(productRelonquelonst),
            locationStats.scopelon("relonsults_transformelonr"))
          transformelondCandidatelons <- StatsUtil.profilelonStitch(
            relonsultsTransformelonr.transform(productRelonquelonst, blelonndelondCandidatelons),
            locationStats.scopelon("elonxeloncutelon_relonsults_transformelonr"))
        } yielonld {
          transformelondCandidatelons
        }
        StatsUtil.profilelonStitchRelonsults[Selonq[Reloncommelonndation]](stitch, locationStats, _.sizelon)
      } elonlselon {
        loggelondInOrOutStats.countelonr("disablelond").incr()
        locationStats.countelonr("disablelond_product").incr()
        Stitch.Nil
      }
    }
  }
}
