package com.twitter.tweetypie

import com.twitter.storage.client.manhattan.kv.ManhattanValue
import java.nio.ByteBuffer

package object storage {
  type TweetId = Long
  type FieldId = Short

  type TweetManhattanValue = ManhattanValue[ByteBuffer]
}
