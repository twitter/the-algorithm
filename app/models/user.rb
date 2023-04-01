class User < ApplicationRecord
  audited
  include WorksOwner

  devise :database_authenticatable,
         :confirmable,
         :registerable,
         :rememberable,
         :trackable,
         :validatable,
         :lockable,
         :recoverable

  # Must come after Devise modules in order to alias devise_valid_password?
  # properly
  include BackwardsCompatiblePasswordDecryptor

  # Allows other models to get the current user with User.current_user
  cattr_accessor :current_user

  # Authorization plugin
  acts_as_authorized_user
  acts_as_authorizable
  has_many :roles_users
  has_many :roles, through: :roles_users, dependent: :destroy

  ### BETA INVITATIONS ###
  has_many :invitations, as: :creator
  has_one :invitation, as: :invitee
  has_many :user_invite_requests, dependent: :destroy

  attr_accessor :invitation_token
  # attr_accessible :invitation_token
  after_create :mark_invitation_redeemed, :remove_from_queue

  has_many :external_authors, dependent: :destroy
  has_many :external_creatorships, foreign_key: "archivist_id"

  has_many :fannish_next_of_kins, dependent: :delete_all, inverse_of: :kin, foreign_key: :kin_id
  has_one :fannish_next_of_kin, dependent: :destroy

  has_many :favorite_tags, dependent: :destroy

  has_one :default_pseud, -> { where(is_default: true) }, class_name: "Pseud", inverse_of: :user
  delegate :id, to: :default_pseud, prefix: true

  has_many :pseuds, dependent: :destroy
  validates_associated :pseuds

  has_one :profile, dependent: :destroy
  validates_associated :profile

  has_one :preference, dependent: :destroy
  validates_associated :preference

  has_many :blocks_as_blocked, class_name: "Block", dependent: :delete_all, inverse_of: :blocked, foreign_key: :blocked_id
  has_many :blocks_as_blocker, class_name: "Block", dependent: :delete_all, inverse_of: :blocker, foreign_key: :blocker_id
  has_many :blocked_users, through: :blocks_as_blocker, source: :blocked

  # The block (if it exists) with this user as the blocker and
  # User.current_user as the blocked:
  has_one :block_of_current_user,
          -> { where(blocked: User.current_user) },
          class_name: "Block", foreign_key: :blocker_id, inverse_of: :blocker

  # The block (if it exists) with User.current_user as the blocker and this
  # user as the blocked:
  has_one :block_by_current_user,
          -> { where(blocker: User.current_user) },
          class_name: "Block", foreign_key: :blocked_id, inverse_of: :blocked

  has_many :mutes_as_muted, class_name: "Mute", dependent: :delete_all, inverse_of: :muted, foreign_key: :muted_id
  has_many :mutes_as_muter, class_name: "Mute", dependent: :delete_all, inverse_of: :muter, foreign_key: :muter_id
  has_many :muted_users, through: :mutes_as_muter, source: :muted

  # The mute (if it exists) with User.current_user as the muter and this
  # user as the muted:
  has_one :mute_by_current_user,
          -> { where(muter: User.current_user) },
          class_name: "Mute", foreign_key: :muted_id, inverse_of: :muted

  has_many :skins, foreign_key: "author_id", dependent: :nullify
  has_many :work_skins, foreign_key: "author_id", dependent: :nullify

  before_create :create_default_associateds
  before_destroy :remove_user_from_kudos

  before_update :add_renamed_at, if: :will_save_change_to_login?
  after_update :update_pseud_name
  after_update :log_change_if_login_was_edited
  after_update :log_email_change, if: :saved_change_to_email?

  after_commit :reindex_user_creations_after_rename

  has_many :collection_participants, through: :pseuds
  has_many :collections, through: :collection_participants
  has_many :invited_collections, -> { where("collection_participants.participant_role = ?", CollectionParticipant::INVITED) }, through: :collection_participants, source: :collection
  has_many :participated_collections, -> { where("collection_participants.participant_role IN (?)", [CollectionParticipant::OWNER, CollectionParticipant::MODERATOR, CollectionParticipant::MEMBER]) }, through: :collection_participants, source: :collection
  has_many :maintained_collections, -> { where("collection_participants.participant_role IN (?)", [CollectionParticipant::OWNER, CollectionParticipant::MODERATOR]) }, through: :collection_participants, source: :collection
  has_many :owned_collections, -> { where("collection_participants.participant_role = ?", CollectionParticipant::OWNER) }, through: :collection_participants, source: :collection

  has_many :challenge_signups, through: :pseuds
  has_many :offer_assignments, through: :pseuds
  has_many :pinch_hit_assignments, through: :pseuds
  has_many :request_claims, class_name: "ChallengeClaim", foreign_key: "claiming_user_id", inverse_of: :claiming_user
  has_many :gifts, -> { where(rejected: false) }, through: :pseuds
  has_many :gift_works, -> { distinct }, through: :pseuds
  has_many :rejected_gifts, -> { where(rejected: true) }, class_name: "Gift", through: :pseuds
  has_many :rejected_gift_works, -> { distinct }, through: :pseuds
  has_many :readings, dependent: :delete_all
  has_many :bookmarks, through: :pseuds
  has_many :bookmark_collection_items, through: :bookmarks, source: :collection_items
  has_many :comments, through: :pseuds
  has_many :kudos

  # Nested associations through creatorships got weird after 3.0.x
  has_many :creatorships, through: :pseuds

  has_many :works, -> { distinct }, through: :pseuds
  has_many :series, -> { distinct }, through: :pseuds
  has_many :chapters, through: :pseuds

  has_many :related_works, through: :works
  has_many :parent_work_relationships, through: :works

  has_many :tags, through: :works
  has_many :filters, through: :works
  has_many :direct_filters, through: :works

  has_many :bookmark_tags, through: :bookmarks, source: :tags

  has_many :subscriptions, dependent: :destroy
  has_many :followings,
           class_name: "Subscription",
           as: :subscribable,
           dependent: :destroy
  has_many :subscribed_users,
           through: :subscriptions,
           source: :subscribable,
           source_type: "User"
  has_many :subscribers,
           through: :followings,
           source: :user

  thread_cattr_accessor :should_update_wrangling_activity
  has_many :wrangling_assignments, dependent: :destroy
  has_many :fandoms, through: :wrangling_assignments
  has_many :wrangled_tags, class_name: "Tag", as: :last_wrangler
  has_one :last_wrangling_activity, dependent: :destroy

  has_many :inbox_comments, dependent: :destroy
  has_many :feedback_comments, -> { where(is_deleted: false, approved: true).order(created_at: :desc) }, through: :inbox_comments

  has_many :log_items, dependent: :destroy
  validates_associated :log_items

  after_update :expire_caches

  def expire_caches
    return unless saved_change_to_login?
    self.works.each do |work|
      work.touch
      work.expire_caches
    end
  end

  def remove_user_from_kudos
    # TODO: AO3-5054 Expire kudos cache when deleting a user.
    # TODO: AO3-2195 Display orphaned kudos (no users; no IPs so not counted as guest kudos).
    Kudo.where(user: self).update_all(user_id: nil)
  end

  def read_inbox_comments
    inbox_comments.where(read: true)
  end
  def unread_inbox_comments
    inbox_comments.where(read: false)
  end
  def unread_inbox_comments_count
    unread_inbox_comments.with_bad_comments_removed.count
  end

  scope :alphabetical, -> { order(:login) }
  scope :starting_with, -> (letter) { where('login like ?', "#{letter}%") }
  scope :valid, -> { where(banned: false, suspended: false) }
  scope :out_of_invites, -> { where(out_of_invites: true) }

  ## used in app/views/users/new.html.erb
  validates_length_of :login,
                      within: ArchiveConfig.LOGIN_LENGTH_MIN..ArchiveConfig.LOGIN_LENGTH_MAX,
                      too_short: ts("^User name is too short (minimum is %{min_login} characters)",
                                    min_login: ArchiveConfig.LOGIN_LENGTH_MIN),
                      too_long: ts("^User name is too long (maximum is %{max_login} characters)",
                                   max_login: ArchiveConfig.LOGIN_LENGTH_MAX)

  # allow nil so can save existing users
  validates_length_of :password,
                      within: ArchiveConfig.PASSWORD_LENGTH_MIN..ArchiveConfig.PASSWORD_LENGTH_MAX,
                      allow_nil: true,
                      too_short: ts("is too short (minimum is %{min_pwd} characters)",
                                    min_pwd: ArchiveConfig.PASSWORD_LENGTH_MIN),
                      too_long: ts("is too long (maximum is %{max_pwd} characters)",
                                   max_pwd: ArchiveConfig.PASSWORD_LENGTH_MAX)

  validates_format_of :login,
                      message: ts("^User name must be %{min_login} to %{max_login} characters (A-Z, a-z, _, 0-9 only), no spaces, cannot begin or end with underscore (_).",
                                  min_login: ArchiveConfig.LOGIN_LENGTH_MIN,
                                  max_login: ArchiveConfig.LOGIN_LENGTH_MAX),
                      with: /\A[A-Za-z0-9]\w*[A-Za-z0-9]\Z/
  validates :login, uniqueness: { message: ts("^User name has already been taken") }
  validate :login, :username_is_not_recently_changed, if: :will_save_change_to_login?

  validates :email, email_veracity: true, email_format: true, uniqueness: true

  # Virtual attribute for age check and terms of service
    attr_accessor :age_over_13
    attr_accessor :terms_of_service
    # attr_accessible :age_over_13, :terms_of_service

  validates_acceptance_of :terms_of_service,
                          allow_nil: false,
                          message: ts("Sorry, you need to accept the Terms of Service in order to sign up."),
                          if: :first_save?

  validates_acceptance_of :age_over_13,
                          allow_nil: false,
                          message: ts("Sorry, you have to be over 13!"),
                          if: :first_save?

  def to_param
    login
  end

  # Override of Devise method to allow user to login with login OR username as
  # well as to make login case insensitive without losing user-preferred case
  # for login display
  def self.find_first_by_auth_conditions(tainted_conditions, options = {})
    conditions = devise_parameter_filter.filter(tainted_conditions).merge(options)
    login = conditions.delete(:login)
    relation = self.where(conditions)

    if login.present?
      # MySQL is case-insensitive with utf8mb4_unicode_ci so we don't have to use
      # lowercase values
      relation = relation.where(["login = :value OR email = :value",
                                 value: login])
    end

    relation.first
  end

  def self.for_claims(claims_ids)
    joins(:request_claims).
    where("challenge_claims.id IN (?)", claims_ids)
  end

  # Find users with a particular role and/or by name, email, and/or id
  # Options: inactive, page, exact
  def self.search_by_role(role, name, email, user_id, options = {})
    return if role.blank? && name.blank? && email.blank? && user_id.blank?

    users = User.distinct.order(:login)
    if options[:inactive]
      users = users.where("confirmed_at IS NULL")
    end
    if role.present?
      users = users.joins(:roles).where("roles.id = ?", role.id)
    end
    if name.present?
      users = users.filter_by_name(name, options[:exact])
    end
    if email.present?
      users = users.filter_by_email(email, options[:exact])
    end
    if user_id.present?
      users = users.where(["users.id = ?", user_id])
    end
    users.paginate(page: options[:page] || 1)
  end

  # Scope to look for users by pseud name:
  def self.filter_by_name(name, exact)
    if exact
      joins(:pseuds).where(["pseuds.name = ?", name])
    else
      joins(:pseuds).where(["pseuds.name LIKE ?", "%#{name}%"])
    end
  end

  # Scope to look for users by email:
  def self.filter_by_email(email, exact)
    if exact
      where(["email = ?", email])
    else
      where(["email LIKE ?", "%#{email}%"])
    end
  end

  def self.search_multiple_by_email(emails = [])
    # Normalise and dedupe emails
    all_emails = emails.map(&:downcase)
    unique_emails = all_emails.uniq
    # Find users and their email addresses
    users = User.where(email: unique_emails)
    found_emails = users.map(&:email).map(&:downcase)
    # Remove found users from the total list of unique emails and count duplicates
    not_found_emails = unique_emails - found_emails
    num_duplicates = emails.size - unique_emails.size

    [users, not_found_emails, num_duplicates]
  end

  ### AUTHENTICATION AND PASSWORDS
  def active?
    !confirmed_at.nil?
  end

  def activate
    return false if self.active?
    self.update_attribute(:confirmed_at, Time.now.utc)
  end

  def create_default_associateds
    self.pseuds << Pseud.new(name: self.login, is_default: true)
    self.profile = Profile.new
    self.preference = Preference.new(preferred_locale: Locale.default.id)
  end

  def prevent_password_resets?
    is_protected_user? || no_resets?
  end

  protected
    def first_save?
      self.new_record?
    end

  public

  # Returns an array (of pseuds) of this user's co-authors
  def coauthors
     works.collect(&:pseuds).flatten.uniq - pseuds
  end

  # Gets the user's most recent unposted work
  def unposted_work
    return @unposted_work if @unposted_work
    @unposted_work = unposted_works.first
  end

  def unposted_works
    return @unposted_works if @unposted_works
    @unposted_works = works.where(posted: false).order("works.created_at DESC")
  end

  # removes ALL unposted works
  def wipeout_unposted_works
    works.where(posted: false).destroy_all
  end

  # Removes all of the user's series that don't have any listed works.
  def destroy_empty_series
    series.left_joins(:serial_works).where(serial_works: { id: nil }).
      destroy_all
  end

  # Checks authorship of any sort of object
  def is_author_of?(item)
    if item.respond_to?(:pseud_id)
      pseud_ids.include?(item.pseud_id)
    elsif item.respond_to?(:user_id)
      id == item.user_id
    elsif item.respond_to?(:pseuds)
      item.pseuds.pluck(:user_id).include?(id)
    elsif item.respond_to?(:author)
      self == item.author
    elsif item.respond_to?(:creator_id)
      self.id == item.creator_id
    else
      false
    end
  end

  # Gets the number of works by this user that the current user can see
  def visible_work_count
    Work.owned_by(self).visible_to_user(User.current_user).revealed.non_anon.distinct.count(:id)
  end

  # Gets the user account for authored objects if orphaning is enabled
  def self.orphan_account
    User.fetch_orphan_account if ArchiveConfig.ORPHANING_ALLOWED
  end

  # Is this user an authorized tag wrangler?
  def tag_wrangler
    self.is_tag_wrangler?
  end

  def is_tag_wrangler?
    has_role?(:tag_wrangler)
  end

  # Is this user an authorized archivist?
  def archivist
    self.is_archivist?
  end

  def is_archivist?
    has_role?(:archivist)
  end

  # Is this user an authorized official?
  def official
    has_role?(:official)
  end

  # Is this user a protected user? These are users experiencing certain types
  # of harassment.
  def protected_user
    self.is_protected_user?
  end

  def is_protected_user?
    has_role?(:protected_user)
  end

  # Is this user assigned the no resets role? These users do not wish to receive
  # password resets.
  def no_resets?
    has_role?(:no_resets)
  end

  # Creates log item tracking changes to user
  def create_log_item(options = {})
    options.reverse_merge! note: "System Generated", user_id: self.id
    LogItem.create(options)
  end

  def update_last_wrangling_activity
    return unless is_tag_wrangler?

    last_activity = LastWranglingActivity.find_or_create_by(user: self)
    last_activity.touch
  end

  # Returns true if user is the sole author of a work
  # Should also be true if the user has used more than one of their pseuds on a work
  def is_sole_author_of?(item)
   other_pseuds = item.pseuds - pseuds
   self.is_author_of?(item) && other_pseuds.blank?
 end

  # Returns array of works where the user is the sole author
  def sole_authored_works
    @sole_authored_works = []
    works.where(posted: 1).each do |w|
      if self.is_sole_author_of?(w)
        @sole_authored_works << w
      end
    end
    return @sole_authored_works
  end

  # Returns array of the user's co-authored works
  def coauthored_works
    @coauthored_works = []
    works.where(posted: 1).each do |w|
      unless self.is_sole_author_of?(w)
        @coauthored_works << w
      end
    end
    return @coauthored_works
  end

  ### BETA INVITATIONS ###

  #If a new user has an invitation_token (meaning they were invited), the method sets the redeemed_at column for that invitation to Time.now
  def mark_invitation_redeemed
    unless self.invitation_token.blank?
      invitation = Invitation.find_by(token: self.invitation_token)
      if invitation
        self.update_attribute(:invitation_id, invitation.id)
        invitation.mark_as_redeemed(self)
      end
    end
  end

  # Existing users should be removed from the invitations queue
  def remove_from_queue
    invite_request = InviteRequest.find_by(email: self.email)
    invite_request.destroy if invite_request
  end

  def fix_user_subscriptions
    # Delete any subscriptions the user has to deleted items because this causes
    # the user's subscription page to error
    @subscriptions = subscriptions.includes(:subscribable)
    @subscriptions.to_a.each do |sub|
      if sub.name.nil?
        sub.destroy
      end
    end
  end

  def set_user_work_dates
    # Fix user stats page error caused by the existence of works with nil revised_at dates
    works.each do |work|
      if work.revised_at.nil?
        work.save
      end
      IndexQueue.enqueue(work, :main)
    end
  end

  def reindex_user_creations
    IndexQueue.enqueue_ids(Work, works.pluck(:id), :main)
    IndexQueue.enqueue_ids(Bookmark, bookmarks.pluck(:id), :main)
    IndexQueue.enqueue_ids(Series, series.pluck(:id), :main)
    IndexQueue.enqueue_ids(Pseud, pseuds.pluck(:id), :main)
  end

  private

  # Create and/or return a user account for holding orphaned works
  def self.fetch_orphan_account
    orphan_account = User.find_or_create_by(login: "orphan_account")
    if orphan_account.new_record?
      Rails.logger.fatal "You must have a User with the login 'orphan_account'. Please create one."
    end
    orphan_account
  end

  def update_pseud_name
    return unless saved_change_to_login? && login_before_last_save.present?
    old_pseud = pseuds.where(name: login_before_last_save).first
    if login.downcase == login_before_last_save.downcase
      old_pseud.name = login
      old_pseud.save!
    else
      new_pseud = pseuds.where(name: login).first
      # do nothing if they already have the matching pseud
      if new_pseud.blank?
        if old_pseud.present?
          # change the old pseud to match
          old_pseud.name = login
          old_pseud.save!(validate: false)
        else
          # shouldn't be able to get here, but just in case
          Pseud.create!(name: login, user_id: id)
        end
      end
    end
  end

  def reindex_user_creations_after_rename
    return unless saved_change_to_login? && login_before_last_save.present?
    # Everything is indexed with the user's byline,
    # which has the old username, so they all need to be reindexed.
    reindex_user_creations
  end

  def add_renamed_at
    self.renamed_at = Time.current
  end

  def log_change_if_login_was_edited
    create_log_item(action: ArchiveConfig.ACTION_RENAME, note: "Old Username: #{login_before_last_save}; New Username: #{login}") if saved_change_to_login?
  end

  def log_email_change
    current_admin = User.current_user if User.current_user.is_a?(Admin)
    options = {
      action: ArchiveConfig.ACTION_NEW_EMAIL,
      admin_id: current_admin&.id
    }
    options[:note] = "Change made by #{current_admin&.login}" if current_admin
    create_log_item(options)
  end

  def remove_stale_from_autocomplete
    Rails.logger.debug "Removing stale from autocomplete: #{autocomplete_search_string_was}"
    self.class.remove_from_autocomplete(self.autocomplete_search_string_was, self.autocomplete_prefixes, self.autocomplete_value_was)
  end

  def username_is_not_recently_changed
    change_interval_days = ArchiveConfig.USER_RENAME_LIMIT_DAYS
    return unless renamed_at && change_interval_days.days.ago <= renamed_at

    errors.add(:login,
               :changed_too_recently,
               count: change_interval_days,
               renamed_at: I18n.l(renamed_at, format: :long))
  end

  # Extra callback to make sure readings are deleted in an order consistent
  # with the ReadingsJob.
  #
  # TODO: In the long term, it might be better to change the indexes on the
  # readings table so that it deletes things in the correct order by default if
  # we just set dependent: :delete_all, but for now we need to explicitly sort
  # by work_id to make sure that the readings are locked in the correct order.
  before_destroy :clear_readings, prepend: true
  def clear_readings
    readings.order(:work_id).each(&:delete)
  end
end
