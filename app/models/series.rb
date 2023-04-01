class Series < ApplicationRecord
  include Bookmarkable
  include Searchable
  include Creatable

  has_many :serial_works, dependent: :destroy
  has_many :works, through: :serial_works
  has_many :work_tags, -> { distinct }, through: :works, source: :tags
  has_many :work_pseuds, -> { distinct }, through: :works, source: :pseuds

  has_many :taggings, as: :taggable, dependent: :destroy
  has_many :tags, through: :taggings, source: :tagger, source_type: 'Tag'

  has_many :subscriptions, as: :subscribable, dependent: :destroy

  validates_presence_of :title
  validates_length_of :title,
    minimum: ArchiveConfig.TITLE_MIN,
    too_short: ts("must be at least %{min} letters long.", min: ArchiveConfig.TITLE_MIN)

  validates_length_of :title,
    maximum: ArchiveConfig.TITLE_MAX,
    too_long: ts("must be less than %{max} letters long.", max: ArchiveConfig.TITLE_MAX)

  # return title.html_safe to overcome escaping done by sanitiser
  def title
    read_attribute(:title).try(:html_safe)
  end

  validates_length_of :summary,
    allow_blank: true,
    maximum: ArchiveConfig.SUMMARY_MAX,
    too_long: ts("must be less than %{max} letters long.", max: ArchiveConfig.SUMMARY_MAX)

  validates_length_of :series_notes,
    allow_blank: true,
    maximum: ArchiveConfig.NOTES_MAX,
    too_long: ts("must be less than %{max} letters long.", max: ArchiveConfig.NOTES_MAX)

  after_save :adjust_restricted
  after_update :expire_caches
  after_update_commit :update_work_index

  scope :visible_to_registered_user, -> { where(hidden_by_admin: false).order('series.updated_at DESC') }
  scope :visible_to_all, -> { where(hidden_by_admin: false, restricted: false).order('series.updated_at DESC') }

  scope :exclude_anonymous, -> {
    joins("INNER JOIN `serial_works` ON (`series`.`id` = `serial_works`.`series_id`)
           INNER JOIN `works` ON (`works`.`id` = `serial_works`.`work_id`)").
    group("series.id").
    having("MAX(works.in_anon_collection) = 0 AND MAX(works.in_unrevealed_collection) = 0")
  }

  scope :for_pseuds, lambda {|pseuds|
    joins(:approved_creatorships).
    where("creatorships.pseud_id IN (?)", pseuds.collect(&:id))
  }

  scope :for_blurb, -> { includes(:work_tags, :pseuds) }

  def posted_works
    self.works.posted
  end

  def works_in_order
    works.order("serial_works.position")
  end

  # Get the filters for the works in this series
  def filters
    Tag.joins("JOIN filter_taggings ON tags.id = filter_taggings.filter_id
               JOIN works ON works.id = filter_taggings.filterable_id
               JOIN serial_works ON serial_works.work_id = works.id").
        where("serial_works.series_id = #{self.id} AND
               works.posted = 1 AND
               filter_taggings.filterable_type = 'Work'").
        group("tags.id")
  end

  def direct_filters
    filters.where("filter_taggings.inherited = 0")
  end

  # visibility aped from the work model
  def visible?(user = User.current_user)
    return true if user.is_a?(Admin)

    if posted && !hidden_by_admin
      user.is_a?(User) || !restricted
    else
      user_is_owner_or_invited?(user)
    end
  end

  # Override the default definition to check whether the user was invited to
  # any works in the series.
  def user_is_owner_or_invited?(user)
    return false unless user.is_a?(User)
    return true if super

    works.joins(:creatorships).merge(user.creatorships).exists? ||
      works.joins(chapters: :creatorships).merge(user.creatorships).exists?
  end

  def visible_work_count
    if User.current_user.nil?
      self.works.posted.unrestricted.count
    else
      self.works.posted.count
    end
  end

  def visible_word_count
    if User.current_user.nil?
      # visible_works_wordcount = self.works.posted.unrestricted.sum(:word_count)
      visible_works_wordcount = self.works.posted.unrestricted.pluck(:word_count).compact.sum
    else
      # visible_works_wordcount = self.works.posted.sum(:word_count)
      visible_works_wordcount = self.works.posted.pluck(:word_count).compact.sum
    end
    visible_works_wordcount
  end

  def anonymous?
    !self.works.select { |work| work.anonymous? }.empty?
  end

  def unrevealed?
    !self.works.select { |work| work.unrevealed? }.empty?
  end

  # if the series includes an unrestricted work, restricted should be false
  # if the series includes no unrestricted works, restricted should be true
  def adjust_restricted
    unless self.restricted? == !(self.works.where(restricted: false).count > 0)
      self.restricted = !(self.works.where(restricted: false).count > 0)
      self.save!(validate: false)
    end
  end

  # Visibility has changed, which means we need to reindex
  # the series' bookmarker pseuds, to update their bookmark counts.
  def should_reindex_pseuds?
    pertinent_attributes = %w[id restricted hidden_by_admin]
    destroyed? || (saved_changes.keys & pertinent_attributes).present?
  end

  def expire_caches
    # Expire cached work blurbs and metas if series title changes
    self.works.each(&:touch) if saved_change_to_title?
  end

  # Change the positions of the serial works in the series
  def reorder_list(positions)
    SortableList.new(self.serial_works.in_order).reorder_list(positions)
  end

  def position_of(work)
    serial_works.where(work_id: work.id).pluck(:position).first
  end

  # return list of pseuds on this series
  def allpseuds
    works.collect(&:pseuds).flatten.compact.uniq.sort
  end

  # Remove a user (and all their pseuds) as an author of this series.
  #
  # We call Work#remove_author before destroying the series creatorships to
  # make sure that we can handle tricky chapter creatorship cases.
  def remove_author(author_to_remove)
    pseuds_with_author_removed = pseuds.where.not(user_id: author_to_remove.id)
    raise Exception.new("Sorry, we can't remove all authors of a series.") if pseuds_with_author_removed.empty?
    transaction do
      authored_works_in_series = self.works.merge(author_to_remove.works)

      authored_works_in_series.each do |work|
        work.remove_author(author_to_remove)
      end

      creatorships.where(pseud: author_to_remove.pseuds).destroy_all
    end
  end

  # returns list of fandoms on this series
  def allfandoms
    works.collect(&:fandoms).flatten.compact.uniq.sort
  end

  def author_tags
    self.work_tags.select{|t| t.type == "Relationship"}.sort + self.work_tags.select{|t| t.type == "Character"}.sort + self.work_tags.select{|t| t.type == "Freeform"}.sort
  end

  def tag_groups
    self.work_tags.group_by { |t| t.type.to_s }
  end

  # Grabs the earliest published_at date of the visible works in the series
  def published_at
    if self.works.visible.posted.blank?
      self.created_at
    else
      Work.in_series(self).visible.collect(&:published_at).compact.uniq.sort.first
    end
  end

  def revised_at
    if self.works.visible.posted.blank?
      self.updated_at
    else
      Work.in_series(self).visible.collect(&:revised_at).compact.uniq.sort.last
    end
  end

  ######################
  # SEARCH
  ######################

  def bookmarkable_json
    as_json(
      root: false,
      only: [
        :title, :summary, :hidden_by_admin, :restricted, :created_at,
        :complete
      ],
      methods: [
        :revised_at, :posted, :tag, :filter_ids, :rating_ids,
        :archive_warning_ids, :category_ids, :fandom_ids, :character_ids,
        :relationship_ids, :freeform_ids, :creators,
        :word_count, :work_types]
    ).merge(
      language_id: language&.short,
      anonymous: anonymous?,
      unrevealed: unrevealed?,
      pseud_ids: anonymous? || unrevealed? ? nil : pseud_ids,
      user_ids: anonymous? || unrevealed? ? nil : user_ids,
      bookmarkable_type: 'Series',
      bookmarkable_join: { name: "bookmarkable" }
    )
  end

  def update_work_index
    self.works.each(&:enqueue_to_index) if saved_change_to_title?
  end

  def word_count
    self.works.posted.pluck(:word_count).compact.sum
  end

  # FIXME: should series have their own language?
  def language
    works.first.language if works.present?
  end

  def posted
    !posted_works.empty?
  end
  alias_method :posted?, :posted

  # Simple name to make it easier for people to use in full-text search
  def tag
    (work_tags + filters).uniq.map{ |t| t.name }
  end

  # Index all the filters for pulling works
  def filter_ids
    (work_tags.pluck(:id) + filters.pluck(:id)).uniq
  end

  # Index only direct filters (non meta-tags) for facets
  def filters_for_facets
    @filters_for_facets ||= direct_filters
  end
  def rating_ids
    filters_for_facets.select{ |t| t.type.to_s == 'Rating' }.map{ |t| t.id }
  end
  def archive_warning_ids
    filters_for_facets.select{ |t| t.type.to_s == 'ArchiveWarning' }.map{ |t| t.id }
  end
  def category_ids
    filters_for_facets.select{ |t| t.type.to_s == 'Category' }.map{ |t| t.id }
  end
  def fandom_ids
    filters_for_facets.select{ |t| t.type.to_s == 'Fandom' }.map{ |t| t.id }
  end
  def character_ids
    filters_for_facets.select{ |t| t.type.to_s == 'Character' }.map{ |t| t.id }
  end
  def relationship_ids
    filters_for_facets.select{ |t| t.type.to_s == 'Relationship' }.map{ |t| t.id }
  end
  def freeform_ids
    filters_for_facets.select{ |t| t.type.to_s == 'Freeform' }.map{ |t| t.id }
  end

  def creators
    anonymous? ? ['Anonymous'] : pseuds.map(&:byline)
  end

  def work_types
    works.map(&:work_types).flatten.uniq
  end
end
