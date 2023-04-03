namelonspacelon java com.twittelonr.cr_mixelonr.thriftjava
#@namelonspacelon scala com.twittelonr.cr_mixelonr.thriftscala
#@namelonspacelon strato com.twittelonr.cr_mixelonr

struct HomelonContelonxt {
	2: optional i32 maxRelonsults // elonnablelond for QuaityFactor relonlatelond DDGs only
} (pelonrsistelond='truelon', hasPelonrsonalData='falselon')

struct NotificationsContelonxt {
	1: optional i32 delonvNull // not beloning uselond. it's a placelonholdelonr
} (pelonrsistelond='truelon', hasPelonrsonalData='falselon')

struct elonxplorelonContelonxt {
  1: relonquirelond bool isVidelonoOnly
} (pelonrsistelond='truelon', hasPelonrsonalData='falselon')

union ProductContelonxt {
	1: HomelonContelonxt homelonContelonxt
	2: NotificationsContelonxt notificationsContelonxt
	3: elonxplorelonContelonxt elonxplorelonContelonxt
} (pelonrsistelond='truelon', hasPelonrsonalData='falselon')
