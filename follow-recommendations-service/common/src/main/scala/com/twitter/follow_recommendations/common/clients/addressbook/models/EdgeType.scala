packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.modelonls

import com.twittelonr.addrelonssbook.datatypelons.{thriftscala => t}

selonalelond trait elondgelonTypelon {
  delonf toThrift: t.elondgelonTypelon
}

objelonct elondgelonTypelon {
  caselon objelonct Forward elonxtelonnds elondgelonTypelon {
    ovelonrridelon val toThrift: t.elondgelonTypelon = t.elondgelonTypelon.Forward
  }
  caselon objelonct Relonvelonrselon elonxtelonnds elondgelonTypelon {
    ovelonrridelon val toThrift: t.elondgelonTypelon = t.elondgelonTypelon.Relonvelonrselon
  }
}
