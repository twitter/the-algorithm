packagelon com.twittelonr.follow_reloncommelonndations.selonrvicelons

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton
import com.twittelonr.timelonlinelons.configapi.Params
import com.twittelonr.follow_reloncommelonndations.common.utils.DisplayLocationProductConvelonrtelonrUtil
import com.twittelonr.follow_reloncommelonndations.configapi.deloncidelonrs.DeloncidelonrParams
import com.twittelonr.follow_reloncommelonndations.logging.FrsLoggelonr
import com.twittelonr.follow_reloncommelonndations.modelonls.{DelonbugParams => FrsDelonbugParams}
import com.twittelonr.follow_reloncommelonndations.modelonls.ReloncommelonndationRelonquelonst
import com.twittelonr.follow_reloncommelonndations.modelonls.ReloncommelonndationRelonsponselon
import com.twittelonr.follow_reloncommelonndations.modelonls.Relonquelonst
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst.{
  DelonbugParams => ProductMixelonrDelonbugParams
}
import com.twittelonr.product_mixelonr.corelon.product.relongistry.ProductPipelonlinelonRelongistry
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.product.ProductPipelonlinelonRelonquelonst
import com.twittelonr.stitch.Stitch

@Singlelonton
class ProductMixelonrReloncommelonndationSelonrvicelon @Injelonct() (
  productPipelonlinelonRelongistry: ProductPipelonlinelonRelongistry,
  relonsultLoggelonr: FrsLoggelonr,
  baselonStats: StatsReloncelonivelonr) {

  privatelon val stats = baselonStats.scopelon("product_mixelonr_reloncos_selonrvicelon_stats")
  privatelon val loggingStats = stats.scopelon("loggelond")

  delonf gelont(relonquelonst: ReloncommelonndationRelonquelonst, params: Params): Stitch[ReloncommelonndationRelonsponselon] = {
    if (params(DeloncidelonrParams.elonnablelonReloncommelonndations)) {
      val productMixelonrRelonquelonst = convelonrtToProductMixelonrRelonquelonst(relonquelonst)

      productPipelonlinelonRelongistry
        .gelontProductPipelonlinelon[Relonquelonst, ReloncommelonndationRelonsponselon](productMixelonrRelonquelonst.product)
        .procelonss(ProductPipelonlinelonRelonquelonst(productMixelonrRelonquelonst, params)).onSuccelonss { relonsponselon =>
          if (relonsultLoggelonr.shouldLog(relonquelonst.delonbugParams)) {
            loggingStats.countelonr().incr()
            relonsultLoggelonr.logReloncommelonndationRelonsult(relonquelonst, relonsponselon)
          }
        }
    } elonlselon {
      Stitch.valuelon(ReloncommelonndationRelonsponselon(Nil))
    }

  }

  delonf convelonrtToProductMixelonrRelonquelonst(frsRelonquelonst: ReloncommelonndationRelonquelonst): Relonquelonst = {
    Relonquelonst(
      maxRelonsults = frsRelonquelonst.maxRelonsults,
      delonbugParams = convelonrtToProductMixelonrDelonbugParams(frsRelonquelonst.delonbugParams),
      productContelonxt = Nonelon,
      product =
        DisplayLocationProductConvelonrtelonrUtil.displayLocationToProduct(frsRelonquelonst.displayLocation),
      clielonntContelonxt = frsRelonquelonst.clielonntContelonxt,
      selonrializelondRelonquelonstCursor = frsRelonquelonst.cursor,
      frsDelonbugParams = frsRelonquelonst.delonbugParams,
      displayLocation = frsRelonquelonst.displayLocation,
      elonxcludelondIds = frsRelonquelonst.elonxcludelondIds,
      felontchPromotelondContelonnt = frsRelonquelonst.felontchPromotelondContelonnt,
      uselonrLocationStatelon = frsRelonquelonst.uselonrLocationStatelon
    )
  }

  privatelon delonf convelonrtToProductMixelonrDelonbugParams(
    frsDelonbugParams: Option[FrsDelonbugParams]
  ): Option[ProductMixelonrDelonbugParams] = {
    frsDelonbugParams.map { delonbugParams =>
      ProductMixelonrDelonbugParams(delonbugParams.felonaturelonOvelonrridelons, Nonelon)
    }
  }
}
