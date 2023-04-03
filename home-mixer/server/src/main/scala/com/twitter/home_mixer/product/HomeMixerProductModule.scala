packagelon com.twittelonr.homelon_mixelonr.product

import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.product_mixelonr.corelon.product.relongistry.ProductPipelonlinelonRelongistryConfig

objelonct HomelonMixelonrProductModulelon elonxtelonnds TwittelonrModulelon {

  ovelonrridelon delonf configurelon(): Unit = {
    bind[ProductPipelonlinelonRelongistryConfig].to[HomelonProductPipelonlinelonRelongistryConfig]
  }
}
