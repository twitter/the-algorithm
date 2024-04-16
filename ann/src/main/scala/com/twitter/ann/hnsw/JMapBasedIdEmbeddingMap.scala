package com.tw ter.ann.hnsw

 mport com.tw ter.ann.common.Embedd ngType.Embedd ngVector
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.search.common.f le.AbstractF le
 mport java. o.OutputStream
 mport java.ut l.concurrent.ConcurrentHashMap
 mport scala.collect on.JavaConverters._

pr vate[hnsw] object JMapBased dEmbedd ngMap {

  /**
   * Creates  n- mory concurrent hashmap based conta ner that for stor ng  d embedd ng mapp ng.
   * @param expectedEle nts: Expected num of ele nts for s z ng h nt, need not be exact.
   */
  def apply n mory[T](expectedEle nts:  nt):  dEmbedd ngMap[T] =
    new JMapBased dEmbedd ngMap[T](
      new ConcurrentHashMap[T, Embedd ngVector](expectedEle nts),
      Opt on.empty
    )

  /**
   * Creates  n- mory concurrent hashmap based conta ner that can be ser al zed to d sk for stor ng  d embedd ng mapp ng.
   * @param expectedEle nts: Expected num of ele nts for s z ng h nt, need not be exact.
   * @param  nject on :  nject on for typed  d T to Array[Byte]
   */
  def apply n moryW hSer al zat on[T](
    expectedEle nts:  nt,
     nject on:  nject on[T, Array[Byte]]
  ):  dEmbedd ngMap[T] =
    new JMapBased dEmbedd ngMap[T](
      new ConcurrentHashMap[T, Embedd ngVector](expectedEle nts),
      So ( nject on)
    )

  /**
   * Loads  d embedd ng mapp ng  n  n- mory concurrent hashmap.
   * @param embedd ngF le: Local/Hdfs f le path for embedd ngs
   * @param  nject on :  nject on for typed  d T to Array[Byte]
   * @param numEle nts: Expected num of ele nts for s z ng h nt, need not be exact
   */
  def load n mory[T](
    embedd ngF le: AbstractF le,
     nject on:  nject on[T, Array[Byte]],
    numEle nts: Opt on[ nt] = Opt on.empty
  ):  dEmbedd ngMap[T] = {
    val map = numEle nts match {
      case So (ele nts) => new ConcurrentHashMap[T, Embedd ngVector](ele nts)
      case None => new ConcurrentHashMap[T, Embedd ngVector]()
    }
    Hnsw OUt l.loadEmbedd ngs(
      embedd ngF le,
       nject on,
      new JMapBased dEmbedd ngMap(map, So ( nject on))
    )
  }
}

pr vate[t ] class JMapBased dEmbedd ngMap[T](
  map: java.ut l.concurrent.ConcurrentHashMap[T, Embedd ngVector],
   nject on: Opt on[ nject on[T, Array[Byte]]])
    extends  dEmbedd ngMap[T] {
  overr de def put fAbsent( d: T, embedd ng: Embedd ngVector): Embedd ngVector = {
    map.put fAbsent( d, embedd ng)
  }

  overr de def put( d: T, embedd ng: Embedd ngVector): Embedd ngVector = {
    map.put( d, embedd ng)
  }

  overr de def get( d: T): Embedd ngVector = {
    map.get( d)
  }

  overr de def  er():  erator[(T, Embedd ngVector)] =
    map
      .entrySet()
      . erator()
      .asScala
      .map(e => (e.getKey, e.getValue))

  overr de def s ze():  nt = map.s ze()

  overr de def toD rectory(embedd ngF le: OutputStream): Un  = {
    Hnsw OUt l.saveEmbedd ngs(embedd ngF le,  nject on.get,  er())
  }
}
