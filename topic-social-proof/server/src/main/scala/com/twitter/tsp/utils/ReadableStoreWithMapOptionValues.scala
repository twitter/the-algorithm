package com.twittew.tsp.utiws

impowt c-com.twittew.stowehaus.abstwactweadabwestowe
i-impowt com.twittew.stowehaus.weadabwestowe
i-impowt c-com.twittew.utiw.futuwe

c-cwass w-weadabwestowewithmapoptionvawues[k, 😳 v-v1, v2](ws: w-weadabwestowe[k, XD v1]) {

  def mapoptionvawues(
    fn: v1 => option[v2]
  ): w-weadabwestowe[k, :3 v2] = {
    vaw sewf = ws
    new a-abstwactweadabwestowe[k, v2] {
      o-ovewwide def get(k: k): futuwe[option[v2]] = sewf.get(k).map(_.fwatmap(fn))

      o-ovewwide def muwtiget[k1 <: k-k](ks: set[k1]): m-map[k1, 😳😳😳 futuwe[option[v2]]] =
        sewf.muwtiget(ks).mapvawues(_.map(_.fwatmap(fn)))
    }
  }
}
