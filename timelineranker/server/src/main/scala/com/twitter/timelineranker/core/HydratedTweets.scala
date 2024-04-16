package com.tw ter.t  l neranker.core

 mport com.tw ter.t  l nes.model.t et.HydratedT et

case class HydratedT ets(
  outerT ets: Seq[HydratedT et],
   nnerT ets: Seq[HydratedT et] = Seq.empty)
