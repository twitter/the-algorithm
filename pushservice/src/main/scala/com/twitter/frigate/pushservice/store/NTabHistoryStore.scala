package com.tw ter.fr gate.pushserv ce.store

 mport com.tw ter. rm .store.common.ReadableWr ableStore
 mport com.tw ter.not f cat onserv ce.thr ftscala.Gener cNot f cat onOverr deKey
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons.B naryCompactScala nject on
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons.Long nject on
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons.Str ng nject on
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo nt
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.Component
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.Descr ptorP1L1
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.KeyDescr ptor
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.ValueDescr ptor
 mport com.tw ter.ut l.Future

case class NTab toryStore(mhEndpo nt: ManhattanKVEndpo nt, dataset: Str ng)
    extends ReadableWr ableStore[(Long, Str ng), Gener cNot f cat onOverr deKey] {

  pr vate val keyDesc: Descr ptorP1L1.EmptyKey[Long, Str ng] =
    KeyDescr ptor(Component(Long nject on), Component(Str ng nject on))

  pr vate val gener cNot fKeyValDesc: ValueDescr ptor.EmptyValue[Gener cNot f cat onOverr deKey] =
    ValueDescr ptor[Gener cNot f cat onOverr deKey](
      B naryCompactScala nject on(Gener cNot f cat onOverr deKey)
    )

  overr de def get(key: (Long, Str ng)): Future[Opt on[Gener cNot f cat onOverr deKey]] = {
    val (user d,  mpress on d) = key
    val mhKey = keyDesc.w hDataset(dataset).w hPkey(user d).w hLkey( mpress on d)

    St ch
      .run(mhEndpo nt.get(mhKey, gener cNot fKeyValDesc))
      .map { opt onMhValue =>
        opt onMhValue.map(_.contents)
      }
  }

  overr de def put(keyValue: ((Long, Str ng), Gener cNot f cat onOverr deKey)): Future[Un ] = {
    val ((user d,  mpress on d), gener cNot fOverr deKey) = keyValue
    val mhKey = keyDesc.w hDataset(dataset).w hPkey(user d).w hLkey( mpress on d)
    val mhVal = gener cNot fKeyValDesc.w hValue(gener cNot fOverr deKey)
    St ch.run(mhEndpo nt. nsert(mhKey, mhVal))
  }

}
