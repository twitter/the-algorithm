package com.tw ter.ho _m xer.store

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.ho _m xer.store.ManhattanRealGraphKVDescr ptor._
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons.B naryScala nject on
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo nt
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.ReadOnlyKeyDescr ptor
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.ValueDescr ptor
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport com.tw ter.wtf.cand date.{thr ftscala => wtf}

object ManhattanRealGraphKVDescr ptor {
   mpl c  val byteArray2Buf = B ject ons.BytesB ject on

  val realGraphDatasetNa  = "real_graph_scores_ n"
  val key nject on =  nject on.connect[Long, Array[Byte]].andT n(B ject ons.Bytes nject on)
  val keyDesc = ReadOnlyKeyDescr ptor(key nject on)
  val valueDesc = ValueDescr ptor(B naryScala nject on(wtf.Cand dateSeq))
  val realGraphDatasetKey = keyDesc.w hDataset(realGraphDatasetNa )
}

/**
 * Hydrates real graph  n network scores for a v e r
 */
class RealGraph nNetworkScoresStore(manhattanKVEndpo nt: ManhattanKVEndpo nt)
    extends ReadableStore[Long, Seq[wtf.Cand date]] {

  overr de def get(v e r d: Long): Future[Opt on[Seq[wtf.Cand date]]] = St ch
    .run(manhattanKVEndpo nt.get(realGraphDatasetKey.w hPkey(v e r d), valueDesc))
    .map(_.map(mhResponse => mhResponse.contents.cand dates))
}
