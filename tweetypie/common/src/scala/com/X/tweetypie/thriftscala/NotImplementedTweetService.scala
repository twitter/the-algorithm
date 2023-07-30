package com.X.tweetypie.thriftscala

import com.X.finagle.service.FailedService

class NotImplementedTweetService
    extends TweetService$FinagleClient(
      new FailedService(new UnsupportedOperationException("not implemented"))
    )
