class ExternalWork < ApplicationRecord
  include UrlHelpers
  include Bookmarkable
  include Filterable
  include Searchable

  has_many :related_works, as: :parent

  belongs_to :language

  # .duplicate.count.size returns the number of URLs with multiple external works
  scope :duplicate, -> { group(:url).having("count(DISTINCT id) > 1") }

  scope :for_blurb, -> { includes(:language, :tags) }

  AUTHOR_LENGTH_MAX = 500

  validates_presence_of :title
  validates_length_of :title, minimum: ArchiveConfig.TITLE_MIN,
                              too_short: ts("must be at least %{min} characters long.",
                                            min: ArchiveConfig.TITLE_MIN)
  validates_length_of :title, maximum: ArchiveConfig.TITLE_MAX,
                              too_long: ts("must be less than %{max} characters long.",
                                           max: ArchiveConfig.TITLE_MAX)

  validates_length_of :summary, allow_blank: true, maximum: ArchiveConfig.SUMMARY_MAX,
                                too_long: ts("must be less than %{max} characters long.",
                                             max: ArchiveConfig.SUMMARY_MAX)

  validates :author, presence: { message: ts("can't be blank") }
  validates_length_of :author, maximum: AUTHOR_LENGTH_MAX,
                               too_long: ts("must be less than %{max} characters long.",
                                            max: AUTHOR_LENGTH_MAX)

  validates :user_defined_tags_count,
            at_most: { maximum: proc { ArchiveConfig.USER_DEFINED_TAGS_MAX } }

  # TODO: External works should have fandoms, but they currently don't get added through the
  # post new work form so we can't validate them
  #validates_presence_of :fandoms

  before_validation :cleanup_url
  validates :url, presence: true, url_format: true, url_active: true
  def cleanup_url
    self.url = reformat_url(self.url) if self.url
  end

  # Allow encoded characters to display correctly in titles
  def title
    read_attribute(:title).try(:html_safe)
  end

  ########################################################################
  # VISIBILITY
  ########################################################################
  # Adapted from work.rb

  scope :visible_to_all, -> { where(hidden_by_admin: false) }
  scope :visible_to_registered_user, -> { where(hidden_by_admin: false) }
  scope :visible_to_admin, -> { where("") }

  # a complicated dynamic scope here:
  # if the user is an Admin, we use the "visible_to_admin" scope
  # if the user is not a logged-in User, we use the "visible_to_all" scope
  # otherwise, we use a join to get userids and then get all posted works that are either unhidden OR belong to this user.
  # Note: in that last case we have to use select("DISTINCT works.") because of cases where the same user appears twice
  # on a work.
  scope :visible_to_user, lambda {|user| user.is_a?(Admin) ? visible_to_admin : visible_to_all}

  # Use the current user to determine what external works are visible
  scope :visible, -> { visible_to_user(User.current_user) }

  # Visible unless we're hidden by admin, in which case only an Admin can see.
  def visible?(user=User.current_user)
    self.hidden_by_admin? ? user.kind_of?(Admin) : true
  end

  # Visibility has changed, which means we need to reindex
  # the external work's bookmarker pseuds, to update their bookmark counts.
  def should_reindex_pseuds?
    pertinent_attributes = %w[id hidden_by_admin]
    destroyed? || (saved_changes.keys & pertinent_attributes).present?
  end

  ######################
  # SEARCH
  ######################

  def bookmarkable_json
    as_json(
      root: false,
      only: [
        :title, :summary, :hidden_by_admin, :created_at
      ],
      methods: [
        :posted, :restricted, :tag, :filter_ids, :rating_ids,
        :archive_warning_ids, :category_ids, :fandom_ids, :character_ids,
        :relationship_ids, :freeform_ids, :creators, :revised_at
      ]
    ).merge(
      language_id: language&.short,
      bookmarkable_type: "ExternalWork",
      bookmarkable_join: { name: "bookmarkable" }
    )
  end

  def posted
    true
  end
  alias_method :posted?, :posted

  def restricted
    false
  end
  alias_method :restricted?, :restricted

  def creators
    [author]
  end

  def revised_at
    created_at
  end
end
