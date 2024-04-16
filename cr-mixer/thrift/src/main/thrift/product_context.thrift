na space java com.tw ter.cr_m xer.thr ftjava
#@na space scala com.tw ter.cr_m xer.thr ftscala
#@na space strato com.tw ter.cr_m xer

struct Ho Context {
	2: opt onal  32 maxResults // enabled for Qua yFactor related DDGs only
} (pers sted='true', hasPersonalData='false')

struct Not f cat onsContext {
	1: opt onal  32 devNull // not be ng used.  's a placeholder
} (pers sted='true', hasPersonalData='false')

struct ExploreContext {
  1: requ red bool  sV deoOnly
} (pers sted='true', hasPersonalData='false')

un on ProductContext {
	1: Ho Context ho Context
	2: Not f cat onsContext not f cat onsContext
	3: ExploreContext exploreContext
} (pers sted='true', hasPersonalData='false')
