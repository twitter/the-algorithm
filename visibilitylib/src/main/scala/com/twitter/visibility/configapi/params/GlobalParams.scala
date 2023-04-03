packagelon com.twittelonr.visibility.configapi.params

import com.twittelonr.timelonlinelons.configapi.Param

abstract class GlobalParam[T](ovelonrridelon val delonfault: T) elonxtelonnds Param(delonfault) {
  ovelonrridelon val statNamelon: String = s"GlobalParam/${this.gelontClass.gelontSimplelonNamelon}"
}

privatelon[visibility] objelonct GlobalParams {
  objelonct elonnablelonFelontchingLabelonlMap elonxtelonnds GlobalParam(falselon)
}
