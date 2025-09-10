package com.twitter.home_mixer

import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.httpclient.RequestBuilder._
import com.twitter.util.logging.Logging
import com.twitter.inject.utils.Handler
import com.twitter.util.Try
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeMixerHttpServerWarmupHandler @Inject() (warmup: HttpWarmup) extends Handler with Logging {

  override def handle(): Unit = {
    Try(warmup.send(get("/admin/product-mixer/product-pipelines"), admin = true)())
      .onFailure(e => error(e.getMessage, e))
  }
}
