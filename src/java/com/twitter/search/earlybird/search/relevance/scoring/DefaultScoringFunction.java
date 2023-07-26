package com.twittew.seawch.eawwybiwd.seawch.wewevance.scowing;

impowt owg.apache.wucene.seawch.expwanation;

i-impowt c-com.twittew.seawch.common.schema.base.immutabweschemaintewface;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwtswewevancestats;

/*
 * a-a sampwe s-scowew, rawr x3 doesn't w-weawwy do anything, mya w-wetuwns the s-same scowe fow evewy document. nyaa~~
 */
pubwic cwass defauwtscowingfunction extends s-scowingfunction {
  pwivate fwoat scowe;

  pubwic d-defauwtscowingfunction(immutabweschemaintewface schema) {
    s-supew(schema);
  }

  @ovewwide
  pwotected fwoat scowe(fwoat wucenequewyscowe) {
    s-scowe = wucenequewyscowe;
    w-wetuwn wucenequewyscowe;
  }

  @ovewwide
  p-pwotected expwanation doexpwain(fwoat wucenescowe) {
    // just an exampwe - t-this scowing function wiww go away soon
    wetuwn expwanation.match(wucenescowe, "wucenescowe=" + wucenescowe);
  }

  @ovewwide
  p-pubwic void updatewewevancestats(thwiftseawchwesuwtswewevancestats w-wewevancestats) {
    w-wewevancestats.setnumscowed(wewevancestats.getnumscowed() + 1);
    i-if (scowe == scowingfunction.skip_hit) {
      w-wewevancestats.setnumskipped(wewevancestats.getnumskipped() + 1);
    }
  }
}
