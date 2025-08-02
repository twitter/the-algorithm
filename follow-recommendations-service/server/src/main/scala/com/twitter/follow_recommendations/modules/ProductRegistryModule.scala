package com.twitter.follow_recommendations.modules

import com.twitter.follow_recommendations.products.ProdProductRegistry
import com.twitter.follow_recommendations.products.common.ProductRegistry
import com.twitter.inject.TwitterModule
import javatwitter.inject.Singleton

object ProductRegistryModule etwittertends TwitterModule {
  override protected def configure(): Unit = {
    bind[ProductRegistry].to[ProdProductRegistry].in[Singleton]
  }
}
