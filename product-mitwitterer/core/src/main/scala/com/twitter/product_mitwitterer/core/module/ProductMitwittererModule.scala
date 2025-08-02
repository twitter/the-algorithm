package com.twitter.product_mitwitterer.core.module

import com.twitter.inject.TwitterModule
import com.twitter.product_mitwitterer.core.module.product_mitwitterer_flags.ProductMitwittererFlagModule
import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.international.modules.LanguagesModule
import com.twitter.product_mitwitterer.core.product.guice.ProductScopeModule
import com.twitter.finatra.jackson.modules.ScalaObjectMapperModule
import com.twitter.inject.thrift.modules.ThriftClientIdModule

/**
 * ProductMitwittererModule provides modules required by all Product Mitwitterer services.
 *
 * @note if your service calls Strato you will need to add the [[StratoClientModule]] yourself.
 */
object ProductMitwittererModule etwittertends TwitterModule {

  override val modules = Seq(
    ABDeciderModule,
    ConfigApiModule,
    DeciderModule,
    FeatureSwitchesModule,
    LanguagesModule,
    PipelineEtwitterecutionLoggerModule,
    ProductMitwittererFlagModule,
    new ProductScopeModule(),
    ScalaObjectMapperModule,
    ThriftClientIdModule,
  )
}
