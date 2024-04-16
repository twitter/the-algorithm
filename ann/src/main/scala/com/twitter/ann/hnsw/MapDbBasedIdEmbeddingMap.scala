package com.tw ter.ann.hnsw

 mport com.tw ter.ann.common.Embedd ngType.Embedd ngVector
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.ml.ap .embedd ng.Embedd ng
 mport com.tw ter.search.common.f le.AbstractF le
 mport java. o.OutputStream
 mport org.mapdb.DBMaker
 mport org.mapdb.HTreeMap
 mport org.mapdb.Ser al zer
 mport scala.collect on.JavaConverters._

/**
 * T  class currently only support query ng and creates map db on fly from thr ft ser al zed embedd ng mapp ng
 *  mple nt  ndex creat on w h t  or altoget r replace mapdb w h so  better perform ng solut on as   takes a lot of t   to create/query or precreate wh le ser al z ng thr ft embedd ngs
 */
pr vate[hnsw] object MapDbBased dEmbedd ngMap {

  /**
   * Loads  d embedd ng mapp ng  n mapDB based conta ner leverag ng  mory mapped f les.
   * @param embedd ngF le: Local/Hdfs f le path for embedd ngs
   * @param  nject on :  nject on for typed  d T to Array[Byte]
   */
  def loadAsReadonly[T](
    embedd ngF le: AbstractF le,
     nject on:  nject on[T, Array[Byte]]
  ):  dEmbedd ngMap[T] = {
    val d skDb = DBMaker
      .tempF leDB()
      .concurrencyScale(32)
      .f leMmapEnable()
      .f leMmapEnable fSupported()
      .f leMmapPreclearD sable()
      .cleanerHackEnable()
      .closeOnJvmShutdown()
      .make()

    val mapDb = d skDb
      .hashMap("mapdb", Ser al zer.BYTE_ARRAY, Ser al zer.FLOAT_ARRAY)
      .createOrOpen()

    Hnsw OUt l.loadEmbedd ngs(
      embedd ngF le,
       nject on,
      new MapDbBased dEmbedd ngMap(mapDb,  nject on)
    )
  }
}

pr vate[t ] class MapDbBased dEmbedd ngMap[T](
  mapDb: HTreeMap[Array[Byte], Array[Float]],
   nject on:  nject on[T, Array[Byte]])
    extends  dEmbedd ngMap[T] {
  overr de def put fAbsent( d: T, embedd ng: Embedd ngVector): Embedd ngVector = {
    val value = mapDb.put fAbsent( nject on.apply( d), embedd ng.toArray)
     f (value == null) null else Embedd ng(value)
  }

  overr de def put( d: T, embedd ng: Embedd ngVector): Embedd ngVector = {
    val value = mapDb.put( nject on.apply( d), embedd ng.toArray)
     f (value == null) null else Embedd ng(value)
  }

  overr de def get( d: T): Embedd ngVector = {
    Embedd ng(mapDb.get( nject on.apply( d)))
  }

  overr de def  er():  erator[(T, Embedd ngVector)] = {
    mapDb
      .entrySet()
      . erator()
      .asScala
      .map(entry => ( nject on. nvert(entry.getKey).get, Embedd ng(entry.getValue)))
  }

  overr de def s ze():  nt = mapDb.s ze()

  overr de def toD rectory(embedd ngF le: OutputStream): Un  = {
    Hnsw OUt l.saveEmbedd ngs(embedd ngF le,  nject on,  er())
  }
}
