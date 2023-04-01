#include <twapi.h>

enum {
  TARGET_FOLLOWING,
  TARGET_FORYOU,
};

enum {
  LESS = -1,
  SAME = 0,
  MORE = 1
};

static int ranking_heuristic(twapi_tweet_t a, twapi_tweet_t b) {
  /* Demote all crypto related tweets */
  if (twapi_tweet_contains(a, "cryptocurrency") ||
      twapi_tweet_contains(a, "bitcoin") ||
      twapi_tweet_contains(a, "ethereum") ||
      twapi_tweet_contains(a, "eth") ||
      twapi_tweet_contains(a, "btc") ||
      twapi_tweet_contains(a, "nft") ||
      twapi_tweet_contains(a, "bayc") ||
      twapi_tweet_contains(a, "dogecoin") ||
      twapi_tweet_contains(a, "doge"))
  {
    return LESS;
  }

  /* Demote all AI related tweets */
  if (twapi_tweet_contains(a, "AI") ||
      twapi_tweet_contains(a, "midjourney") ||
      twapi_tweet_contains(a, "dalle") ||
      twapi_tweet_contains(a, "openai") ||
      twapi_tweet_contains(a, "chatgpt") ||
      twapi_tweet_contains(a, "bing"))
  {
    return LESS;  
  }

  /* Demote all "Elon Musk" related tweets */
  if (twapi_tweet_contains(a, "Elon") ||
      twapi_tweet_contains(a, "Elon Musk") ||
      twapi_tweet_contains(a, "Musk"))
  {
    return LESS;  
  }

  /* Promote cool tweets */
  if (twapi_tweet_contains(a, "art") ||
      twapi_tweet_contains(a, "drawing") ||
      twapi_tweet_contains(a, "furry") ||
      twapi_tweet_contains(a, "waluigi") ||
      twapi_tweet_contains(a, "wario") ||
      twapi_tweet_contains(a, "commission") ||
      twapi_tweet_contains(a, "commodore64") ||
      twapi_tweet_contains(a, "emulation") ||
      twapi_tweet_contains(a, "emulator") ||
      twapi_tweet_contains(a, "pirate"))
  {
    return MORE;
  }

  /* Sort by likes over time */
  return (a.likes/a.lifespan_in_seconds) < (b.likes/b.lifespan_in_seconds) ? LESS : MORE;
}


twapi_query_t twalg_recommendation(int target, int user_id, int scroll_start_timestamp) {
  twapi_query_t query = twapi_query_begin();
  twapi_query_before(&query, scroll_start_timestamp);

  switch (target) {
    case TARGET_FOLLOWING: twapi_query_following(&query, user_id); 
                           break;

    case TARGET_FORYOU:    /* Don't show any twitter blue users */
                           twapi_query_twitterblue(&query, false);
                           /* Don't show Elon Musk's tweets */
                           twapi_query_not_user(&query, "elonmusk");
                           break;
  }

  /* Ranking heuristic */
  twapi_query_sort(&query, ranking_heuristic);

  return query;
}
