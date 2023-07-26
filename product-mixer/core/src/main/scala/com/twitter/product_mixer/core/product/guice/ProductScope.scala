package com.twittew.pwoduct_mixew.cowe.pwoduct.guice
impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wequest.pwoduct
i-impowt c-com.googwe.inject.key

/**
 * a-a speciawization o-of simpwescope - a-a simpwe guice s-scope that takes a-an initiaw pwoduct m-mixew pwoduct as a key
 */
cwass pwoductscope extends simpwescope {
  def w-wet[t](pwoduct: pwoduct)(f: => t): t = supew.wet(map(key.get(cwassof[pwoduct]) -> p-pwoduct))(f)
}
