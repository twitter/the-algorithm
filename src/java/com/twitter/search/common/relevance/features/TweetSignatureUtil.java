package com.twitter.search.common.relevance.features;

public final class TweetSignatureUtil {
  private TweetSignatureUtil() {
  }

  /** Converts the signature in args[0] to a TweetIntegerShingleSignature. */
  public static void main(String[] args) throws Etwitterception {
    if (args.length < 1) {
      throw new RuntimeEtwitterception("Please provide signature value.");
    }
    int signature = Integer.parseInt(args[0]);
    System.out.println(TweetIntegerShingleSignature.deserialize(signature).toString());
  }
}
