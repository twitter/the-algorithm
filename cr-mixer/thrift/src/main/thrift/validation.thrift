namespace java com.twitter.cr_mixer.thriftjava
#@namespace scala com.twitter.cr_mixer.thriftscala
#@namespace strato com.twitter.cr_mixer

// ValidationErrorCode is used to identify classes of client errors returned from a Product Mixer
// service. Use [[PipelineFailureExceptionMapper]] to adapt pipeline failures into thrift errors.
enum ValidationErrorCode {
  PRODUCT_DISABLED = 1
  PLACEHOLDER_2 = 2
} (hasPersonalData='false')

exception ValidationException {
  1: ValidationErrorCode errorCode
  2: string msg
} (hasPersonalData='false')

exception ValidationExceptionList {
  1: list<ValidationException> errors
} (hasPersonalData='false')
