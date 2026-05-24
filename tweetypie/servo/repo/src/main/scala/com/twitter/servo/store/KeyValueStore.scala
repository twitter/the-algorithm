package com.twitter.servo.store

import com.twitter.util.Future

trait KeyValueStore[C, K, V, R] {
  def put(ctx: C, key: K, value: Option[V]): Future[R] = multiPut(ctx, Seq((key -> value)))
  def multiPut(ctx: C, kvs: Seq[(K, Option[V])]): Future[R]
}

trait SimpleKeyValueStore[K, V] extends KeyValueStore[Unit, K, V, Unit] {
  def put(key: K, value: Option[V]): Future[Unit] = multiPut((), Seq(key -> value))
  def multiPut(kvs: Seq[(K, Option[V])]): Future[Unit] = multiPut((), kvs)
}
