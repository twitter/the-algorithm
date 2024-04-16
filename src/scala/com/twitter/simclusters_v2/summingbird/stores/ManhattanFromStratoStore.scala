package com.tw ter.s mclusters_v2.summ ngb rd.stores

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. o.Buf
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.summ ngb rd.stores.Pers stentT etEmbedd ngStore.T  stamp
 mport com.tw ter.s mclusters_v2.thr ftscala.Pers stentS mClustersEmbedd ng
 mport com.tw ter.storage.cl ent.manhattan.kv.Guarantee
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl ent
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo ntBu lder
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.FullBufKey
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.ValueDescr ptor
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan_kv.ManhattanEndpo ntStore
 mport com.tw ter.strato.catalog.Vers on
 mport com.tw ter.strato.conf g.MValEncod ng
 mport com.tw ter.strato.conf g.Nat veEncod ng
 mport com.tw ter.strato.conf g.PkeyLkey2
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.data.Type
 mport com.tw ter.strato.mh.Manhattan nject ons
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.strato.thr ft.ScroogeConv mpl c s._

object ManhattanFromStratoStore {
  /* T  enables read ng from a MH store w re t  data  s wr ten by Strato. Strato uses a un que
  encod ng (Conv) wh ch needs to be reconstructed for each MH store based on t  type of data that
   s wr ten to  . Once that encod ng  s generated on start-up,   can read from t  store l ke
  any ot r ReadableStore.
   */
  def createPers stentT etStore(
    dataset: Str ng,
    mhMtlsParams: ManhattanKVCl entMtlsParams,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): ReadableStore[(T et d, T  stamp), Pers stentS mClustersEmbedd ng] = {
    val app d = "s mclusters_embedd ngs_prod"
    val dest = "/s/manhattan/o ga.nat ve-thr ft"

    val endpo nt = createMhEndpo nt(
      app d = app d,
      dest = dest,
      mhMtlsParams = mhMtlsParams,
      statsRece ver = statsRece ver)

    val (
      key nj:  nject on[(T et d, T  stamp), FullBufKey],
      valueDesc: ValueDescr ptor.EmptyValue[Pers stentS mClustersEmbedd ng]) =
       nject onsFromPkeyLkeyValueStruct[T et d, T  stamp, Pers stentS mClustersEmbedd ng](
        dataset = dataset,
        pkType = Type.Long,
        lkType = Type.Long)

    ManhattanEndpo ntStore
      .readable[(T et d, T  stamp), Pers stentS mClustersEmbedd ng, FullBufKey](
        endpo nt = endpo nt,
        keyDescBu lder = key nj,
        emptyValDesc = valueDesc)
  }

  pr vate def createMhEndpo nt(
    app d: Str ng,
    dest: Str ng,
    mhMtlsParams: ManhattanKVCl entMtlsParams,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ) = {
    val mhc = ManhattanKVCl ent. mo zedByDest(
      app d = app d,
      dest = dest,
      mtlsParams = mhMtlsParams
    )

    ManhattanKVEndpo ntBu lder(mhc)
      .defaultGuarantee(Guarantee.SoftDcRead Wr es)
      .statsRece ver(statsRece ver)
      .bu ld()
  }

  pr vate def  nject onsFromPkeyLkeyValueStruct[PK: Conv, LK: Conv, V <: Thr ftStruct: Man fest](
    dataset: Str ng,
    pkType: Type,
    lkType: Type
  ): ( nject on[(PK, LK), FullBufKey], ValueDescr ptor.EmptyValue[V]) = {
    // Strato uses a un que encod ng (Conv) so   need to rebu ld that based on t  pkey, lkey and
    // value type before convert ng   to t  Manhattan  nject ons for key -> FullBufKey and
    // value -> Buf
    val valueConv: Conv[V] = ScroogeConv.fromStruct[V]

    val mhEncod ngMapp ng = PkeyLkey2(
      pkey = pkType,
      lkey = lkType,
      value = valueConv.t,
      pkeyEncod ng = Nat veEncod ng,
      lkeyEncod ng = Nat veEncod ng,
      valueEncod ng = MValEncod ng()
    )

    val (key nj:  nject on[(PK, LK), FullBufKey], value nj:  nject on[V, Buf], _, _) =
      Manhattan nject ons.fromPkeyLkey[PK, LK, V](mhEncod ngMapp ng, dataset, Vers on.Default)

    val valDesc: ValueDescr ptor.EmptyValue[V] = ValueDescr ptor.EmptyValue(value nj)

    (key nj, valDesc)
  }
}
