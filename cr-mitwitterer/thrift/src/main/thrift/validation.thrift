namespace java com.twitter.cr_mitwitterer.thriftjava
#@namespace scala com.twitter.cr_mitwitterer.thriftscala
#@namespace strato com.twitter.cr_mitwitterer

// ValidationErrorCode is used to identify classes of client errors returned from a Product Mitwitterer
// service. Use [[PipelineFailureEtwitterceptionMapper]] to adapt pipeline failures into thrift errors.
enum ValidationErrorCode {
  PRODUCT_DISABLED = 1
  PLACEHOLDER_2 = 2
} (hasPersonalData='false')

etwitterception ValidationEtwitterception {
  1: ValidationErrorCode errorCode
  2: string msg
} (hasPersonalData='false')

etwitterception ValidationEtwitterceptionList {
  1: list<ValidationEtwitterception> errors
} (hasPersonalData='false')
