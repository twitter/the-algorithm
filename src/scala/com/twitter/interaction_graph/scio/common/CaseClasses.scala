package com.tw ter. nteract on_graph.sc o.common

 mport com.tw ter. nteract on_graph.thr ftscala.FeatureNa 

/**  nteract on Graph Raw  nput type def nes a common type for edge / vertex feature calculat on
 *   has f elds: (s ce  d, dest nat on  d, Feature Na , age of t  relat onsh p ( n days),
 * and value to be aggregated)
 */
case class  nteract onGraphRaw nput(
  src: Long,
  dst: Long,
  na : FeatureNa ,
  age:  nt,
  featureValue: Double)

case class FeatureKey(
  src: Long,
  dest: Long,
  na : FeatureNa )

case class T epcred(user d: Long, t epcred: Short)
