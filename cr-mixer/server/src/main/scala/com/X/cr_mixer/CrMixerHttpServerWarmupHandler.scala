package com.X.cr_mixer

import com.X.finatra.http.routing.HttpWarmup
import com.X.finatra.httpclient.RequestBuilder._
import com.X.inject.Logging
import com.X.inject.utils.Handler
import com.X.util.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrMixerHttpServerWarmupHandler @Inject() (warmup: HttpWarmup) extends Handler with Logging {

  override def handle(): Unit = {
    Try(warmup.send(get("/admin/cr-mixer/product-pipelines"), admin = true)())
      .onFailure(e => error(e.getMessage, e))
  }
}
