package com.twitter.tweetypie.thriftscala

import com.twitter.finagle.service.FailedService

class NotImplementedTweetService
    extends TweetService$FinagleClient(
      new FailedService(new UnsupportedOperationException("not implemented"))
    )
