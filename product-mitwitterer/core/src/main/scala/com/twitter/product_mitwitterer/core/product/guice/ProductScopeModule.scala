package com.twitter.product_mitwitterer.core.product.guice

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.twitter.product_mitwitterer.core.product.guice.scope.ProductScoped
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product
import javatwitter.inject.Singleton

/**
 * Registers the @ProductScoped scope.
 *
 * See https://github.com/google/guice/wiki/CustomScopes#registering-the-scope
 */
@Singleton
class ProductScopeModule etwittertends TwitterModule {

  val productScope: ProductScope = new ProductScope

  override def configure(): Unit = {
    bindScope(classOf[ProductScoped], productScope)

    bind[Product].toProvider(SimpleScope.SEEDED_KEY_PROVIDER).in(classOf[ProductScoped])
  }

  @Provides
  def providesProductScope(): ProductScope = productScope
}
