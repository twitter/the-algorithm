package com.tw ter.s mclusters_v2.hdfs_s ces. nject ons

 mport com.tw ter.b ject on.Bufferable
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.{
  ScalaCompactThr ft,
  gener c nject on
}
 mport com.tw ter.s mclusters_v2.thr ftscala.ClusterDeta ls

object ClusterDeta ls nject on {
  val  nject on = KeyVal nject on[(Str ng,  nt), ClusterDeta ls](
    gener c nject on(Bufferable. nject onOf[(Str ng,  nt)]),
    ScalaCompactThr ft(ClusterDeta ls)
  )
}
