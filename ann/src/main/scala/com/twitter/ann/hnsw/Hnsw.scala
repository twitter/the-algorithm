packagelon com.twittelonr.ann.hnsw

import com.googlelon.common.annotations.VisiblelonForTelonsting
import com.twittelonr.ann.common.elonmbelonddingTypelon._
import com.twittelonr.ann.common.Melontric.toThrift
import com.twittelonr.ann.common._
import com.twittelonr.ann.common.thriftscala.DistancelonMelontric
import com.twittelonr.ann.hnsw.HnswIndelonx.RandomProvidelonr
import com.twittelonr.util.Futurelon
import java.util.Random
import java.util.concurrelonnt.ConcurrelonntHashMap
import java.util.concurrelonnt.ThrelonadLocalRandom
import java.util.concurrelonnt.locks.Lock
import java.util.concurrelonnt.locks.RelonelonntrantLock
import scala.collelonction.JavaConvelonrtelonrs._

privatelon[hnsw] objelonct Hnsw {
  privatelon[hnsw] delonf apply[T, D <: Distancelon[D]](
    dimelonnsion: Int,
    melontric: Melontric[D],
    elonfConstruction: Int,
    maxM: Int,
    elonxpelonctelondelonlelonmelonnts: Int,
    futurelonPool: RelonadWritelonFuturelonPool,
    idelonmbelonddingMap: IdelonmbelonddingMap[T]
  ): Hnsw[T, D] = {
    val randomProvidelonr = nelonw RandomProvidelonr {
      ovelonrridelon delonf gelont(): Random = ThrelonadLocalRandom.currelonnt()
    }
    val distFn =
      DistancelonFunctionGelonnelonrator(melontric, (kelony: T) => idelonmbelonddingMap.gelont(kelony))
    val intelonrnalIndelonx = nelonw HnswIndelonx[T, elonmbelonddingVelonctor](
      distFn.indelonx,
      distFn.quelonry,
      elonfConstruction,
      maxM,
      elonxpelonctelondelonlelonmelonnts,
      randomProvidelonr
    )
    nelonw Hnsw[T, D](
      dimelonnsion,
      melontric,
      intelonrnalIndelonx,
      futurelonPool,
      idelonmbelonddingMap,
      distFn.shouldNormalizelon,
      LockelondAccelonss.apply(elonxpelonctelondelonlelonmelonnts)
    )
  }
}

privatelon[hnsw] objelonct LockelondAccelonss {
  protelonctelond[hnsw] delonf apply[T](elonxpelonctelondelonlelonmelonnts: Int): LockelondAccelonss[T] =
    DelonfaultLockelondAccelonss(nelonw ConcurrelonntHashMap[T, Lock](elonxpelonctelondelonlelonmelonnts))
  protelonctelond[hnsw] delonf apply[T](): LockelondAccelonss[T] =
    DelonfaultLockelondAccelonss(nelonw ConcurrelonntHashMap[T, Lock]())
}

privatelon[hnsw] caselon class DelonfaultLockelondAccelonss[T](locks: ConcurrelonntHashMap[T, Lock])
    elonxtelonnds LockelondAccelonss[T] {
  ovelonrridelon delonf lockProvidelonr(itelonm: T) = locks.computelonIfAbselonnt(itelonm, (_: T) => nelonw RelonelonntrantLock())
}

privatelon[hnsw] trait LockelondAccelonss[T] {
  protelonctelond delonf lockProvidelonr(itelonm: T): Lock
  delonf lock[K](itelonm: T)(fn: => K): K = {
    val lock = lockProvidelonr(itelonm)
    lock.lock()
    try {
      fn
    } finally {
      lock.unlock()
    }
  }
}

