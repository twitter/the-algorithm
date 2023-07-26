namespace java com.twittew.tweetypie.geo.thwiftjava
#@namespace scawa com.twittew.tweetypie.geo.thwiftscawa
#@namespace s-stwato com.twittew.tweetypie.geo
n-nyamespace p-py gen.twittew.tweetypie.geo
n-namespace wb tweetypie

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                                                      //
//   t-this fiwe contains t-type definitions t-to suppowt t-the geo fiewd added to tweet fwexibwe schema onwy. 🥺                  //
//   it is unwikewy to b-be we-usabwe so tweat it them as pwivate outside t-the subpackage defined hewe. (⑅˘꒳˘)                    //
//                                                                                                                      //
//   i-in wespect to back stowage, nyaa~~ considew it has wimited capacity, :3 p-pwovisioned to addwess pawticuwaw u-use cases. ( ͡o ω ͡o )         //
//   thewe i-is nyo fwee wesouwces outside its cuwwent usage pwus a futuwe pwojection (see s-stowage capacity bewow). mya        //
//   fow exampwe:                                                                                                       //
//    1- adding extwa fiewds to t-tweetwocationinfo wiww wikewy wequiwe e-extwa stowage. (///ˬ///✿)                                    //
//    2- i-incwease on f-fwont-woad qps (wead o-ow wwite) may wequiwe extwa shawding to nyot i-impact deway pewcentiwes. (˘ω˘)         //
//   faiwuwe t-to obsewve these may impact tweetypie wwite-path and wead-path. ^^;;                                            //
//                                                                                                                      //
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * fwags how a _pwace_ is pubwished i-into a tweet (a.k.a. (✿oωo) geotagging). (U ﹏ U)
 */
e-enum geotagpwacesouwce {
  /**
   * t-tweet i-is tagged to a pwace but it is impossibwe to detewmine its souwce. -.-
   * e-e.g.: c-cweated fwom nyon-too cwients ow w-wegacy too cwients
   */
  u-unknown     = 0
  /**
   * tweet is t-tagged to a pwace by wevewse geocoding i-its coowdinates. ^•ﻌ•^
   */
  coowdinates = 1
  /**
   * tweet i-is tagged to a pwace by the cwient a-appwication on usew's behawf. rawr
   * n-ny.b.: coowdinates i-is nyot auto because the api wequest doesn't pubwish a pwace
   */
  auto        = 2
  expwicit    = 3

  // fwee to use, (˘ω˘) a-added fow backwawds c-compatibiwity on cwient c-code. nyaa~~
  wesewved_4  = 4
  w-wesewved_5  = 5
  w-wesewved_6  = 6
  wesewved_7  = 7
}

/**
 * infowmation about tweet's w-wocation(s). UwU
 * designed to enabwe custom consumption expewiences of the tweet's w-wocation(s). :3
 * e.g.: tweet's p-pewspectivaw view o-of a wocation e-entity
 *
 * to guawantee usew's w-wights of pwivacy:
 *
 * - o-onwy i-incwude usew's p-pubwished wocation data ow unpubwished wocation d-data that
 *   i-is expwicitwy set a-as pubwicwy avaiwabwe b-by the usew. (⑅˘꒳˘)
 *
 * - n-nyevew incwude usew's unpubwished (aka shawed) wocation d-data that
 *   is nyot expwicitwy set as pubwicwy avaiwabwe by the usew. (///ˬ///✿)
 *
 *   e.g.: usew i-is asked to shawe theiw gps coowdinates with twittew fwom mobiwe c-cwient, ^^;;
 *         u-undew the guawantee i-it won't be made pubwicwy a-avaiwabwe. >_<
 *
 * design nyotes:
 * - t-tweet's g-geotagged pwace is wepwesented by tweet.pwace instead of being a fiewd hewe. rawr x3
 */
stwuct tweetwocationinfo {
  /**
   * w-wepwesents how the tweet a-authow pubwished the "fwom" wocation i-in a tweet (a.k.a g-geo-tagged). /(^•ω•^)
   */
  1: optionaw geotagpwacesouwce geotag_pwace_souwce
}(pewsisted='twue', :3 h-haspewsonawdata='fawse')
