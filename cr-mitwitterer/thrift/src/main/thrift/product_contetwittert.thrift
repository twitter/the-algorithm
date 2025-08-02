namespace java com.twitter.cr_mitwitterer.thriftjava
#@namespace scala com.twitter.cr_mitwitterer.thriftscala
#@namespace strato com.twitter.cr_mitwitterer

struct HomeContetwittert {
	2: optional i32 matwitterResults // enabled for QuaityFactor related DDGs only
} (persisted='true', hasPersonalData='false')

struct NotificationsContetwittert {
	1: optional i32 devNull // not being used. it's a placeholder
} (persisted='true', hasPersonalData='false')

struct EtwitterploreContetwittert {
  1: required bool isVideoOnly
} (persisted='true', hasPersonalData='false')

union ProductContetwittert {
	1: HomeContetwittert homeContetwittert
	2: NotificationsContetwittert notificationsContetwittert
	3: EtwitterploreContetwittert etwitterploreContetwittert
} (persisted='true', hasPersonalData='false')
