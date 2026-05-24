package com.twitter.product_mixer.core.pipeline

/**
 * PipelineCursor represents any product-specific cursor model. Typically the PipelineCursor will be
 * a de-serialized base 64 thrift struct from initial request.
 */
trait PipelineCursor

/**
 * HasPipelineCursor indicates that a [[PipelineQuery]] has a cursor
 */
trait HasPipelineCursor[+Cursor <: PipelineCursor] {
  def pipelineCursor: Option[Cursor]

  /**
   * If the cursor is not present, this typically means that we are on the first page
   */
  def isFirstPage: Boolean = pipelineCursor.isEmpty
}

/**
 * UrtPipelineCursor represents a URT product-specific cursor model. Typically the UrtPipelineCursor
 * will be a de-serialized base 64 thrift struct from initial request.
 */
trait UrtPipelineCursor extends PipelineCursor {

  /** See [[UrtCursorBuilder]] for background on building initialSortIndex */
  def initialSortIndex: Long
}

object UrtPipelineCursor {
  def getCursorInitialSortIndex(query: PipelineQuery with HasPipelineCursor[_]): Option[Long] = {
    query.pipelineCursor match {
      case Some(cursor: UrtPipelineCursor) => Some(cursor.initialSortIndex)
      case _ => None
    }
  }
}
