package com.twitter.product_mixer.core.product.guice

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.product_mixer.core.model.marshalling.request.Product
import javax.inject.Singleton

/**
 * Registers the @ProductScoped scope.
 *
 * See https://github.com/google/guice/wiki/CustomScopes#registering-the-scope
 */
@Singleton
class ProductScopeModule extends TwitterModule {

  val productScope: ProductScope = new ProductScope

  override def configure(): Unit = {
    bindScope(classOf[ProductScoped], productScope)

    bind[Product].toProvider(SimpleScope.SEEDED_KEY_PROVIDER).in(classOf[ProductScoped])
  }

  @Provides
  def providesProductScope(): ProductScope = productScope
}
