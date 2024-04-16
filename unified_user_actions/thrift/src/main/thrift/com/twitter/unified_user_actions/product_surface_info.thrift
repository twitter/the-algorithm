#@na space java com.tw ter.un f ed_user_act ons.thr ftjava
#@na space scala com.tw ter.un f ed_user_act ons.thr ftscala
#@na space strato com.tw ter.un f ed_user_act ons

 nclude "com/tw ter/un f ed_user_act ons/ tadata.thr ft"
 nclude "com/tw ter/search/common/constants/query.thr ft"
 nclude "com/tw ter/search/common/constants/result.thr ft"


/*
 * Represents t  product surface on wh ch an act on took place.
 * See reference that del neates var ous product surfaces:
 * https://docs.google.com/docu nt/d/1PS2ZOyNUoUdO45zxhE7dH3L8KUcqJwo6Vx-XUGGFo6U
 * Note: t   mple ntat on  re may not reflect t  above doc exactly.
 */
enum ProductSurface {
  // 1 - 19 for Ho 
  Ho T  l ne = 1
  // 20 - 39 for Not f cat ons
  Not f cat onTab = 20
  PushNot f cat on = 21
  Ema lNot f cat on = 22
  // 40 - 59 for Search
  SearchResultsPage = 40
  SearchTypea ad = 41
  // 60 - 79 for T et Deta ls Page (Conversat on Page)
  T etDeta lsPage = 60
  // 80 - 99 for Prof le Page
  Prof lePage = 80
  // 100 - 119 for ?
  RESERVED_100 = 100
  // 120 - 139 for ?
  RESERVED_120 = 120
}(pers sted='true', hasPersonalData='false')

un on ProductSurface nfo {
  // 1 matc s t  enum  ndex Ho T  l ne  n ProductSurface
  1: Ho T  l ne nfo ho T  l ne nfo
  // 20 matc s t  enum  ndex Not f cat onTab  n ProductSurface
  20: Not f cat onTab nfo not f cat onTab nfo
  // 21 matc s t  enum  ndex PushNot f cat on  n ProductSurface
  21: PushNot f cat on nfo pushNot f cat on nfo
  // 22 matc s t  enum  ndex Ema lNot f cat on  n ProductSurface
  22: Ema lNot f cat on nfo ema lNot f cat on nfo
  // 40 matc s t  enum  ndex SearchResultPage  n ProductSurface
  40: SearchResultsPage nfo searchResultsPage nfo
  // 41 matc s t  enum  ndex SearchTypea ad  n ProductSurface
  41: SearchTypea ad nfo searchTypea ad nfo
  // 60 matc s t  enum  ndex T etDeta lsPage  n ProductSurface
  60: T etDeta lsPage nfo t etDeta lsPage nfo
  // 80 matc s t  enum  ndex Prof lePage  n ProductSurface
  80: Prof lePage nfo prof lePage nfo
}(pers sted='true', hasPersonalData='false')

/*
 * Please keep t  m n mal to avo d over ad.   should only
 * conta n h gh value Ho  T  l ne spec f c attr butes.
 */
struct Ho T  l ne nfo {
  // suggestType  s deprecated, please do't re-use!
  // 1: opt onal  32 suggestType
  2: opt onal str ng suggest onType
  3: opt onal  32  njectedPos  on
}(pers sted='true', hasPersonalData='false')

struct Not f cat onTab nfo {
 /*
  * Note that t  f eld represents t  ` mpress on d`  n a Not f cat on Tab not f cat on.
  *   has been rena d to `not f cat on d`  n UUA so that t  na  effect vely represents t 
  * value   holds,  .e., a un que  d for a not f cat on and request.
  */
  1: requ red str ng not f cat on d(personalDataType='Un versallyUn que dent f erUu d')
}(pers sted='true', hasPersonalData='false')

struct PushNot f cat on nfo {
 /*
  * Note that t  f eld represents t  ` mpress on d`  n a Push Not f cat on.
  *   has been rena d to `not f cat on d`  n UUA so that t  na  effect vely represents t 
  * value   holds,  .e., a un que  d for a not f cat on and request.
  */
  1: requ red str ng not f cat on d(personalDataType='Un versallyUn que dent f erUu d')
}(pers sted='true', hasPersonalData='false')

struct Ema lNot f cat on nfo {
 /*
  * Note that t  f eld represents t  ` mpress on d`  n an Ema l Not f cat on.
  *   has been rena d to `not f cat on d`  n UUA so that t  na  effect vely represents t 
  * value   holds,  .e., a un que  d for a not f cat on and request.
  */
  1: requ red str ng not f cat on d(personalDataType='Un versallyUn que dent f erUu d')
}(pers sted='true', hasPersonalData='false')


struct T etDeta lsPage nfo {
  // To be deprecated, please don't re-use!
  // Only reason to keep   now  s Sparrow doesn't take empty struct. Once t re  s a real
  // f eld   should just com nt   out.
  1: requ red l st<str ng> breadcrumbV ews(personalDataType = ' bs ePage')
  // Deprecated, please don't re-use!
  // 2: requ red l st< tadata.BreadcrumbT et> breadcrumbT ets(personalDataType = 'T et d')
}(pers sted='true', hasPersonalData='true')

struct Prof lePage nfo {
  // To be deprecated, please don't re-use!
  // Only reason to keep   now  s Sparrow doesn't take empty struct. Once t re  s a real
  // f eld   should just com nt   out.
  1: requ red l st<str ng> breadcrumbV ews(personalDataType = ' bs ePage')
  // Deprecated, please don't re-use!
  // 2: requ red l st< tadata.BreadcrumbT et> breadcrumbT ets(personalDataType = 'T et d')
}(pers sted='true', hasPersonalData='true')

struct SearchResultsPage nfo {
  // search query str ng
  1: requ red str ng query(personalDataType = 'SearchQuery')
  // Attr but on of t  search (e.g. Typed Query, Hashtag Cl ck, etc.)
  // see http://go/sgb/src/thr ft/com/tw ter/search/common/constants/query.thr ft for deta ls
  2: opt onal query.Thr ftQueryS ce queryS ce
  // 0- ndexed pos  on of  em  n l st of search results
  3: opt onal  32  emPos  on
  // Attr but on of t  t et result (e.g. Q G, Earlyb rd, etc)
  // see http://go/sgb/src/thr ft/com/tw ter/search/common/constants/result.thr ft for deta ls
  4: opt onal set<result.T etResultS ce> t etResultS ces
  // Attr but on of t  user result (e.g. ExpertSearch, Q G, etc)
  // see http://go/sgb/src/thr ft/com/tw ter/search/common/constants/result.thr ft for deta ls
  5: opt onal set<result.UserResultS ce> userResultS ces
  // T  query f lter type on t  Search Results Page (SRP) w n t  act on took place.
  // Cl ck ng on a tab  n SRP appl es a query f lter automat cally.
  6: opt onal SearchQueryF lterType queryF lterType
}(pers sted='true', hasPersonalData='true')

struct SearchTypea ad nfo {
  // search query str ng
  1: requ red str ng query(personalDataType = 'SearchQuery')
  // 0- ndexed pos  on of  em  n l st of typea ad drop-down
  2: opt onal  32  emPos  on
}(pers sted='true', hasPersonalData='true')

enum SearchQueryF lterType {
  // f lter to top ranked content for a query
  TOP = 1
  // f lter to latest content for a query
  LATEST = 2
  // f lter to user results for a query
  PEOPLE = 3
  // f lter to photo t et results for a query
  PHOTOS = 4
  // f lter to v deo t et results for a query
  V DEOS = 5
}
