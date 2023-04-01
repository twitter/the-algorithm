class Work < ApplicationRecord
  include Filterable
  include CreationNotifier
  include Collectible
  include Bookmarkable
  include Searchable
  include BookmarkCountCaching
  include WorkChapterCountCaching
  include Creatable

  ########################################################################
  # ASSOCIATIONS
  ########################################################################

  has_many :external_creatorships, as: :creation, dependent: :destroy, inverse_of: :creation
  has_many :archivists, through: :external_creatorships
  has_many :external_author_names, through: :external_creatorships, inverse_of: :works
  has_many :external_authors, -> { distinct }, through: :external_author_names

  # we do NOT use dependent => destroy here because we want to destroy chapters in REVERSE order
  has_many :chapters, inverse_of: :work, autosave: true

  has_many :serial_works, dependent: :destroy
  has_many :series, through: :serial_works

  has_many :related_works, as: :parent
  has_many :approved_related_works, -> { where(reciprocal: 1) }, as: :parent, class_name: "RelatedWork"
  has_many :parent_work_relationships, class_name: "RelatedWork", dependent: :destroy
  has_many :children, through: :related_works, source: :work
  has_many :approved_children, through: :approved_related_works, source: :work

  accepts_nested_attributes_for :parent_work_relationships, allow_destroy: true, reject_if: proc { |attrs| attrs.values_at(:url, :author, :title).all?(&:blank?) }

  has_many :gifts, dependent: :destroy
  accepts_nested_attributes_for :gifts, allow_destroy: true

  has_many :subscriptions, as: :subscribable, dependent: :destroy

  has_many :challenge_assignments, as: :creation
  has_many :challenge_claims, as: :creation
  accepts_nested_attributes_for :challenge_claims

  acts_as_commentable
  has_many :total_comments, class_name: 'Comment', through: :chapters
  has_many :kudos, as: :commentable, dependent: :destroy

  has_many :original_creators, class_name: "WorkOriginalCreator", dependent: :destroy

  belongs_to :language
  belongs_to :work_skin
  validate :work_skin_allowed, on: :save
  def work_skin_allowed
    unless self.users.include?(self.work_skin.author) || (self.work_skin.public? && self.work_skin.official?)
      errors.add(:base, ts("You do not have permission to use that custom work stylesheet."))
    end
  end
  # statistics
  has_many :work_links, dependent: :destroy
  has_one :stat_counter, dependent: :destroy
  after_create :create_stat_counter
  def create_stat_counter
    counter = self.build_stat_counter
    counter.save
  end
  # moderation
  has_one :moderated_work, dependent: :destroy

  ########################################################################
  # VIRTUAL ATTRIBUTES
  ########################################################################

  # Virtual attribute to use as a placeholder for pseuds before the work has been saved
  # Can't write to work.pseuds until the work has an id
  attr_accessor :new_parent, :url_for_parent
  attr_accessor :new_gifts
  attr_accessor :preview_mode

  # return title.html_safe to overcome escaping done by sanitiser
  def title
    read_attribute(:title).try(:html_safe)
  end

  ########################################################################
  # VALIDATION
  ########################################################################
  validates_presence_of :title
  validates_length_of :title,
    minimum: ArchiveConfig.TITLE_MIN,
    too_short: ts("must be at least %{min} characters long.", min: ArchiveConfig.TITLE_MIN)

  validates_length_of :title,
    maximum: ArchiveConfig.TITLE_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.TITLE_MAX)

  validates_length_of :summary,
    allow_blank: true,
    maximum: ArchiveConfig.SUMMARY_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.SUMMARY_MAX)

  validates_length_of :notes,
    allow_blank: true,
    maximum: ArchiveConfig.NOTES_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.NOTES_MAX)

  validates_length_of :endnotes,
    allow_blank: true,
    maximum: ArchiveConfig.NOTES_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.NOTES_MAX)

  validate :language_present_and_supported

  def language_present_and_supported
    errors.add(:base, ts("Language cannot be blank.")) if self.language.blank?
  end

  # Makes sure the title has no leading spaces
  validate :clean_and_validate_title

  def clean_and_validate_title
    unless self.title.blank?
      self.title = self.title.strip
      if self.title.length < ArchiveConfig.TITLE_MIN
        errors.add(:base, ts("Title must be at least %{min} characters long without leading spaces.", min: ArchiveConfig.TITLE_MIN))
        throw :abort
      else
        self.title_to_sort_on = self.sorted_title
      end
    end
  end

  def validate_published_at
    return unless first_chapter

    if !self.first_chapter.published_at
      self.first_chapter.published_at = Date.current
    elsif self.first_chapter.published_at > Date.current
      errors.add(:base, ts("Publication date can't be in the future."))
      throw :abort
    end
  end

  validates :fandom_string,
            presence: { message: "^Please fill in at least one fandom." }
  validates :archive_warning_string,
            presence: { message: "^Please select at least one warning." }
  validates :rating_string,
            presence: { message: "^Please choose a rating." }

  # rephrases the "chapters is invalid" message
  after_validation :check_for_invalid_chapters
  def check_for_invalid_chapters
    if self.errors[:chapters].any?
      self.errors.add(:base, ts("Please enter your story in the text field below."))
      self.errors.delete(:chapters)
    end
  end

  validates :user_defined_tags_count,
            at_most: { maximum: proc { ArchiveConfig.USER_DEFINED_TAGS_MAX } }

  # If the recipient doesn't allow gifts, it should not be possible to give them
  # a gift work unless it fulfills a gift exchange assignment or non-anonymous
  # prompt meme claim for the recipient.
  # We don't want the work to save if the gift shouldn't exist, but the gift
  # model can't access a work's challenge_assignments or challenge_claims until
  # the work and its assignments and claims are saved. Gifts are created after
  # the work is saved, so it's too late then to prevent the work from saving.
  # Additionally, the work's assignments and claims don't appear to be available
  # by the time gift validations run, which means the gift is never created if
  # the user doesn't allow them.
  validate :new_recipients_allow_gifts

  def new_recipients_allow_gifts
    return if self.new_gifts.blank?

    self.new_gifts.each do |gift|
      next if gift.pseud.blank?
      next if gift.pseud&.user&.preference&.allow_gifts?
      next if self.challenge_assignments.map(&:requesting_pseud).include?(gift.pseud)
      next if self.challenge_claims.reject { |c| c.request_prompt.anonymous? }.map(&:requesting_pseud).include?(gift.pseud)

      self.errors.add(:base, ts("#{gift.pseud.byline} does not accept gifts."))
    end
  end

  enum comment_permissions: {
    enable_all: 0,
    disable_anon: 1,
    disable_all: 2
  }, _suffix: :comments

  ########################################################################
  # HOOKS
  # These are methods that run before/after saves and updates to ensure
  # consistency and that associated variables are updated.
  ########################################################################

  before_save :clean_and_validate_title, :validate_published_at, :ensure_revised_at

  after_save :post_first_chapter
  before_save :set_word_count

  after_save :save_chapters, :save_new_gifts

  before_create :set_anon_unrevealed
  after_create :notify_after_creation

  after_update :adjust_series_restriction, :notify_after_update

  before_save :hide_spam
  after_save :moderate_spam
  after_save :notify_of_hiding

  after_save :notify_recipients, :expire_caches, :update_pseud_index, :update_tag_index, :touch_series
  after_destroy :expire_caches, :update_pseud_index

  before_destroy :send_deleted_work_notification, prepend: true
  def send_deleted_work_notification
    if self.posted?
      users = self.pseuds.collect(&:user).uniq
      orphan_account = User.orphan_account
      unless users.blank?
        for user in users
          next if user == orphan_account
          # Check to see if this work is being deleted by an Admin
          if User.current_user.is_a?(Admin)
            # this has to use the synchronous version because the work is going to be destroyed
            UserMailer.admin_deleted_work_notification(user, self).deliver_now
          else
            # this has to use the synchronous version because the work is going to be destroyed
            UserMailer.delete_work_notification(user, self).deliver_now
          end
        end
      end
    end
  end

  def expire_caches
    pseuds.each do |pseud|
      pseud.update_works_index_timestamp!
      pseud.user.update_works_index_timestamp!
    end

    collections.each do |this_collection|
      collection = this_collection
      # Flush this collection and all its parents
      loop do
        collection.update_works_index_timestamp!
        collection = collection.parent
        break unless collection
      end
    end

    filters.each do |tag|
      tag.update_works_index_timestamp!
    end

    tags.each do |tag|
      tag.update_tag_cache
    end

    Work.expire_work_blurb_version(id)
    Work.flush_find_by_url_cache unless imported_from_url.blank?
  end

  def update_pseud_index
    return unless should_reindex_pseuds?
    IndexQueue.enqueue_ids(Pseud, pseud_ids, :background)
  end

  # Visibility has changed, which means we need to reindex
  # the work's pseuds, to update their work counts, as well as
  # the work's bookmarker pseuds, to update their bookmark counts.
  def should_reindex_pseuds?
    pertinent_attributes = %w(id posted restricted in_anon_collection
                              in_unrevealed_collection hidden_by_admin)
    destroyed? || (saved_changes.keys & pertinent_attributes).present?
  end

  # If the work gets posted, we should (potentially) reindex the tags,
  # so they get the correct draft-only status.
  def update_tag_index
    return unless saved_change_to_posted?
    taggings.each(&:update_search)
  end

  def self.work_blurb_version_key(id)
    "/v3/work_blurb_tag_cache_key/#{id}"
  end

  def self.work_blurb_version(id)
    Rails.cache.fetch(Work.work_blurb_version_key(id), raw: true) { rand(1..1000) }
  end

  def self.expire_work_blurb_version(id)
    Rails.cache.increment(Work.work_blurb_version_key(id))
  end

  # When works are done being reindexed, expire the appropriate caches
  def self.successful_reindex(ids)
    CacheMaster.expire_caches(ids)
    tag_ids = FilterTagging.where(filterable_id: ids, filterable_type: 'Work').
                            group(:filter_id).
                            pluck(:filter_id)

    collection_ids = CollectionItem.where(item_id: ids, item_type: 'Work').
                                    group(:collection_id).
                                    pluck(:collection_id)

    pseuds = Pseud.select("pseuds.id, pseuds.user_id").
                    joins(:creatorships).
                    where(creatorships: {
                      creation_id: ids,
                      creation_type: 'Work'
                      }
                    )

    pseuds.each { |p| p.update_works_index_timestamp! }
    User.expire_ids(pseuds.map(&:user_id).uniq)
    Tag.expire_ids(tag_ids)
    Collection.expire_ids(collection_ids)
  end

  # TODO: AO3-6085 We can use touch_all once we update to Rails 6.
  def touch_series
    series.each(&:touch) if saved_change_to_in_anon_collection?
  end

  after_destroy :destroy_chapters_in_reverse
  def destroy_chapters_in_reverse
    chapters.sort_by(&:position).reverse.each(&:destroy)
  end

  after_destroy :clean_up_assignments
  def clean_up_assignments
    self.challenge_assignments.each {|a| a.creation = nil; a.save!}
  end

  ########################################################################
  # RESQUE
  ########################################################################

  include AsyncWithResque
  @queue = :utilities

  ########################################################################
  # IMPORTING
  ########################################################################

  def self.find_by_url_generation_key
    "/v1/find_by_url_generation_key"
  end

  def self.find_by_url_generation
    Rails.cache.fetch(Work.find_by_url_generation_key, raw: true) { rand(1..1000) }
  end

  def self.flush_find_by_url_cache
    Rails.cache.increment(Work.find_by_url_generation_key)
  end

  def self.find_by_url_cache_key(url)
    url = UrlFormatter.new(url)
    "/v1/find_by_url/#{Work.find_by_url_generation}/#{url.encoded}"
  end

  # Match `url` to a work's imported_from_url field using progressively fuzzier matching:
  # 1. first exact match
  # 2. first exact match with variants of the provided url
  # 3. first match on variants of both the imported_from_url and the provided url if there is a partial match

  def self.find_by_url_uncached(url)
    url = UrlFormatter.new(url)
    Work.where(imported_from_url: url.original).first ||
      Work.where(imported_from_url: [url.minimal, url.no_www, url.with_www, url.encoded, url.decoded]).first ||
      Work.where("imported_from_url LIKE ?", "%#{url.minimal_no_http}%").select { |w|
        work_url = UrlFormatter.new(w.imported_from_url)
        ['original', 'minimal', 'no_www', 'with_www', 'encoded', 'decoded'].any? { |method|
          work_url.send(method) == url.send(method)
        }
      }.first
  end

  def self.find_by_url(url)
    Rails.cache.fetch(Work.find_by_url_cache_key(url)) do
      find_by_url_uncached(url)
    end
  end

  # Remove all pseuds associated with a particular user. Raises an exception if
  # this would result in removing all creators from the work.
  #
  # Callbacks handle most of the work when deleting creatorships, but we do
  # have one special case: if a co-created work has a chapter that only has
  # one listed creator, and that creator removes themselves from the work, we
  # need to update the chapter to add the other creators on the work.
  def remove_author(author_to_remove)
    pseuds_with_author_removed = pseuds.where.not(user_id: author_to_remove.id)
    raise Exception.new("Sorry, we can't remove all creators of a work.") if pseuds_with_author_removed.empty?

    transaction do
      chapters.each do |chapter|
        if (chapter.pseuds - author_to_remove.pseuds).empty?
          pseuds_with_author_removed.each do |new_pseud|
            chapter.creatorships.find_or_create_by(pseud: new_pseud)
          end
        end

        chapter.creatorships.where(pseud: author_to_remove.pseuds).destroy_all
      end

      creatorships.where(pseud: author_to_remove.pseuds).destroy_all
    end
  end

  # Override the default behavior so that we also check for creatorships
  # associated with one of the chapters.
  def user_is_owner_or_invited?(user)
    return false unless user.is_a?(User)
    return true if super

    chapters.joins(:creatorships).merge(user.creatorships).exists?
  end

  def set_challenge_info
    # if this is fulfilling a challenge, add the collection and recipient
    challenge_assignments.each do |assignment|
      add_to_collection(assignment.collection)
      self.gifts << Gift.new(pseud: assignment.requesting_pseud) unless (assignment.requesting_pseud.blank? || recipients && recipients.include?(assignment.request_byline))
    end
  end

  # If this is fulfilling a challenge claim, add the collection.
  #
  # Unlike set_challenge_info, we don't automatically add the prompter as a
  # recipient, because (a) some prompters are anonymous, so there has to be a
  # prompter notification (separate from the recipient notification) ensuring
  # that anonymous prompters are notified, and (b) if the prompter is not
  # anonymous, they'll receive two notifications with roughly the same info
  # (gift notification + prompter notification).
  def set_challenge_claim_info
    challenge_claims.each do |claim|
      add_to_collection(claim.collection)
    end
  end

  def challenge_assignment_ids
    challenge_assignments.map(&:id)
  end

  def challenge_claim_ids
    challenge_claims.map(&:id)
  end

  # Only allow a work to fulfill an assignment assigned to one of this work's authors
  def challenge_assignment_ids=(ids)
    self.challenge_assignments =
      ids.map { |id| id.blank? ? nil : ChallengeAssignment.find(id) }.compact.
      select { |assign| (self.users + [User.current_user]).compact.include?(assign.offering_user) }
  end

  def recipients=(recipient_names)
    new_gifts = []
    gifts = [] # rebuild the list of associated gifts using the new list of names
    # add back in the rejected gift recips; we don't let users delete rejected gifts in order to prevent regifting
    recip_names = recipient_names.split(',') + self.gifts.are_rejected.collect(&:recipient)
    recip_names.uniq.each do |name|
      name.strip!
      gift = self.gifts.for_name_or_byline(name).first
      if gift
        gifts << gift # new gifts are added after saving, not now
        new_gifts << gift unless self.posted # all gifts are new if work not posted
      else
        g = self.gifts.new(recipient: name)
        if g.valid?
          new_gifts << g # new gifts are added after saving, not now
        else
          g.errors.full_messages.each { |msg| self.errors.add(:base, msg) }
        end
      end
    end
    self.gifts = gifts
    self.new_gifts = new_gifts
  end

  def recipients(for_form = false)
    names = (for_form ? self.gifts.not_rejected : self.gifts).collect(&:recipient)
    names << self.new_gifts.collect(&:recipient) if self.new_gifts.present?
    names.flatten.uniq.join(",")
  end

  def save_new_gifts
    return if self.new_gifts.blank?

    self.new_gifts.each do |gift|
      next if self.gifts.for_name_or_byline(gift.recipient).present?

      # Recreate the gift once the work is saved. This ensures the work_id is
      # set properly.
      Gift.create(recipient: gift.recipient, work: self)
    end
  end

  def marked_for_later?(user)
    Reading.where(work_id: self.id, user_id: user.id, toread: true).exists?
  end

  ########################################################################
  # VISIBILITY
  ########################################################################

  def visible?(user = User.current_user)
    return true if user.is_a?(Admin)

    if posted && !hidden_by_admin
      user.is_a?(User) || !restricted
    else
      user_is_owner_or_invited?(user)
    end
  end

  def unrevealed?(user=User.current_user)
    # eventually here is where we check if it's in a challenge that hasn't been made public yet
    #!self.collection_items.unrevealed.empty?
    in_unrevealed_collection?
  end

  def anonymous?(user = User.current_user)
    # here we check if the story is in a currently-anonymous challenge
    #!self.collection_items.anonymous.empty?
    in_anon_collection?
  end

  before_update :bust_anon_caching
  def bust_anon_caching
    if in_anon_collection_changed?
      async(:poke_cached_creator_comments)
    end
  end

  # This work's collections and parent collections
  def all_collections
    Collection.where(id: self.collection_ids) || []
  end

  ########################################################################
  # VERSIONS & REVISION DATES
  ########################################################################

  def set_revised_at(date=nil)
    date ||= self.chapters.where(posted: true).maximum('published_at') ||
             self.revised_at || self.created_at || Time.current

    if date.instance_of?(Date)
      # We need a time, not a Date. So if the date is today, set it to the
      # current time; otherwise, set it to noon UTC (so that almost every
      # single time zone will have the revised_at date match the published_at
      # date, and those that don't will have revised_at follow published_at).
      date = (date == Date.current) ? Time.current : date.to_time(:utc).noon
    end

    self.revised_at = date
  end

  def set_revised_at_by_chapter(chapter)
    # Invalidate chapter count cache
    self.invalidate_work_chapter_count(self)
    return if self.posted? && !chapter.posted?

    if (self.new_record? || chapter.posted_changed?) && chapter.published_at == Date.current
      self.set_revised_at(Time.current) # a new chapter is being posted, so most recent update is now
    else
      # Calculate the most recent chapter publication date:
      max_date = self.chapters.where('id != ? AND posted = 1', chapter.id).maximum('published_at')
      max_date = max_date.nil? ? chapter.published_at : [max_date, chapter.published_at].max

      # Update revised_at to match the chapter publication date unless the
      # dates already match:
      set_revised_at(max_date) unless revised_at && revised_at.to_date == max_date
    end
  end

  # Just to catch any cases that haven't gone through set_revised_at
  def ensure_revised_at
    self.set_revised_at if self.revised_at.nil?
  end

  def published_at
    self.first_chapter.published_at
  end

  # ensure published_at date is correct: reset its value for non-backdated works
  # "chapter" arg should be the unsaved session instance of the work's first chapter
  def reset_published_at(chapter)
    if !self.backdate
      if self.backdate_changed? # work was backdated but now it's not
        # so reset its date to our best guess at its original pub date:
        chapter.published_at = self.created_at.to_date
      else # pub date may have changed without user's explicitly setting backdate option
        # so reset it to the previous value:
        chapter.published_at = chapter.published_at_was || Date.current
      end
    end
  end

  def default_date
    backdate = first_chapter.try(:published_at) if self.backdate
    backdate || Date.current
  end

  ########################################################################
  # SERIES
  ########################################################################

  # Virtual attribute for series
  def series_attributes=(attributes)
    if !attributes[:id].blank?
      old_series = Series.find(attributes[:id])
      if old_series.pseuds.none? { |pseud| pseud.user == User.current_user }
        errors.add(:base, ts("You can't add a work to that series."))
        return
      end
      unless old_series.blank? || self.series.include?(old_series)
        self.serial_works.build(series: old_series)
      end
    elsif !attributes[:title].blank?
      new_series = Series.new
      new_series.title = attributes[:title]
      new_series.restricted = self.restricted
      (User.current_user.pseuds & self.pseuds_after_saving).each do |pseud|
        # Only add the current user's pseuds now -- the after_create callback
        # on the serial work will do the rest.
        new_series.creatorships.build(pseud: pseud)
      end
      self.serial_works.build(series: new_series)
    end
  end

  # Make sure the series restriction level is in line with its works
  def adjust_series_restriction
    unless self.series.blank?
      self.series.each {|s| s.adjust_restricted }
    end
  end

  ########################################################################
  # CHAPTERS
  ########################################################################

  # Save chapter data when the work is updated
  def save_chapters
    !self.chapters.first.save(validate: false)
  end

  # If the work is posted, the first chapter should be posted too
  def post_first_chapter
    chapter_one = self.first_chapter

    return unless self.saved_change_to_posted? && self.posted
    return if chapter_one&.posted

    chapter_one.published_at = Date.current unless self.backdate
    chapter_one.posted = true
    chapter_one.save
  end

  # Virtual attribute for first chapter
  def chapter_attributes=(attributes)
    self.new_record? ? self.chapters.build(attributes) : self.chapters.first.attributes = attributes
    self.chapters.first.posted = self.posted
  end

  # Virtual attribute for # of chapters
  def wip_length
    self.expected_number_of_chapters.nil? ? "?" : self.expected_number_of_chapters
  end

  def wip_length=(number)
    number = number.to_i
    self.expected_number_of_chapters = (number != 0 && number >= self.chapters.length) ? number : nil
  end

  # Change the positions of the chapters in the work
  def reorder_list(positions)
    SortableList.new(chapters_in_order(include_drafts: true)).reorder_list(positions)
    # We're caching the chapter positions in the comment blurbs
    # so we need to expire them
    async(:poke_cached_comments)
  end

  def poke_cached_comments
    self.comments.each { |c| c.touch }
  end

  def poke_cached_creator_comments
    self.creator_comments.each { |c| c.touch }
  end

  # Get the total number of chapters for a work
  def number_of_chapters
    Rails.cache.fetch(key_for_chapter_total_counting(self)) do
      self.chapters.count
    end
  end

  # Get the total number of posted chapters for a work
  # Issue 1316: total number needs to reflect the actual number of chapters posted
  # rather than the total number of chapters indicated by user
  def number_of_posted_chapters
    Rails.cache.fetch(key_for_chapter_posted_counting(self)) do
      self.chapters.posted.count
    end
  end

  def chapters_in_order(include_drafts: false, include_content: true)
    # in order
    chapters = self.chapters.order('position ASC')
    # only posted chapters unless specified
    chapters = chapters.where(posted: true) unless include_drafts
    # when doing navigation pass false as contents are not needed
    chapters = chapters.select('published_at, id, work_id, title, position, posted') unless include_content
    chapters
  end

  # Gets the current first chapter
  def first_chapter
    if self.new_record?
      self.chapters.first || self.chapters.build
    else
      self.chapters.order('position ASC').first
    end
  end

  # Gets the current last chapter
  def last_chapter
    self.chapters.order('position DESC').first
  end

  # Gets the current last posted chapter
  def last_posted_chapter
    self.chapters.posted.order('position DESC').first
  end

  # Returns true if a work has or will have more than one chapter
  def chaptered?
    self.expected_number_of_chapters != 1
  end

  # Returns true if a work has more than one chapter
  def multipart?
    self.number_of_chapters > 1
  end

  after_save :update_complete_status
  # Note: this can mark a work complete but it can also mark a complete work
  # as incomplete if its status has changed
  def update_complete_status
    # self.chapters.posted.count ( not self.number_of_posted_chapter , here be dragons )
    self.complete = self.chapters.posted.count == expected_number_of_chapters
    if self.will_save_change_to_attribute?(:complete)
      Work.where(id: id).update_all(["complete = ?", complete])
    end
  end

  # Returns true if a work is not yet complete
  def is_wip
    self.expected_number_of_chapters.nil? || self.expected_number_of_chapters != self.number_of_posted_chapters
  end

  # Returns true if a work is complete
  def is_complete
    return !self.is_wip
  end

  # 1/1, 2/3, 5/?, etc.
  def chapter_total_display
    current = self.posted? ? self.number_of_posted_chapters : 1
    current.to_s + '/' + self.wip_length.to_s
  end

  # Set the value of word_count to reflect the length of the chapter content
  # Called before_save
  def set_word_count(preview = false)
    if self.new_record? || preview
      self.word_count = 0
      chapters.each do |chapter|
        self.word_count += chapter.set_word_count
      end
    else
      # AO3-3498: For posted works, the word count is visible to people other than the creator and
      # should only include posted chapters. For drafts, we can count everything.
      self.word_count = if self.posted
                          Chapter.select("SUM(word_count) AS work_word_count").where(work_id: self.id, posted: true).first.work_word_count
                        else
                          Chapter.select("SUM(word_count) AS work_word_count").where(work_id: self.id).first.work_word_count
                        end
    end
  end

  #######################################################################
  # TAGGING
  # Works are taggable objects.
  #######################################################################

  # When the filters on a work change, we need to perform some extra checks.
  def self.reindex_for_filter_changes(ids, filter_taggings, queue)
    # The crossover/OTP status of a work can change without actually changing
    # the filters (e.g. if you have a work tagged with canonical fandom A and
    # unfilterable fandom B, synning B to A won't change the work's filters,
    # but the work will immediately stop qualifying as a crossover). So we want
    # to reindex all works whose filters were checked, not just the works that
    # had their filters changed.
    IndexQueue.enqueue_ids(Work, ids, queue)

    # Only works are included in the filter count, so if a work's
    # filter-taggings change, the FilterCount probably needs updating.
    FilterCount.enqueue_filters(filter_taggings.map(&:filter_id))

    # From here, we only want to update works whose filter_taggings have
    # actually changed.
    changed_ids = filter_taggings.map(&:filterable_id)
    return unless changed_ids.present?

    # Reindex any series associated with works whose filters have changed.
    series_ids = SerialWork.where(work_id: changed_ids).pluck(:series_id)
    IndexQueue.enqueue_ids(Series, series_ids, queue)

    # Reindex any pseuds associated with works whose filters have changed.
    pseud_ids = Creatorship.where(creation_id: changed_ids,
                                  creation_type: "Work",
                                  approved: true).pluck(:pseud_id)
    IndexQueue.enqueue_ids(Pseud, pseud_ids, queue)
  end

  # FILTERING CALLBACKS
  after_save :adjust_filter_counts

  # We need to do a recount for our filters if:
  # - the work is brand new
  # - the work is posted from a draft
  # - the work is hidden or unhidden by an admin
  # - the work's restricted status has changed
  # Note that because the two filter counts both include unrevealed works, we
  # don't need to check whether in_unrevealed_collection has changed -- it
  # won't change the counts either way.
  # (Modelled on Work.should_reindex_pseuds?)
  def should_reset_filters?
    pertinent_attributes = %w(id posted restricted hidden_by_admin)
    (saved_changes.keys & pertinent_attributes).present?
  end

  # Recalculates filter counts on all the work's filters
  def adjust_filter_counts
    FilterCount.enqueue_filters(filters.reload) if should_reset_filters?
  end

  ################################################################################
  # COMMENTING & BOOKMARKS
  # We don't actually have comments on works currently but on chapters.
  # Comment support -- work acts as a commentable object even though really we
  # override to consolidate the comments on all the chapters.
  ################################################################################

  # Gets all comments for all chapters in the work
  def find_all_comments
    Comment.where(
      parent_type: 'Chapter',
      parent_id: self.chapters.pluck(:id)
    )
  end

  # Returns number of comments
  # Hidden and deleted comments are referenced in the view because of
  # the threading system - we don't necessarily need to
  # hide their existence from other users
  def count_all_comments
    find_all_comments.count
  end

  # Count the number of comment threads visible to the user (i.e. excluding
  # threads that have been marked as spam). Used on the work stats page.
  def comment_thread_count
    comments.where(approved: true).count
  end

  # returns the top-level comments for all chapters in the work
  def comments
    Comment.where(
      commentable_type: 'Chapter',
      commentable_id: self.chapters.pluck(:id)
    )
  end

  # All comments left by the creators of this work
  def creator_comments
    pseud_ids = Pseud.where(user_id: self.pseuds.pluck(:user_id)).pluck(:id)
    find_all_comments.where(pseud_id: pseud_ids)
  end

  def guest_kudos_count
    Rails.cache.fetch "works/#{id}/guest_kudos_count-v2" do
      kudos.by_guest.count
    end
  end

  def all_kudos_count
    Rails.cache.fetch "works/#{id}/kudos_count-v2" do
      kudos.count
    end
  end

  def update_stat_counter
    counter = self.stat_counter || self.create_stat_counter
    counter.update(
      kudos_count: self.kudos.count,
      comments_count: self.count_visible_comments_uncached,
      bookmarks_count: self.bookmarks.where(private: false).count
    )
  end

  ########################################################################
  # RELATED WORKS
  # These are for inspirations/remixes/etc
  ########################################################################

  def parents_after_saving
    parent_work_relationships.reject(&:marked_for_destruction?)
  end

  #################################################################################
  #
  # In this section we define various named scopes that can be chained together
  # to do finds in the database
  #
  #################################################################################

  public

  scope :id_only, -> { select("works.id") }

  scope :ordered_by_title_desc, -> { order("title_to_sort_on DESC") }
  scope :ordered_by_title_asc, -> { order("title_to_sort_on ASC") }
  scope :ordered_by_word_count_desc, -> { order("word_count DESC") }
  scope :ordered_by_word_count_asc, -> { order("word_count ASC") }
  scope :ordered_by_hit_count_desc, -> { order("hit_count DESC") }
  scope :ordered_by_hit_count_asc, -> { order("hit_count ASC") }
  scope :ordered_by_date_desc, -> { order("revised_at DESC") }
  scope :ordered_by_date_asc, -> { order("revised_at ASC") }

  scope :recent, lambda { |*args| where("revised_at > ?", (args.first || 4.weeks.ago.to_date)) }
  scope :within_date_range, lambda { |*args| where("revised_at BETWEEN ? AND ?", (args.first || 4.weeks.ago), (args.last || Time.now)) }
  scope :posted, -> { where(posted: true) }
  scope :unposted, -> { where(posted: false) }
  scope :not_spam, -> { where(spam: false) }
  scope :restricted , -> { where(restricted: true) }
  scope :unrestricted, -> { where(restricted: false) }
  scope :hidden, -> { where(hidden_by_admin: true) }
  scope :unhidden, -> { where(hidden_by_admin: false) }
  scope :visible_to_all, -> { posted.unrestricted.unhidden }
  scope :visible_to_registered_user, -> { posted.unhidden }
  scope :visible_to_admin, -> { posted }
  scope :visible_to_owner, -> { posted }
  scope :all_with_tags, -> { includes(:tags) }

  scope :giftworks_for_recipient_name, lambda { |name| select("DISTINCT works.*").joins(:gifts).where("recipient_name = ?", name).where("gifts.rejected = FALSE") }

  scope :non_anon, -> { where(in_anon_collection: false) }
  scope :unrevealed, -> { where(in_unrevealed_collection: true) }
  scope :revealed, -> { where(in_unrevealed_collection: false) }
  scope :latest, -> { visible_to_all.
                      revealed.
                      order("revised_at DESC").
                      limit(ArchiveConfig.ITEMS_PER_PAGE) }

  # a complicated dynamic scope here:
  # if the user is an Admin, we use the "visible_to_admin" scope
  # if the user is not a logged-in User, we use the "visible_to_all" scope
  # otherwise, we use a join to get userids and then get all posted works that are either unhidden OR belong to this user.
  # Note: in that last case we have to use select("DISTINCT works.") because of cases where the same user appears twice
  # on a work.
  def self.visible_to_user(user=User.current_user)
    case user.class.to_s
    when 'Admin'
      visible_to_admin
    when 'User'
      select("DISTINCT works.*").
      posted.
      joins({pseuds: :user}).
      where("works.hidden_by_admin = false OR users.id = ?", user.id)
    else
      visible_to_all
    end
  end

  # Use the current user to determine what works are visible
  def self.visible(user=User.current_user)
    visible_to_user(user)
  end

  scope :owned_by, lambda {|user| select("DISTINCT works.*").joins({pseuds: :user}).where('users.id = ?', user.id)}

  def self.in_series(series)
    joins(:series).
    where("series.id = ?", series.id)
  end

  scope :with_columns_for_blurb, lambda {
    select(:id, :created_at, :updated_at, :expected_number_of_chapters,
           :posted, :language_id, :restricted, :title, :summary, :word_count,
           :hidden_by_admin, :revised_at, :complete, :in_anon_collection,
           :in_unrevealed_collection, :summary_sanitizer_version)
  }

  scope :with_includes_for_blurb, lambda {
    includes(:pseuds)
  }

  scope :for_blurb, -> { with_columns_for_blurb.with_includes_for_blurb }

  ########################################################################
  # SORTING
  ########################################################################

  SORTED_AUTHOR_REGEX = %r{^[\+\-=_\?!'"\.\/]}

  def authors_to_sort_on
    if self.anonymous?
      "Anonymous"
    else
      self.pseuds.sort.map(&:name).join(",  ").downcase.gsub(SORTED_AUTHOR_REGEX, '')
    end
  end

  def sorted_title
    sorted_title = self.title.downcase.gsub(/^["'\.\/]/, '')
    sorted_title = sorted_title.gsub(/^(an?) (.*)/, '\2, \1')
    sorted_title = sorted_title.gsub(/^the (.*)/, '\1, the')
    sorted_title = sorted_title.rjust(5, "0") if sorted_title.match(/^\d/)
    sorted_title
  end

  # sort works by title
  def <=>(another_work)
    self.title_to_sort_on <=> another_work.title_to_sort_on
  end

  ########################################################################
  # SPAM CHECKING
  ########################################################################

  def akismet_attributes
    content = chapters_in_order(include_drafts: true).map(&:content).join
    user = users.first
    {
      comment_type: "fanwork-post",
      key: ArchiveConfig.AKISMET_KEY,
      blog: ArchiveConfig.AKISMET_NAME,
      user_ip: ip_address,
      comment_date_gmt: created_at.to_time.iso8601,
      blog_lang: language.short,
      comment_author: user.login,
      comment_author_email: user.email,
      comment_content: content
    }
  end

  def spam_checked?
    spam_checked_at.present?
  end

  def check_for_spam
    return unless %w(staging production).include?(Rails.env)
    self.spam = Akismetor.spam?(akismet_attributes)
    self.spam_checked_at = Time.now
    save
  end

  def hide_spam
    return unless spam?
    admin_settings = AdminSetting.current
    if admin_settings.hide_spam?
      self.hidden_by_admin = true
    end
  end

  def moderate_spam
    ModeratedWork.register(self) if spam?
  end

  def mark_as_spam!
    update_attribute(:spam, true)
    ModeratedWork.mark_reviewed(self)
    # don't submit spam reports unless in production mode
    Rails.env.production? && Akismetor.submit_spam(akismet_attributes)
  end

  def mark_as_ham!
    update(spam: false, hidden_by_admin: false)
    ModeratedWork.mark_approved(self)
    # don't submit ham reports unless in production mode
    Rails.env.production? && Akismetor.submit_ham(akismet_attributes)
  end

  def notify_of_hiding
    return unless hidden_by_admin? && saved_change_to_hidden_by_admin?
    users.each do |user|
      if spam?
        UserMailer.admin_spam_work_notification(id, user.id).deliver_after_commit
      else
        UserMailer.admin_hidden_work_notification(id, user.id).deliver_after_commit
      end
    end
  end

  #############################################################################
  #
  # SEARCH INDEX
  #
  #############################################################################

  def document_json
    WorkIndexer.new({}).document(self)
  end

  def bookmarkable_json
    as_json(
      root: false,
      only: [
        :title, :summary, :hidden_by_admin, :restricted, :posted,
        :created_at, :revised_at, :word_count, :complete
      ],
      methods: [
        :tag, :filter_ids, :rating_ids, :archive_warning_ids, :category_ids,
        :fandom_ids, :character_ids, :relationship_ids, :freeform_ids,
        :creators, :collection_ids, :work_types
      ]
    ).merge(
      language_id: language&.short,
      anonymous: anonymous?,
      unrevealed: unrevealed?,
      pseud_ids: anonymous? || unrevealed? ? nil : pseud_ids,
      user_ids: anonymous? || unrevealed? ? nil : user_ids,
      bookmarkable_type: 'Work',
      bookmarkable_join: { name: "bookmarkable" }
    )
  end

  def collection_ids
    approved_collections.pluck(:id, :parent_id).flatten.uniq.compact
  end

  delegate :comments_count, :kudos_count, :bookmarks_count,
           to: :stat_counter, allow_nil: true

  def hits
    stat_counter&.hit_count
  end

  def creators
    if anonymous?
      ["Anonymous"]
    else
      pseuds.map(&:byline) + external_author_names.pluck(:name)
    end
  end

  # A work with multiple fandoms which are not related
  # to one another can be considered a crossover
  def crossover
    # Short-circuit the check if there's only one fandom tag:
    return false if fandoms.size == 1

    # Replace fandoms with their mergers if possible,
    # as synonyms should have no meta tags themselves
    all_without_syns = fandoms.map { |f| f.merger || f }.uniq

    # For each fandom, find the set of all meta tags for that fandom (including
    # the fandom itself).
    meta_tag_groups = all_without_syns.map do |f|
      # TODO: This is more complicated than it has to be. Once the
      # meta_taggings table is fixed so that the inherited meta-tags are
      # correctly calculated, this can be simplified.
      boundary = [f] + f.meta_tags
      all_meta_tags = []

      until boundary.empty?
        all_meta_tags.concat(boundary)
        boundary = boundary.flat_map(&:meta_tags).uniq - all_meta_tags
      end

      all_meta_tags.uniq
    end

    # Two fandoms are "related" if they share at least one meta tag. A work is
    # considered a crossover if there is no single fandom on the work that all
    # the other fandoms on the work are "related" to.
    meta_tag_groups.none? do |meta_tags1|
      meta_tag_groups.all? do |meta_tags2|
        (meta_tags1 & meta_tags2).any?
      end
    end
  end

  # Does this work have only one relationship tag?
  # (not counting synonyms)
  def otp
    return true if relationships.size == 1

    all_without_syns = relationships.map { |r| r.merger ? r.merger : r }.uniq.compact
    all_without_syns.count == 1
  end

  # Quick and dirty categorization of the most obvious stuff
  # To be replaced by actual categories
  def work_types
    types = []
    video_ids = [44011] # Video
    audio_ids = [70308, 1098169] # Podfic, Audio Content
    art_ids = [7844, 125758, 3863] # Fanart, Arts
    types << "Video" if (filter_ids & video_ids).present?
    types << "Audio" if (filter_ids & audio_ids).present?
    types << "Art" if (filter_ids & art_ids).present?
    # Very arbitrary cut off here, but wanted to make sure we
    # got fic + art/podfic/video tagged as text as well
    if types.empty? || (word_count && word_count > 200)
      types << "Text"
    end
    types
  end

  # To be replaced by actual category
  # Can't use the 'Meta' tag since that has too many different uses
  def nonfiction
    nonfiction_tags = [125773, 66586, 123921, 747397] # Essays, Nonfiction, Reviews, Reference
    (filter_ids & nonfiction_tags).present?
  end

  # Determines if this work allows invitations to collections,
  # meaning that at least one of the creators has opted-in.
  def allow_collection_invitation?
    users.any? { |user| user.preference.allow_collection_invitation }
  end
end
