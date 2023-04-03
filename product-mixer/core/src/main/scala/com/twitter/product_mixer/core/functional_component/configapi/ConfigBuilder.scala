packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.configapi.relongistry.GlobalParamRelongistry
import com.twittelonr.product_mixelonr.corelon.product.relongistry.ProductParamRelongistry
import com.twittelonr.timelonlinelons.configapi.CompositelonConfig
import com.twittelonr.timelonlinelons.configapi.Config
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class ConfigBuildelonr @Injelonct() (
  productParamRelongistry: ProductParamRelongistry,
  globalParamRelongistry: GlobalParamRelongistry) {

  delonf build(): Config =
    nelonw CompositelonConfig(productParamRelongistry.build() ++ Selonq(globalParamRelongistry.build()))
}
