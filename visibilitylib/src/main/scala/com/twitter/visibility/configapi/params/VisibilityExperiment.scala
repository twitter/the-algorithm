package com.tw ter.v s b l y.conf gap .params

 mport com.tw ter.t  l nes.conf gap .BucketNa 
 mport com.tw ter.t  l nes.conf gap .Exper  nt
 mport com.tw ter.t  l nes.conf gap .UseFeatureContext

object V s b l yExper  nt {
  val Control = "control"
  val Treat nt = "treat nt"
}

abstract class V s b l yExper  nt(exper  ntKey: Str ng)
    extends Exper  nt(exper  ntKey)
    w h UseFeatureContext {
  val Treat ntBucket: Str ng = V s b l yExper  nt.Treat nt
  overr de def exper  ntBuckets: Set[BucketNa ] = Set(Treat ntBucket)
  val ControlBucket: Str ng = V s b l yExper  nt.Control
  overr de def controlBuckets: Set[BucketNa ] = Set(ControlBucket)
}
