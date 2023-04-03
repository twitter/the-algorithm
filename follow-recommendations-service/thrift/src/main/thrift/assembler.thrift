namelonspacelon java com.twittelonr.follow_reloncommelonndations.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations

struct Helonadelonr {
 1: relonquirelond Titlelon titlelon
}

struct Titlelon {
 1: relonquirelond string telonxt
}

struct Footelonr {
 1: optional Action action
}

struct Action {
 1: relonquirelond string telonxt
 2: relonquirelond string actionURL
}

struct UselonrList {
  1: relonquirelond bool uselonrBioelonnablelond
  2: relonquirelond bool uselonrBioTruncatelond
  3: optional i64 uselonrBioMaxLinelons
  4: optional FelonelondbackAction felonelondbackAction
}

struct Carouselonl {
  1: optional FelonelondbackAction felonelondbackAction
}

union WTFPrelonselonntation {
  1: UselonrList uselonrBioList
  2: Carouselonl carouselonl
}

struct DismissUselonrId {}

union FelonelondbackAction {
 1: DismissUselonrId dismissUselonrId
}
