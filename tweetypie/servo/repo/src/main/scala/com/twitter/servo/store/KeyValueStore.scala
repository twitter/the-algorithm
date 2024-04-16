package com.tw ter.servo.store

 mport com.tw ter.ut l.Future

tra  KeyValueStore[C, K, V, R] {
  def put(ctx: C, key: K, value: Opt on[V]): Future[R] = mult Put(ctx, Seq((key -> value)))
  def mult Put(ctx: C, kvs: Seq[(K, Opt on[V])]): Future[R]
}

tra  S mpleKeyValueStore[K, V] extends KeyValueStore[Un , K, V, Un ] {
  def put(key: K, value: Opt on[V]): Future[Un ] = mult Put((), Seq(key -> value))
  def mult Put(kvs: Seq[(K, Opt on[V])]): Future[Un ] = mult Put((), kvs)
}
