package com.X.home_mixer

import com.X.finatra.http.routing.HttpWarmup
import com.X.finatra.httpclient.RequestBuilder._
import com.X.util.logging.Logging
import com.X.inject.utils.Handler
import com.X.util.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeMixerHttpServerWarmupHandler @Inject() (warmup: HttpWarmup) extends Handler with Logging {

  override def handle(): Unit = {
    Try(warmup.send(get("/admin/product-mixer/product-pipelines"), admin = true)())
      .onFailure(e => error(e.getMessage, e))
  }
}
