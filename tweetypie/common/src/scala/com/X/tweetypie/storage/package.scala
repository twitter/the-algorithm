package com.X.tweetypie

import com.X.storage.client.manhattan.kv.ManhattanValue
import java.nio.ByteBuffer

package object storage {
  type TweetId = Long
  type FieldId = Short

  type TweetManhattanValue = ManhattanValue[ByteBuffer]
}
