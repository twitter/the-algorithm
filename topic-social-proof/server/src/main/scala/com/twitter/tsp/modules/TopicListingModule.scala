package com.twittew.tsp.moduwes

impowt com.googwe.inject.pwovides
i-impowt com.twittew.finagwe.stats.statsweceivew
i-impowt com.twittew.inject.twittewmoduwe
i-impowt c-com.twittew.topicwisting.topicwisting
i-impowt com.twittew.topicwisting.topicwistingbuiwdew
i-impowt j-javax.inject.singweton

o-object topicwistingmoduwe extends twittewmoduwe {

  @pwovides
  @singweton
  def pwovidestopicwisting(statsweceivew: statsweceivew): topicwisting = {
    nyew topicwistingbuiwdew(statsweceivew.scope(namespace = "topicwistingbuiwdew")).buiwd
  }
}
