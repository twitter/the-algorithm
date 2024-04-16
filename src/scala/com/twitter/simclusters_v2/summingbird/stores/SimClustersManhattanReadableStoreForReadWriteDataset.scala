package com.tw ter.s mclusters_v2.summ ngb rd.stores
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl ent
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo ntBu lder
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.Component
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.Descr ptorP1L0
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.KeyDescr ptor
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.ValueDescr ptor
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanCluster
 mport com.tw ter.storehaus_ nternal.manhattan.Adama
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons.B naryScala nject on
 mport com.tw ter.storage.cl ent.manhattan.kv.Guarantee
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons.Long nject on
 mport com.tw ter.ut l.Future

/**
 * Manhattan Readable Store to fetch s mcluster embedd ng from a read-wr e dataset.
 * Only read operat ons are allo d through t  store.
 * @param app d T  "appl cat on  d"
 * @param datasetNa  T  MH dataset na .
 * @param label T  human readable label for t  f nagle thr ft cl ent
 * @param mtlsParams Cl ent serv ce  dent f er to use to aut nt cate w h Manhattan serv ce
 * @param manhattanCluster Manhattan RW cluster
 **/
class S mClustersManhattanReadableStoreForReadWr eDataset(
  app d: Str ng,
  datasetNa : Str ng,
  label: Str ng,
  mtlsParams: ManhattanKVCl entMtlsParams,
  manhattanCluster: ManhattanCluster = Adama)
    extends ReadableStore[S mClustersEmbedd ng d, ClustersUser s nterested n] {
  /*
  Sett ng up a new bu lder to read from Manhattan RW dataset. T   s spec f cally requ red for
  BeT project w re   update t  MH RW dataset (every 2 h s) us ng cloud shuttle serv ce.
   */
  val destNa  = manhattanCluster.w lyNa 
  val endPo nt = ManhattanKVEndpo ntBu lder(ManhattanKVCl ent(app d, destNa , mtlsParams, label))
    .defaultGuarantee(Guarantee.SoftDcRead Wr es)
    .bu ld()

  val keyDesc = KeyDescr ptor(Component(Long nject on), Component()).w hDataset(datasetNa )
  val valueDesc = ValueDescr ptor(B naryScala nject on(ClustersUser s nterested n))

  overr de def get(
    embedd ng d: S mClustersEmbedd ng d
  ): Future[Opt on[ClustersUser s nterested n]] = {
    embedd ng d match {
      case S mClustersEmbedd ng d(t Embedd ngType, t ModelVers on,  nternal d.User d(user d)) =>
        val populatedKey: Descr ptorP1L0.FullKey[Long] = keyDesc.w hPkey(user d)
        // returns result
        val mhValue = St ch.run(endPo nt.get(populatedKey, valueDesc))
        mhValue.map {
          case So (x) => Opt on(x.contents)
          case _ => None
        }
      case _ => Future.None
    }
  }
}
