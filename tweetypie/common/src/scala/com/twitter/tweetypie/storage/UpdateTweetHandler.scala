package com.tw ter.t etyp e.storage

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.kv.Den edManhattanExcept on
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanValue
 mport com.tw ter.t etyp e.storage.T etUt ls._
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.T  

object UpdateT etHandler {
  def apply(
     nsert: ManhattanOperat ons. nsert,
    stats: StatsRece ver
  ): T etStorageCl ent.UpdateT et = { (tpT et: T et, f elds: Seq[F eld]) =>
    requ re(
      f elds.forall(!T etF elds.coreF eld ds.conta ns(_)),
      "Core f elds cannot be mod f ed by call ng updateT et; use addT et  nstead."
    )
    requ re(
      areAllF eldsDef ned(tpT et, f elds),
      s" nput t et $tpT et does not have spec f ed f elds $f elds set"
    )

    val now = T  .now
    val storedT et = StorageConvers ons.toStoredT etForF elds(tpT et, f elds.toSet)
    val t et d = storedT et. d
    Stats.updatePerF eldQpsCounters("updateT et", f elds.map(_. d), 1, stats)

    val (f eld ds, st c sPerT et) =
      f elds.map { f eld =>
        val f eld d = f eld. d
        val t etKey = T etKey.f eldKey(t et d, f eld d)
        val blob = storedT et.getF eldBlob(f eld d).get
        val value = ManhattanValue(TF eldBlobCodec.toByteBuffer(blob), So (now))
        val record = T etManhattanRecord(t etKey, value)

        (f eld d,  nsert(record).l ftToTry)
      }.unz p

    St ch.collect(st c sPerT et).map { seqOfTr es =>
      val f eldkeyAndMhResults = f eld ds.z p(seqOfTr es).toMap
      //  f even a s ngle f eld was rate l m ed,   w ll send an overall OverCapac y T etResponse
      val wasRateL m ed = f eldkeyAndMhResults.ex sts { keyAndResult =>
        keyAndResult._2 match {
          case Throw(e: Den edManhattanExcept on) => true
          case _ => false
        }
      }

       f (wasRateL m ed) {
        bu ldT etOverCapac yResponse("updateT ets", t et d, f eldkeyAndMhResults)
      } else {
        bu ldT etResponse("updateT ets", t et d, f eldkeyAndMhResults)
      }
    }
  }

  pr vate def areAllF eldsDef ned(tpT et: T et, f elds: Seq[F eld]) = {
    val storedT et = StorageConvers ons.toStoredT etForF elds(tpT et, f elds.toSet)
    f elds.map(_. d).forall(storedT et.getF eldBlob(_). sDef ned)
  }
}
