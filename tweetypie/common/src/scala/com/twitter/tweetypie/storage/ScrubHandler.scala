package com.tw ter.t etyp e.storage

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanValue
 mport com.tw ter.t etyp e.storage.T etUt ls._
 mport com.tw ter.ut l.T  

/**
 * Deletes data for t  scrubbed f eld and wr es a  tadata record.
 * Prov des scrub funct onal y. R ght now,   only allow t  scrubb ng of t  geo f eld.
 *   should be s mple to add more f elds to t  allowl st  f needed.
 */
object ScrubHandler {

  val scrubF eldsAllowl st: Set[F eld] = Set(F eld.Geo)

  def apply(
     nsert: ManhattanOperat ons. nsert,
    delete: ManhattanOperat ons.Delete,
    scr be: Scr be,
    stats: StatsRece ver
  ): T etStorageCl ent.Scrub =
    (unf lteredT et ds: Seq[T et d], columns: Seq[F eld]) => {
      val t et ds = unf lteredT et ds.f lter(_ > 0)

      requ re(columns.nonEmpty, "Must spec fy f elds to scrub")
      requ re(
        columns.toSet.s ze == columns.s ze,
        s"Dupl cate f elds to scrub spec f ed: $columns"
      )
      requ re(
        columns.forall(scrubF eldsAllowl st.conta ns(_)),
        s"Cannot scrub $columns; scrubbable f elds are restr cted to $scrubF eldsAllowl st"
      )

      Stats.addW dthStat("scrub", " ds", t et ds.s ze, stats)
      val mhT  stamp = T  .now

      val st c s = t et ds.map { t et d =>
        val delet onSt c s = columns.map { f eld =>
          val mhKeyToDelete = T etKey.f eldKey(t et d, f eld. d)
          delete(mhKeyToDelete, So (mhT  stamp)).l ftToTry
        }

        val collectedSt ch =
          St ch.collect(delet onSt c s).map(collectW hRateL m C ck).lo rFromTry

        collectedSt ch
          .flatMap { _ =>
            val scrubbedSt c s = columns.map { column =>
              val scrubbedKey = T etKey.scrubbedF eldKey(t et d, column. d)
              val record =
                T etManhattanRecord(
                  scrubbedKey,
                  ManhattanValue(Str ngCodec.toByteBuffer(""), So (mhT  stamp))
                )

               nsert(record).l ftToTry
            }

            St ch.collect(scrubbedSt c s)
          }
          .map(collectW hRateL m C ck)
      }

      St ch.collect(st c s).map(collectW hRateL m C ck).lo rFromTry.onSuccess { _ =>
        t et ds.foreach {  d => scr be.logScrubbed( d, columns.map(_. d.to nt), mhT  stamp) }
      }
    }
}
