package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.ExternalStringRegistry
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
