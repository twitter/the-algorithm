package com.X.tweetypie

import com.X.context.XContext
package object repository {
  // Bring Tweetypie permitted XContext into scope
  val XContext: XContext =
    com.X.context.XContext(com.X.tweetypie.XContextPermit)
}
