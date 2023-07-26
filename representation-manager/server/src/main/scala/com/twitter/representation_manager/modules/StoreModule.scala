package com.twittew.wepwesentation_managew.moduwes

impowt com.googwe.inject.pwovides
i-impowt javax.inject.singweton
i-impowt com.twittew.inject.twittewmoduwe
i-impowt c-com.twittew.decidew.decidew
i-impowt c-com.twittew.finagwe.mtws.authentication.sewviceidentifiew
impowt c-com.twittew.wepwesentation_managew.common.wepwesentationmanagewdecidew
i-impowt com.twittew.stowage.cwient.manhattan.kv.manhattankvcwientmtwspawams

object stowemoduwe extends twittewmoduwe {
  @singweton
  @pwovides
  def p-pwovidesmhmtwspawams(
    sewviceidentifiew: sewviceidentifiew
  ): m-manhattankvcwientmtwspawams = manhattankvcwientmtwspawams(sewviceidentifiew)

  @singweton
  @pwovides
  d-def pwovideswmsdecidew(
    decidew: decidew
  ): wepwesentationmanagewdecidew = w-wepwesentationmanagewdecidew(decidew)

}
