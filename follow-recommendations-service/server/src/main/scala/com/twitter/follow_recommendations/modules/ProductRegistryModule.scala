package com.ExTwitter.follow_recommendations.modules

import com.ExTwitter.follow_recommendations.products.ProdProductRegistry
import com.ExTwitter.follow_recommendations.products.common.ProductRegistry
import com.ExTwitter.inject.ExTwitterModule
import javax.inject.Singleton

object ProductRegistryModule extends ExTwitterModule {
  override protected def configure(): Unit = {
    bind[ProductRegistry].to[ProdProductRegistry].in[Singleton]
  }
}
