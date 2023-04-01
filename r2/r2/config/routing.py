# The contents of this file are subject to the Common Public Attribution
# License Version 1.0. (the "License"); you may not use this file except in
# compliance with the License. You may obtain a copy of the License at
# http://code.reddit.com/LICENSE. The License is based on the Mozilla Public
# License Version 1.1, but Sections 14 and 15 have been added to cover use of
# software over a computer network and provide for limited attribution for the
# Original Developer. In addition, Exhibit A has been modified to be consistent
# with Exhibit B.
#
# Software distributed under the License is distributed on an "AS IS" basis,
# WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
# the specific language governing rights and limitations under the License.
#
# The Original Code is reddit.
#
# The Original Developer is the Initial Developer.  The Initial Developer of
# the Original Code is reddit Inc.
#
# All portions of the code written by reddit are Copyright (c) 2006-2015 reddit
# Inc. All Rights Reserved.
###############################################################################

"""
Setup your Routes options here
"""
from routes import Mapper


def not_in_sr(environ, results):
    return ('subreddit' not in environ and
            'sub_domain' not in environ and
            'domain' not in environ)


# FIXME: submappers with path prefixes are broken in Routes 1.11. Once we
# upgrade, we should be able to replace this ugliness with submappers.
def partial_connect(mc, **override_args):
    def connect(path, **kwargs):
        if 'path_prefix' in override_args:
            path = override_args['path_prefix'] + path
        kwargs.update(override_args)
        mc(path, **kwargs)
    return connect


