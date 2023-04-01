class Collection < ApplicationRecord
  include Filterable
  include UrlHelpers
  include WorksOwner

  has_attached_file :icon,
  styles: { standard: "100x100>" },
  url: "/system/:class/:attachment/:id/:style/:basename.:extension",
  path: %w(staging production).include?(Rails.env) ? ":class/:attachment/:id/:style.:extension" : ":rails_root/public:url",
  storage: %w(staging production).include?(Rails.env) ? :s3 : :filesystem,
  s3_protocol: "https",
  s3_credentials: "#{Rails.root}/config/s3.yml",
  bucket: %w(staging production).include?(Rails.env) ? YAML.load_file("#{Rails.root}/config/s3.yml")['bucket'] : "",
  default_url: "/images/skins/iconsets/default/icon_collection.png"

  validates_attachment_content_type :icon, content_type: /image\/\S+/, allow_nil: true
  validates_attachment_size :icon, less_than: 500.kilobytes, allow_nil: true

  belongs_to :parent, class_name: "Collection", inverse_of: :children
  has_many :children, class_name: "Collection", foreign_key: "parent_id", inverse_of: :parent

  has_one :collection_profile, dependent: :destroy
  accepts_nested_attributes_for :collection_profile

  has_one :collection_preference, dependent: :destroy
  accepts_nested_attributes_for :collection_preference

  before_create :ensure_associated
  def ensure_associated
    self.collection_preference = CollectionPreference.new unless  self.collection_preference
    self.collection_profile = CollectionProfile.new unless  self.collection_profile
  end


  belongs_to :challenge, dependent: :destroy, polymorphic: true
  has_many :prompts, dependent: :destroy

  has_many :signups, class_name: "ChallengeSignup", dependent: :destroy
  has_many :potential_matches, dependent: :destroy
  has_many :assignments, class_name: "ChallengeAssignment", dependent: :destroy
  has_many :claims, class_name: "ChallengeClaim", dependent: :destroy

  # We need to get rid of all of these if the challenge is destroyed
  after_save :clean_up_challenge
  def clean_up_challenge
    return if self.challenge_id

    assignments.each(&:destroy)
    potential_matches.each(&:destroy)
    signups.each(&:destroy)
    prompts.each(&:destroy)
  end

  has_many :collection_items, dependent: :destroy
  accepts_nested_attributes_for :collection_items, allow_destroy: true
  has_many :approved_collection_items, -> { approved_by_both }, class_name: "CollectionItem"

  has_many :works, through: :collection_items, source: :item, source_type: 'Work'
  has_many :approved_works, -> { posted }, through: :approved_collection_items, source: :item, source_type: "Work"

  has_many :bookmarks, through: :collection_items, source: :item, source_type: 'Bookmark'
  has_many :approved_bookmarks, through: :approved_collection_items, source: :item, source_type: "Bookmark"

  has_many :collection_participants, dependent: :destroy
  accepts_nested_attributes_for :collection_participants, allow_destroy: true

  has_many :participants, through: :collection_participants, source: :pseud
  has_many :users, through: :participants, source: :user
  has_many :invited, -> { where('collection_participants.participant_role = ?', CollectionParticipant::INVITED) }, through: :collection_participants, source: :pseud
  has_many :owners, -> { where('collection_participants.participant_role = ?', CollectionParticipant::OWNER) }, through: :collection_participants, source: :pseud
  has_many :moderators, -> { where('collection_participants.participant_role = ?', CollectionParticipant::MODERATOR) }, through: :collection_participants, source: :pseud
  has_many :members, -> { where('collection_participants.participant_role = ?', CollectionParticipant::MEMBER) }, through: :collection_participants, source: :pseud
  has_many :posting_participants, -> { where('collection_participants.participant_role in (?)', [CollectionParticipant::MEMBER,CollectionParticipant::MODERATOR, CollectionParticipant::OWNER ] ) }, through: :collection_participants, source: :pseud



  CHALLENGE_TYPE_OPTIONS = [
                             ["", ""],
                             [ts("Gift Exchange"), "GiftExchange"],
                             [ts("Prompt Meme"), "PromptMeme"],
                           ]

  before_validation :clear_icon

  validate :must_have_owners
  def must_have_owners
    # we have to use collection participants because the association may not exist until after
    # the collection is saved
    errors.add(:base, ts("Collection has no valid owners.")) if (self.collection_participants + (self.parent ? self.parent.collection_participants : [])).select {|p| p.is_owner?}.empty?
  end

  validate :collection_depth
  def collection_depth
    if (self.parent && self.parent.parent) || (self.parent && !self.children.empty?) || (!self.children.empty? && !self.children.collect(&:children).flatten.empty?)
      errors.add(:base, ts("Sorry, but %{name} is a subcollection, so it can't also be a parent collection.", name: parent.name))
    end
  end

  validate :parent_exists
  def parent_exists
    unless parent_name.blank? || Collection.find_by(name: parent_name)
      errors.add(:base, ts("We couldn't find a collection with name %{name}.", name: parent_name))
    end
  end

  validate :parent_is_allowed
  def parent_is_allowed
    if parent
      if parent == self
        errors.add(:base, ts("You can't make a collection its own parent."))
      elsif parent_id_changed? && !parent.user_is_maintainer?(User.current_user)
        errors.add(:base, ts("You have to be a maintainer of %{name} to make a subcollection.", name: parent.name))
      end
    end
  end

  validates_presence_of :name, message: ts("Please enter a name for your collection.")
  validates :name, uniqueness: { message: ts("Sorry, that name is already taken. Try again, please!") }
  validates_length_of :name,
    minimum: ArchiveConfig.TITLE_MIN,
    too_short: ts("must be at least %{min} characters long.", min: ArchiveConfig.TITLE_MIN)
  validates_length_of :name,
    maximum: ArchiveConfig.TITLE_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.TITLE_MAX)
  validates_format_of :name,
    message: ts('must begin and end with a letter or number; it may also contain underscores. It may not contain any other characters, including spaces.'),
    with: /\A[A-Za-z0-9]\w*[A-Za-z0-9]\Z/
  validates_length_of :icon_alt_text, allow_blank: true, maximum: ArchiveConfig.ICON_ALT_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.ICON_ALT_MAX)
  validates_length_of :icon_comment_text, allow_blank: true, maximum: ArchiveConfig.ICON_COMMENT_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.ICON_COMMENT_MAX)

  validates :email, email_veracity: {allow_blank: true}

  validates_presence_of :title, message: ts("Please enter a title to be displayed for your collection.")
  validates_length_of :title,
    minimum: ArchiveConfig.TITLE_MIN,
    too_short: ts("must be at least %{min} characters long.", min: ArchiveConfig.TITLE_MIN)
  validates_length_of :title,
    maximum: ArchiveConfig.TITLE_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.TITLE_MAX)
  validate :no_reserved_strings
  def no_reserved_strings
    errors.add(:title, ts("^Sorry, the ',' character cannot be in a collection Display Title.")) if
      title.match(/\,/)
  end

  # return title.html_safe to overcome escaping done by sanitiser
  def title
    read_attribute(:title).try(:html_safe)
  end

  validates_length_of :description,
    allow_blank: true,
    maximum: ArchiveConfig.SUMMARY_MAX,
    too_long: ts("must be less than %{max} characters long.", max: ArchiveConfig.SUMMARY_MAX)

  validates_format_of :header_image_url, allow_blank: true, with: URI::regexp(%w(http https)), message: ts("is not a valid URL.")
  validates_format_of :header_image_url, allow_blank: true, with: /\.(png|gif|jpg)$/, message: ts("can only point to a gif, jpg, or png file."), multiline: true

  validates :tags_after_saving,
            length: { maximum: ArchiveConfig.COLLECTION_TAGS_MAX,
                      message: "^Sorry, a collection can only have %{count} tags." }

  scope :top_level, -> { where(parent_id: nil) }
  scope :closed, -> { joins(:collection_preference).where("collection_preferences.closed = ?", true) }
  scope :not_closed, -> { joins(:collection_preference).where("collection_preferences.closed = ?", false) }
  scope :moderated, -> { joins(:collection_preference).where("collection_preferences.moderated = ?", true) }
  scope :unmoderated, -> { joins(:collection_preference).where("collection_preferences.moderated = ?", false) }
  scope :unrevealed, -> { joins(:collection_preference).where("collection_preferences.unrevealed = ?", true) }
  scope :anonymous, -> { joins(:collection_preference).where("collection_preferences.anonymous = ?", true) }
  scope :no_challenge, -> { where(challenge_type: nil) }
  scope :gift_exchange, -> { where(challenge_type: 'GiftExchange') }
  scope :prompt_meme, -> { where(challenge_type: 'PromptMeme') }
  scope :name_only, -> { select("collections.name") }
  scope :by_title, -> { order(:title) }

  before_validation :cleanup_url
  def cleanup_url
    self.header_image_url = reformat_url(self.header_image_url) if self.header_image_url
  end

  # Get only collections with running challenges
  def self.signup_open(challenge_type)
    if challenge_type == "PromptMeme"
      not_closed.where(challenge_type: challenge_type).
        joins("INNER JOIN prompt_memes on prompt_memes.id = challenge_id").where("prompt_memes.signup_open = 1").
        where("prompt_memes.signups_close_at > ?", Time.now).order("prompt_memes.signups_close_at DESC")
    elsif challenge_type == "GiftExchange"
      not_closed.where(challenge_type: challenge_type).
        joins("INNER JOIN gift_exchanges on gift_exchanges.id = challenge_id").where("gift_exchanges.signup_open = 1").
        where("gift_exchanges.signups_close_at > ?", Time.now).order("gift_exchanges.signups_close_at DESC")
    end
  end

  scope :with_name_like, lambda {|name|
    where("collections.name LIKE ?", '%' + name + '%').
    limit(10)
  }

  scope :with_title_like, lambda {|title|
    where("collections.title LIKE ?", '%' + title + '%')
  }

  scope :with_item_count, -> {
    select("collections.*, count(distinct collection_items.id) as item_count").
    joins("left join collections child_collections on child_collections.parent_id = collections.id
           left join collection_items on ( (collection_items.collection_id = child_collections.id OR collection_items.collection_id = collections.id)
                                     AND collection_items.user_approval_status = 1
                                     AND collection_items.collection_approval_status = 1)").
    group("collections.id")
  }

  def to_param
   name_was
  end

  # Change membership of collection(s) from a particular pseud to the orphan account
  def self.orphan(pseuds, collections, default=true)
    for pseud in pseuds
      for collection in collections
        if pseud && collection && collection.owners.include?(pseud)
          orphan_pseud = default ? User.orphan_account.default_pseud : User.orphan_account.pseuds.find_or_create_by(name: pseud.name)
          pseud.change_membership(collection, orphan_pseud)
        end
      end
    end
  end

  ## AUTOCOMPLETE
  # set up autocomplete and override some methods
  include AutocompleteSource

  def autocomplete_search_string
    "#{name} #{title}"
  end

  def autocomplete_search_string_before_last_save
    "#{name_before_last_save} #{title_before_last_save}"
  end

  def autocomplete_prefixes
    [ "autocomplete_collection_all",
      "autocomplete_collection_#{closed? ? 'closed' : 'open'}" ]
  end

  def autocomplete_score
    all_items.approved_by_collection.approved_by_user.count
  end
  ## END AUTOCOMPLETE


  def parent_name=(name)
    @parent_name = name
    self.parent = Collection.find_by(name: name)
  end

  def parent_name
    @parent_name || (self.parent ? self.parent.name : "")
  end

  def all_owners
    (self.owners + (self.parent ? self.parent.owners : [])).uniq
  end

  def all_moderators
    (self.moderators + (self.parent ? self.parent.moderators : [])).uniq
  end

  def all_members
    (self.members + (self.parent ? self.parent.members : [])).uniq
  end

  def all_posting_participants
    (self.posting_participants + (self.parent ? self.parent.posting_participants : [])).uniq
  end

  def all_participants
    (self.participants + (self.parent ? self.parent.participants : [])).uniq
  end

  def all_items
    CollectionItem.where(collection_id: ([self.id] + self.children.pluck(:id)))
  end

  def maintainers
    self.all_owners + self.all_moderators
  end

  def user_is_owner?(user)
    user && user != :false && !(user.pseuds & self.all_owners).empty?
  end

  def user_is_moderator?(user)
    user && user != :false && !(user.pseuds & self.all_moderators).empty?
  end

  def user_is_maintainer?(user)
    user && user != :false && !(user.pseuds & (self.all_moderators + self.all_owners)).empty?
  end

  def user_is_participant?(user)
    user && user != :false && !get_participating_pseuds_for_user(user).empty?
  end

  def user_is_posting_participant?(user)
    user && user != :false && !(user.pseuds & self.all_posting_participants).empty?
  end

  def get_participating_pseuds_for_user(user)
    (user && user != :false) ? user.pseuds & self.all_participants : []
  end

  def get_participants_for_user(user)
    return [] unless user
    CollectionParticipant.in_collection(self).for_user(user)
  end

  def assignment_notification
    self.collection_profile.assignment_notification || (parent ? parent.collection_profile.assignment_notification : "")
  end

  def gift_notification
    self.collection_profile.gift_notification || (parent ? parent.collection_profile.gift_notification : "")
  end

  def moderated? ; self.collection_preference.moderated ; end
  def closed? ; self.collection_preference.closed ; end
  def unrevealed? ; self.collection_preference.unrevealed ; end
  def anonymous? ; self.collection_preference.anonymous ; end
  def challenge? ; !self.challenge.nil? ; end

  def gift_exchange?
    return self.challenge_type == "GiftExchange"
  end
  def prompt_meme?
    return self.challenge_type == "PromptMeme"
  end

  def get_maintainers_email
    return self.email if !self.email.blank?
    return parent.email if parent && !parent.email.blank?
    "#{self.maintainers.collect(&:user).flatten.uniq.collect(&:email).join(',')}"
  end

  def notify_maintainers(subject, message)
    # send maintainers a notice via email
    UserMailer.collection_notification(self.id, subject, message).deliver_later
  end

  include AsyncWithResque
  @queue = :collection

  def reveal!
    async(:reveal_collection_items)
  end

  def reveal_authors!
    async(:reveal_collection_item_authors)
  end

  def reveal_collection_items
    approved_collection_items.each { |collection_item| collection_item.update_attribute(:unrevealed, false) }
    send_reveal_notifications
  end

  def reveal_collection_item_authors
    approved_collection_items.each { |collection_item| collection_item.update_attribute(:anonymous, false) }
  end

  def send_reveal_notifications
    approved_collection_items.each {|collection_item| collection_item.notify_of_reveal}
  end

  def self.sorted_and_filtered(sort, filters, page)
    pagination_args = {page: page}

    # build up the query with scopes based on the options the user specifies
    query = Collection.top_level

    if !filters[:title].blank?
      # we get the matching collections out of autocomplete and use their ids
      ids = Collection.autocomplete_lookup(search_param: filters[:title],
                autocomplete_prefix: (filters[:closed].blank? ? "autocomplete_collection_all" : (filters[:closed] ? "autocomplete_collection_closed" : "autocomplete_collection_open"))
             ).map {|result| Collection.id_from_autocomplete(result)}
      query = query.where("collections.id in (?)", ids)
    else
      query = (filters[:closed] == "true" ? query.closed : query.not_closed) if !filters[:closed].blank?
    end
    query = (filters[:moderated] == "true" ? query.moderated : query.unmoderated) if !filters[:moderated].blank?
    if filters[:challenge_type].present?
      if filters[:challenge_type] == "gift_exchange"
        query = query.gift_exchange
      elsif filters[:challenge_type] == "prompt_meme"
        query = query.prompt_meme
      elsif filters[:challenge_type] == "no_challenge"
        query = query.no_challenge
      end
    end
    query = query.order(sort)

    if !filters[:fandom].blank?
      fandom = Fandom.find_by_name(filters[:fandom])
      if fandom
        (fandom.approved_collections & query).paginate(pagination_args)
      else
        []
      end
    else
      query.paginate(pagination_args)
    end
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
end
