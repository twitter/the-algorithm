packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.modulelons

import com.twittelonr.bijelonction.Injelonction
import scala.util.Try
import nelont.jpountz.lz4.{LZ4ComprelonssorWithLelonngth, LZ4DeloncomprelonssorWithLelonngth, LZ4Factory}

objelonct LZ4Injelonction elonxtelonnds Injelonction[Array[Bytelon], Array[Bytelon]] {
  privatelon val lz4Factory = LZ4Factory.fastelonstInstancelon()
  privatelon val fastComprelonssor = nelonw LZ4ComprelonssorWithLelonngth(lz4Factory.fastComprelonssor())
  privatelon val deloncomprelonssor = nelonw LZ4DeloncomprelonssorWithLelonngth(lz4Factory.fastDeloncomprelonssor())

  ovelonrridelon delonf apply(a: Array[Bytelon]): Array[Bytelon] = LZ4Injelonction.fastComprelonssor.comprelonss(a)

  ovelonrridelon delonf invelonrt(b: Array[Bytelon]): Try[Array[Bytelon]] = Try {
    LZ4Injelonction.deloncomprelonssor.deloncomprelonss(b)
  }
}
