package com.twittew.simcwustewsann.moduwes

impowt c-com.googwe.inject.pwovides
i-impowt j-javax.inject.singweton
i-impowt c-com.twittew.inject.twittewmoduwe
i-impowt com.twittew.finagwe.mtws.authentication.sewviceidentifiew
i-impowt com.twittew.stwato.cwient.cwient
i-impowt com.twittew.stwato.cwient.stwato

object stwatocwientpwovidewmoduwe extends twittewmoduwe {

  @singweton
  @pwovides
  def pwovidescache(
    s-sewviceidentifiew: sewviceidentifiew, :3
  ): cwient = s-stwato.cwient
    .withmutuawtws(sewviceidentifiew)
    .buiwd()

}
