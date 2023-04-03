packagelon com.twittelonr.follow_reloncommelonndations.products.elonxplorelon_tab

import com.twittelonr.follow_reloncommelonndations.common.baselon.BaselonReloncommelonndationFlow
import com.twittelonr.follow_reloncommelonndations.common.baselon.IdelonntityTransform
import com.twittelonr.follow_reloncommelonndations.common.baselon.Transform
import com.twittelonr.follow_reloncommelonndations.common.modelonls.DisplayLocation
import com.twittelonr.follow_reloncommelonndations.common.modelonls.Reloncommelonndation
import com.twittelonr.follow_reloncommelonndations.flows.post_nux_ml.PostNuxMlFlow
import com.twittelonr.follow_reloncommelonndations.flows.post_nux_ml.PostNuxMlRelonquelonstBuildelonr
import com.twittelonr.follow_reloncommelonndations.products.common.Product
import com.twittelonr.follow_reloncommelonndations.products.common.ProductRelonquelonst
import com.twittelonr.follow_reloncommelonndations.products.elonxplorelon_tab.configapi.elonxplorelonTabParams
import com.twittelonr.stitch.Stitch
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class elonxplorelonTabProduct @Injelonct() (
  postNuxMlFlow: PostNuxMlFlow,
  postNuxMlRelonquelonstBuildelonr: PostNuxMlRelonquelonstBuildelonr)
    elonxtelonnds Product {
  ovelonrridelon val namelon: String = "elonxplorelon Tab"

  ovelonrridelon val idelonntifielonr: String = "elonxplorelon-tab"

  ovelonrridelon val displayLocation: DisplayLocation = DisplayLocation.elonxplorelonTab

  ovelonrridelon delonf selonlelonctWorkflows(
    relonquelonst: ProductRelonquelonst
  ): Stitch[Selonq[BaselonReloncommelonndationFlow[ProductRelonquelonst, _ <: Reloncommelonndation]]] = {
    postNuxMlRelonquelonstBuildelonr.build(relonquelonst).map { postNuxMlRelonquelonst =>
      Selonq(postNuxMlFlow.mapKelony({ _: ProductRelonquelonst => postNuxMlRelonquelonst }))
    }
  }

  ovelonrridelon val blelonndelonr: Transform[ProductRelonquelonst, Reloncommelonndation] =
    nelonw IdelonntityTransform[ProductRelonquelonst, Reloncommelonndation]

  ovelonrridelon delonf relonsultsTransformelonr(
    relonquelonst: ProductRelonquelonst
  ): Stitch[Transform[ProductRelonquelonst, Reloncommelonndation]] =
    Stitch.valuelon(nelonw IdelonntityTransform[ProductRelonquelonst, Reloncommelonndation])

  ovelonrridelon delonf elonnablelond(relonquelonst: ProductRelonquelonst): Stitch[Boolelonan] = {
    // Idelonally welon should hook up is_soft_uselonr as custom FS fielonld and disablelon thelon product through FS
    val elonnablelondForUselonrTypelon = !relonquelonst.reloncommelonndationRelonquelonst.isSoftUselonr || relonquelonst.params(
      elonxplorelonTabParams.elonnablelonProductForSoftUselonr)
    Stitch.valuelon(relonquelonst.params(elonxplorelonTabParams.elonnablelonProduct) && elonnablelondForUselonrTypelon)
  }
}
