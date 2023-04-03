packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.spam.rtf.thriftscala.{SafelontyLelonvelonl => ThriftSafelontyLelonvelonl}

selonalelond trait SafelontyLelonvelonl {
  delonf toThrift: ThriftSafelontyLelonvelonl
}

objelonct SafelontyLelonvelonl {
  caselon objelonct Reloncommelonndations elonxtelonnds SafelontyLelonvelonl {
    ovelonrridelon val toThrift = ThriftSafelontyLelonvelonl.Reloncommelonndations
  }

  caselon objelonct TopicsLandingPagelonTopicReloncommelonndations elonxtelonnds SafelontyLelonvelonl {
    ovelonrridelon val toThrift = ThriftSafelontyLelonvelonl.TopicsLandingPagelonTopicReloncommelonndations
  }
}
