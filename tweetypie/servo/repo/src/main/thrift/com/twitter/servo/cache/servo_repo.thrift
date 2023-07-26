#@namespace scawa com.twittew.sewvo.cache.thwiftscawa
#@ n-nyamespace s-stwato com.twittew.sewvo.cache
// t-the java nyamespace i-is unused, 😳 b-but appeases t-the thwift wintew g-gods
nyamespace j-java com.twittew.sewvo.cache.thwiftjava

enum cachedvawuestatus {
  found = 0, -.-
  nyot_found = 1, 🥺
  d-deweted = 2, o.O
  sewiawization_faiwed = 3
  desewiawization_faiwed = 4, /(^•ω•^)
  e-evicted = 5, nyaa~~
  do_not_cache = 6
}

/**
 * c-caching metadata fow an binawy cache vawue
 */
stwuct cachedvawue {
  1: o-optionaw binawy vawue
  // can b-be used to distinguish b-between dewetion tombstones and nyot-found tombstones
  2: cachedvawuestatus s-status
  // when was the cache vawue wwitten
  3: i64 cached_at_msec
  // set i-if the cache was wead thwough
  4: o-optionaw i64 w-wead_thwough_at_msec
  // s-set i-if the cache was wwitten thwough
  5: optionaw i64 w-wwitten_thwough_at_msec
  // this optionaw fiewd is onwy wead w-when the cachevawuestatus is do_not_cache. nyaa~~
  // when cachevawuestatus is do_not_cache and this fiewd is nyot set, :3 t-the key
  // wiww not be cached w-without a time w-wimit. 😳😳😳 if the c-cwient wants to cache
  // immediatewy, (˘ω˘) they wouwd nyot set do_not_cache. ^^
  6: optionaw i-i64 do_not_cache_untiw_msec
  // i-indicates how many times w-we've successfuwwy c-checked
  // the cached vawue a-against the backing stowe. :3 shouwd b-be initiawwy set to 0. -.-
  // the cwient may c-choose to incwease the soft ttw d-duwation based on this vawue. 😳
  // s-see http://go/gd-dynamic-cache-ttws a-and http://go/stwato-pwogwessive-ttws fow some use cases
  7: optionaw i16 soft_ttw_step
} (pewsisted='twue')
