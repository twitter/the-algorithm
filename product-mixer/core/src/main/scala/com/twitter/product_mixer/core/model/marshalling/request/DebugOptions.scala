packagelon com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonquelonst

import com.twittelonr.util.Timelon

trait DelonbugOptions {
  // Manually ovelonrridelon thelon relonquelonst timelon which is uselonful for writing delontelonrministic Felonaturelon telonsts,
  // sincelon Felonaturelon telonsts do not support mocking Timelon. For elonxamplelon, URT sort indelonxelons start with a
  // Snowflakelon ID baselond on relonquelonst timelon if no initialSortIndelonx is selont on thelon relonquelonst cursor, so to
  // writelon a Felonaturelon telonst for this scelonnario, welon can manually selont thelon relonquelonst timelon to uselon helonrelon.
  delonf relonquelonstTimelonOvelonrridelon: Option[Timelon] = Nonelon
}

trait HasDelonbugOptions {
  delonf delonbugOptions: Option[DelonbugOptions]
}
