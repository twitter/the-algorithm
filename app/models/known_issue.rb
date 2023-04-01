class KnownIssue < ApplicationRecord
  # why is this included here? FIXME?
  include HtmlCleaner

  validates_presence_of :title
  validates_length_of :title,
    minimum: ArchiveConfig.TITLE_MIN,
    too_short: ts("must be at least %{min} letters long.", min: ArchiveConfig.TITLE_MIN)

  validates_length_of :title,
    maximum: ArchiveConfig.TITLE_MAX,
    too_long: ts("must be less than %{max} letters long.", max: ArchiveConfig.TITLE_MAX)

  validates_presence_of :content
  validates_length_of :content, minimum: ArchiveConfig.CONTENT_MIN,
    too_short: ts("must be at least %{min} letters long.", min: ArchiveConfig.CONTENT_MIN)

  validates_length_of :content, maximum: ArchiveConfig.CONTENT_MAX,
    too_long: ts("cannot be more than %{max} characters long.", max: ArchiveConfig.CONTENT_MAX)
end
