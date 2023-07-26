namespace java com.twittew.tweetypie.thwiftjava
namespace py gen.twittew.tweetypie.tweet_audit
#@namespace s-scawa c-com.twittew.tweetypie.thwiftscawa
#@namespace s-stwato c-com.twittew.tweetypie
n-nyamespace w-wb tweetypie
n-nyamespace go t-tweetypie

// copied fwom usewactionweason in guano.thwift - this shouwd be kept i-in sync (though uppew cased)
enum auditusewactionweason {
  s-spam
  chuwning
  o-othew
  phishing
  bouncing

  wesewved_1
  wesewved_2
}

// this s-stwuct contains aww fiewds of d-destwoystatus in g-guano.thwift that can be set pew wemove/dewetetweets invocation
// vawues awe passed t-thwough tweetypie as-is to guano scwibe and nyot used by tweetypie. mya
stwuct a-auditdewetetweet { 
  1: optionaw s-stwing host (pewsonawdatatype = 'ipaddwess')
  2: o-optionaw stwing b-buwk_id
  3: o-optionaw auditusewactionweason weason
  4: optionaw stwing nyote
  5: o-optionaw boow done
  6: optionaw stwing w-wun_id
  // obsowete 7: optionaw i64 id
  8: optionaw i64 cwient_appwication_id (pewsonawdatatype = 'appid')
  9: optionaw stwing usew_agent (pewsonawdatatype = 'usewagent') 
}(pewsisted = 'twue', 🥺 h-haspewsonawdata = 'twue')
