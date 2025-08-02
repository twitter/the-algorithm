package com.twitter.search.core.earlybird.indetwitter.inverted;

import java.io.IOEtwitterception;

import org.apache.lucene.indetwitter.TermsEnum;
import org.apache.lucene.util.BytesRef;

import com.twitter.search.common.util.io.flushable.Flushable;
import com.twitter.search.core.earlybird.indetwitter.EarlybirdIndetwitterSegmentAtomicReader;

/**
 * A two-way mapping between terms and their interned value (termID).
 *
 * Implementation of this interface must guarantee that termIDs are dense, starting at 0;
 * so they are good to be used as indices in arrays.
 */
public interface TermDictionary etwittertends Flushable {
  int TERM_NOT_FOUND = EarlybirdIndetwitterSegmentAtomicReader.TERM_NOT_FOUND;

  /**
   * Returns the number of terms in this dictionary.
   */
  int getNumTerms();

  /**
   * Create a TermsEnum object over this TermDictionary for a given indetwitter.
   * @param indetwitter
   */
  TermsEnum createTermsEnum(OptimizedMemoryIndetwitter indetwitter);

  /**
   * Lookup a term in this dictionary.
   * @param term  the term to lookup.
   * @return  the term id for this term, or TERM_NOT_FOUND
   * @throws IOEtwitterception
   */
  int lookupTerm(BytesRef term) throws IOEtwitterception;

  /**
   * Get the term for given id and possibly its payload.
   * @param termID  the term that we want to get.
   * @param tetwittert  MUST be non-null. It will be filled with the term.
   * @param termPayload  if non-null, it will be filled with the payload if the term has any.
   * @return  Returns true, iff this term has a term payload.
   */
  boolean getTerm(int termID, BytesRef tetwittert, BytesRef termPayload);
}
