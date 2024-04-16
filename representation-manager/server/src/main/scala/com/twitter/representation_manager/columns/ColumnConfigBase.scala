package com.tw ter.representat on_manager.columns

 mport com.tw ter.strato.access.Access.LdapGroup
 mport com.tw ter.strato.conf g.Contact nfo
 mport com.tw ter.strato.conf g.FromColumns
 mport com.tw ter.strato.conf g.Has
 mport com.tw ter.strato.conf g.Pref x
 mport com.tw ter.strato.conf g.Serv ce dent f erPattern

object ColumnConf gBase {

  /******************  nternal perm ss ons *******************/
  val recosPerm ss ons: Seq[com.tw ter.strato.conf g.Pol cy] = Seq()

  /****************** External perm ss ons *******************/
  // T   s used to grant l m ed access to  mbers outs de of RP team.
  val externalPerm ss ons: Seq[com.tw ter.strato.conf g.Pol cy] = Seq()

  val contact nfo: Contact nfo = Contact nfo(
    descr pt on = "Please contact Relevance Platform for more deta ls",
    contactEma l = "no-reply@tw ter.com",
    ldapGroup = "ldap",
    j raProject = "J RA",
    l nks = Seq("http://go/rms-runbook")
  )
}
