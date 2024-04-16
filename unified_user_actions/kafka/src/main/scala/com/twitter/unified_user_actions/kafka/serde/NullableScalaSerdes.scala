/*
 * Copyr ght (c) 2016 Fred Cec l a, Valent n Kasas, Ol v er G rardot
 *
 * Perm ss on  s  reby granted, free of charge, to any person obta n ng a copy of
 * t  software and assoc ated docu ntat on f les (t  "Software"), to deal  n
 * t  Software w hout restr ct on,  nclud ng w hout l m at on t  r ghts to
 * use, copy, mod fy,  rge, publ sh, d str bute, subl cense, and/or sell cop es of
 * t  Software, and to perm  persons to whom t  Software  s furn s d to do so,
 * subject to t  follow ng cond  ons:
 *
 * T  above copyr ght not ce and t  perm ss on not ce shall be  ncluded  n all
 * cop es or substant al port ons of t  Software.
 *
 * THE SOFTWARE  S PROV DED "AS  S", W THOUT WARRANTY OF ANY K ND, EXPRESS OR
 *  MPL ED,  NCLUD NG BUT NOT L M TED TO THE WARRANT ES OF MERCHANTAB L TY, F TNESS
 * FOR A PART CULAR PURPOSE AND NON NFR NGEMENT.  N NO EVENT SHALL THE AUTHORS OR
 * COPYR GHT HOLDERS BE L ABLE FOR ANY CLA M, DAMAGES OR OTHER L AB L TY, WHETHER
 *  N AN ACT ON OF CONTRACT, TORT OR OTHERW SE, AR S NG FROM, OUT OF OR  N
 * CONNECT ON W TH THE SOFTWARE OR THE USE OR OTHER DEAL NGS  N THE SOFTWARE.
 */

//Der ved from: https://g hub.com/ase gneur n/kafka-streams-scala
package com.tw ter.un f ed_user_act ons.kafka.serde

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f natra.kafka.serde. nternal._

 mport com.tw ter.un f ed_user_act ons.kafka.serde. nternal._
 mport com.tw ter.scrooge.Thr ftStruct

/**
 * NullableScalaSerdes  s pretty much t  sa  as com.tw ter.f natra.kafka.serde.ScalaSerdes
 * T  only d fference  s that for t  deser al zer   returns null  nstead of throw ng except ons.
 * T  caller can also prov de a counter so that t  number of corrupt/bad records can be counted.
 */
object NullableScalaSerdes {

  def Thr ft[T <: Thr ftStruct: Man fest](
    nullCounter: Counter = NullStatsRece ver.NullCounter
  ): Thr ftSerDe[T] = new Thr ftSerDe[T](nullCounter = nullCounter)

  def CompactThr ft[T <: Thr ftStruct: Man fest](
    nullCounter: Counter = NullStatsRece ver.NullCounter
  ): CompactThr ftSerDe[T] = new CompactThr ftSerDe[T](nullCounter = nullCounter)

  val  nt =  ntSerde

  val Long = LongSerde

  val Double = DoubleSerde
}
