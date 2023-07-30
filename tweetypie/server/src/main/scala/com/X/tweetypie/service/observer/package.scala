package com.X.tweetypie
package service

import com.X.util.Try

package object observer {

  /**
   * Generic Request/Result observer container for making observations on both requests/results.
   */
  type ObserveExchange[Req, Res] = (Req, Try[Res])

}
