package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.richtetwittert.twitter_tetwittert

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.ReferenceObject
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertAlignment
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertEntity
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertFormat
import scala.annotation.tailrec
import scala.collection.mutable

object TwitterTetwittertRenderer {

  /**
   * Creates a new [[TwitterTetwittertRenderer]] instance.
   * @param tetwittert      The initial tetwittert representation
   * @param rtl       Defines whether this tetwittert is in an RTL language
   * @param alignment Assigns the [[RichTetwittertAlignment]] of the given tetwittert for display
   * @return          A new [[TwitterTetwittertRenderer]] instance
   */
  def apply(
    tetwittert: String,
    rtl: Option[Boolean] = None,
    alignment: Option[RichTetwittertAlignment] = None
  ): TwitterTetwittertRenderer = {
    TwitterTetwittertRenderer(rtl, alignment).append(tetwittert)
  }

  /**
   * Creates a new [[TwitterTetwittertRenderer]] instance from a product-mitwitterer [[RichTetwittert]] object.
   * Converts Unicode entity indetwitteres into JVM String indetwitteres.
   * @param richTetwittert  The product-mitwitterer [[RichTetwittert]] representation
   * @return          A new [[TwitterTetwittertRenderer]] instance
   */
  def fromRichTetwittert(richTetwittert: RichTetwittert): TwitterTetwittertRenderer = {
    val builder = TwitterTetwittertRenderer(richTetwittert.tetwittert, richTetwittert.rtl, richTetwittert.alignment)
    richTetwittert.entities.foreach { e =>
      val startIndetwitter = richTetwittert.tetwittert.offsetByCodePoints(0, e.fromIndetwitter)
      val endIndetwitter = richTetwittert.tetwittert.offsetByCodePoints(0, e.toIndetwitter)
      e.format.foreach { f =>
        builder.setFormat(startIndetwitter, endIndetwitter, f)
      }
      e.ref.foreach { r =>
        builder.setRefObject(startIndetwitter, endIndetwitter, r)
      }
    }
    builder
  }

  private def buildRichTetwittertEntity(
    tetwittert: String,
    entity: TwitterTetwittertRendererEntity[_]
  ): RichTetwittertEntity = {
    val fromIndetwitter = tetwittert.codePointCount(0, entity.startIndetwitter)
    val toIndetwitter = tetwittert.codePointCount(0, entity.endIndetwitter)

    entity.value match {
      case format: RichTetwittertFormat =>
        RichTetwittertEntity(fromIndetwitter, toIndetwitter, ref = None, format = Some(format))
      case ref: ReferenceObject =>
        RichTetwittertEntity(fromIndetwitter, toIndetwitter, ref = Some(ref), format = None)
    }
  }
}

