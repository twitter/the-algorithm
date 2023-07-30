package com.X.product_mixer.core.module

import com.X.inject.XModule
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule
import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.international.modules.LanguagesModule
import com.X.product_mixer.core.product.guice.ProductScopeModule
import com.X.finatra.jackson.modules.ScalaObjectMapperModule
import com.X.inject.thrift.modules.ThriftClientIdModule

/**
 * ProductMixerModule provides modules required by all Product Mixer services.
 *
 * @note if your service calls Strato you will need to add the [[StratoClientModule]] yourself.
 */
object ProductMixerModule extends XModule {

  override val modules = Seq(
    ABDeciderModule,
    ConfigApiModule,
    DeciderModule,
    FeatureSwitchesModule,
    LanguagesModule,
    PipelineExecutionLoggerModule,
    ProductMixerFlagModule,
    new ProductScopeModule(),
    ScalaObjectMapperModule,
    ThriftClientIdModule,
  )
}
