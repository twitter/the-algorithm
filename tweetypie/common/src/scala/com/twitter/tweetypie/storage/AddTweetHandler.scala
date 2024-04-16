package com.tw ter.t etyp e.storage

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanValue
 mport com.tw ter.t etyp e.storage.T etUt ls.collectW hRateL m C ck
 mport com.tw ter.t etyp e.storage_ nternal.thr ftscala.StoredT et
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.ut l.T  

object AddT etHandler {
  pr vate[storage] type  nternalAddT et = (
    T et,
    ManhattanOperat ons. nsert,
    Scr be,
    StatsRece ver,
    T  
  ) => St ch[Un ]

  def apply(
     nsert: ManhattanOperat ons. nsert,
    scr be: Scr be,
    stats: StatsRece ver
  ): T etStorageCl ent.AddT et =
    t et => doAddT et(t et,  nsert, scr be, stats, T  .now)

  def makeRecords(
    storedT et: StoredT et,
    t  stamp: T  
  ): Seq[T etManhattanRecord] = {
    val core = CoreF eldsCodec.fromT et(storedT et)
    val packedCoreF eldsBlob = CoreF eldsCodec.toTF eldBlob(core)
    val coreRecord =
      T etManhattanRecord(
        T etKey.coreF eldsKey(storedT et. d),
        ManhattanValue(TF eldBlobCodec.toByteBuffer(packedCoreF eldsBlob), So (t  stamp))
      )

    val ot rF eld ds =
      T etF elds.nonCore nternalF elds ++ T etF elds.getAdd  onalF eld ds(storedT et)

    val ot rF elds =
      storedT et
        .getF eldBlobs(ot rF eld ds)
        .map {
          case (f eld d, tF eldBlob) =>
            T etManhattanRecord(
              T etKey.f eldKey(storedT et. d, f eld d),
              ManhattanValue(TF eldBlobCodec.toByteBuffer(tF eldBlob), So (t  stamp))
            )
        }
        .toSeq
    ot rF elds :+ coreRecord
  }

  pr vate[storage] val doAddT et:  nternalAddT et = (
    t et: T et,
     nsert: ManhattanOperat ons. nsert,
    scr be: Scr be,
    stats: StatsRece ver,
    t  stamp: T  
  ) => {
    assert(t et.coreData. sDef ned, s"T et ${t et. d}  s m ss ng coreData: $t et")

    val storedT et = StorageConvers ons.toStoredT et(t et)
    val records = makeRecords(storedT et, t  stamp)
    val  nserts = records.map( nsert)
    val  nsertsW hRateL m C ck =
      St ch.collect( nserts.map(_.l ftToTry)).map(collectW hRateL m C ck).lo rFromTry

    Stats.updatePerF eldQpsCounters(
      "addT et",
      T etF elds.getAdd  onalF eld ds(storedT et),
      1,
      stats
    )

     nsertsW hRateL m C ck.un .onSuccess { _ => scr be.logAdded(storedT et) }
  }
}
