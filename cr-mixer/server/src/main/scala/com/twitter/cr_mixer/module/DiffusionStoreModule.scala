package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.thr ftscala.T etsW hScore
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.Apollo
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanRO
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanROConf g
 mport com.tw ter.storehaus_ nternal.ut l.Appl cat on D
 mport com.tw ter.storehaus_ nternal.ut l.DatasetNa 
 mport com.tw ter.storehaus_ nternal.ut l.HDFSPath
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object D ffus onStoreModule extends Tw terModule {
  type User d = Long
   mpl c  val longCodec =  mpl c ly[ nject on[Long, Array[Byte]]]
   mpl c  val t etRecs nject on:  nject on[T etsW hScore, Array[Byte]] =
    B naryScalaCodec(T etsW hScore)

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Ret etBasedD ffus onRecsMhStore)
  def ret etBasedD ffus onRecsMhStore(
    serv ce dent f er: Serv ce dent f er
  ): ReadableStore[Long, T etsW hScore] = {
    val manhattanROConf g = ManhattanROConf g(
      HDFSPath(""), // not needed
      Appl cat on D("cr_m xer_apollo"),
      DatasetNa ("d ffus on_ret et_t et_recs"),
      Apollo
    )

    bu ldT etRecsStore(serv ce dent f er, manhattanROConf g)
  }

  pr vate def bu ldT etRecsStore(
    serv ce dent f er: Serv ce dent f er,
    manhattanROConf g: ManhattanROConf g
  ): ReadableStore[Long, T etsW hScore] = {

    ManhattanRO
      .getReadableStoreW hMtls[Long, T etsW hScore](
        manhattanROConf g,
        ManhattanKVCl entMtlsParams(serv ce dent f er)
      )(longCodec, t etRecs nject on)
  }
}
