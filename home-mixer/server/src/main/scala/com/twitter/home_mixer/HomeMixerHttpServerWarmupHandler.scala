package com.ExTwitter.home_mixer

import com.ExTwitter.finatra.http.routing.HttpWarmup
import com.ExTwitter.finatra.httpclient.RequestBuilder._
import com.ExTwitter.util.logging.Logging
import com.ExTwitter.inject.utils.Handler
import com.ExTwitter.util.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeMixerHttpServerWarmupHandler @Inject() (warmup: HttpWarmup) extends Handler with Logging {

  override def handle(): Unit = {
    Try(warmup.send(get("/admin/product-mixer/product-pipelines"), admin = true)())
      .onFailure(e => error(e.getMessage, e))
  }
}
