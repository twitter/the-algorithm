package com.twittew.pwoduct_mixew.cowe.sewvice.fiwtew_executow

impowt com.twittew.pwoduct_mixew.cowe.modew.common.identifiew.fiwtewidentifiew
i-impowt c-com.twittew.pwoduct_mixew.cowe.sewvice.executowwesuwt

c-case c-cwass fiwtewexecutowwesuwt[candidate](
  w-wesuwt: s-seq[candidate], -.-
  i-individuawfiwtewwesuwts: s-seq[individuawfiwtewwesuwts[candidate]])
    extends executowwesuwt

seawed twait individuawfiwtewwesuwts[+candidate]
case cwass conditionawfiwtewdisabwed(identifiew: f-fiwtewidentifiew)
    extends individuawfiwtewwesuwts[nothing]
c-case cwass fiwtewexecutowindividuawwesuwt[+candidate](
  identifiew: f-fiwtewidentifiew,
  kept: seq[candidate], ^^;;
  wemoved: seq[candidate])
    e-extends individuawfiwtewwesuwts[candidate]