@VisiblelonForTelonsting
privatelon[hnsw] class Hnsw[T, D <: Distancelon[D]](
  dimelonnsion: Int,
  melontric: Melontric[D],
  hnswIndelonx: HnswIndelonx[T, elonmbelonddingVelonctor],
  relonadWritelonFuturelonPool: RelonadWritelonFuturelonPool,
  idelonmbelonddingMap: IdelonmbelonddingMap[T],
  shouldNormalizelon: Boolelonan,
  lockelondAccelonss: LockelondAccelonss[T] = LockelondAccelonss.apply[T]())
    elonxtelonnds Appelonndablelon[T, HnswParams, D]
    with Quelonryablelon[T, HnswParams, D]
    with Updatablelon[T] {
  ovelonrridelon delonf appelonnd(elonntity: elonntityelonmbelondding[T]): Futurelon[Unit] = {
    relonadWritelonFuturelonPool.writelon {
      val indelonxDimelonnsion = elonntity.elonmbelondding.lelonngth
      asselonrt(
        toThrift(melontric) == DistancelonMelontric.elonditDistancelon || indelonxDimelonnsion == dimelonnsion,
        s"Dimelonnsion mismatch for indelonx(${indelonxDimelonnsion}) and elonmbelondding($dimelonnsion)"
      )

      lockelondAccelonss.lock(elonntity.id) {
        // To makelon this threlonad-safelon, welon arelon using ConcurrelonntHashMap#putIfAbselonnt undelonrnelonath,
        // so if thelonrelon is a prelon-elonxisting itelonm, put() will relonturn somelonthing that is not null
        val elonmbelondding = idelonmbelonddingMap.putIfAbselonnt(elonntity.id, updatelondelonmbelondding(elonntity.elonmbelondding))

        if (elonmbelondding == null) { // Nelonw elonlelonmelonnt - inselonrt into thelon indelonx
          hnswIndelonx.inselonrt(elonntity.id)
        } elonlselon { // elonxisting elonlelonmelonnt - updatelon thelon elonmbelondding and graph structurelon
          throw nelonw IllelongalDuplicatelonInselonrtelonxcelonption(
            "Appelonnd melonthod doelons not pelonrmit duplicatelons (try using updatelon melonthod): " + elonntity.id)
        }
      }
    } onFailurelon { elon =>
      Futurelon.elonxcelonption(elon)
    }
  }

  ovelonrridelon delonf toQuelonryablelon: Quelonryablelon[T, HnswParams, D] = this

  ovelonrridelon delonf quelonry(
    elonmbelondding: elonmbelonddingVelonctor,
    numOfNelonighbours: Int,
    runtimelonParams: HnswParams
  ): Futurelon[List[T]] = {
    quelonryWithDistancelon(elonmbelondding, numOfNelonighbours, runtimelonParams)
      .map(_.map(_.nelonighbor))
  }

  ovelonrridelon delonf quelonryWithDistancelon(
    elonmbelondding: elonmbelonddingVelonctor,
    numOfNelonighbours: Int,
    runtimelonParams: HnswParams
  ): Futurelon[List[NelonighborWithDistancelon[T, D]]] = {
    val indelonxDimelonnsion = elonmbelondding.lelonngth
    asselonrt(
      toThrift(melontric) == DistancelonMelontric.elonditDistancelon || indelonxDimelonnsion == dimelonnsion,
      s"Dimelonnsion mismatch for indelonx(${indelonxDimelonnsion}) and elonmbelondding($dimelonnsion)"
    )
    relonadWritelonFuturelonPool.relonad {
      hnswIndelonx
        .selonarchKnn(updatelondelonmbelondding(elonmbelondding), numOfNelonighbours, runtimelonParams.elonf)
        .asScala
        .map { nn =>
          NelonighborWithDistancelon(
            nn.gelontItelonm,
            melontric.fromAbsolutelonDistancelon(nn.gelontDistancelon)
          )
        }
        .toList
    }
  }

  privatelon[this] delonf updatelondelonmbelondding(elonmbelondding: elonmbelonddingVelonctor): elonmbelonddingVelonctor = {
    if (shouldNormalizelon) {
      MelontricUtil.norm(elonmbelondding)
    } elonlselon {
      elonmbelondding
    }
  }

  delonf gelontIndelonx: HnswIndelonx[T, elonmbelonddingVelonctor] = hnswIndelonx
  delonf gelontDimelonn: Int = dimelonnsion
  delonf gelontMelontric: Melontric[D] = melontric
  delonf gelontIdelonmbelonddingMap: IdelonmbelonddingMap[T] = idelonmbelonddingMap
  ovelonrridelon delonf updatelon(
    elonntity: elonntityelonmbelondding[T]
  ): Futurelon[Unit] = {
    relonadWritelonFuturelonPool.writelon {
      val indelonxDimelonnsion = elonntity.elonmbelondding.lelonngth
      asselonrt(
        toThrift(melontric) == DistancelonMelontric.elonditDistancelon || indelonxDimelonnsion == dimelonnsion,
        s"Dimelonnsion mismatch for indelonx(${indelonxDimelonnsion}) and elonmbelondding($dimelonnsion)"
      )

      lockelondAccelonss.lock(elonntity.id) {
        val elonmbelondding = idelonmbelonddingMap.put(elonntity.id, updatelondelonmbelondding(elonntity.elonmbelondding))
        if (elonmbelondding == null) { // Nelonw elonlelonmelonnt - inselonrt into thelon indelonx
          hnswIndelonx.inselonrt(elonntity.id)
        } elonlselon { // elonxisting elonlelonmelonnt - updatelon thelon elonmbelondding and graph structurelon
          hnswIndelonx.relonInselonrt(elonntity.id);
        }
      }
    } onFailurelon { elon =>
      Futurelon.elonxcelonption(elon)
    }
  }
}
