## `phrase-exports/`

These files are updated by Phrase and should not be edited. Refer to `.phrase.yml`.

## `rails-i18n/`

We use locale files, pluralization and transliteration rules from the installed
gem [rails-i18n](https://github.com/svenfuchs/rails-i18n).

This folder contains patches to rails-i18n.

We [manually install](https://github.com/svenfuchs/rails-i18n#manual-installation)
the following locales from rails-i18n version 7.0.3 in order to rename them.
Aside from the locale name, the files should not be edited.

- "mr-IN" to "mr"
- "tl" to "fil"
