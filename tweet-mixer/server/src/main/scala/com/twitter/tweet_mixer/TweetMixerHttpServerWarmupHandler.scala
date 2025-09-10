package com.twitter.tweet_mixer

import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.http.request.RequestBuilder._
import com.twitter.inject.utils.Handler
import com.twitter.util.Try
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetMixerHttpServerWarmupHandler @Inject() (warmup: HttpWarmup)
    extends Handler
    with Logging {

  override def handle(): Unit = {
    Try(warmup.send(get("/admin/product-mixer/product-pipelines"), admin = true)())
      .onFailure(e => error(e.getMessage, e))
  }
}
