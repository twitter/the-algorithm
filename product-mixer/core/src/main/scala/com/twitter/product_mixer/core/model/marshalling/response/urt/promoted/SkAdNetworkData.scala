package com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted

case class SkAdNetworkData(
  vers on: Opt on[Str ng], // vers on of t  SKAdNetwork protocol
  srcApp d: Opt on[Str ng], // app show ng t  ad (Tw ter app or app promot ng through MOPUB)
  dstApp d: Opt on[Str ng], // app be ng promoted
  adNetwork d: Opt on[Str ng], // t  ad-network- d be ng used
  campa gn d: Opt on[Long], // t  sk-campa gn- d - d fferent from t  Tw ter campa gn  d
   mpress onT   nM ll s: Opt on[Long], // t  t  stamp of t   mpress on
  nonce: Opt on[Str ng], // nonce used to generate t  s gnature
  s gnature: Opt on[Str ng], // t  s gned payload
  f del yType: Opt on[Long] // th
)