case class TwitterTetwittertRenderer(
  rtl: Option[Boolean],
  alignment: Option[RichTetwittertAlignment],
) {
  private[this] val tetwittertBuilder = new mutable.StringBuilder()

  private[richtetwittert] val formatBuffer =
    mutable.ArrayBuffer[TwitterTetwittertRendererEntity[RichTetwittertFormat]]()
  private[richtetwittert] val refObjectBuffer =
    mutable.ArrayBuffer[TwitterTetwittertRendererEntity[ReferenceObject]]()

  /**
   * Appends a string with attached [[RichTetwittertFormat]] and [[ReferenceObject]] information.
   * @param string    The tetwittert to append to the end of the etwitteristing tetwittert
   * @param format    The [[RichTetwittertFormat]] assigned to the new tetwittert
   * @param refObject The [[ReferenceObject]] assigned to the new tetwittert
   * @return          this
   */
  def append(
    string: String,
    format: Option[RichTetwittertFormat] = None,
    refObject: Option[ReferenceObject] = None
  ): TwitterTetwittertRenderer = {
    if (string.nonEmpty) {
      val start = tetwittertBuilder.length
      val end = start + string.length
      format.foreach { f =>
        formatBuffer.append(TwitterTetwittertRendererEntity(start, end, f))
      }
      refObject.foreach { r =>
        refObjectBuffer.append(TwitterTetwittertRendererEntity(start, end, r))
      }
      tetwittertBuilder.append(string)
    }
    this
  }

  /**
   * Builds a new [[RichTetwittert]] thrift instance with Unicode entity ranges.
   */
  def build: RichTetwittert = {
    val richTetwittertString = this.tetwittert
    val richTetwittertEntities = this.entities
      .map { e =>
        TwitterTetwittertRenderer.buildRichTetwittertEntity(richTetwittertString, e)
      }

    RichTetwittert(
      tetwittert = richTetwittertString,
      rtl = rtl,
      alignment = alignment,
      entities = richTetwittertEntities.toList
    )
  }

  /**
   * Modifies the TwitterTetwittertRenderer with the provided [[TwitterTetwittertRendererProcessor]]
   */
  def transform(twitterTetwittertProcessor: TwitterTetwittertRendererProcessor): TwitterTetwittertRenderer = {
    twitterTetwittertProcessor.process(this)
  }

  /**
   * Builds and returns a sorted list of [[TwitterTetwittertRendererEntity]] with JVM String indetwitter entity ranges.
   */
  def entities: Seq[TwitterTetwittertRendererEntity[_]] = {
    buildEntities(formatBuffer.toList, refObjectBuffer.toList)
  }

  /**
   * Assigns a [[RichTetwittertFormat]] to the given range while keeping etwitteristing formatting information.
   * New formatting will only be assigned to unformatted tetwittert ranges.
   * @param start  Start indetwitter to apply formatting (inclusive)
   * @param end    End indetwitter to apply formatting (etwitterclusive)
   * @param format The format to assign
   * @return       this
   */
  def mergeFormat(start: Int, end: Int, format: RichTetwittertFormat): TwitterTetwittertRenderer = {
    validateRange(start, end)
    var injectionIndetwitter: Option[Int] = None
    var entity = TwitterTetwittertRendererEntity(start, end, format)

    val buffer = mutable.ArrayBuffer[TwitterTetwittertRendererEntity[RichTetwittertFormat]]()
    val iterator = formatBuffer.zipWithIndetwitter.reverseIterator

    while (iterator.hasNetwittert && injectionIndetwitter.isEmpty) {
      iterator.netwittert match {
        case (e, i) if e.startIndetwitter >= end =>
          buffer.append(e)

        case (e, i) if e.enclosedIn(entity.startIndetwitter, entity.endIndetwitter) =>
          val endEntity = entity.copy(startIndetwitter = e.endIndetwitter)
          if (endEntity.nonEmpty) { buffer.append(endEntity) }
          buffer.append(e)
          entity = entity.copy(endIndetwitter = e.startIndetwitter)

        case (e, i) if e.encloses(entity.startIndetwitter, entity.endIndetwitter) =>
          buffer.append(e.copy(startIndetwitter = entity.endIndetwitter))
          buffer.append(e.copy(endIndetwitter = entity.startIndetwitter))
          injectionIndetwitter = Some(i + 1)

        case (e, i) if e.startsBetween(entity.startIndetwitter, entity.endIndetwitter) =>
          buffer.append(e)
          entity = entity.copy(endIndetwitter = e.startIndetwitter)

        case (e, i) if e.endsBetween(entity.startIndetwitter, entity.endIndetwitter) =>
          buffer.append(e)
          entity = entity.copy(startIndetwitter = e.endIndetwitter)
          injectionIndetwitter = Some(i + 1)

        case (e, i) if e.endIndetwitter <= entity.startIndetwitter =>
          buffer.append(e)
          injectionIndetwitter = Some(i + 1)

        case _ => // do nothing
      }
    }

    val indetwitter = injectionIndetwitter.map(_ - 1).getOrElse(0)
    formatBuffer.remove(indetwitter, formatBuffer.length - indetwitter)
    formatBuffer.appendAll(buffer.reverse)

    if (entity.nonEmpty) {
      formatBuffer.insert(injectionIndetwitter.getOrElse(0), entity)
    }

    this
  }

  /**
   * Removes tetwittert, formatting, and refObject information from the given range.
   * @param start  Start indetwitter to apply formatting (inclusive)
   * @param end    End indetwitter to apply formatting (etwitterclusive)
   * @return       this
   */
  def remove(start: Int, end: Int): TwitterTetwittertRenderer = replace(start, end, "")

  /**
   * Replaces tetwittert, formatting, and refObject information in the given range.
   * @param start     Start indetwitter to apply formatting (inclusive)
   * @param end       End indetwitter to apply formatting (etwitterclusive)
   * @param string    The new tetwittert to insert
   * @param format    The [[RichTetwittertFormat]] assigned to the new tetwittert
   * @param refObject The [[ReferenceObject]] assigned to the new tetwittert
   * @return          this
   */
  def replace(
    start: Int,
    end: Int,
    string: String,
    format: Option[RichTetwittertFormat] = None,
    refObject: Option[ReferenceObject] = None
  ): TwitterTetwittertRenderer = {
    validateRange(start, end)

    val newEnd = start + string.length
    val formatInjectIndetwitter = removeAndOffsetFormats(start, end, string.length)
    val refObjectInjectIndetwitter = removeAndOffsetRefObjects(start, end, string.length)
    format.foreach { f =>
      formatBuffer.insert(formatInjectIndetwitter, TwitterTetwittertRendererEntity(start, newEnd, f))
    }
    refObject.foreach { r =>
      refObjectBuffer.insert(refObjectInjectIndetwitter, TwitterTetwittertRendererEntity(start, newEnd, r))
    }
    tetwittertBuilder.replace(start, end, string)

    this
  }

  /**
   * Assigns a [[RichTetwittertFormat]] to the given range. Trims etwitteristing format ranges that overlap the
   * new format range. Removes format ranges that fall within the new range.
   * @param start  Start indetwitter to apply formatting (inclusive)
   * @param end    End indetwitter to apply formatting (etwitterclusive)
   * @param format The format to assign
   * @return       this
   */
  def setFormat(start: Int, end: Int, format: RichTetwittertFormat): TwitterTetwittertRenderer = {
    validateRange(start, end)
    val bufferIndetwitter = removeAndOffsetFormats(start, end, end - start)
    formatBuffer.insert(bufferIndetwitter, TwitterTetwittertRendererEntity(start, end, format))

    this
  }

  private[this] def removeAndOffsetFormats(start: Int, end: Int, newSize: Int): Int = {
    val newEnd = start + newSize
    val offset = newEnd - end
    var injectionIndetwitter: Option[Int] = None

    val buffer = mutable.ArrayBuffer[TwitterTetwittertRendererEntity[RichTetwittertFormat]]()
    val iterator = formatBuffer.zipWithIndetwitter.reverseIterator

    while (iterator.hasNetwittert && injectionIndetwitter.isEmpty) {
      iterator.netwittert match {
        case (e, i) if e.startIndetwitter >= end =>
          buffer.append(e.offset(offset))
        case (e, i) if e.encloses(start, end) =>
          buffer.append(e.copy(startIndetwitter = newEnd, endIndetwitter = e.endIndetwitter + offset))
          buffer.append(e.copy(endIndetwitter = e.endIndetwitter + offset))
          injectionIndetwitter = Some(i + 1)
        case (e, i) if e.endsBetween(start, end) =>
          buffer.append(e.copy(endIndetwitter = start))
          injectionIndetwitter = Some(i + 1)
        case (e, i) if e.startsBetween(start, end) =>
          buffer.append(e.copy(startIndetwitter = newEnd, endIndetwitter = e.endIndetwitter + offset))
        case (e, i) if e.endIndetwitter <= start =>
          buffer.append(e)
          injectionIndetwitter = Some(i + 1)
        case _ => // do nothing
      }
    }
    val indetwitter = injectionIndetwitter.map(_ - 1).getOrElse(0)
    formatBuffer.remove(indetwitter, formatBuffer.length - indetwitter)
    formatBuffer.appendAll(buffer.reverse)

    injectionIndetwitter.getOrElse(0)
  }

  private[this] def validateRange(start: Int, end: Int): Unit = {
    require(
      start >= 0 && start < tetwittertBuilder.length && end > start && end <= tetwittertBuilder.length,
      s"The start ($start) and end ($end) indetwitteres must be within the tetwittert range (0..${tetwittertBuilder.length})"
    )
  }

  /**
   * Assigns a [[ReferenceObject]] to the given range. Since it makes little sense to trim object
   * ranges, etwitteristing intersecting or overlapping ranges are removed entirely.
   * @param start  Start indetwitter to apply formatting (inclusive)
   * @param end       End indetwitter to apply formatting (etwitterclusive)
   * @param refObject The [[ReferenceObject]] to assign
   * @return          this
   */
  def setRefObject(start: Int, end: Int, refObject: ReferenceObject): TwitterTetwittertRenderer = {
    validateRange(start, end)
    val bufferIndetwitter = removeAndOffsetRefObjects(start, end, end - start)
    refObjectBuffer.insert(bufferIndetwitter, TwitterTetwittertRendererEntity(start, end, refObject))

    this
  }

  private[this] def removeAndOffsetRefObjects(start: Int, end: Int, newSize: Int): Int = {
    val newEnd = start + newSize
    val offset = newEnd - end
    var injectionIndetwitter: Option[Int] = None

    val buffer = mutable.ArrayBuffer[TwitterTetwittertRendererEntity[ReferenceObject]]()
    val iterator = refObjectBuffer.zipWithIndetwitter.reverseIterator

    while (iterator.hasNetwittert && injectionIndetwitter.isEmpty) {
      iterator.netwittert match {
        case (e, i) if e.startIndetwitter >= end => buffer.append(e.offset(offset))
        case (e, i) if e.endIndetwitter <= start => injectionIndetwitter = Some(i + 1)
        case _ => // do nothing
      }
    }
    val indetwitter = injectionIndetwitter.getOrElse(0)
    refObjectBuffer.remove(indetwitter, refObjectBuffer.length - indetwitter)
    refObjectBuffer.appendAll(buffer.reverse)

    indetwitter
  }

  /**
   * Builds and returns the full TwitterTetwittertRenderer tetwittert with any changes applied to the builder instance.
   */
  def tetwittert: String = {
    tetwittertBuilder.mkString
  }

  @tailrec
  private def buildEntities(
    formats: List[TwitterTetwittertRendererEntity[RichTetwittertFormat]],
    refs: List[TwitterTetwittertRendererEntity[ReferenceObject]],
    acc: List[TwitterTetwittertRendererEntity[_]] = List()
  ): Seq[TwitterTetwittertRendererEntity[_]] = {
    (formats, refs) match {
      case (Nil, Nil) => acc
      case (remainingFormats, Nil) => acc ++ remainingFormats
      case (Nil, remainingRefs) => acc ++ remainingRefs

      case (format +: remainingFormats, ref +: remainingRefs)
          if format.startIndetwitter < ref.startIndetwitter || (format.startIndetwitter == ref.startIndetwitter && format.endIndetwitter < ref.endIndetwitter) =>
        buildEntities(remainingFormats, refs, acc :+ format)

      case (format +: remainingFormats, ref +: remainingRefs)
          if format.startIndetwitter == ref.startIndetwitter && format.endIndetwitter == ref.endIndetwitter =>
        buildEntities(remainingFormats, remainingRefs, acc :+ format :+ ref)

      case (_, ref +: remainingRefs) =>
        buildEntities(formats, remainingRefs, acc :+ ref)
    }
  }
}

case class TwitterTetwittertRendererEntity[+T] private[richtetwittert] (
  startIndetwitter: Int,
  endIndetwitter: Int,
  value: T) {
  require(startIndetwitter <= endIndetwitter, "startIndetwitter must be <= than endIndetwitter")

  def nonEmpty: Boolean = !isEmpty

  def isEmpty: Boolean = startIndetwitter == endIndetwitter

  private[richtetwittert] def enclosedIn(start: Int, end: Int): Boolean = {
    start <= startIndetwitter && endIndetwitter <= end
  }

  private[richtetwittert] def encloses(start: Int, end: Int): Boolean = {
    startIndetwitter < start && end < endIndetwitter
  }

  private[richtetwittert] def endsBetween(start: Int, end: Int): Boolean = {
    start < endIndetwitter && endIndetwitter <= end && startIndetwitter < start
  }

  private[richtetwittert] def offset(num: Int): TwitterTetwittertRendererEntity[T] = {
    copy(startIndetwitter = startIndetwitter + num, endIndetwitter = endIndetwitter + num)
  }

  private[richtetwittert] def startsBetween(start: Int, end: Int): Boolean = {
    startIndetwitter >= start && startIndetwitter < end && endIndetwitter > end
  }
}
