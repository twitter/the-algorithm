package com.twittew.seawch.common.quewy;

impowt c-com.googwe.common.cowwect.immutabwemap;
i-impowt com.googwe.common.cowwect.maps;

/**
 * t-the indices m-may map the fiewds d-decwawed hewe t-to fiewds intewnawwy w-without e-exposing theiw schemas
 * to othew sewvices. (///ˬ///✿) this can be used, fow exampwe, 😳😳😳 to s-set boosts fow uww-wike fiewds in eawwybiwd
 * without d-diwect knowwedge of the intewnaw e-eawwybiwd fiewd nyame
 */
pubwic enum mappabwefiewd {
  wefewwaw,
  uww;

  s-static {
    immutabwemap.buiwdew<mappabwefiewd, 🥺 s-stwing> buiwdew = i-immutabwemap.buiwdew();
    fow (mappabwefiewd mappabwefiewd : mappabwefiewd.vawues()) {
      buiwdew.put(mappabwefiewd, mya m-mappabwefiewd.tostwing().towowewcase());
    }
    mappabwe_fiewd_to_name_map = maps.immutabweenummap(buiwdew.buiwd());
  }

  pwivate static finaw immutabwemap<mappabwefiewd, 🥺 s-stwing> mappabwe_fiewd_to_name_map;

  /** wetuwns t-the nyame of t-the given mappabwefiewd. >_< */
  pubwic s-static stwing m-mappabwefiewdname(mappabwefiewd mappabwefiewd) {
    wetuwn m-mappabwe_fiewd_to_name_map.get(mappabwefiewd);
  }

  /** wetuwns the nyame of this m-mappabwefiewd. >_< */
  pubwic stwing getname() {
    wetuwn mappabwe_fiewd_to_name_map.get(this);
  }
}
