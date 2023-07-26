package com.twittew.seawch.common.schema.eawwybiwd;

impowt java.utiw.set;

i-impowt c-com.googwe.common.annotations.visibwefowtesting;
i-impowt com.googwe.common.cowwect.immutabweset;

/**
 * a-a wist o-of existing eawwybiwd c-cwustews. (ˆ ﻌ ˆ)♡
 */
p-pubwic enum e-eawwybiwdcwustew {
  /**
   * weawtime eawwybiwd cwustew. 😳😳😳 has 100% of tweet fow about 7 days. :3
   */
  w-weawtime, OwO
  /**
   * pwotected eawwybiwd c-cwustew. (U ﹏ U) has onwy tweets fwom pwotected a-accounts. >w<
   */
  pwotected, (U ﹏ U)
  /**
   * fuww awchive cwustew. 😳 has aww tweets u-untiw about 2 days ago. (ˆ ﻌ ˆ)♡
   */
  f-fuww_awchive, 😳😳😳
  /**
   * s-supewwoot cwustew. (U ﹏ U) tawks to the othew cwustews instead of tawking d-diwectwy to eawwybiwds. (///ˬ///✿)
   */
  supewwoot, 😳

  /**
   * a dedicated cwustew fow candidate genewation u-use cases based on eawwybiwd i-in home/pushsewvice
   */
  w-weawtime_cg;

  p-pubwic s-stwing getnamefowstats() {
    wetuwn nyame().towowewcase();
  }

  pubwic static b-boowean isawchive(eawwybiwdcwustew cwustew) {
    wetuwn iscwustewinset(cwustew, 😳 a-awchive_cwustews);
  }

  pubwic static boowean istwittewmemowyfowmatcwustew(eawwybiwdcwustew cwustew) {
    wetuwn iscwustewinset(cwustew, σωσ twittew_in_memowy_index_fowmat_genewaw_puwpose_cwustews);
  }

  p-pubwic static boowean haseawwybiwds(eawwybiwdcwustew c-cwustew) {
    w-wetuwn cwustew != s-supewwoot;
  }

  pwivate static boowean iscwustewinset(eawwybiwdcwustew c-cwustew, rawr x3 set<eawwybiwdcwustew> s-set) {
    wetuwn set.contains(cwustew);
  }

  p-pwotected static f-finaw immutabweset<eawwybiwdcwustew> awchive_cwustews =
      i-immutabweset.of(fuww_awchive);

  @visibwefowtesting
  pubwic static f-finaw immutabweset<eawwybiwdcwustew>
          twittew_in_memowy_index_fowmat_genewaw_puwpose_cwustews =
      immutabweset.of(
          w-weawtime, OwO
          pwotected);

  @visibwefowtesting
  p-pubwic static finaw immutabweset<eawwybiwdcwustew> t-twittew_in_memowy_index_fowmat_aww_cwustews =
      immutabweset.of(
          w-weawtime, /(^•ω•^)
          pwotected, 😳😳😳
          weawtime_cg);

  /**
   * constant fow fiewd used in genewaw puwpose cwustews, ( ͡o ω ͡o )
   * n-nyote that g-genewaw_puwpose_cwustews does n-nyot incwude weawtime_cg. >_< i-if you w-wish to incwude weawtime_cg,
   * pwease use aww_cwustews
   */
  pwotected static f-finaw immutabweset<eawwybiwdcwustew> genewaw_puwpose_cwustews =
      immutabweset.of(
          weawtime, >w<
          pwotected, rawr
          f-fuww_awchive, 😳
          supewwoot);

  p-pwotected static f-finaw immutabweset<eawwybiwdcwustew> a-aww_cwustews =
      immutabweset.of(
          w-weawtime, >w<
          pwotected, (⑅˘꒳˘)
          f-fuww_awchive, OwO
          s-supewwoot, (ꈍᴗꈍ)
          w-weawtime_cg);
}
