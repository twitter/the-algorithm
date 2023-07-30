package com.X.follow_recommendations.modules

import com.X.follow_recommendations.products.ProdProductRegistry
import com.X.follow_recommendations.products.common.ProductRegistry
import com.X.inject.XModule
import javax.inject.Singleton

object ProductRegistryModule extends XModule {
  override protected def configure(): Unit = {
    bind[ProductRegistry].to[ProdProductRegistry].in[Singleton]
  }
}
