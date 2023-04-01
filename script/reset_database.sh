#!/bin/bash

case "${RAILS_ENV}" in
test) ;;
development) ;;
*)
  echo "Only supported in test and development (e.g. 'RAILS_ENV=test ./script/reset_database.sh')"
  exit 1
  ;;
esac

bundle install
bundle exec rake db:drop
bundle exec rake db:create
bundle exec rails db:environment:set
bundle exec rake db:schema:load
bundle exec rake db:migrate
if [ "${RAILS_ENV}" = "test" ] ; then
  exit 0
fi
bundle exec rake db:otwseed

bundle exec rake work:missing_stat_counters
bundle exec rake skins:load_site_skins

bundle exec rake search:index_tags
bundle exec rake search:index_works
bundle exec rake search:index_pseuds
bundle exec rake search:index_bookmarks
