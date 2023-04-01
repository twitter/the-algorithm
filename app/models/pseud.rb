class Pseud < ApplicationRecord
  include Searchable
  include WorksOwner

  has_attached_file :icon,
    styles: { standard: "100x100>" },
    path: if Rails.env.production?
            ":attachment/:id/:style.:extension"
          elsif Rails.env.staging?
            ":rails_env/:attachment/:id/:style.:extension"
          else
            ":rails_root/public/system/:rails_env/:class/:attachment/:id_partition/:style/:filename"
          end,
    storage: %w(staging production).include?(Rails.env) ? :s3 : :filesystem,
    s3_protocol: "https",
    s3_credentials: "#{Rails.root}/config/s3.yml",
    bucket: %w(staging production).include?(Rails.env) ? YAML.load_file("#{Rails.root}/config/s3.yml")['bucket'] : "",
    default_url: "/images/skins/iconsets/default/icon_user.png"

  validates_attachment_content_type :icon,
                                    content_type: %w[image/gif image/jpeg image/png],
                                    allow_nil: true

  validates_attachment_size :icon, less_than: 500.kilobytes, allow_nil: true

  NAME_LENGTH_MIN = 1
  NAME_LENGTH_MAX = 40
  DESCRIPTION_MAX = 500

  belongs_to :user

  delegate :login, to: :user, prefix: true, allow_nil: true
  alias user_name user_login

  has_many :bookmarks, dependent: :destroy
  has_many :recs, -> { where(rec: true) }, class_name: 'Bookmark'
  has_many :comments

  has_many :creatorships, dependent: :destroy
  has_many :approved_creatorships, -> { Creatorship.approved }, class_name: "Creatorship"

  has_many :works, through: :approved_creatorships, source: :creation, source_type: "Work"
  has_many :chapters, through: :approved_creatorships, source: :creation, source_type: "Chapter"
  has_many :series, through: :approved_creatorships, source: :creation, source_type: "Series"

  has_many :tags, through: :works
  has_many :filters, through: :works
  has_many :direct_filters, through: :works
  has_many :collection_participants, dependent: :destroy
  has_many :collections, through: :collection_participants
  has_many :tag_set_ownerships, dependent: :destroy
  has_many :tag_sets, through: :tag_set_ownerships
  has_many :challenge_signups, dependent: :destroy
  has_many :gifts, -> { where(rejected: false) }
  has_many :gift_works, through: :gifts, source: :work
  has_many :rejected_gifts, -> { where(rejected: true) }, class_name: "Gift"
  has_many :rejected_gift_works, through: :rejected_gifts, source: :work

  has_many :offer_assignments, -> { where("challenge_assignments.sent_at IS NOT NULL") }, through: :challenge_signups
  has_many :pinch_hit_assignments, -> { where("challenge_assignments.sent_at IS NOT NULL") }, class_name: "ChallengeAssignment", foreign_key: "pinch_hitter_id"

  has_many :prompts, dependent: :destroy

  before_validation :clear_icon

  validates_presence_of :name
  validates_length_of :name,
    within: NAME_LENGTH_MIN..NAME_LENGTH_MAX,
    too_short: ts("is too short (minimum is %{min} characters)", min: NAME_LENGTH_MIN),
    too_long: ts("is too long (maximum is %{max} characters)", max: NAME_LENGTH_MAX)
  validates :name, uniqueness: { scope: :user_id }
  validates_format_of :name,
    message: ts('can contain letters, numbers, spaces, underscores, and dashes.'),
    with: /\A[\p{Word} -]+\Z/u
  validates_format_of :name,
    message: ts('must contain at least one letter or number.'),
    with: /\p{Alnum}/u
  validates_length_of :description, allow_blank: true, maximum: DESCRIPTION_MAX,
    too_long: ts("must be less than %{max} characters long.", max: DESCRIPTION_MAX)
  validates_length_of :icon_alt_text, allow_blank: true, maximum: ArchiveConfig.ICON_ALT_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.ICON_ALT_MAX)
  validates_length_of :icon_comment_text, allow_blank: true, maximum: ArchiveConfig.ICON_COMMENT_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.ICON_COMMENT_MAX)

  after_update :check_default_pseud
  after_update :expire_caches
  after_commit :reindex_creations, :touch_comments

  def self.not_orphaned
    where("user_id != ?", User.orphan_account)
  end

  # Enigel Dec 12 08: added sort method
  # sorting by pseud name or by login name in case of equality
  def <=>(other)
    (self.name.downcase <=> other.name.downcase) == 0 ? (self.user_name.downcase <=> other.user_name.downcase) : (self.name.downcase <=> other.name.downcase)
  end

  def to_param
    name
  end

  scope :public_work_count_for, -> (pseud_ids) {
    select('pseuds.id, count(pseuds.id) AS work_count')
      .joins(:works)
      .where(
        pseuds: { id: pseud_ids }, works: { posted: true, hidden_by_admin: false, restricted: false }
      ).group('pseuds.id')
  }

  scope :posted_work_count_for, -> (pseud_ids) {
    select('pseuds.id, count(pseuds.id) AS work_count')
      .joins(:works)
      .where(
        pseuds: { id: pseud_ids }, works: { posted: true, hidden_by_admin: false }
      ).group('pseuds.id')
  }

  scope :public_rec_count_for, -> (pseud_ids) {
    select('pseuds.id, count(pseuds.id) AS rec_count')
    .joins(:bookmarks)
    .where(
      pseuds: { id: pseud_ids }, bookmarks: { private: false, hidden_by_admin: false, rec: true }
    )
    .group('pseuds.id')
  }

  def self.rec_counts_for_pseuds(pseuds)
    if pseuds.blank?
      {}
    else
      pseuds_with_counts = Pseud.public_rec_count_for(pseuds.collect(&:id))
      count_hash = {}
      pseuds_with_counts.each {|p| count_hash[p.id] = p.rec_count.to_i}
      count_hash
    end
  end

  def self.work_counts_for_pseuds(pseuds)
    if pseuds.blank?
      {}
    else
      if User.current_user.nil?
        pseuds_with_counts = Pseud.public_work_count_for(pseuds.collect(&:id))
      else
        pseuds_with_counts = Pseud.posted_work_count_for(pseuds.collect(&:id))
      end

      count_hash = {}
      pseuds_with_counts.each {|p| count_hash[p.id] = p.work_count.to_i}
      count_hash
    end
  end

  def unposted_works
    @unposted_works = self.works.where(posted: false).order(created_at: :desc)
  end


  # look up by byline
  scope :by_byline, -> (byline) {
    joins(:user)
      .where('users.login = ? AND pseuds.name = ?',
        (byline.include?('(') ? byline.split('(', 2)[1].strip.chop : byline),
        (byline.include?('(') ? byline.split('(', 2)[0].strip : byline)
      )
  }

  # Produces a byline that indicates the user's name if pseud is not unique
  def byline
    (name != user_name) ? "#{name} (#{user_name})" : name
  end

  # get the former byline
  def byline_was
    past_name = name_was.blank? ? name : name_was
    # if we have a user and their login has changed get the old one
    past_user_name = user.blank? ? "" : (user.login_was.blank? ? user.login : user.login_was)
    (past_name != past_user_name) ? "#{past_name} (#{past_user_name})" : past_name
  end

  # Parse a string of the "pseud.name (user.login)" format into a pseud
  def self.parse_byline(byline, options = {})
    pseud_name = ""
    login = ""
    if byline.include?("(")
      pseud_name, login = byline.split('(', 2)
      pseud_name = pseud_name.strip
      login = login.strip.chop
      conditions = ['users.login = ? AND pseuds.name = ?', login, pseud_name]
    else
      pseud_name = byline.strip
      if options[:assume_matching_login]
        conditions = ['users.login = ? AND pseuds.name = ?', pseud_name, pseud_name]
      else
        conditions = ['pseuds.name = ?', pseud_name]
      end
    end
    Pseud.joins(:user).where(conditions)
  end

  # Takes a comma-separated list of bylines
  # Returns a hash containing an array of pseuds and an array of bylines that couldn't be found
  def self.parse_bylines(list, options = {})
    valid_pseuds = []
    ambiguous_pseuds = {}
    failures = []
    banned_pseuds = []
    bylines = list.split ","
    for byline in bylines
      pseuds = Pseud.parse_byline(byline, options)
      if pseuds.length == 1
        pseud = pseuds.first
        if pseud.user.banned? || pseud.user.suspended?
          banned_pseuds << pseud
        else
          valid_pseuds << pseud
        end
      elsif pseuds.length > 1
        ambiguous_pseuds[pseuds.first.name] = pseuds
      else
        failures << byline.strip
      end
    end
    {
      pseuds: valid_pseuds.flatten.uniq,
      ambiguous_pseuds: ambiguous_pseuds,
      invalid_pseuds: failures,
      banned_pseuds: banned_pseuds.flatten.uniq.map(&:byline)
    }
  end

  ## AUTOCOMPLETE
  # set up autocomplete and override some methods
  include AutocompleteSource
  def autocomplete_prefixes
    [ "autocomplete_pseud" ]
  end

  def autocomplete_value
    "#{id}#{AUTOCOMPLETE_DELIMITER}#{byline}"
  end

  # This method is for use in before_* callbacks
  def autocomplete_value_was
    "#{id}#{AUTOCOMPLETE_DELIMITER}#{byline_was}"
  end

  # See byline_before_last_save for the reasoning behind why both this and
  # autocomplete_value_was exist in this model
  #
  # This method is for use in after_* callbacks
  def autocomplete_value_before_last_save
    "#{id}#{AUTOCOMPLETE_DELIMITER}#{byline_before_last_save}"
  end

  def byline_before_last_save
    past_name = name_before_last_save.blank? ? name : name_before_last_save

    # In this case, self belongs to a user that has already been saved
    # during it's (self's) callback cycle, which means we need to
    # look *back* at the user's [attributes]_before_last_save, since
    # [attribute]_was for the pseud's user will behave as if this were an
    # after_* callback on the user, instead of a before_* callback on self.
    #
    # see psued_sweeper.rb:13 for more context
    #
    past_user_name = user.blank? ? "" : (user.login_before_last_save.blank? ? user.login : user.login_before_last_save)
    (past_name != past_user_name) ? "#{past_name} (#{past_user_name})" : past_name
  end

  # This method is for removing stale autocomplete records in a before_*
  # callback, such as the one used in PseudSweeper
  #
  # This is a particular case for the Pseud model
  def remove_stale_from_autocomplete_before_save
    Rails.logger.debug "Removing stale from autocomplete: #{autocomplete_search_string_was}"
    self.class.remove_from_autocomplete(self.autocomplete_search_string_was, self.autocomplete_prefixes, self.autocomplete_value_was)
  end


  ## END AUTOCOMPLETE

  def replace_me_with_default
    replacement = user.default_pseud

    # We don't use change_ownership here because we want to transfer both
    # approved and unapproved creatorships.
    self.creatorships.includes(:creation).each do |creatorship|
      next if creatorship.creation.nil?

      existing =
        replacement.creatorships.find_by(creation: creatorship.creation)

      if existing
        existing.update(approved: existing.approved || creatorship.approved)
      else
        creatorship.update(pseud: replacement)
      end
    end

    # Update the pseud ID for all comments. Also updates the timestamp, so that
    # the cache is invalidated and the pseud change will be visible.
    Comment.where(pseud_id: self.id).update_all(pseud_id: replacement.id,
                                                updated_at: Time.now)
    change_collections_membership
    change_gift_recipients
    change_challenge_participation
    self.destroy
  end

  # Change the ownership of a creation from one pseud to another
  def change_ownership(creation, pseud, options={})
    transaction do
      # Update children before updating the creation itself, since deleting
      # creatorships from the creation will also delete them from the creation's
      # children.
      unless options[:skip_children]
        children = if creation.is_a?(Work)
                     creation.chapters
                   elsif creation.is_a?(Series)
                     creation.works
                   else
                     []
                   end

        children.each do |child|
          change_ownership(child, pseud, options)
        end
      end

      # Should only add new creatorships if we're an approved co-creator.
      if creation.creatorships.approved.where(pseud: self).exists?
        creation.creatorships.find_or_create_by(pseud: pseud)
      end

      # But we should delete all creatorships, even invited ones:
      creation.creatorships.where(pseud: self).destroy_all

      if creation.is_a?(Work)
        creation.series.each do |series|
          if series.work_pseuds.where(id: id).exists?
            series.creatorships.find_or_create_by(pseud: pseud)
          else
            change_ownership(series, pseud, options.merge(skip_children: true))
          end
        end
        comments = creation.total_comments.where("comments.pseud_id = ?", self.id)
        comments.each do |comment|
          comment.update_attribute(:pseud_id, pseud.id)
        end
      end

      # make sure changes affect caching/search/author fields
      creation.save
    end
  end

  def change_membership(collection, new_pseud)
    self.collection_participants.in_collection(collection).each do |cparticipant|
      cparticipant.pseud = new_pseud
      cparticipant.save
    end
  end

  def change_challenge_participation
    # We want to update all prompts associated with this pseud, but although
    # each prompt contains a pseud_id column, they're not indexed on it. That
    # means doing the search Prompt.where(pseud_id: self.id) would require
    # searching all rows of the prompts table. So instead, we do a join on the
    # challenge_signups table and look up prompts whose ChallengeSignup has the
    # pseud_id that we want to change.
    Prompt.joins(:challenge_signup).
      where("challenge_signups.pseud_id = #{id}").
      update_all("prompts.pseud_id = #{user.default_pseud.id}")

    ChallengeSignup.where("pseud_id = #{self.id}").update_all("pseud_id = #{self.user.default_pseud.id}")
    ChallengeAssignment.where("pinch_hitter_id = #{self.id}").update_all("pinch_hitter_id = #{self.user.default_pseud.id}")
    return
  end

  def change_gift_recipients
    Gift.where("pseud_id = #{self.id}").update_all("pseud_id = #{self.user.default_pseud.id}")
  end

  def change_bookmarks_ownership
    Bookmark.where("pseud_id = #{self.id}").update_all("pseud_id = #{self.user.default_pseud.id}")
  end

  def change_collections_membership
    CollectionParticipant.where("pseud_id = #{self.id}").update_all("pseud_id = #{self.user.default_pseud.id}")
  end

  def check_default_pseud
    if !self.is_default? && self.user.pseuds.to_enum.find(&:is_default?) == nil
      default_pseud = self.user.pseuds.select{|ps| ps.name.downcase == self.user_name.downcase}.first
      default_pseud.update_attribute(:is_default, true)
    end
  end

  def expire_caches
    if saved_change_to_name?
      self.works.each{ |work| work.touch }
    end
  end

  def touch_comments
    comments.touch_all
  end

  # Delete current icon (thus reverting to archive default icon)
  def delete_icon=(value)
    @delete_icon = !value.to_i.zero?
  end

  def delete_icon
    !!@delete_icon
  end
  alias_method :delete_icon?, :delete_icon

  def clear_icon
    self.icon = nil if delete_icon? && !icon.dirty?
  end

  #################################
  ## SEARCH #######################
  #################################

  def collection_ids
    collections.pluck(:id)
  end

  def document_json
    PseudIndexer.new({}).document(self)
  end

  def should_reindex_creations?
    pertinent_attributes = %w[id name]
    destroyed? || (saved_changes.keys & pertinent_attributes).present?
  end

  # If the pseud gets renamed, anything indexed with the old name needs to be reindexed:
  # works, series, bookmarks.
  def reindex_creations
    return unless should_reindex_creations?
    IndexQueue.enqueue_ids(Work, works.pluck(:id), :main)
    IndexQueue.enqueue_ids(Bookmark, bookmarks.pluck(:id), :main)
    IndexQueue.enqueue_ids(Series, series.pluck(:id), :main)
  end
end
