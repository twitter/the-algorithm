# Be sure to restart your server when you modify this file.

Otwarchive::Application.config.session_store :cookie_store, key: '_otwarchive_session', expire_after: ArchiveConfig.DEFAULT_SESSION_LENGTH_IN_WEEKS.weeks

# Use the database for sessions instead of the cookie-based default,
# which shouldn't be used to store highly confidential information
# (create the session table with "rake db:sessions:create")
# Otwarchive::Application.config.session_store :active_record_store
