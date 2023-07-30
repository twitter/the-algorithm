package com.X.product_mixer.core.module

import com.google.inject.Provides
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.ssl.OpportunisticTls
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ServiceLocal
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.StratoLocalRequestTimeout
import com.X.strato.client.Client
import com.X.strato.client.Strato
import com.X.util.Duration
import javax.inject.Singleton

/**
 * Product Mixer prefers to use a single strato client module over having a variety with different
 * timeouts. Latency Budgets in Product Mixer systems should be defined at the application layer.
 */
object StratoClientModule extends XModule {

  @Provides
  @Singleton
  def providesStratoClient(
    serviceIdentifier: ServiceIdentifier,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    @Flag(StratoLocalRequestTimeout) timeout: Option[Duration]
  ): Client = {
    val stratoClient = Strato.client.withMutualTls(serviceIdentifier, OpportunisticTls.Required)

    // For local development it can be useful to have a larger timeout than the Strato default of
    // 280ms. We strongly discourage setting client-level timeouts outside of this use-case. We
    // recommend setting an overall timeout for your pipeline's end-to-end running time.
    if (isServiceLocal && timeout.isDefined)
      stratoClient.withRequestTimeout(timeout.get).build()
    else {
      stratoClient.build()
    }
  }
}
