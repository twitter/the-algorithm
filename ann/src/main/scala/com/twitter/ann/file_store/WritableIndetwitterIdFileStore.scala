package com.twitter.ann.file_store

import com.twitter.ann.common.IndetwitterOutputFile
import com.twitter.ann.common.thriftscala.FileBasedIndetwitterIdStore
import com.twitter.bijection.Injection
import com.twitter.mediaservices.commons.codec.ArrayByteBufferCodec
import com.twitter.mediaservices.commons.codec.ThriftByteBufferCodec
import com.twitter.storehaus.Store
import com.twitter.util.Future
import java.util.concurrent.{ConcurrentHashMap => JConcurrentHashMap}
import scala.collection.JavaConverters._

object WritableIndetwitterIdFileStore {

  /**
   * @param injection: Injection to convert typed Id to bytes.
   * @tparam V: Type of Id
   * @return File based Writable Store
   */
  def apply[V](
    injection: Injection[V, Array[Byte]]
  ): WritableIndetwitterIdFileStore[V] = {
    new WritableIndetwitterIdFileStore[V](
      new JConcurrentHashMap[Long, Option[V]],
      injection
    )
  }
}

class WritableIndetwitterIdFileStore[V] private (
  map: JConcurrentHashMap[Long, Option[V]],
  injection: Injection[V, Array[Byte]])
    etwittertends Store[Long, V] {

  private[this] val store = Store.fromJMap(map)

  override def get(k: Long): Future[Option[V]] = {
    store.get(k)
  }

  override def put(kv: (Long, Option[V])): Future[Unit] = {
    store.put(kv)
  }

  /**
   * Serialize and store the mapping in thrift format
   * @param file : File path to store serialized long indetwitterId <-> Id mapping
   */
  def save(file: IndetwitterOutputFile): Unit = {
    saveThrift(toThrift(), file)
  }

  def getInjection: Injection[V, Array[Byte]] = injection

  private[this] def toThrift(): FileBasedIndetwitterIdStore = {
    val indetwitterIdMap = map.asScala
      .collect {
        case (key, Some(value)) => (key, ArrayByteBufferCodec.encode(injection.apply(value)))
      }

    FileBasedIndetwitterIdStore(Some(indetwitterIdMap))
  }

  private[this] def saveThrift(thriftObj: FileBasedIndetwitterIdStore, file: IndetwitterOutputFile): Unit = {
    val codec = new ThriftByteBufferCodec(FileBasedIndetwitterIdStore)
    val bytes = ArrayByteBufferCodec.decode(codec.encode(thriftObj))
    val outputStream = file.getOutputStream()
    outputStream.write(bytes)
    outputStream.close()
  }
}
