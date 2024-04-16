package com.tw ter.cr_m xer.conf g

 mport com.tw ter.ut l.Durat on

case class T  outConf g(
  /* Default t  outs for cand date generator */
  serv ceT  out: Durat on,
  s gnalFetchT  out: Durat on,
  s m lar yEng neT  out: Durat on,
  annServ ceCl entT  out: Durat on,
  /* For Uteg Cand date Generator */
  utegS m lar yEng neT  out: Durat on,
  /* For User State Store */
  userStateUnderly ngStoreT  out: Durat on,
  userStateStoreT  out: Durat on,
  /* For FRS based t ets */
  // T  out passed to EarlyB rd server
  earlyb rdServerT  out: Durat on,
  // T  out set on CrM xer s de
  earlyb rdS m lar yEng neT  out: Durat on,
  frsBasedT etEndpo ntT  out: Durat on,
  top cT etEndpo ntT  out: Durat on,
  // T  out Sett ngs for Nav  gRPC Cl ent
  nav RequestT  out: Durat on)
