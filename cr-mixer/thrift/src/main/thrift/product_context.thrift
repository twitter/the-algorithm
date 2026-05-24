namespace java com.twitter.cr_mixer.thriftjava
#@namespace scala com.twitter.cr_mixer.thriftscala
#@namespace strato com.twitter.cr_mixer

struct HomeContext {
	2: optional i32 maxResults // enabled for QuaityFactor related DDGs only
} (persisted='true', hasPersonalData='false')

struct NotificationsContext {
	1: optional i32 devNull // not being used. it's a placeholder
} (persisted='true', hasPersonalData='false')

struct ExploreContext {
  1: required bool isVideoOnly
} (persisted='true', hasPersonalData='false')

union ProductContext {
	1: HomeContext homeContext
	2: NotificationsContext notificationsContext
	3: ExploreContext exploreContext
} (persisted='true', hasPersonalData='false')
