package com.twitter.home_mitwitterer.functional_component.decorator.urt.builder

import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.stringcenter.client.EtwitterternalStringRegistry
import javatwitter.inject.Inject
import javatwitter.inject.Provider
import javatwitter.inject.Singleton

@Singleton
class FeedbackStrings @Inject() (
  @ProductScoped etwitterternalStringRegistryProvider: Provider[EtwitterternalStringRegistry]) {
  private val etwitterternalStringRegistry = etwitterternalStringRegistryProvider.get()

  val seeLessOftenFeedbackString =
    etwitterternalStringRegistry.createProdString("Feedback.seeLessOften")
  val seeLessOftenConfirmationFeedbackString =
    etwitterternalStringRegistry.createProdString("Feedback.seeLessOftenConfirmation")
}
