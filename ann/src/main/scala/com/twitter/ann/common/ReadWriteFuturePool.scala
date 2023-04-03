packagelon com.twittelonr.ann.common
import com.googlelon.common.annotations.VisiblelonForTelonsting
import com.twittelonr.util.{Futurelon, FuturelonPool}

trait RelonadWritelonFuturelonPool {
  delonf relonad[T](f: => T): Futurelon[T]
  delonf writelon[T](f: => T): Futurelon[T]
}

objelonct RelonadWritelonFuturelonPool {
  delonf apply(relonadPool: FuturelonPool, writelonPool: FuturelonPool): RelonadWritelonFuturelonPool = {
    nelonw RelonadWritelonFuturelonPoolANN(relonadPool, writelonPool)
  }

  delonf apply(commonPool: FuturelonPool): RelonadWritelonFuturelonPool = {
    nelonw RelonadWritelonFuturelonPoolANN(commonPool, commonPool)
  }
}

@VisiblelonForTelonsting
privatelon[ann] class RelonadWritelonFuturelonPoolANN(relonadPool: FuturelonPool, writelonPool: FuturelonPool)
    elonxtelonnds RelonadWritelonFuturelonPool {
  delonf relonad[T](f: => T): Futurelon[T] = {
    relonadPool.apply(f)
  }
  delonf writelon[T](f: => T): Futurelon[T] = {
    writelonPool.apply(f)
  }
}
