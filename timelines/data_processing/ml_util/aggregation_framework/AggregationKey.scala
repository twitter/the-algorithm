package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work

 mport com.tw ter.b ject on.Bufferable
 mport com.tw ter.b ject on. nject on
 mport scala.ut l.Try

/**
 * Case class that represents t  "group ng" key for any aggregate feature.
 * Used by Summ ngb rd to output aggregates to t  key-value "store" us ng sumByKey()
 *
 * @d screteFeaturesBy d All d screte feature ds (+ values) that are part of t  key
 * @textFeaturesBy d All str ng feature ds (+ values) that are part of t  key
 *
 * Example 1: t  user aggregate features  n aggregatesv1 all group by USER_ D,
 * wh ch  s a d screte feature. W n stor ng t se features, t  key would be:
 *
 * d screteFeaturesBy d = Map(hash(USER_ D) -> <t  actual user  d>), textFeaturesBy d = Map()
 *
 * Ex 2:  f aggregat ng grouped by USER_ D, AUTHOR_ D, t et l nk url, t  key would be:
 *
 * d screteFeaturesBy d = Map(hash(USER_ D) -> <actual user  d>, hash(AUTHOR_ D) -> <actual author  d>),
 * textFeaturesBy d = Map(hash(URL_FEATURE) -> <t  l nk url>)
 *
 *   could have just used a DataRecord for t  key, but   wanted to make   strongly typed
 * and only support group ng by d screte and str ng features, so us ng a case class  nstead.
 *
 * Re: eff c ency, stor ng t  hash of t  feature  n add  on to just t  feature value
 *  s so what more  neff c ent than only stor ng t  feature value  n t  key, but  
 * adds flex b l y to group mult ple types of aggregates  n t  sa  output store.  f  
 * dec de t   sn't a good tradeoff to make later,   can reverse/refactor t  dec s on.
 */
case class Aggregat onKey(
  d screteFeaturesBy d: Map[Long, Long],
  textFeaturesBy d: Map[Long, Str ng])

/**
 * A custom  nject on for t  above case class,
 * so that Summ ngb rd knows how to store    n Manhattan.
 */
object Aggregat onKey nject on extends  nject on[Aggregat onKey, Array[Byte]] {
  /*  nject on from tuple representat on of Aggregat onKey to Array[Byte] */
  val featureMaps nject on:  nject on[(Map[Long, Long], Map[Long, Str ng]), Array[Byte]] =
    Bufferable. nject onOf[(Map[Long, Long], Map[Long, Str ng])]

  def apply(aggregat onKey: Aggregat onKey): Array[Byte] =
    featureMaps nject on(Aggregat onKey.unapply(aggregat onKey).get)

  def  nvert(ab: Array[Byte]): Try[Aggregat onKey] =
    featureMaps nject on. nvert(ab).map(Aggregat onKey.tupled(_))
}
