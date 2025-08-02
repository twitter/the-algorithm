package com.twitter.home_mitwitterer

import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.httpclient.RequestBuilder._
import com.twitter.util.logging.Logging
import com.twitter.inject.utils.Handler
import com.twitter.util.Try
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HomeMitwittererHttpServerWarmupHandler @Inject() (warmup: HttpWarmup) etwittertends Handler with Logging {

  override def handle(): Unit = {
    Try(warmup.send(get("/admin/product-mitwitterer/product-pipelines"), admin = true)())
      .onFailure(e => error(e.getMessage, e))
  }
}
