package com.ExTwitter.cr_mixer

import com.ExTwitter.finatra.http.routing.HttpWarmup
import com.ExTwitter.finatra.httpclient.RequestBuilder._
import com.ExTwitter.inject.Logging
import com.ExTwitter.inject.utils.Handler
import com.ExTwitter.util.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrMixerHttpServerWarmupHandler @Inject() (warmup: HttpWarmup) extends Handler with Logging {

  override def handle(): Unit = {
    Try(warmup.send(get("/admin/cr-mixer/product-pipelines"), admin = true)())
      .onFailure(e => error(e.getMessage, e))
  }
}
