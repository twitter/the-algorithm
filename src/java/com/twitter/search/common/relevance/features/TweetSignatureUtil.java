package com.twitter.search.common.relevance.features;

public final class TweetSignatureUtil {
  private TweetSignatureUtil() {
  }

  /** Converts the signature in args[0] to a TweetIntegerShingleSignature. */
  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      throw new RuntimeException("Please provide signature value.");
    }
    int signature = Integer.parseInt(args[0]);
    System.out.println(TweetIntegerShingleSignature.deserialize(signature).toString());
  }
}