def make_map(config):
    map = Mapper(explicit=False)
    map.minimization = True
    mc = map.connect

    # Username-relative userpage redirects, need to be defined here in case
    # a plugin defines a `/user/:name` handler.
    mc('/user/me', controller='user', action='rel_user_redirect')
    mc('/user/me/*rest', controller='user', action='rel_user_redirect')

    for plugin in reversed(config['r2.plugins']):
        plugin.add_routes(mc)

    mc('/admin/', controller='awards')

    mc('/robots.txt', controller='robots', action='robots')
    mc('/crossdomain', controller='robots', action='crossdomain')

    mc('/login', controller='forms', action='login')
    mc('/register', controller='forms', action='register')
    mc('/logout', controller='forms', action='logout')
    mc('/verify', controller='forms', action='verify')
    mc('/adminon', controller='forms', action='adminon')
    mc('/adminoff', controller='forms', action='adminoff')
    mc('/submit', controller='front', action='submit')

    # redirect old urls to the new
    ABOUT_BASE = "https://about.reddit.com/"
    mc('/about', controller='redirect', action='redirect', dest=ABOUT_BASE, 
       conditions={'function':not_in_sr})
    mc('/about/values', controller='redirect', action='redirect', dest=ABOUT_BASE)
    mc('/about/team', controller='redirect', action='redirect',
       dest=ABOUT_BASE)
    mc('/about/alien', controller='redirect', action='redirect',
       dest=ABOUT_BASE + "press")
    mc('/jobs', controller='redirect', action='redirect',
       dest=ABOUT_BASE + "careers")

    mc('/over18', controller='post', action='over18')
    mc('/quarantine', controller='post', action='quarantine')
    mc('/quarantine_optout', controller='api', action='quarantine_optout')

    mc('/traffic', controller='front', action='site_traffic')
    mc('/traffic/languages/:langcode', controller='front',
       action='lang_traffic', langcode='')
    mc('/traffic/adverts/:code', controller='front',
       action='advert_traffic', code='')
    mc('/traffic/subreddits/report', controller='front',
       action='subreddit_traffic_report')
    mc('/account-activity', controller='front', action='account_activity')

    mc('/subreddits/create', controller='front', action='newreddit')
    mc('/subreddits/search', controller='front', action='search_reddits')
    mc('/subreddits/login', controller='forms', action='login')
    mc('/subreddits/:where', controller='reddits', action='listing',
       where='popular', conditions={'function':not_in_sr},
       requirements=dict(where="popular|new|banned|employee|gold|default|"
                               "quarantine|featured"))
    # If no subreddit is specified, might as well show a list of 'em.
    mc('/r', controller='redirect', action='redirect', dest='/subreddits')

    mc('/subreddits/mine/:where', controller='myreddits', action='listing',
       where='subscriber', conditions={'function':not_in_sr},
       requirements=dict(where='subscriber|contributor|moderator'))

    # These routes are kept for backwards-compatibility reasons
    # Using the above /subreddits/ ones instead is preferable
    mc('/reddits/create', controller='front', action='newreddit')
    mc('/reddits/search', controller='front', action='search_reddits')
    mc('/reddits/login', controller='forms', action='login')
    mc('/reddits/:where', controller='reddits', action='listing',
       where='popular', conditions={'function':not_in_sr},
       requirements=dict(where="popular|new|banned"))

    mc('/reddits/mine/:where', controller='myreddits', action='listing',
       where='subscriber', conditions={'function':not_in_sr},
       requirements=dict(where='subscriber|contributor|moderator'))

    mc('/buttons', controller='buttons', action='button_demo_page')

    #/button.js and buttonlite.js - the embeds
    mc('/button', controller='buttons', action='button_embed')
    mc('/buttonlite', controller='buttons', action='button_lite')

    mc('/widget', controller='buttons', action='widget_demo_page')

    mc('/awards', controller='front', action='awards')
    mc('/awards/confirm/:code', controller='front',
       action='confirm_award_claim')
    mc('/awards/claim/:code', controller='front', action='claim_award')
    mc('/awards/received', controller='front', action='received_award')

    mc('/i18n', controller='redirect', action='redirect',
       dest='https://www.reddit.com/r/i18n')
    mc('/feedback', controller='redirect', action='redirect',
       dest='/contact')
    mc('/contact', controller='frontunstyled', action='contact_us')

    mc('/admin/awards', controller='awards')
    mc('/admin/awards/:awardcn/:action', controller='awards',
       requirements=dict(action="give|winners"))

    mc('/admin/creddits', controller='admintool', action='creddits')
    mc('/admin/gold', controller='admintool', action='gold')

    mc('/user/:username/about', controller='user', action='about',
       where='overview')
    mc('/user/:username/trophies', controller='user', action='trophies')
    mc('/user/:username/:where', controller='user', action='listing',
       where='overview')
    mc('/user/:username/saved/:category', controller='user', action='listing',
       where='saved')

    multi_prefixes = (
       partial_connect(mc, path_prefix='/user/:username/m/:multipath'),
       partial_connect(mc, path_prefix='/me/m/:multipath', my_multi=True),
       partial_connect(mc, path_prefix='/me/f/:filtername'),
    )

    for connect in multi_prefixes:
       connect('/', controller='hot', action='listing')
       connect('/submit', controller='front', action='submit')
       connect('/:sort', controller='browse', sort='top',
          action='listing', requirements=dict(sort='top|controversial'))
       connect('/:controller', action='listing',
          requirements=dict(controller="hot|new|rising|randomrising|ads"))

    mc('/user/:username/:where/:show', controller='user', action='listing')
    
    mc('/explore', controller='front', action='explore')
    mc('/api/recommend/feedback', controller='api', action='rec_feedback')

    mc("/newsletter", controller="newsletter", action="newsletter")

    mc("/gtm/jail", controller="googletagmanager", action="jail")
    mc("/gtm", controller="googletagmanager", action="gtm")

    mc('/oembed', controller='oembed', action='oembed')

    mc('/about/rules', controller='front', action='rules')
    mc('/about/sidebar', controller='front', action='sidebar')
    mc('/about/sticky', controller='front', action='sticky')
    mc('/about/flair', controller='front', action='flairlisting')
    mc('/about', controller='front', action='about')
    for connect in (mc,) + multi_prefixes:
       connect('/about/message/:where', controller='message',
          action='listing')
       connect('/about/log', controller='front', action='moderationlog')
       connect('/about/:location', controller='front',
          action='spamlisting',
          requirements=dict(location='reports|spam|modqueue|unmoderated|edited'))
       connect('/about/:where', controller='userlistlisting',
          requirements=dict(where='contributors|banned|muted|wikibanned|'
              'wikicontributors|moderators'), action='listing')
       connect('/about/:location', controller='front', action='editreddit',
          requirements=dict(location='edit|stylesheet|traffic|about'))
       connect('/comments', controller='comments', action='listing')
       connect('/comments/gilded', action='listing', controller='gilded')
       connect('/gilded', action='listing', controller='gilded')
       connect('/search', controller='front', action='search')

    mc('/u/:username', controller='redirect', action='user_redirect')
    mc('/u/:username/*rest', controller='redirect', action='user_redirect')

    # preserve timereddit URLs from 4/1/2012
    mc('/t/:timereddit', controller='redirect', action='timereddit_redirect')
    mc('/t/:timereddit/*rest', controller='redirect',
       action='timereddit_redirect')

    # /prefs/friends is also aliased to /api/v1/me/friends
    mc('/prefs/:where', controller='userlistlisting',
        action='user_prefs', requirements=dict(where='blocked|friends'))
    mc('/prefs/:location', controller='forms', action='prefs',
       location='options')

    mc('/info/0:article/*rest', controller='front',
       action='oldinfo', dest='comments', type='ancient')
    mc('/info/:article/:dest/:comment', controller='front',
       action='oldinfo', type='old', dest='comments', comment=None)


    mc('/related/:article/:title', controller='front',
       action='related', title=None)
    mc('/details/:article/:title', controller='front',
       action='details', title=None)
    mc('/traffic/:link/:campaign', controller='front', action='traffic',
       campaign=None)
    mc('/comments/:article/:title/:comment', controller='front',
       action='comments', title=None, comment=None)
    mc('/duplicates/:article/:title', controller='front',
       action='duplicates', title=None)

    mc('/mail/optout', controller='forms', action='optout')
    mc('/mail/optin', controller='forms', action='optin')
    mc('/mail/unsubscribe/:user/:key', controller='forms',
       action='unsubscribe_emails')
    mc('/stylesheet', controller='front', action='stylesheet')

    mc('/share/close', controller='front', action='share_close')

    # sponsor endpoints
    mc('/sponsor/report', controller='sponsor', action='report')
    mc('/sponsor/inventory', controller='sponsor', action='promote_inventory')
    mc('/sponsor/lookup_user', controller='sponsor', action="lookup_user")

    # sponsor listings
    mc('/sponsor/promoted/:sort', controller='sponsorlisting', action='listing',
       requirements=dict(sort="future_promos|pending_promos|unpaid_promos|"
                              "rejected_promos|live_promos|edited_live_promos|"
                              "underdelivered|reported|house|fraud|all|"
                              "unapproved_campaigns|by_platform"))
    mc('/sponsor', controller='sponsorlisting', action="listing",
       sort="all")
    mc('/sponsor/promoted/', controller='sponsorlisting', action="listing",
       sort="all")
    mc('/sponsor/promoted/live_promos/:sr', controller='sponsorlisting',
       sort='live_promos', action='listing')


    # listings of user's promos
    mc('/promoted/:sort', controller='promotelisting', action="listing",
       requirements=dict(sort="future_promos|pending_promos|unpaid_promos|"
                              "rejected_promos|live_promos|edited_live_promos|"
                              "all"))
    mc('/promoted/', controller='promotelisting', action="listing", sort="all")

    # editing endpoints
    mc('/promoted/new_promo', controller='promote', action='new_promo')
    mc('/promoted/edit_promo/:link', controller='promote', action='edit_promo')
    mc('/promoted/pay/:link/:campaign', controller='promote', action='pay')
    mc('/promoted/refund/:link/:campaign', controller='promote',
       action='refund')

    mc('/health', controller='health', action='health')
    mc('/health/ads', controller='health', action='promohealth')
    mc('/health/caches', controller='health', action='cachehealth')

    mc('/', controller='hot', action='listing')

    mc('/:controller', action='listing',
       requirements=dict(controller="hot|new|rising|randomrising|ads"))
    mc('/saved', controller='user', action='saved_redirect')

    mc('/by_id/:names', controller='byId', action='listing')

    mc('/:sort', controller='browse', sort='top', action='listing',
       requirements=dict(sort='top|controversial'))

    mc('/message/compose', controller='message', action='compose')
    mc('/message/messages/:mid', controller='message', action='listing',
       where="messages")
    mc('/message/:where', controller='message', action='listing')
    mc('/message/moderator/:subwhere', controller='message', action='listing',
       where='moderator')

    mc('/thanks', controller='forms', action="claim", secret='')
    mc('/thanks/:secret', controller='forms', action="claim")

    mc('/gold', controller='forms', action="gold", is_payment=False)
    mc('/gold/payment', controller='forms', action="gold", is_payment=True)
    mc('/gold/creditgild/:passthrough', controller='forms', action='creditgild')
    mc('/gold/thanks', controller='front', action='goldthanks')
    mc('/gold/subscription', controller='forms', action='subscription')
    mc('/gilding', controller='front', action='gilding')
    mc('/creddits', controller='redirect', action='redirect', 
       dest='/gold?goldtype=creddits')

    mc('/password', controller='forms', action="password")
    mc('/random', controller='front', action="random")
    mc('/:action', controller='embed',
       requirements=dict(action="blog"))
    mc('/help/gold', controller='redirect', action='redirect',
       dest='/gold/about')

    mc('/help/:page', controller='policies', action='policy_page',
       conditions={'function':not_in_sr},
       requirements={'page':'contentpolicy|privacypolicy|useragreement'})
    mc('/rules', controller='redirect', action='redirect',
        dest='/help/contentpolicy')
    mc('/faq', controller='redirect', action='redirect',
       dest='https://reddit.zendesk.com/')

    mc('/wiki/create/*page', controller='wiki', action='wiki_create')
    mc('/wiki/edit/*page', controller='wiki', action='wiki_revise')
    mc('/wiki/revisions', controller='wiki', action='wiki_recent')
    mc('/wiki/revisions/*page', controller='wiki', action='wiki_revisions')
    mc('/wiki/settings/*page', controller='wiki', action='wiki_settings')
    mc('/wiki/discussions/*page', controller='wiki', action='wiki_discussions')
    mc('/wiki/pages', controller='wiki', action='wiki_listing')

    mc('/api/wiki/edit', controller='wikiapi', action='wiki_edit')
    mc('/api/wiki/hide', controller='wikiapi', action='wiki_revision_hide')
    mc('/api/wiki/delete', controller='wikiapi', action='wiki_revision_delete')
    mc('/api/wiki/revert', controller='wikiapi', action='wiki_revision_revert')
    mc('/api/wiki/alloweditor/:act', controller='wikiapi',
       requirements=dict(act="del|add"), action='wiki_allow_editor')

    mc('/wiki/*page', controller='wiki', action='wiki_page')
    mc('/wiki/', controller='wiki', action='wiki_page')

    mc('/:action', controller='wiki', requirements=dict(action="help"))
    mc('/help/*page', controller='wiki', action='wiki_redirect')
    mc('/w/*page', controller='wiki', action='wiki_redirect')

    mc('/goto', controller='toolbar', action='goto')
    mc('/tb/:link_id', controller='front', action='link_id_redirect')
    mc('/toolbar/*frame', controller='toolbar', action='redirect')

    mc('/c/:comment_id', controller='front', action='comment_by_id')

    mc('/s/*urloid', controller='toolbar', action='s')
    # additional toolbar-related rules just above the catchall

    mc('/resetpassword/:key', controller='forms',
       action='resetpassword')
    mc('/verification/:key', controller='forms',
       action='verify_email')
    mc('/resetpassword', controller='forms',
       action='resetpassword')

    mc('/modify_hsts_grant', controller='front', action='modify_hsts_grant')

    mc('/post/:action/:url_user', controller='post',
       requirements=dict(action="login|reg"))
    mc('/post/:action', controller='post',
       requirements=dict(action="options|over18|unlogged_options|optout"
                         "|optin|login|reg|explore_settings"))

    mc('/api', controller='redirect', action='redirect', dest='/dev/api')
    mc('/api/distinguish/:how', controller='api', action="distinguish")
    mc('/api/spendcreddits', controller='ipn', action="spendcreddits")
    mc('/api/stripecharge/gold', controller='stripe', action='goldcharge')
    mc('/api/modify_subscription', controller='stripe',
       action='modify_subscription')
    mc('/api/cancel_subscription', controller='stripe',
       action='cancel_subscription')
    mc('/api/stripewebhook/gold/:secret', controller='stripe',
       action='goldwebhook')
    mc('/api/coinbasewebhook/gold/:secret', controller='coinbase',
       action='goldwebhook')
    mc('/api/rgwebhook/gold/:secret', controller='redditgifts',
       action='goldwebhook')
    mc('/api/ipn/:secret', controller='ipn', action='ipn')
    mc('/ipn/:secret', controller='ipn', action='ipn')
    mc('/api/:action/:url_user', controller='api',
       requirements=dict(action="login|register"))
    mc('/api/gadget/click/:ids', controller='api', action='gadget',
       type='click')
    mc('/api/gadget/:type', controller='api', action='gadget')
    mc('/api/zendeskreply', controller='mailgunwebhook', action='zendeskreply')
    mc('/api/:action', controller='promoteapi',
       requirements=dict(action=("promote|unpromote|edit_promo|ad_s3_callback|"
                                 "ad_s3_params|freebie|promote_note|update_pay|"
                                 "edit_campaign|delete_campaign|"
                                 "check_inventory|"
                                 "refund_campaign|terminate_campaign|"
                                 "review_fraud|create_promo|"
                                 "toggle_pause_campaign")))
    mc('/api/:action', controller='apiminimal',
       requirements=dict(action="new_captcha"))
    mc('/api/:type', controller='api',
       requirements=dict(type='wikibannednote|bannednote|mutednote'),
       action='relnote')

    # Route /api/multi here to prioritize it over the /api/:action rule
    mc("/api/multi", controller="multiapi", action="multi",
       conditions={"method": ["POST"]})

    mc('/api/:action', controller='api')
    
    mc('/api/recommend/sr/:srnames', controller='api',
       action='subreddit_recommendations')

    mc('/api/server_seconds_visibility', controller='api',
       action='server_seconds_visibility')

    mc("/api/multi/mine", controller="multiapi", action="my_multis")
    mc("/api/multi/user/:username", controller="multiapi", action="list_multis")
    mc("/api/multi/copy", controller="multiapi", action="multi_copy")
    mc("/api/multi/rename", controller="multiapi", action="multi_rename")
    mc("/api/multi/*multipath/r/:srname", controller="multiapi", action="multi_subreddit")
    mc("/api/multi/*multipath/description", controller="multiapi", action="multi_description")
    mc("/api/multi/*multipath", controller="multiapi", action="multi")
    mc("/api/filter/*multipath/r/:srname", controller="multiapi", action="multi_subreddit")
    mc("/api/filter/*multipath", controller="multiapi", action="multi")

    mc("/api/v1/:action", controller="oauth2frontend",
       requirements=dict(action="authorize"))
    mc("/api/v1/:action", controller="oauth2access",
       requirements=dict(action="access_token|revoke_token"))
    mc("/api/v1/:action", controller="apiv1scopes",
       requirements=dict(action="scopes"))
    mc("/api/v1/user/:username/trophies",
       controller="apiv1user", action="usertrophies")
    mc("/api/v1/:action", controller="apiv1login",
       requirements=dict(action="register|login"))
    mc("/api/v1/:action", controller="apiv1user")
    # Same controller/action as /prefs/friends
    mc("/api/v1/me/:where", controller="userlistlisting",
        action="user_prefs", requirements=dict(where="friends"))
    mc("/api/v1/me/:action", controller="apiv1user")
    mc("/api/v1/me/:action/:username", controller="apiv1user")

    mc("/api/v1/gold/gild/:fullname", controller="apiv1gold", action="gild")
    mc("/api/v1/gold/give/:username", controller="apiv1gold", action="give")

    mc('/dev', controller='redirect', action='redirect', dest='/dev/api')
    mc('/dev/api', controller='apidocs', action='docs')
    mc('/dev/api/:mode', controller='apidocs', action='docs',
       requirements=dict(mode="oauth"))

    mc("/button_info", controller="api", action="url_info", limit=1)

    mc('/captcha/:iden', controller='captcha', action='captchaimg')

    mc('/mediaembed/:link/:credentials',
       controller="mediaembed", action="mediaembed", credentials=None)

    mc('/code', controller='redirect', action='redirect',
       dest='http://github.com/reddit/')

    mc('/socialite', controller='redirect', action='redirect',
       dest='https://addons.mozilla.org/firefox/addon/socialite/')

    # Used for showing ads
    mc("/ads/", controller="ad", action="ad")

    mc("/try", controller="forms", action="try_compact")

    mc("/web/timings", controller="weblog", action="timings")

    mc("/web/log/:level", controller="weblog", action="message",
       requirements=dict(level="error"))

    mc("/web/poisoning", controller="weblog", action="report_cache_poisoning")

    # This route handles displaying the error page and
    # graphics used in the 404/500
    # error pages. It should likely stay at the top
    # to ensure that the error page is
    # displayed properly.
    mc('/error/document/:id', controller='error', action="document")

    # these should be near the buttom, because they should only kick
    # in if everything else fails. It's the attempted catch-all
    # reddit.com/http://... and reddit.com/34fr
    mc('/:link_id', controller='front', action='link_id_redirect',
       requirements=dict(link_id='[0-9a-z]{1,6}'))
    mc('/:urloid', controller='toolbar', action='s',
       requirements=dict(urloid=r'(\w+\.\w{2,}|https?).*'))

    mc("/*url", controller='front', action='catchall')

    return map
