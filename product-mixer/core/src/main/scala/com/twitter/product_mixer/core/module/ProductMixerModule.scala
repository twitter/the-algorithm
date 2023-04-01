package com.twitter.product_mixer.core.module

import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule
import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.international.modules.LanguagesModule
import com.twitter.product_mixer.core.product.guice.ProductScopeModule
import com.twitter.finatra.jackson.modules.ScalaObjectMapperModule
import com.twitter.inject.thrift.modules.ThriftClientIdModule

/**
 * ProductMixerModule provides modules required by all Product Mixer services.
 *
 * @note if your service calls Strato you will need to add the [[StratoClientModule]] yourself.
 */
object ProductMixerModule extends TwitterModule {

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
