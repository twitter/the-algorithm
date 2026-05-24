package com.twitter.tweet_mixer.utils

import java.util.concurrent.ConcurrentMap

class ConcurrentMapCache[K, V](val underlying: ConcurrentMap[K, V]) {

  def get(key: K): Option[V] = Option(underlying.get(key))

  def set(key: K, value: V): V = {
    underlying.put(key, value)
  }
}
