package com.X.home_mixer.functional_component.decorator.urt.builder

import com.X.product_mixer.core.product.guice.scope.ProductScoped
import com.X.stringcenter.client.ExternalStringRegistry
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class FeedbackStrings @Inject() (
  @ProductScoped externalStringRegistryProvider: Provider[ExternalStringRegistry]) {
  private val externalStringRegistry = externalStringRegistryProvider.get()

  val seeLessOftenFeedbackString =
    externalStringRegistry.createProdString("Feedback.seeLessOften")
  val seeLessOftenConfirmationFeedbackString =
    externalStringRegistry.createProdString("Feedback.seeLessOftenConfirmation")
}
