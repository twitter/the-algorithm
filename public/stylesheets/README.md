Only the default site skin is affected by changes made to the files in this
directory. After any deploy that includes changes to default site skin, the
Systems Committee runs a special task to ensure the skin is updated on all
servers.

All other skins are stored in and retrieved from the database. For performance,
certain public site skins are cached via the admin interface. Caching a skin
creates a stylesheet file (or multiple files, in the case of skins with parents)
and places the file(s) in a subdirectory within public/stylesheets/skins. For
example, the files for a cached skin named Dark Mode would be located in
public/stylesheets/skins/skin_000_dark_mode (the 000 would match the skin's
database ID).

We keep master copies of each public site skin in public/stylesheets/masters.
This serves several purposes. First, it provides a back-up copy in case the
version in the database is ever deleted or overwritten. Second, it allows us to
include code comments, which are stripped by the sanitizer. Third, it ensures
changes to public site skins can go through the same pull request and review
process as all other code changes.

Remember, though: changes to a skin's files in the masters directory will not
affect the skin on production! An admin will still need to edit the skin via
the admin interface and paste in the updated master version.
