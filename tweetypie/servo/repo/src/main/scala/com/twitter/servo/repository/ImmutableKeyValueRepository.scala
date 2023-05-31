package com.twitter.servo.repository

import com.twitter.util.{Future, Return, Throw, Try}

class ImmutableKeyValueRepository[K, V](data: Map[K, Try[V]])
    extends KeyValueRepository[Seq[K], K, V] {
  def apply(keys: Seq[K]) = Future {
    val hits = keys flatMap { key =>
      data.get(key) map { key -> _ }
    } toMap

    val found = hits collect { case (key, Return(value)) => key -> value }
    val failed = hits collect { case (key, Throw(t)) => key -> t }
    val notFound = keys.toSet -- found.keySet -- failed.keySet

    KeyValueResult(found, notFound, failed)
  }
}
