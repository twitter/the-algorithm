package com.tw ter.t  l neranker.conf g

 mport com.tw ter.storehaus.Store
 mport com.tw ter.t  l neranker.recap.model.ContentFeatures
 mport com.tw ter.t  l nes.model.T et d
class Cl entWrappers(conf g: Runt  Conf gurat on) {
  pr vate[t ] val backendCl entConf gurat on = conf g.underly ngCl ents

  val contentFeaturesCac : Store[T et d, ContentFeatures] =
    backendCl entConf gurat on.contentFeaturesCac 
}
