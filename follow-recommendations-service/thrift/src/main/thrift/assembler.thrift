namespace java com.twitter.follow_recommendations.thriftjava
#@namespace scala com.twitter.follow_recommendations.thriftscala
#@namespace strato com.twitter.follow_recommendations

struct Header {
 1: required Title title
}

struct Title {
 1: required string text
}

struct Footer {
 1: optional Action action
}

struct Action {
 1: required string text
 2: required string actionURL
}

struct UserList {
  1: required bool userBioEnabled
  2: required bool userBioTruncated
  3: optional i64 userBioMaxLines
  4: optional FeedbackAction feedbackAction
}

struct Carousel {
  1: optional FeedbackAction feedbackAction
}

union WTFPresentation {
  1: UserList userBioList
  2: Carousel carousel
}

struct DismissUserId {}

union FeedbackAction {
 1: DismissUserId dismissUserId
}
