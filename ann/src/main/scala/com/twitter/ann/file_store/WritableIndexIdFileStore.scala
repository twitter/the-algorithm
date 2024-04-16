package com.tw ter.ann.f le_store

 mport com.tw ter.ann.common. ndexOutputF le
 mport com.tw ter.ann.common.thr ftscala.F leBased ndex dStore
 mport com.tw ter.b ject on. nject on
 mport com.tw ter. d aserv ces.commons.codec.ArrayByteBufferCodec
 mport com.tw ter. d aserv ces.commons.codec.Thr ftByteBufferCodec
 mport com.tw ter.storehaus.Store
 mport com.tw ter.ut l.Future
 mport java.ut l.concurrent.{ConcurrentHashMap => JConcurrentHashMap}
 mport scala.collect on.JavaConverters._

object Wr able ndex dF leStore {

  /**
   * @param  nject on:  nject on to convert typed  d to bytes.
   * @tparam V: Type of  d
   * @return F le based Wr able Store
   */
  def apply[V](
     nject on:  nject on[V, Array[Byte]]
  ): Wr able ndex dF leStore[V] = {
    new Wr able ndex dF leStore[V](
      new JConcurrentHashMap[Long, Opt on[V]],
       nject on
    )
  }
}

class Wr able ndex dF leStore[V] pr vate (
  map: JConcurrentHashMap[Long, Opt on[V]],
   nject on:  nject on[V, Array[Byte]])
    extends Store[Long, V] {

  pr vate[t ] val store = Store.fromJMap(map)

  overr de def get(k: Long): Future[Opt on[V]] = {
    store.get(k)
  }

  overr de def put(kv: (Long, Opt on[V])): Future[Un ] = {
    store.put(kv)
  }

  /**
   * Ser al ze and store t  mapp ng  n thr ft format
   * @param f le : F le path to store ser al zed long  ndex d <->  d mapp ng
   */
  def save(f le:  ndexOutputF le): Un  = {
    saveThr ft(toThr ft(), f le)
  }

  def get nject on:  nject on[V, Array[Byte]] =  nject on

  pr vate[t ] def toThr ft(): F leBased ndex dStore = {
    val  ndex dMap = map.asScala
      .collect {
        case (key, So (value)) => (key, ArrayByteBufferCodec.encode( nject on.apply(value)))
      }

    F leBased ndex dStore(So ( ndex dMap))
  }

  pr vate[t ] def saveThr ft(thr ftObj: F leBased ndex dStore, f le:  ndexOutputF le): Un  = {
    val codec = new Thr ftByteBufferCodec(F leBased ndex dStore)
    val bytes = ArrayByteBufferCodec.decode(codec.encode(thr ftObj))
    val outputStream = f le.getOutputStream()
    outputStream.wr e(bytes)
    outputStream.close()
  }
}
