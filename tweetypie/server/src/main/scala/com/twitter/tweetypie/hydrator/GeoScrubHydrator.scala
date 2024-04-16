package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.core._
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.thr ftscala._

/**
 * T  hydrator, wh ch  s really more of a "repa rer", scrubs at read-t   geo data
 * that should have been scrubbed but wasn't.  For any t et w h geo data,   c cks
 * t  last geo-scrub t  stamp,  f any, for t  user, and  f t  t et was created before
 * that t  stamp,   removes t  geo data.
 */
object GeoScrubHydrator {
  type Data = (Opt on[GeoCoord nates], Opt on[Place d])
  type Type = ValueHydrator[Data, T etCtx]

  pr vate[t ] val mod f edNoneNoneResult = ValueState.mod f ed((None, None))

  def apply(repo: GeoScrubT  stampRepos ory.Type, scr beT et d: FutureEffect[T et d]): Type =
    ValueHydrator[Data, T etCtx] { (curr, ctx) =>
      repo(ctx.user d).l ftToTry.map {
        case Return(geoScrubT  )  f ctx.createdAt <= geoScrubT   =>
          scr beT et d(ctx.t et d)
          mod f edNoneNoneResult

        // no-op on fa lure and no result
        case _ => ValueState.unmod f ed(curr)
      }
    }.only f { case ((coords, place), _) => coords.nonEmpty || place.nonEmpty }
}
