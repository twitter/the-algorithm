package com.twittew.pwoduct_mixew.cowe.functionaw_component.configapi

impowt com.twittew.pwoduct_mixew.cowe.functionaw_component.configapi.wegistwy.gwobawpawamwegistwy
i-impowt com.twittew.pwoduct_mixew.cowe.pwoduct.wegistwy.pwoductpawamwegistwy
i-impowt com.twittew.timewines.configapi.compositeconfig
i-impowt c-com.twittew.timewines.configapi.config
i-impowt j-javax.inject.inject
i-impowt javax.inject.singweton

@singweton
c-cwass configbuiwdew @inject() (
  pwoductpawamwegistwy: pwoductpawamwegistwy, (U ﹏ U)
  gwobawpawamwegistwy: g-gwobawpawamwegistwy) {

  def buiwd(): config =
    n-nyew compositeconfig(pwoductpawamwegistwy.buiwd() ++ seq(gwobawpawamwegistwy.buiwd()))
}
