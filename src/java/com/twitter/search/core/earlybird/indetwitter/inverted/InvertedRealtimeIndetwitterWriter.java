package com.twitter.search.core.earlybird.indetwitter.inverted;

import com.google.common.base.Preconditions;

import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.BytesRef;

import com.twitter.search.common.hashtable.HashTable;
import com.twitter.search.common.util.analysis.TermPayloadAttribute;
import com.twitter.search.core.earlybird.facets.FacetCountingArrayWriter;
import com.twitter.search.core.earlybird.facets.FacetIDMap.FacetField;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdRealtimeIndetwitterSegmentWriter;

public class InvertedRealtimeIndetwitterWriter
    implements EarlybirdRealtimeIndetwitterSegmentWriter.InvertedDocConsumer {
  private final InvertedRealtimeIndetwitter invertedIndetwitter;
  private final FacetCountingArrayWriter facetArray;
  private final FacetField facetField;

  private TermToBytesRefAttribute termAtt;
  private TermPayloadAttribute termPayloadAtt;
  private PayloadAttribute payloadAtt;
  private boolean currentDocIsOffensive;

  /**
   * Creates a new writer for writing to an inverted in-memory real-time indetwitter.
   */
  public InvertedRealtimeIndetwitterWriter(
          InvertedRealtimeIndetwitter indetwitter,
          FacetField facetField,
          FacetCountingArrayWriter facetArray) {
    super();
    this.invertedIndetwitter = indetwitter;
    this.facetArray = facetArray;
    this.facetField = facetField;
  }

  @Override
  public void start(AttributeSource attributeSource, boolean docIsOffensive) {
    termAtt = attributeSource.addAttribute(TermToBytesRefAttribute.class);
    termPayloadAtt = attributeSource.addAttribute(TermPayloadAttribute.class);
    payloadAtt = attributeSource.addAttribute(PayloadAttribute.class);
    currentDocIsOffensive = docIsOffensive;
  }

  /**
   * Adds a posting to the provided inverted indetwitter.
   *
   * @param termBytesRef is a payload that is stored with the term. It is only stored once for each
   *                     term.
   * @param postingPayload is a byte payload that will be stored separately for every posting.
   * @return term id of the added posting.
   */
  public static int indetwitterTerm(InvertedRealtimeIndetwitter invertedIndetwitter, BytesRef termBytesRef,
      int docID, int position, BytesRef termPayload,
      BytesRef postingPayload, TermPointerEncoding termPointerEncoding) {

    InvertedRealtimeIndetwitter.TermHashTable hashTable = invertedIndetwitter.getHashTable();
    BaseByteBlockPool termPool = invertedIndetwitter.getTermPool();

    TermsArray termsArray = invertedIndetwitter.getTermsArray();

    long hashTableInfoForBytesRef = hashTable.lookupItem(termBytesRef);
    int termID = HashTable.decodeItemId(hashTableInfoForBytesRef);
    int hashTableSlot = HashTable.decodeHashPosition(hashTableInfoForBytesRef);

    invertedIndetwitter.adjustMatwitterPosition(position);

    if (termID == HashTable.EMPTY_SLOT) {
      // First time we are seeing this token since we last flushed the hash.
      // the LSB in tetwittertStart denotes whether this term has a term payload
      int tetwittertStart = ByteTermUtils.copyToTermPool(termPool, termBytesRef);
      boolean hasTermPayload = termPayload != null;
      int termPointer = termPointerEncoding.encodeTermPointer(tetwittertStart, hasTermPayload);

      if (hasTermPayload) {
        ByteTermUtils.copyToTermPool(termPool, termPayload);
      }

      termID = invertedIndetwitter.getNumTerms();
      invertedIndetwitter.incrementNumTerms();
      if (termID >= termsArray.getSize()) {
        termsArray = invertedIndetwitter.growTermsArray();
      }

      termsArray.termPointers[termID] = termPointer;

      Preconditions.checkState(hashTable.slots()[hashTableSlot] == HashTable.EMPTY_SLOT);
      hashTable.setSlot(hashTableSlot, termID);

      if (invertedIndetwitter.getNumTerms() * 2 >= hashTable.numSlots()) {
        invertedIndetwitter.rehashPostings(2 * hashTable.numSlots());
      }

      // Insert termID into termsSkipList.
      invertedIndetwitter.insertToTermsSkipList(termBytesRef, termID);
    }

    invertedIndetwitter.incrementSumTotalTermFreq();
    invertedIndetwitter.getPostingList()
        .appendPosting(termID, termsArray, docID, position, postingPayload);

    return termID;
  }

  /**
   * Delete a posting that was inserted out of order.
   *
   * This function needs work before it is used in production:
   * - It should take an isDocOffensive parameter so we can decrement the offensive
   *   document count for the term.
   * - It doesn't allow the same concurrency guarantees that the other posting methods do.
   */
  public static void deletePosting(
      InvertedRealtimeIndetwitter invertedIndetwitter, BytesRef termBytesRef, int docID) {

    long hashTableInfoForBytesRef = invertedIndetwitter.getHashTable().lookupItem(termBytesRef);
    int termID = HashTable.decodeItemId(hashTableInfoForBytesRef);

    if (termID != HashTable.EMPTY_SLOT) {
      // Have seen this term before, and the field that supports deletes.
      invertedIndetwitter.getPostingList().deletePosting(termID, invertedIndetwitter.getTermsArray(), docID);
    }
  }

  @Override
  public void add(int docID, int position) {
    final BytesRef payload;
    if (payloadAtt == null) {
      payload = null;
    } else {
      payload = payloadAtt.getPayload();
    }

    BytesRef termPayload = termPayloadAtt.getTermPayload();

    int termID = indetwitterTerm(invertedIndetwitter, termAtt.getBytesRef(),
        docID, position, termPayload, payload,
        invertedIndetwitter.getTermPointerEncoding());

    if (termID == -1) {
      return;
    }

    TermsArray termsArray = invertedIndetwitter.getTermsArray();

    if (currentDocIsOffensive && termsArray.offensiveCounters != null) {
      termsArray.offensiveCounters[termID]++;
    }

    if (facetField != null) {
      facetArray.addFacet(docID, facetField.getFacetId(), termID);
    }
  }

  @Override
  public void finish() {
    payloadAtt = null;
    termPayloadAtt = null;
  }
}
