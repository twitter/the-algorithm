class Bookmark < ApplicationRecord
  include Collectible
  include Searchable
  include Responder
  include Taggable

  belongs_to :bookmarkable, polymorphic: true, inverse_of: :bookmarks
  belongs_to :pseud

  validates_length_of :bookmarker_notes,
    maximum: ArchiveConfig.NOTES_MAX, too_long: ts("must be less than %{max} letters long.", max: ArchiveConfig.NOTES_MAX)

  validate :not_already_bookmarked_by_user, on: :create
  def not_already_bookmarked_by_user
    return unless self.pseud && self.bookmarkable

    return if self.pseud.user.bookmarks.where(bookmarkable: self.bookmarkable).empty?

    errors.add(:base, ts("You have already bookmarked that."))
  end

  validate :check_new_external_work
  def check_new_external_work
    return unless bookmarkable.is_a?(ExternalWork) && bookmarkable.new_record?

    errors.add(:base, "Fandom tag is required") if bookmarkable.fandom_string.blank?

    return if bookmarkable.valid?

    bookmarkable.errors.full_messages.each do |message|
      errors.add(:base, message)
    end
  end

  default_scope -> { order("bookmarks.id DESC") } # id's stand in for creation date

  # renaming scope :public -> :is_public because otherwise it overlaps with the "public" keyword
  scope :is_public, -> { where(private: false, hidden_by_admin: false) }
  scope :not_public, -> { where(private: true) }
  scope :not_private, -> { where(private: false) }
  scope :since, lambda { |*args| where("bookmarks.created_at > ?", (args.first || 1.week.ago)) }
  scope :recs, -> { where(rec: true) }

  scope :join_work, -> {
    joins("LEFT JOIN works ON (bookmarks.bookmarkable_id = works.id AND bookmarks.bookmarkable_type = 'Work')").
    merge(Work.visible_to_all)
  }

  scope :join_series, -> {
    joins("LEFT JOIN series ON (bookmarks.bookmarkable_id = series.id AND bookmarks.bookmarkable_type = 'Series')").
    merge(Series.visible_to_all)
  }

  scope :join_external_works, -> {
    joins("LEFT JOIN external_works ON (bookmarks.bookmarkable_id = external_works.id AND bookmarks.bookmarkable_type = 'ExternalWork')").
    merge(ExternalWork.visible_to_all)
  }

  scope :join_bookmarkable, -> {
    joins("LEFT JOIN works ON (bookmarks.bookmarkable_id = works.id AND bookmarks.bookmarkable_type = 'Work')
           LEFT JOIN series ON (bookmarks.bookmarkable_id = series.id AND bookmarks.bookmarkable_type = 'Series')
           LEFT JOIN external_works ON (bookmarks.bookmarkable_id = external_works.id AND bookmarks.bookmarkable_type = 'ExternalWork')")
  }

  scope :visible_to_all, -> {
    is_public.with_bookmarkable_visible_to_all
  }

  scope :visible_to_registered_user, -> {
    is_public.with_bookmarkable_visible_to_registered_user
  }

  # Scope for retrieving bookmarks with a bookmarkable visible to registered
  # users (regardless of the bookmark's hidden_by_admin/private status).
  scope :with_bookmarkable_visible_to_registered_user, -> {
    join_bookmarkable.where(
      "(works.posted = 1 AND works.hidden_by_admin = 0) OR
      (series.hidden_by_admin = 0) OR
      (external_works.hidden_by_admin = 0)"
    )
  }

  # Scope for retrieving bookmarks with a bookmarkable visible to logged-out
  # users (regardless of the bookmark's hidden_by_admin/private status).
  scope :with_bookmarkable_visible_to_all, -> {
    join_bookmarkable.where(
      "(works.posted = 1 AND works.restricted = 0 AND works.hidden_by_admin = 0) OR
      (series.restricted = 0 AND series.hidden_by_admin = 0) OR
      (external_works.hidden_by_admin = 0)"
    )
  }

  # Scope for retrieving bookmarks with a missing bookmarkable (regardless of
  # the bookmark's hidden_by_admin/private status).
  scope :with_missing_bookmarkable, -> {
    join_bookmarkable.where(
      "works.id IS NULL AND series.id IS NULL AND external_works.id IS NULL"
    )
  }

  scope :visible_to_admin, -> { not_private }

  scope :latest, -> { is_public.limit(ArchiveConfig.ITEMS_PER_PAGE).join_work }

  scope :for_blurb, -> { includes(:bookmarkable, :pseud, :tags, :collections) }

  # a complicated dynamic scope here:
  # if the user is an Admin, we use the "visible_to_admin" scope
  # if the user is not a logged-in User, we use the "visible_to_all" scope
  # otherwise, we use a join to get userids and then get all posted works that are either unhidden OR belong to this user.
  # Note: in that last case we have to use select("DISTINCT works.") because of cases where the same user appears twice
  # on a work.
  scope :visible_to_user, lambda {|user|
    if user.is_a?(Admin)
      visible_to_admin
    elsif !user.is_a?(User)
      visible_to_all
    else
      select("DISTINCT bookmarks.*").
      visible_to_registered_user.
      joins("JOIN pseuds as p1 ON p1.id = bookmarks.pseud_id JOIN users ON users.id = p1.user_id").
      where("bookmarks.hidden_by_admin = 0 OR users.id = ?", user.id)
    end
  }

  # Use the current user to determine what works are visible
  scope :visible, -> { visible_to_user(User.current_user) }

  before_destroy :invalidate_bookmark_count
  after_save :invalidate_bookmark_count, :update_pseud_index

  after_create :update_work_stats
  after_destroy :update_work_stats, :update_pseud_index

  def invalidate_bookmark_count
    work = Work.where(id: self.bookmarkable_id)
    if work.present? && self.bookmarkable_type == 'Work'
      work.first.invalidate_public_bookmarks_count
    end
  end

  # We index the bookmark count, so if it should change, update the pseud
  def update_pseud_index
    return unless destroyed? || saved_change_to_id? || saved_change_to_private? || saved_change_to_hidden_by_admin?
    IndexQueue.enqueue_id(Pseud, pseud_id, :background)
  end

  def visible?(current_user=User.current_user)
    return true if current_user == self.pseud.user
    unless current_user == :false || !current_user
      # Admins should not see private bookmarks
      return true if current_user.is_a?(Admin) && self.private == false
    end
    if !(self.private? || self.hidden_by_admin?)
      if self.bookmarkable.nil?
        # only show bookmarks for deleted works to the user who
        # created the bookmark
        return true if pseud.user == current_user
      else
        if self.bookmarkable_type == 'Work' || self.bookmarkable_type == 'Series' || self.bookmarkable_type == 'ExternalWork'
          return true if self.bookmarkable.visible?(current_user)
        else
          return true
        end
      end
    end
    return false
  end

  # Returns the number of bookmarks on an item visible to the current user
  def self.count_visible_bookmarks(bookmarkable, current_user=:false)
    bookmarkable.bookmarks.visible.size
  end

  # TODO: Is this necessary anymore?
  before_destroy :save_parent_info

  # Because of the way the elasticsearch parent/child index is set up, we need
  # to know what the bookmarkable type and id was in order to delete the
  # bookmark from the index after it's been deleted from the database
  def save_parent_info
    expire_time = (Time.now + 2.weeks).to_i
    REDIS_GENERAL.setex(
      "deleted_bookmark_parent_#{self.id}",
      expire_time,
      "#{bookmarkable_id}-#{bookmarkable_type.underscore}"
    )
  end

  #################################
  ## SEARCH #######################
  #################################

  def document_json
    BookmarkIndexer.new({}).document(self)
  end

  def bookmarker
    pseud.try(:byline)
  end

  def with_notes
    bookmarker_notes.present?
  end

  def collection_ids
    approved_collections.pluck(:id, :parent_id).flatten.uniq.compact
  end

  def bookmarkable_date
    if bookmarkable.respond_to?(:revised_at)
      bookmarkable.revised_at
    elsif bookmarkable.respond_to?(:updated_at)
      bookmarkable.updated_at
    end
  end
end
