na space java com.tw ter.follow_recom ndat ons.thr ftjava
#@na space scala com.tw ter.follow_recom ndat ons.thr ftscala
#@na space strato com.tw ter.follow_recom ndat ons

struct  ader {
 1: requ red T le t le
}

struct T le {
 1: requ red str ng text
}

struct Footer {
 1: opt onal Act on act on
}

struct Act on {
 1: requ red str ng text
 2: requ red str ng act onURL
}

struct UserL st {
  1: requ red bool userB oEnabled
  2: requ red bool userB oTruncated
  3: opt onal  64 userB oMaxL nes
  4: opt onal FeedbackAct on feedbackAct on
}

struct Carousel {
  1: opt onal FeedbackAct on feedbackAct on
}

un on WTFPresentat on {
  1: UserL st userB oL st
  2: Carousel carousel
}

struct D sm ssUser d {}

un on FeedbackAct on {
 1: D sm ssUser d d sm ssUser d
}
