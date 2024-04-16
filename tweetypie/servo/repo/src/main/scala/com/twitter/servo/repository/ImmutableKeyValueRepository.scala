package com.tw ter.servo.repos ory

 mport com.tw ter.ut l.{Future, Return, Throw, Try}

class  mmutableKeyValueRepos ory[K, V](data: Map[K, Try[V]])
    extends KeyValueRepos ory[Seq[K], K, V] {
  def apply(keys: Seq[K]) = Future {
    val h s = keys flatMap { key =>
      data.get(key) map { key -> _ }
    } toMap

    val found = h s collect { case (key, Return(value)) => key -> value }
    val fa led = h s collect { case (key, Throw(t)) => key -> t }
    val notFound = keys.toSet -- found.keySet -- fa led.keySet

    KeyValueResult(found, notFound, fa led)
  }
}
