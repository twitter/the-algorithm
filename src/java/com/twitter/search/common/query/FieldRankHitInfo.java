package com.twitter.search.common.query;

/**
 * When a hit (on a part of the query tree) occurs, this class is passed to HitAttributeCollector
 * for collection.
 *
 * This implementation carries the following info:
 * <ul>
 *   <li>The field that matched (the field ID is recorded)</li>
 *   <li>The query node that matched (the query node rank is recorded)</li>
 *   <li>The ID of the last doc that matched this query</li>
 * </ul>
 *
 * Each IdentifiableQuery should be associated with one FieldRankHitInfo, which is passed to a
 * HitAttributeCollector when a hit occurs.
 */
public class FieldRankHitInfo {
  protected static final int UNSET_DOC_ID = -1;

  private final int fieldId;
  private final int rank;
  private int docId = UNSET_DOC_ID;

  public FieldRankHitInfo(int fieldId, int rank) {
    this.fieldId = fieldId;
    this.rank = rank;
  }

  public int getFieldId() {
    return fieldId;
  }

  public int getRank() {
    return rank;
  }

  public int getDocId() {
    return docId;
  }

  public void setDocId(int docId) {
    this.docId = docId;
  }

  public void resetDocId() {
    this.docId = UNSET_DOC_ID;
  }
}
