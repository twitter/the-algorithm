package com.twitter.follow_recommendations.modules

import com.twitter.follow_recommendations.products.ProdProductRegistry
import com.twitter.follow_recommendations.products.common.ProductRegistry
import com.twitter.inject.TwitterModule
import javax.inject.Singleton

object ProductRegistryModule extends TwitterModule {
  override protected def configure(): Unit = {
    bind[ProductRegistry].to[ProdProductRegistry].in[Singleton]
  }
}
