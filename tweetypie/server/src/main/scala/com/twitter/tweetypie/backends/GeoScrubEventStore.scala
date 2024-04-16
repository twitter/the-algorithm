package com.tw ter.t etyp e
package backends

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons._
 mport com.tw ter.storage.cl ent.manhattan.kv._
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl._
 mport com.tw ter.ut l.T  

/**
 * Read and wr e t  t  stamp of t  last delete_locat on_data request
 * for a user. T   s used as a safeguard to prevent leak ng geo data
 * w h t ets that have not yet been scrubbed or  re m ssed dur ng t 
 * geo scrubb ng process.
 */
object GeoScrubEventStore {
  type GetGeoScrubT  stamp = User d => St ch[Opt on[T  ]]
  type SetGeoScrubT  stamp = FutureArrow[(User d, T  ), Un ]

  pr vate[t ] val KeyDesc =
    KeyDescr ptor(
      Component(Long nject on),
      Component(Long nject on, Str ng nject on)
    ).w hDataset("geo_scrub")

  pr vate[t ] val ValDesc = ValueDescr ptor(Long nject on)

  // T  modulus determ nes how user  ds get ass gned to PKeys, and
  // thus to shards w h n t  MH cluster. T  or g n of t  spec f c
  // value has been lost to t  , but  's  mportant that   don't
  // change  , or else t  ex st ng data w ll be  naccess ble.
  pr vate[t ] val PKeyModulus: Long = 25000L

  pr vate[t ] def toKey(user d: Long) =
    KeyDesc
      .w hPkey(user d % PKeyModulus)
      .w hLkey(user d, "_last_scrub")

  def apply(cl ent: ManhattanKVCl ent, conf g: Conf g, ctx: Backend.Context): GeoScrubEventStore = {
    new GeoScrubEventStore {
      val getGeoScrubT  stamp: User d => St ch[Opt on[T  ]] = {
        val endpo nt = conf g.read.endpo nt(cl ent)

        (user d: User d) => {
          endpo nt
            .get(toKey(user d), ValDesc)
            .map(_.map(value => T  .fromM ll seconds(value.contents)))
        }
      }

      val setGeoScrubT  stamp: SetGeoScrubT  stamp = {
        val endpo nt = conf g.wr e.endpo nt(cl ent)

        FutureArrow {
          case (user d, t  stamp) =>
            val key = toKey(user d)

            // Use t  geo scrub t  stamp as t  MH entry t  stamp. T 
            // ensures that whatever t  stamp  s h g st w ll w n any
            // update races.
            val value = ValDesc.w hValue(t  stamp. nM ll seconds, t  stamp)
            St ch.run(endpo nt. nsert(key, value))
        }
      }
    }
  }

  case class Endpo ntConf g(requestT  out: Durat on, maxRetryCount:  nt) {
    def endpo nt(cl ent: ManhattanKVCl ent): ManhattanKVEndpo nt =
      ManhattanKVEndpo ntBu lder(cl ent)
        .defaultMaxT  out(requestT  out)
        .maxRetryCount(maxRetryCount)
        .bu ld()
  }

  case class Conf g(read: Endpo ntConf g, wr e: Endpo ntConf g)
}

tra  GeoScrubEventStore {
   mport GeoScrubEventStore._
  val getGeoScrubT  stamp: GetGeoScrubT  stamp
  val setGeoScrubT  stamp: SetGeoScrubT  stamp
}
