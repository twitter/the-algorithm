package com.tw ter.t etyp e.storage

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.St chSeqGroup
 mport com.tw ter.storage.cl ent.manhattan.kv.Den edManhattanExcept on
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanExcept on
 mport com.tw ter.t etyp e.storage.T etStateRecord.BounceDeleted
 mport com.tw ter.t etyp e.storage.T etStateRecord.HardDeleted
 mport com.tw ter.t etyp e.storage.T etStateRecord.SoftDeleted
 mport com.tw ter.t etyp e.storage.T etStorageCl ent.GetT et
 mport com.tw ter.t etyp e.storage.T etUt ls._
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  

object GetT etHandler {
  pr vate[t ] val logger = Logger(getClass)

  //////////////////////////////////////////////////
  // Logg ng racy reads for later val dat on.

  val RacyT etW ndow: Durat on = 10.seconds

  /**
   *  f t  read  s soon after t  t et was created, t n   would usually
   * expect   to be served from cac . T  early read  nd cates that t 
   * t et  s prone to cons stency  ssues, so   log what's present  n
   * Manhattan at t  t   of t  read for later analys s.
   */
  pr vate[t ] def logRacyRead(t et d: T et d, records: Seq[T etManhattanRecord]): Un  =
     f (Snowflake d. sSnowflake d(t et d)) {
      val t etAge = T  .now.s nce(Snowflake d(t et d).t  )
       f (t etAge <= RacyT etW ndow) {
        val sb = new Str ngBu lder
        sb.append("racy_t et_read\t")
          .append(t et d)
          .append('\t')
          .append(t etAge. nM ll seconds) // Log t  age for analys s purposes
        records.foreach { rec =>
          sb.append('\t')
            .append(rec.lkey)
          rec.value.t  stamp.foreach { ts =>
            //  f t re  s a t  stamp for t  key, log   so that   can tell
            // later on w t r a value should have been present.   expect
            // keys wr ten  n a s ngle wr e to have t  sa  t  stamp, and
            // generally, keys wr ten  n separate wr es w ll have d fferent
            // t  stamps. T  t  stamp value  s opt onal  n Manhattan, but
            //   expect t re to always be a value for t  t  stamp.
            sb.append(':')
              .append(ts. nM ll seconds)
          }
        }
        logger. nfo(sb.toStr ng)
      }
    }

  /**
   * Convert a set of records from Manhattan  nto a GetT et.Response.
   */
  def t etResponseFromRecords(
    t et d: T et d,
    mhRecords: Seq[T etManhattanRecord],
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): GetT et.Response =
     f (mhRecords. sEmpty) {
      GetT et.Response.NotFound
    } else {
      //  f no  nternal f elds are present or no requ red f elds present,   cons der t  t et
      // as not returnable (even  f so  add  onal f elds are present)
      def t etFromRecords(t et d: T et d, mhRecords: Seq[T etManhattanRecord]) = {
        val storedT et = bu ldStoredT et(t et d, mhRecords)
         f (storedT et.getF eldBlobs(expectedF elds).nonEmpty) {
           f ( sVal d(storedT et)) {
            statsRece ver.counter("val d"). ncr()
            So (StorageConvers ons.fromStoredT et(storedT et))
          } else {
            log. nfo(s" nval d T et  d: $t et d")
            statsRece ver.counter(" nval d"). ncr()
            None
          }
        } else {
          // T  T et conta ned none of t  f elds def ned  n `expectedF elds`
          log. nfo(s"Expected F elds Not Present T et  d: $t et d")
          statsRece ver.counter("expected_f elds_not_present"). ncr()
          None
        }
      }

      val stateRecord = T etStateRecord.mostRecent(mhRecords)
      stateRecord match {
        // so   ot r cases don't requ re an attempt to construct a T et
        case So (_: SoftDeleted) | So (_: HardDeleted) => GetT et.Response.Deleted

        // all ot r cases requ re an attempt to construct a T et, wh ch may not be successful
        case _ =>
          logRacyRead(t et d, mhRecords)
          (stateRecord, t etFromRecords(t et d, mhRecords)) match {
            // BounceDeleted conta ns t  T et data so that callers can access data on t  t 
            // t et (e.g. hard delete daemon requ res conversat on d and user d. T re are no
            // plans for T etyp e server to make use of t  returned t et at t  t  .
            case (So (_: BounceDeleted), So (t et)) => GetT et.Response.BounceDeleted(t et)
            case (So (_: BounceDeleted), None) => GetT et.Response.Deleted
            case (_, So (t et)) => GetT et.Response.Found(t et)
            case _ => GetT et.Response.NotFound
          }
      }
    }

  def apply(read: ManhattanOperat ons.Read, statsRece ver: StatsRece ver): GetT et = {

    object stats {
      val getT etScope = statsRece ver.scope("getT et")
      val den edCounter: Counter = getT etScope.counter("mh_den ed")
      val mhExcept onCounter: Counter = getT etScope.counter("mh_except on")
      val nonFatalExcept onCounter: Counter = getT etScope.counter("non_fatal_except on")
      val notFoundCounter: Counter = getT etScope.counter("not_found")
    }

    object mhGroup extends St chSeqGroup[T et d, Seq[T etManhattanRecord]] {
      overr de def run(t et ds: Seq[T et d]): St ch[Seq[Seq[T etManhattanRecord]]] = {
        Stats.addW dthStat("getT et", "t et ds", t et ds.s ze, statsRece ver)
        St ch.traverse(t et ds)(read(_))
      }
    }

    t et d =>
       f (t et d <= 0) {
        St ch.NotFound
      } else {
        St ch
          .call(t et d, mhGroup)
          .map(mhRecords => t etResponseFromRecords(t et d, mhRecords, stats.getT etScope))
          .l ftToTry
          .map {
            case Throw(mhExcept on: Den edManhattanExcept on) =>
              stats.den edCounter. ncr()
              Throw(RateL m ed("", mhExcept on))

            // Encountered so  ot r Manhattan error
            case t @ Throw(_: ManhattanExcept on) =>
              stats.mhExcept onCounter. ncr()
              t

            // So th ng else happened
            case t @ Throw(ex) =>
              stats.nonFatalExcept onCounter. ncr()
              T etUt ls.log
                .warn ng(ex, s"Unhandled except on  n GetT etHandler for t et d: $t et d")
              t

            case r @ Return(GetT et.Response.NotFound) =>
              stats.notFoundCounter. ncr()
              r

            case r @ Return(_) => r
          }
          .lo rFromTry
      }
  }
}
