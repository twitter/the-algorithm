package com.tw ter.ann.manhattan

 mport com.tw ter.ann.common.Embedd ngType.Embedd ngVector
 mport com.tw ter.ann.common.{Embedd ngProducer, Embedd ngType}
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.ml.ap .embedd ng.{Embedd ngB ject on, Embedd ngSerDe}
 mport com.tw ter.ml.ap .{thr ftscala => thr ft}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons
 mport com.tw ter.storage.cl ent.manhattan.b ject ons.B ject ons.B naryScala nject on
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVEndpo nt
 mport com.tw ter.storage.cl ent.manhattan.kv. mpl.{
  Descr ptorP1L0,
  ReadOnlyKeyDescr ptor,
  ValueDescr ptor
}

pr vate[manhattan] class ManhattanEmbedd ngProducer[T](
  keyDescr ptor: Descr ptorP1L0.DKey[T],
  valueDescr ptor: ValueDescr ptor.EmptyValue[Embedd ngVector],
  manhattanEndpo nt: ManhattanKVEndpo nt)
    extends Embedd ngProducer[T] {

  /**
   * Lookup an embedd ng from manhattan g ven a key of type T.
   *
   * @return An embedd ng st ch.
   *         An easy way to get a Future from a St ch  s to run St ch.run(st ch)
   */
  overr de def produceEmbedd ng( nput: T): St ch[Opt on[Embedd ngVector]] = {
    val fullKey = keyDescr ptor.w hPkey( nput)
    val st chResult = manhattanEndpo nt.get(fullKey, valueDescr ptor)
    st chResult.map { resultOpt on =>
      resultOpt on.map(_.contents)
    }
  }
}

object ManhattanEmbedd ngProducer {
  pr vate[manhattan] def keyDescr ptor[T](
     nject on:  nject on[T, Array[Byte]],
    dataset: Str ng
  ): Descr ptorP1L0.DKey[T] =
    ReadOnlyKeyDescr ptor( nject on.andT n(B ject ons.BytesB ject on))
      .w hDataset(dataset)

  pr vate[manhattan] val Embedd ngDescr ptor: ValueDescr ptor.EmptyValue[
    Embedd ngType.Embedd ngVector
  ] = {
    val embedd ngB ject on = new Embedd ngB ject on(Embedd ngSerDe.floatEmbedd ngSerDe)
    val thr ft nject on = B naryScala nject on[thr ft.Embedd ng](thr ft.Embedd ng)
    ValueDescr ptor(embedd ngB ject on.andT n(thr ft nject on))
  }

  def apply[T](
    dataset: Str ng,
     nject on:  nject on[T, Array[Byte]],
    manhattanEndpo nt: ManhattanKVEndpo nt
  ): Embedd ngProducer[T] = {
    val descr ptor = keyDescr ptor( nject on, dataset)
    new ManhattanEmbedd ngProducer(descr ptor, Embedd ngDescr ptor, manhattanEndpo nt)
  }
}
