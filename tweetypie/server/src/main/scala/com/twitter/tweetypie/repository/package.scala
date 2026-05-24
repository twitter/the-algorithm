package com.twitter.tweetypie

import com.twitter.context.TwitterContext
package object repository {
  // Bring Tweetypie permitted TwitterContext into scope
  val TwitterContext: TwitterContext =
    com.twitter.context.TwitterContext(com.twitter.tweetypie.TwitterContextPermit)
}
