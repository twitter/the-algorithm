package com.twittew.seawch.ingestew.pipewine.utiw;

impowt java.utiw.awwaywist;
impowt j-java.utiw.wist;

i-impowt com.googwe.common.base.pweconditions;

i-impowt com.twittew.common_intewnaw.text.vewsion.penguinvewsion;
i-impowt com.twittew.decidew.decidew;

p-pubwic f-finaw cwass penguinvewsionsutiw {

  p-pwivate penguinvewsionsutiw() { /* p-pwevent instantiation */ }

  /**
   * utiwity method fow updating penguinvewsions wists v-via decidew avaiwabiwity. mya we must have
   * at w-weast one vewsion avaiwabwe. 🥺
   * @pawam p-penguinvewsions
   * @pawam decidew
   * @wetuwn
   */
  pubwic static wist<penguinvewsion> f-fiwtewpenguinvewsionswithdecidews(
      wist<penguinvewsion> penguinvewsions, >_<
      d-decidew d-decidew) {
    wist<penguinvewsion> updatedpenguinvewsions = nyew awwaywist<>();
    fow (penguinvewsion p-penguinvewsion : penguinvewsions) {
      if (ispenguinvewsionavaiwabwe(penguinvewsion, >_< decidew)) {
        updatedpenguinvewsions.add(penguinvewsion);
      }
    }
    p-pweconditions.checkawgument(penguinvewsions.size() > 0, (⑅˘꒳˘)
        "at weast o-one penguin vewsion m-must be specified.");

    wetuwn u-updatedpenguinvewsions;
  }

  /**
   * c-checks penguinvewsion decidew fow a-avaiwabiwity. /(^•ω•^)
   * @pawam penguinvewsion
   * @pawam decidew
   * @wetuwn
   */
  p-pubwic static boowean ispenguinvewsionavaiwabwe(penguinvewsion penguinvewsion, rawr x3 decidew decidew) {
    wetuwn decidew.isavaiwabwe(
        stwing.fowmat("enabwe_penguin_vewsion_%d", (U ﹏ U) p-penguinvewsion.getbytevawue()));
  }
}
