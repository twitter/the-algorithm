require "unicode_utils/casefold"

class Tag < ApplicationRecord
  include Searchable
  include StringCleaner
  include WorksOwner
  include Wrangleable
  include Rails.application.routes.url_helpers

  NAME = "Tag"

  # Note: the order of this array is important.
  # It is the order that tags are shown in the header of a work
  # (banned tags are not shown)
  TYPES = ['Rating', 'ArchiveWarning', 'Category', 'Media', 'Fandom', 'Relationship', 'Character', 'Freeform', 'Banned' ]

  # these tags can be filtered on
  FILTERS = TYPES - ['Banned', 'Media']

  # these tags show up on works
  VISIBLE = TYPES - ['Media', 'Banned']

  # these are tags which have been created by users
  # the order is important, and it is the order in which they appear in the tag wrangling interface
  USER_DEFINED = ['Fandom', 'Character', 'Relationship', 'Freeform']

  def self.label_name
    to_s.pluralize
  end

  delegate :document_type, to: :class

  def document_json
    TagIndexer.new({}).document(self)
  end

  def self.taggings_count_expiry(count)
    # What we are trying to do here is work out a resonable amount of time for a work to be cached for
    # This should take the number of taggings and divide it by TAGGINGS_COUNT_CACHE_DIVISOR  ( defaults to 1500 )
    # such that for example 1500, would be naturally be tagged for one minute while 105,000 would be cached for
    # 70 minutes. However we then apply a filter such that the minimum amount of time we will cache something for
    # would be TAGGINGS_COUNT_MIN_TIME ( defaults to 3 minutes ) and the maximum amount of time would be
    # TAGGINGS_COUNT_MAX_TIME ( defaulting to an hour ).
    expiry_time = count / (ArchiveConfig.TAGGINGS_COUNT_CACHE_DIVISOR || 1500)
    [[expiry_time, (ArchiveConfig.TAGGINGS_COUNT_MIN_TIME || 3)].max, (ArchiveConfig.TAGGINGS_COUNT_MAX_TIME || 50) + count % 20 ].min
  end

  def taggings_count_cache_key
    "/v1/taggings_count/#{id}"
  end

  def write_taggings_to_redis(value)
    REDIS_GENERAL.sadd("tag_update", id)
    REDIS_GENERAL.set("tag_update_#{id}_value", value)
    value
  end

  def taggings_count=(value)
    expiry_time = Tag.taggings_count_expiry(value)
    # Only write to the cache if there are more than a number of uses.
    Rails.cache.write(taggings_count_cache_key, value, race_condition_ttl: 10, expires_in: expiry_time.minutes) if value >= ArchiveConfig.TAGGINGS_COUNT_MIN_CACHE_COUNT
    write_taggings_to_redis(value)
  end

  def taggings_count
    cache_read = Rails.cache.read(taggings_count_cache_key)
    return cache_read unless cache_read.nil?
    real_value = taggings.count
    self.taggings_count = real_value
    real_value
  end

  def update_tag_cache
    cache_read = Rails.cache.read(taggings_count_cache_key)
    taggings_count if cache_read.nil? || (cache_read < ArchiveConfig.TAGGINGS_COUNT_MIN_CACHE_COUNT)
  end

  def update_counts_cache(id)
    tag = Tag.find(id)
    tag.taggings_count = tag.taggings.count
  end

  acts_as_commentable
  def commentable_name
    self.name
  end

  # For a tag, the commentable owners are the wranglers of the fandom(s)
  def commentable_owners
    # if the tag is a fandom, grab its wranglers or the wranglers of its canonical merger
    if self.is_a?(Fandom)
      self.canonical? ? self.wranglers : (self.merger_id ? self.merger.wranglers : [])
    # if the tag is any other tag, try to grab all the wranglers of all its parent fandoms, if applicable
    else
      begin
        self.fandoms.collect {|f| f.wranglers}.compact.flatten.uniq
      rescue
        []
      end
    end
  end

  has_many :mergers, foreign_key: 'merger_id', class_name: 'Tag'
  belongs_to :merger, class_name: 'Tag'
  belongs_to :fandom
  belongs_to :media
  belongs_to :last_wrangler, polymorphic: true

  has_many :filter_taggings, foreign_key: 'filter_id', dependent: :destroy
  has_many :filtered_works, through: :filter_taggings, source: :filterable, source_type: 'Work'
  has_many :filtered_external_works, through: :filter_taggings, source: :filterable, source_type: "ExternalWork"
  has_many :filtered_collections, through: :filter_taggings, source: :filterable, source_type: "Collection"

  has_one :filter_count, foreign_key: 'filter_id'
  has_many :direct_filter_taggings,
              -> { where(inherited: 0) },
              class_name: "FilterTagging",
              foreign_key: 'filter_id'

  # not used anymore? has_many :direct_filtered_works, through: :direct_filter_taggings, source: :filterable, source_type: 'Work'

  has_many :common_taggings, foreign_key: 'common_tag_id', dependent: :destroy
  has_many :child_taggings, class_name: 'CommonTagging', as: :filterable
  has_many :children, through: :child_taggings, source: :common_tag
  has_many :parents,
           through: :common_taggings,
           source: :filterable,
           source_type: 'Tag',
           before_remove: :destroy_common_tagging,
           after_remove: :update_wrangler

  has_many :meta_taggings, foreign_key: 'sub_tag_id', dependent: :destroy
  has_many :meta_tags, through: :meta_taggings, source: :meta_tag, before_remove: :destroy_meta_tagging
  has_many :sub_taggings, class_name: 'MetaTagging', foreign_key: 'meta_tag_id', dependent: :destroy
  has_many :sub_tags, through: :sub_taggings, source: :sub_tag, before_remove: :destroy_sub_tagging
  has_many :direct_meta_tags, -> { where('meta_taggings.direct = 1') }, through: :meta_taggings, source: :meta_tag
  has_many :direct_sub_tags, -> { where('meta_taggings.direct = 1') }, through: :sub_taggings, source: :sub_tag
  has_many :taggings, as: :tagger
  has_many :works, through: :taggings, source: :taggable, source_type: 'Work'
  has_many :collections, through: :taggings, source: :taggable, source_type: "Collection"

  has_many :bookmarks, through: :taggings, source: :taggable, source_type: 'Bookmark'
  has_many :external_works, through: :taggings, source: :taggable, source_type: 'ExternalWork'
  has_many :approved_collections, through: :filtered_works

  has_many :favorite_tags, dependent: :destroy

  has_many :set_taggings, dependent: :destroy
  has_many :tag_sets, through: :set_taggings
  has_many :owned_tag_sets, through: :tag_sets

  has_many :tag_set_associations, dependent: :destroy
  has_many :parent_tag_set_associations, class_name: 'TagSetAssociation', foreign_key: 'parent_tag_id', dependent: :destroy

  validates_presence_of :name
  validates :name, uniqueness: true
  validates_length_of :name, minimum: 1, message: "cannot be blank."
  validates_length_of :name,
    maximum: ArchiveConfig.TAG_MAX,
    message: "^Tag name '%{value}' is too long -- try using less than %{count} characters or using commas to separate your tags."
  validates_format_of :name,
    with: /\A[^,*<>^{}=`\\%]+\z/,
    message: "^Tag name '%{value}' cannot include the following restricted characters: , &#94; * < > { } = ` \\ %"

  validates_presence_of :sortable_name

  validate :unwrangleable_status
  def unwrangleable_status
    return unless unwrangleable?

    self.errors.add(:unwrangleable, "can't be set on a canonical or synonymized tag.") if canonical? || merger_id.present?
    self.errors.add(:unwrangleable, "can't be set on an unsorted tag.") if is_a?(UnsortedTag)
  end

  before_validation :check_synonym
  def check_synonym
    if !self.new_record? && self.name_changed?
      # ordinary wranglers can change case and accents but not punctuation or the actual letters in the name
      # admins can change tags with no restriction
      unless User.current_user.is_a?(Admin) || only_case_changed?
        self.errors.add(:name, "can only be changed by an admin.")
      end
    end
    if self.merger_id
      if self.canonical?
        self.errors.add(:base, "A canonical can't be a synonym")
      end
      if self.merger_id == self.id
        self.errors.add(:base, "A tag can't be a synonym of itself.")
      end
      unless self.merger.class == self.class
        self.errors.add(:base, "A tag can only be a synonym of a tag in the same category as itself.")
      end
    end
  end

  before_validation :squish_name
  def squish_name
    self.name = name.squish if self.name
  end

  before_validation :set_sortable_name
  def set_sortable_name
    if sortable_name.blank?
      self.sortable_name = remove_articles_from_string(self.name)
    end
  end

  after_update :queue_flush_work_cache
  def queue_flush_work_cache
    async_after_commit(:flush_work_cache) if saved_change_to_name? || saved_change_to_type?
  end

  def flush_work_cache
    self.work_ids.each do |work|
      Work.expire_work_blurb_version(work)
    end
  end

  before_save :set_last_wrangler
  def set_last_wrangler
    unless User.current_user.nil?
      self.last_wrangler = User.current_user
    end
  end
  def update_wrangler(tag)
    unless User.current_user.nil?
      self.update(last_wrangler: User.current_user)
    end
  end

  after_save :check_type_changes, if: :saved_change_to_type?
  def check_type_changes
    return if type_before_last_save.nil?

    retyped = Tag.find(self.id)

    # Clean up invalid CommonTaggings.
    retyped.common_taggings.destroy_invalid
    retyped.child_taggings.destroy_invalid

    # If the tag has just become a Fandom, it needs the Uncategorized media
    # added to it manually (the after_save hook on Fandom won't take effect,
    # since it's not a Fandom yet)
    retyped.add_media_for_uncategorized if retyped.is_a?(Fandom)
  end

  # Callback for has_many :parents.
  # Destroy the common tagging so we trigger CommonTagging's callbacks when a
  # parent is removed. We're specifically interested in the update_search
  # callback that will reindex the tag and return it to the unwrangled bin.
  def destroy_common_tagging(parent)
    self.common_taggings.find_by(filterable_id: parent.id).try(:destroy)
  end

  scope :id_only, -> { select("tags.id") }

  scope :canonical, -> { where(canonical: true) }
  scope :noncanonical, -> { where(canonical: false) }
  scope :nonsynonymous, -> { noncanonical.where(merger_id: nil) }
  scope :synonymous, -> { noncanonical.where("merger_id IS NOT NULL") }
  scope :unfilterable, -> { nonsynonymous.where(unwrangleable: false) }
  scope :unwrangleable, -> { where(unwrangleable: true) }

  # we need to manually specify a LEFT JOIN instead of just joins(:common_taggings or :meta_taggings) here because
  # what we actually need are the empty rows in the results
  scope :unwrangled, -> { joins("LEFT JOIN `common_taggings` ON common_taggings.common_tag_id = tags.id").where("unwrangleable = 0 AND common_taggings.id IS NULL") }
  scope :in_use, -> { where("canonical = 1 OR taggings_count_cache > 0") }
  scope :first_class, -> { joins("LEFT JOIN `meta_taggings` ON meta_taggings.sub_tag_id = tags.id").where("meta_taggings.id IS NULL") }

  # Tags that have sub tags
  scope :meta_tag, -> { joins(:sub_taggings).where("meta_taggings.id IS NOT NULL").group("tags.id") }
  # Tags that don't have sub tags
  scope :non_meta_tag, -> { joins(:sub_taggings).where("meta_taggings.id IS NULL").group("tags.id") }

  scope :by_popularity, -> { order('taggings_count_cache DESC') }
  scope :by_name, -> { order('sortable_name ASC') }
  scope :by_date, -> { order('created_at DESC') }
  scope :visible, -> { where('type in (?)', VISIBLE).by_name }

  scope :by_pseud, lambda {|pseud|
    joins(works: :pseuds).
    where(pseuds: {id: pseud.id})
  }

  scope :by_type, lambda {|*types| where(types.first.blank? ? "" : {type: types.first})}
  scope :with_type, lambda {|type| where({type: type}) }

  # This will return all tags that have one of the given tags as a parent
  scope :with_parents, lambda {|parents|
    joins(:common_taggings).where("filterable_id in (?)", parents.first.is_a?(Integer) ? parents : (parents.respond_to?(:pluck) ? parents.pluck(:id) : parents.collect(&:id)))
  }

  scope :with_no_parents, -> {
    joins("LEFT JOIN common_taggings ON common_taggings.common_tag_id = tags.id").
    where("filterable_id IS NULL")
  }

  scope :starting_with, lambda {|letter| where('SUBSTR(name,1,1) = ?', letter)}

  scope :visible_to_all_with_count, -> {
    joins(:filter_count).
    select("tags.*, filter_counts.public_works_count as count").
    where('filter_counts.public_works_count > 0 AND tags.canonical = 1')
  }

  scope :visible_to_registered_user_with_count, -> {
    joins(:filter_count).
    select("tags.*, filter_counts.unhidden_works_count as count").
    where('filter_counts.unhidden_works_count > 0 AND tags.canonical = 1')
  }

  scope :public_top, lambda { |tag_count|
    visible_to_all_with_count.
    limit(tag_count).
    order('filter_counts.public_works_count DESC')
  }

  scope :unhidden_top, lambda { |tag_count|
    visible_to_registered_user_with_count.
    limit(tag_count).
    order('filter_counts.unhidden_works_count DESC')
  }

  scope :popular, -> {
    (User.current_user.is_a?(Admin) || User.current_user.is_a?(User)) ?
      visible_to_registered_user_with_count.order('filter_counts.unhidden_works_count DESC') :
      visible_to_all_with_count.order('filter_counts.public_works_count DESC')
  }

  scope :random, -> {
    (User.current_user.is_a?(Admin) || User.current_user.is_a?(User)) ?
    visible_to_registered_user_with_count.random_order :
    visible_to_all_with_count.random_order
  }

  scope :with_count, -> {
    (User.current_user.is_a?(Admin) || User.current_user.is_a?(User)) ?
      visible_to_registered_user_with_count : visible_to_all_with_count
  }

  scope :for_collections, lambda { |collections|
    joins(filtered_works: :approved_collection_items).merge(Work.posted)
      .where("collection_items.collection_id IN (?)", collections.collect(&:id))
  }

  scope :for_collection, lambda { |collection| for_collections([collection]) }

  scope :for_collections_with_count, lambda { |collections|
    for_collections(collections).
    select("tags.*, count(tags.id) as count").
    group(:id).
    order(:name)
  }

  scope :with_scoped_count, lambda {
    select("tags.*, count(tags.id) as count").
    group(:id)
  }

  scope :by_relationships, lambda {|relationships|
    select("DISTINCT tags.*").
    joins(:children).
    where('children_tags.id IN (?)', relationships.collect(&:id))
  }

  # Get the tags for a challenge's signups, checking both the main tag set
  # and the optional tag set for each prompt
  def self.in_challenge(collection, prompt_type=nil)
    ['', 'optional_'].map { |tag_set_type|
      join = "INNER JOIN set_taggings ON (tags.id = set_taggings.tag_id)
        INNER JOIN tag_sets ON (set_taggings.tag_set_id = tag_sets.id)
        INNER JOIN prompts ON (prompts.#{tag_set_type}tag_set_id = tag_sets.id)
        INNER JOIN challenge_signups ON (prompts.challenge_signup_id = challenge_signups.id)"

      tags = self.joins(join).where("challenge_signups.collection_id = ?", collection.id)
      tags = tags.where("prompts.type = ?", prompt_type) if prompt_type.present?
      tags
    }.flatten.compact.uniq
  end

  scope :requested_in_challenge, lambda {|collection|
    in_challenge(collection, 'Request')
  }

  scope :offered_in_challenge, lambda {|collection|
    in_challenge(collection, 'Offer')
  }

  # Code for delayed jobs:
  include AsyncWithActiveJob
  self.async_job_class = TagMethodJob

  # Class methods


  def self.in_prompt_restriction(restriction)
    joins("INNER JOIN set_taggings ON set_taggings.tag_id = tags.id
           INNER JOIN tag_sets ON tag_sets.id = set_taggings.tag_set_id
           INNER JOIN owned_tag_sets ON owned_tag_sets.tag_set_id = tag_sets.id
           INNER JOIN owned_set_taggings ON owned_set_taggings.owned_tag_set_id = owned_tag_sets.id
           INNER JOIN prompt_restrictions ON (prompt_restrictions.id = owned_set_taggings.set_taggable_id AND owned_set_taggings.set_taggable_type = 'PromptRestriction')").
    where("prompt_restrictions.id = ?", restriction.id)
  end

  def self.by_name_without_articles(fieldname = "name")
    fieldname = "name" unless fieldname.match(/^([\w]+\.)?[\w]+$/)
    order(Arel.sql("case when lower(substring(#{fieldname} from 1 for 4)) = 'the ' then substring(#{fieldname} from 5)
            when lower(substring(#{fieldname} from 1 for 2)) = 'a ' then substring(#{fieldname} from 3)
            when lower(substring(#{fieldname} from 1 for 3)) = 'an ' then substring(#{fieldname} from 4)
            else #{fieldname}
            end"))
  end

  def self.in_tag_set(tag_set)
    if tag_set.is_a?(OwnedTagSet)
      joins(:set_taggings).where("set_taggings.tag_set_id = ?", tag_set.tag_set_id)
    else
      joins(:set_taggings).where("set_taggings.tag_set_id = ?", tag_set.id)
    end
  end

  # gives you [parent_name, child_name], [parent_name, child_name], ...
  def self.parent_names(parent_type = 'fandom')
    joins(:parents).where("parents_tags.type = ?", parent_type.capitalize).
    select("parents_tags.name as parent_name, tags.name as child_name").
    by_name_without_articles("parent_name").
    by_name_without_articles("child_name")
  end

  # Because this can be called by a gigantor tag set and all we need are names not objects,
  # we do an end-run around ActiveRecord and just get the results straight from the db, but
  # we borrow the sql from parent_names above
  # returns a hash[parent_name] = child_names
  def self.names_by_parent(child_relation, parent_type = 'fandom')
    hash = {}
    results = ActiveRecord::Base.connection.execute(child_relation.parent_names(parent_type).to_sql)
    results.each {|row| hash[row.first] ||= Array.new; hash[row.first] << row.second}
    hash
  end

  # Used for associations, such as work.fandoms.string
  # Yields a comma-separated list of tag names
  def self.string
    all.map{|tag| tag.name}.join(ArchiveConfig.DELIMITER_FOR_OUTPUT)
  end

  # Use the tag name in urls and escape url-unfriendly characters
  def to_param
    # can't find a tag with a name that hasn't been saved yet
    saved_name = self.name_changed? ? self.name_was : self.name
    saved_name.gsub('/', '*s*').gsub('&', '*a*').gsub('.', '*d*').gsub('?', '*q*').gsub('#', '*h*')
  end

  def display_name
    name
  end

  # Make sure that the global ID doesn't depend on the type, so that we don't
  # experience errors when switching types:
  def to_global_id(options = {})
    GlobalID.create(becomes(Tag), options)
  end

  ## AUTOCOMPLETE
  # set up autocomplete and override some methods
  include AutocompleteSource
  def autocomplete_prefixes
    prefixes = [ "autocomplete_tag_#{type.downcase}", "autocomplete_tag_all" ]
    prefixes
  end

  def add_to_autocomplete(score = nil)
    score ||= autocomplete_score
    if self.is_a?(Character) || self.is_a?(Relationship)
      parents.each do |parent|
        REDIS_AUTOCOMPLETE.zadd(self.transliterate("autocomplete_fandom_#{parent.name.downcase}_#{type.downcase}"), score, autocomplete_value) if parent.is_a?(Fandom)
      end
    end
    super
  end

  def remove_from_autocomplete
    super
    if self.is_a?(Character) || self.is_a?(Relationship)
      parents.each do |parent|
        REDIS_AUTOCOMPLETE.zrem(self.transliterate("autocomplete_fandom_#{parent.name.downcase}_#{type.downcase}"), autocomplete_value) if parent.is_a?(Fandom)
      end
    end
  end

  def remove_stale_from_autocomplete
    super
    if self.is_a?(Character) || self.is_a?(Relationship)
      parents.each do |parent|
        REDIS_AUTOCOMPLETE.zrem(self.transliterate("autocomplete_fandom_#{parent.name.downcase}_#{type.downcase}"), autocomplete_value_before_last_save) if parent.is_a?(Fandom)
      end
    end
  end

  def self.parse_autocomplete_value(current_autocomplete_value)
    current_autocomplete_value.split(AUTOCOMPLETE_DELIMITER, 2)
  end


  def autocomplete_score
    taggings_count_cache
  end

  # look up tags that have been wrangled into a given fandom
  def self.autocomplete_fandom_lookup(options = {})
    options.reverse_merge!({term: "", tag_type: "character", fandom: "", fallback: true})
    search_param = options[:term]
    tag_type = options[:tag_type]
    fandoms = Tag.get_search_terms(options[:fandom])

    # fandom sets are too small to bother breaking up
    # we're just getting ALL the tags in the set(s) for the fandom(s) and then manually matching
    results = []
    fandoms.each do |single_fandom|
      if search_param.blank?
        # just return ALL the characters
        results += REDIS_AUTOCOMPLETE.zrevrange(self.transliterate("autocomplete_fandom_#{single_fandom}_#{tag_type}"), 0, -1)
      else
        search_regex = Tag.get_search_regex(search_param)
        results += REDIS_AUTOCOMPLETE.zrevrange(self.transliterate("autocomplete_fandom_#{single_fandom}_#{tag_type}"), 0, -1).select { |tag| tag.match(search_regex) }
      end
    end
    if options[:fallback] && results.empty? && search_param.length > 0
      # do a standard tag lookup instead
      Tag.autocomplete_lookup(search_param: search_param, autocomplete_prefix: "autocomplete_tag_#{tag_type}")
    else
      results
    end
  end

  ## END AUTOCOMPLETE

  # Substitute characters that are particularly prone to cause trouble in urls
  def self.find_by_name(string)
    return unless string.is_a? String
    string = string.gsub(
      /\*[sadqh]\*/,
      '*s*' => '/',
      '*a*' => '&',
      '*d*' => '.',
      '*q*' => '?',
      '*h*' => '#'
    )
    self.where('tags.name = ?', string).first
  end

  # If a tag by this name exists in another class, add a suffix to disambiguate them
  def self.find_or_create_by_name(new_name)
    if new_name && new_name.is_a?(String)
      new_name.squish!
      tag = Tag.find_by_name(new_name)
      # if the tag exists and has the proper class, or it is an unsorted tag and it can be sorted to the self class
      if tag && (tag.class == self || tag.class == UnsortedTag && tag = tag.recategorize(self.to_s))
        tag
      elsif tag
        self.find_or_create_by_name(new_name + " - " + self.to_s)
      else
        self.create(name: new_name, type: self.to_s)
      end
    end
  end

  def self.create_canonical(name, adult=false)
    tag = self.find_or_create_by_name(name)
    raise "how did this happen?" unless tag
    tag.update_attribute(:canonical,true)
    tag.update_attribute(:adult, adult)
    raise "how did this happen?" unless tag.canonical?
    return tag
  end

  # Inherited tag classes can set this to indicate types of tags with which they may have a parent/child
  # relationship (ie. media: parent, fandom: child; fandom: parent, character: child)
  def parent_types
    []
  end
  def child_types
    []
  end

  # Instance methods that are common to all subclasses (may be overridden in the subclass)

  def unfilterable?
    !(self.canonical? || self.unwrangleable? || self.merger_id.present? || self.mergers.any?)
  end

  # Returns true if a tag has been used in posted works
  def has_posted_works?
    self.works.posted.any?
  end

  # sort tags by name
  def <=>(another_tag)
    name.downcase <=> another_tag.name.downcase
  end

  # only allow changing the tag type for unwrangled tags not used in any tag sets or on any works
  def can_change_type?
    self.unfilterable? && self.set_taggings.count == 0 && self.works.count == 0
  end

  # tags having their type changed need to be reloaded to be seen as an instance of the proper subclass
  def recategorize(new_type)
    self.update_attribute(:type, new_type)
    # return a new instance of the tag, with the correct class
    Tag.find(self.id)
  end

  #### FILTERING ####

  before_update :reindex_associated_for_name_or_type_change
  def reindex_associated_for_name_or_type_change
    return unless name_changed? || type_changed?

    reindex_pseuds = (type == "Fandom") || (type_was == "Fandom")
    async_after_commit(:reindex_associated, reindex_pseuds)
  end

  # Reindex anything even remotely related to this tag. This is overkill in
  # most cases, but necessary when something fundamental like the name or type
  # of a tag has changed.
  def reindex_associated(reindex_pseuds = false)
    works.reindex_all
    external_works.reindex_all
    bookmarks.reindex_all

    filtered_works.reindex_all
    filtered_external_works.reindex_all

    Series.joins(works: :taggings)
      .merge(self.taggings).reindex_all
    Series.joins(works: :filter_taggings)
      .merge(self.filter_taggings).reindex_all

    # We only want to reindex pseuds if this tag is a Fandom. Unfortunately, we
    # can't just check the current type, because tags can change type, and we'd
    # still need to reindex if the old type was Fandom. So we have an option to
    # control it.
    if reindex_pseuds
      Pseud.joins(works: :filter_taggings)
        .merge(self.direct_filter_taggings).reindex_all
    end
  end

  # The version of the tag that should be used for filtering, if any
  def filter
    self.canonical? ? self : ((self.merger && self.merger.canonical?) ? self.merger : nil)
  end

  # Update filters for all works and external works directly tagged with this
  # tag.
  def update_filters_for_taggables
    works.update_filters
    external_works.update_filters
    collections.update_filters
  end

  # Update filters for all works and external works that already have this tag
  # as one of their filters.
  def update_filters_for_filterables
    filtered_works.update_filters
    filtered_external_works.update_filters
    filtered_collections.update_filters
  end

  # When canonical or merger_id changes, only the items directly tagged with
  # this tag need their filters updated, so we queue up a call to
  # update_filters_for_taggables after commit.
  #
  # Note that when a tag becomes non-canonical, all of its filter-taggings need
  # to be deleted. But when a tag becomes non-canonical, all of its mergers and
  # sub-tags will be deleted, which will result in the necessary items having
  # their filters fixed.
  after_update :update_filters_for_canonical_or_merger_change
  def update_filters_for_canonical_or_merger_change
    return unless saved_change_to_canonical? || saved_change_to_merger_id?

    async_after_commit(:update_filters_for_taggables)
  end

  # Recalculate the inherited metatags for this tag, and once those changes
  # are committed, update the filters for every work or external work that's
  # filter-tagged with this tag.
  def update_inherited_meta_tags
    MetaTagging.transaction do
      InheritedMetaTagUpdater.new(self).update

      sub_tags.find_each do |sub_tag|
        InheritedMetaTagUpdater.new(sub_tag).update
      end
    end

    async_after_commit(:update_filters_for_filterables)
  end

  # When deleting a metatag, we destroy the meta-tagging first to trigger the
  # appropriate destroy callback.
  def destroy_meta_tagging(meta_tag)
    meta_taggings.find_by(meta_tag: meta_tag)&.destroy
  end

  # When deleting a subtag, we destroy the sub-tagging first to trigger the
  # appropriate destroy callback.
  def destroy_sub_tagging(sub_tag)
    sub_taggings.find_by(sub_tag: sub_tag)&.destroy
  end

  def reset_filter_count
    FilterCount.enqueue_filter(filter)
  end

  #### END FILTERING ####

  # methods for counting visible

  def visible_works_count
    User.current_user.nil? ? self.works.posted.unhidden.unrestricted.count : self.works.posted.unhidden.count
  end

  def visible_bookmarks_count
    self.bookmarks.is_public.count
  end

  def visible_external_works_count
    self.external_works.where(hidden_by_admin: false).count
  end

  def banned
    self.is_a?(Banned)
  end

  def synonyms
    self.canonical? ? self.mergers : [self.merger] + self.merger.mergers - [self]
  end

  # Add a common tagging association
  def add_association(tag)
    build_association(tag).save
  end

  def has_parent?(tag)
    self.common_taggings.where(filterable_id: tag.id).count > 0
  end

  def has_child?(tag)
    self.child_taggings.where(common_tag_id: tag.id).count > 0
  end

  def associations_to_remove; @associations_to_remove ? @associations_to_remove : []; end
  def associations_to_remove=(taglist)
    taglist.reject {|tid| tid.blank?}.each do |tag_id|
      remove_association(tag_id)
    end
  end

  # Determine how two tags are related and divorce them from each other
  def remove_association(tag_id)
    tag = Tag.find(tag_id)

    if tag.class == self.class
      tag.update(merger: nil) if tag.merger == self
      meta_taggings.where(direct: true, meta_tag: tag).destroy_all
      sub_taggings.where(direct: true, sub_tag: tag).destroy_all
    else
      common_taggings.where(filterable: tag).destroy_all
      child_taggings.where(common_tag: tag).destroy_all
    end

    tag.touch
    self.touch
  end

  # When canonical or merger is changed, we need to make sure that the
  # associations (parents, children, metatags, mergers) are fixed. Note that
  # these are all async calls, so we use async_after_commit to reduce the
  # likelihood of issues with stale data.
  before_update :update_associations_for_canonical_or_merger_change
  def update_associations_for_canonical_or_merger_change
    if (merger_id_changed? && merger_id.present?) ||
       (canonical_changed? && !canonical?)
      async_after_commit(:transfer_or_remove_favorite_tags)
      async_after_commit(:transfer_or_remove_associations)
    end
  end

  # Make it possible to go from a synonym to a canonical in one step.
  before_validation :reset_merger_when_becoming_canonical
  def reset_merger_when_becoming_canonical
    return unless self.canonical_changed? && self.canonical?

    self.merger_id = nil
  end

  # If this tag has a canonical merger, transfer associations to the merger.
  # Then, regardless of whether it has a merger, delete all canonical
  # associations (i.e. meta taggings, and associations where this tag is the
  # parent).
  def transfer_or_remove_associations
    transaction do
      # Try to prevent some concurrency issues.
      lock!

      # Abort if the tag has changed back to being canonical between the time
      # this was enqueued and the time it ran.
      return if self.canonical?

      add_associations_to_merger if self.merger&.canonical?

      self.mergers.find_each { |tag| tag.update(merger_id: nil) }
      self.child_taggings.destroy_all
      self.sub_taggings.destroy_all
      self.meta_taggings.destroy_all
    end
  end

  # When we make this tag a synonym of another canonical tag, we want to move
  # all the associations this tag has (subtags, metatags, etc) over to that
  # canonical tag.
  #
  # The callbacks that occur when changing the associations will trigger the
  # necessary reindexing, so we don't need to call extra reindexing code here.
  def add_associations_to_merger
    self.parents.find_each do |tag|
      self.merger.add_association(tag)
    end

    self.children.find_each do |tag|
      self.merger.add_association(tag)
    end

    self.mergers.find_each { |tag| tag.update(merger: self.merger) }

    merger.parents.where(type: %w[Media Fandom]).find_each do |tag|
      self.add_association(tag)
    end

    self.direct_meta_tags.find_each do |tag|
      meta_tagging = self.merger.meta_taggings.find_or_initialize_by(meta_tag: tag)
      meta_tagging.update(direct: true)
    end

    self.direct_sub_tags.find_each do |tag|
      sub_tagging = self.merger.sub_taggings.find_or_initialize_by(sub_tag: tag)
      sub_tagging.update(direct: true)
    end
  end

  # If this tag has a canonical merger, move all favorite tags to the merger.
  # Otherwise, delete all favorite tags.
  def transfer_or_remove_favorite_tags
    if merger&.canonical
      favorite_tags.find_each do |ft|
        ft.update(tag_id: merger_id)
      end
    end

    # We perform this after the if (instead of as a separate branch) because
    # updating the tag_id can fail if the user has both this tag and its merger
    # as favorite tags. So we want to clean up any failures, which just so
    # happens to be exactly the same thing we need to do if there's no
    # canonical merger to transfer the favorite tags to.
    favorite_tags.find_each(&:destroy)
  end

  attr_reader :meta_tag_string, :sub_tag_string, :merger_string

  # Uses the value of parent_types to determine whether the passed-in tag
  # should be added as a parent or a child, and then generates the association
  # (if it doesn't already exist). If it does already exist, returns the
  # existing CommonTagging object.
  def build_association(tag)
    if parent_types.include?(tag&.type)
      common_taggings.find_or_initialize_by(filterable: tag)
    else
      child_taggings.find_or_initialize_by(common_tag: tag)
    end
  end

  # Splits up the passed-in string into a sequence of individual tag names,
  # then finds (and yields) the tag for each. Used by add_association_string,
  # meta_tag_string=, and sub_tag_string=.
  def parse_tag_string(tag_string)
    tag_string.split(",").map(&:squish).each do |name|
      yield name, Tag.find_by_name(name)
    end
  end

  # Try to create new associations with the tags of type tag_type whose names
  # are listed in tag_string.
  def add_association_string(tag_type, tag_string)
    parse_tag_string(tag_string) do |name, parent|
      prefix = "Cannot add association to '#{name}':"
      if parent && parent.type != tag_type
        errors.add(:base, "#{prefix} #{parent.type} added in #{tag_type} field.")
      else
        association = build_association(parent)
        save_and_gather_errors(association, prefix)
      end
    end
  end

  # Save an item to the database, if it's valid. If it's invalid, read in the
  # error messages from the item and copy them over to this tag.
  def save_and_gather_errors(item, prefix)
    return unless item.new_record? || item.changed?
    return if item.valid? && item.save

    item.errors.full_messages.each do |message|
      errors.add(:base, "#{prefix} #{message}")
    end
  end

  # Find and destroy all invalid CommonTaggings and MetaTaggings associated
  # with this tag.
  def destroy_invalid_associations
    common_taggings.destroy_invalid
    child_taggings.destroy_invalid
    meta_taggings.destroy_invalid
    sub_taggings.destroy_invalid
  end

  # defines fandom_string=, media_string=, character_string=, relationship_string=, freeform_string=
  %w(Fandom Media Character Relationship Freeform).each do |tag_type|
    attr_reader "#{tag_type.downcase}_string"

    define_method("#{tag_type.downcase}_string=") do |tag_string|
      add_association_string(tag_type, tag_string)
    end
  end

  def meta_tag_string=(tag_string)
    parse_tag_string(tag_string) do |name, parent|
      meta_tagging = meta_taggings.find_or_initialize_by(meta_tag: parent)
      meta_tagging.direct = true
      save_and_gather_errors(meta_tagging, "Invalid metatag '#{name}':")
    end
  end

  def sub_tag_string=(tag_string)
    parse_tag_string(tag_string) do |name, sub|
      sub_tagging = sub_taggings.find_or_initialize_by(sub_tag: sub)
      sub_tagging.direct = true
      save_and_gather_errors(sub_tagging, "Invalid subtag '#{name}':")
    end
  end

  def syn_string
    self.merger.name if self.merger
  end

  # Make this tag a synonym of another tag -- tag_string is the name of the other tag (which should be canonical)
  # NOTE for potential confusion
  # "merger" is the canonical tag of which this one will be a synonym
  # "mergers" are the tags which are (currently) synonyms of THIS one
  def syn_string=(tag_string)
    # If the tag_string is blank, our tag should be given no merger
    if tag_string.blank?
      self.merger_id = nil
      return
    end

    new_merger = Tag.find_by(name: tag_string)

    # Bail out if the new merger is the same as the current merger
    return if new_merger && new_merger == self.merger

    # Return an error if a non-admin tries to make a canonical into a synonym
    if self.canonical? && !User.current_user.is_a?(Admin)
      self.errors.add(:base, "Only an admin can make a canonical tag into a synonym of another tag.")
      return
    end

    if new_merger && new_merger == self
      self.errors.add(:base, tag_string + " is considered the same as " + self.name + " by the database.")
    elsif new_merger && !new_merger.canonical?
      self.errors.add(:base, "<a href=\"#{edit_tag_path(new_merger)}\">#{new_merger.name}</a> is not a canonical tag. Please make it canonical before adding synonyms to it.")
    elsif new_merger && new_merger.class != self.class
      self.errors.add(:base, new_merger.name + " is a #{new_merger.type.to_s.downcase}. Synonyms must belong to the same category.")
    elsif !new_merger
      new_merger = self.class.new(name: tag_string, canonical: true)
      unless new_merger.save
        self.errors.add(:base, tag_string + " could not be saved. Please make sure that it's a valid tag name.")
      end
    end

    # If we don't have any errors, update the tag to add the new merger
    if new_merger && self.errors.empty?
      self.canonical = false
      self.merger_id = new_merger.id
    end
  end

  def merger_string=(tag_string)
    names = tag_string.split(',').map(&:squish)
    names.each do |name|
      syn = Tag.find_by_name(name)
      if syn && !syn.canonical?
        syn.update(merger_id: self.id)
      end
    end
  end

  #################################
  ## SEARCH #######################
  #################################

  def unwrangled_query(tag_type, options = {})
    self_type = %w(Character Fandom Media).include?(self.type) ? self.type.downcase : "fandom"
    TagQuery.new(options.merge(
      type: tag_type,
      unwrangleable: false,
      wrangled: false,
      "pre_#{self_type}_ids": [self.id],
      per_page: Tag.per_page
    ))
  end

  def unwrangled_tags(tag_type, options = {})
    unwrangled_query(tag_type, options).search_results
  end

  def unwrangled_tag_count(tag_type)
    key = "unwrangled_#{tag_type}_#{self.id}_#{self.updated_at}"
    Rails.cache.fetch(key, expires_in: 4.hours) do
      unwrangled_query(tag_type).count
    end
  end

  def suggested_parent_tags(parent_type, options = {})
    limit = options[:limit] || 50
    work_ids = works.limit(limit).pluck(:id)
    Tag.distinct.joins(:taggings).where(
      "tags.type" => parent_type,
      taggings: {
        taggable_type: 'Work',
        taggable_id: work_ids
      }
    )
  end

  # For works that haven't been wrangled yet, get the fandom/character tags
  # that are used on their works as a place to start
  def suggested_parent_ids(parent_type)
    return [] if !parent_types.include?(parent_type) ||
      unwrangleable? ||
      parents.by_type(parent_type).exists?

    suggested_parent_tags(parent_type).pluck(:id, :merger_id).
                                       flatten.compact.uniq
  end

  def queue_child_tags_for_reindex
    all_with_child_type = Tag.where(type: child_types & Tag::USER_DEFINED)
    works.select(:id).find_in_batches do |batch|
      relevant_taggings = Tagging.where(taggable: batch)
      tag_ids = all_with_child_type.joins(:taggings).merge(relevant_taggings).distinct.pluck(:id)
      IndexQueue.enqueue_ids(Tag, tag_ids, :background)
    end
  end

  after_create :after_create
  def after_create
    tag = self
    if tag.canonical
      tag.add_to_autocomplete
    end
    update_tag_nominations(tag)
  end

  after_update :after_update
  def after_update
    tag = self
    if tag.saved_change_to_canonical?
      if tag.canonical
        # newly canonical tag
        tag.add_to_autocomplete
      else
        # decanonicalised tag
        tag.remove_from_autocomplete
      end
    elsif tag.canonical
      # clean up the autocomplete
      tag.remove_stale_from_autocomplete
      tag.add_to_autocomplete
    end

    # Expire caching when a merger is added or removed
    if tag.saved_change_to_merger_id?
      if tag.merger_id_before_last_save.present?
        old = Tag.find(tag.merger_id_before_last_save)
        old.update_works_index_timestamp!
      end
      if tag.merger_id.present?
        tag.merger.update_works_index_timestamp!
      end
      async_after_commit(:queue_child_tags_for_reindex)
    end

    # if type has changed, expire the tag's parents' children cache (it stores the children's type)
    if tag.saved_change_to_type?
      tag.parents.each do |parent_tag|
        ActionController::Base.new.expire_fragment("views/tags/#{parent_tag.id}/children")
      end
    end

    # Reindex immediately to update the unwrangled bin.
    if tag.saved_change_to_unwrangleable?
      tag.reindex_document
    end

    update_tag_nominations(tag)
  end

  before_destroy :before_destroy
  def before_destroy
    tag = self
    if Tag::USER_DEFINED.include?(tag.type) && tag.canonical
      tag.remove_from_autocomplete
    end
    update_tag_nominations(tag, true)
  end

  private

  def update_tag_nominations(tag, deleted=false)
    values = {}
    if deleted
      values[:canonical] = false
      values[:exists] = false
      values[:parented] = false
      values[:synonym] = nil
    else
      values[:canonical] = tag.canonical
      values[:synonym] = tag.merger.nil? ? nil : tag.merger.name
      values[:parented] = tag.parents.any? {|p| p.is_a?(Fandom)}
      values[:exists] = true
    end
    TagNomination.where(tagname: tag.name).update_all(values)
  end

  def only_case_changed?
    new_normalized_name = normalize_for_tag_comparison(self.name)
    old_normalized_name = normalize_for_tag_comparison(self.name_was)
    (self.name.downcase == self.name_was.downcase) ||
      (new_normalized_name == old_normalized_name)
  end

  def normalize_for_tag_comparison(string)
    UnicodeUtils.casefold(string).mb_chars.unicode_normalize(:nfkd).gsub(/[\u0300-\u036F]/u, "")
  end
end
