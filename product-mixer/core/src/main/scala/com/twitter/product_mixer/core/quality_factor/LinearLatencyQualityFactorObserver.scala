package com.twittew.pwoduct_mixew.cowe.quawity_factow

impowt com.twittew.utiw.duwation
i-impowt com.twittew.utiw.twy

c-case cwass wineawwatencyquawityfactowobsewvew(
  o-ovewwide vaw q-quawityfactow: w-wineawwatencyquawityfactow)
    e-extends quawityfactowobsewvew {

  o-ovewwide def a-appwy(wesuwt: twy[_], >_< watency: duwation): unit = {
    wesuwt
      .onsuccess(_ => quawityfactow.update(watency))
      .onfaiwuwe {
        case t-t if quawityfactow.config.ignowabwefaiwuwes.isdefinedat(t) => ()
        case _ => quawityfactow.update(duwation.top)
      }
  }
}
