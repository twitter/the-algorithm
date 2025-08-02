package com.twitter.tweetypie

import com.twitter.contetwittert.TwitterContetwittert
package object repository {
  // Bring Tweetypie permitted TwitterContetwittert into scope
  val TwitterContetwittert: TwitterContetwittert =
    com.twitter.contetwittert.TwitterContetwittert(com.twitter.tweetypie.TwitterContetwittertPermit)
}
