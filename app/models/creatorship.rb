class Creatorship < ApplicationRecord
  belongs_to :pseud, inverse_of: :creatorships
  belongs_to :creation, inverse_of: :creatorships, polymorphic: true, touch: true

  scope :approved, -> { where(approved: true) }
  scope :unapproved, -> { where(approved: false) }

  scope :for_user, ->(user) { joins(:pseud).merge(user.pseuds) }

  ########################################
  # VALIDATIONS
  ########################################

  before_validation :update_approved, on: :create

  validates_presence_of :creation
  validates_uniqueness_of :pseud, scope: [:creation_type, :creation_id], on: :create

  validate :check_invalid, on: :create
  validate :check_disallowed, on: :create
  validate :check_banned, on: :create
  validate :check_approved_becoming_false, on: :update

  # Update approval status if this creatorship should be automatically approved.
  def update_approved
    if !approved? && should_automatically_approve?
      self.approved = true
    end
  end

  # Make sure that the pseud exists, and isn't ambiguous.
  #
  # Note that thanks to the definitions of missing? and ambiguous?, this is
  # equivalent to having a validates_presence_of :pseud check, just with
  # different error messages.
  def check_invalid
    if missing?
      errors.add(:base, ts("Could not find a pseud %{name}.", name: byline))
    elsif ambiguous?
      errors.add(:base, ts("The pseud %{name} is ambiguous.", name: byline))
    end
  end

  # Make sure that if this is an invitation, we're not inviting someone who has
  # disabled invitations.
  def check_disallowed
    return if approved? || pseud.nil?
    return if pseud&.user&.preference&.allow_cocreator

    errors.add(:base, ts("%{name} does not allow others to invite them to be a co-creator.",
                         name: pseud.byline))
  end

  # Make sure that the user isn't banned or suspended.
  def check_banned
    return unless pseud&.user&.banned || pseud&.user&.suspended

    errors.add(:base, ts("%{name} is currently banned and cannot be listed as a co-creator.",
                         name: pseud.byline))
  end

  # Make sure that we're not trying to set approved to false, since that could
  # potentially violate some rules about co-creators. (e.g. Having a user
  # listed as a chapter co-creator, but not a work co-creator.)
  def check_approved_becoming_false
    if !approved? && approved_changed?
      errors.add(:base, "Once approved, a creatorship cannot become unapproved.")
    end
  end

  ########################################
  # CALLBACKS
  ########################################

  after_create :add_to_parents
  after_update :add_to_parents, if: :saved_change_to_approved?

  before_destroy :expire_caches
  before_destroy :check_not_last
  before_destroy :save_original_creator
  after_destroy :remove_from_children

  after_commit :update_indices

  after_create_commit :notify_creator, if: :enable_notifications

  # If a pseud is listed as a work co-creator (not invited, actually listed),
  # they should also be listed on all of the work's series. Similarly, if a
  # pseud is listed as a chapter co-creator, they should also be listed on the
  # work.
  def add_to_parents
    return unless approved?

    parents = if creation.is_a?(Work)
                creation.series.to_a
              elsif creation.is_a?(Chapter)
                [creation.work]
              else
                []
              end

    parents.each do |parent|
      parent.creatorships.approve_or_create_by(pseud: pseud)
    end
  end

  # In order to make sure that all chapter co-creators are listed on the work,
  # and all work co-creators are listed on the work's series, we need to make
  # sure that when a creatorship is deleted, the deletion cascades downwards.
  def remove_from_children
    # If the creation is being deleted and it's a work, then its chapters are
    # also going to be deleted (which will cause their creatorships to be
    # deleted as well). If the creation is being deleted and it's a series,
    # then we shouldn't delete the work creatorships. So if the creation is
    # being deleted, we don't want to cascade the deletion downwards.
    return if creation.nil? || creation.destroyed?

    children = if creation.is_a?(Work)
                 creation.chapters.to_a
               elsif creation.is_a?(Series)
                 creation.works.to_a
               else
                 []
               end

    children.each do |child|
      child.creatorships.where(pseud: pseud).destroy_all
    end
  end

  # Make sure that both the creation and the pseud are enqueued to be
  # reindexed.
  def update_indices
    if creation.is_a?(Searchable)
      creation.enqueue_to_index
    end

    if pseud && creation.is_a?(Work)
      IndexQueue.enqueue(pseud, :background)
    end
  end

  # Only enable notifications for new creatorships when explicitly enabled.
  attr_accessor :enable_notifications

  # Notify the pseud of their new creatorship.
  def notify_creator
    return unless User.current_user.is_a?(User) &&
                  pseud.user != User.current_user &&
                  pseud.user != User.orphan_account

    I18n.with_locale(Locale.find(pseud.user.preference.preferred_locale).iso) do
      if approved?
        if User.current_user.try(:is_archivist?)
          UserMailer.creatorship_notification_archivist(id, User.current_user.id).deliver_later
        else
          UserMailer.creatorship_notification(id, User.current_user.id).deliver_later
        end
      else
        UserMailer.creatorship_request(id, User.current_user.id).deliver_later
      end
    end
  end

  # When deleting a creatorship, we want to make sure we're not deleting the
  # very last creatorship for that item.
  def check_not_last
    # We can always delete unapproved creatorships:
    return unless approved?

    # Check that the creation hasn't been deleted, and still has creatorships
    # left:
    return if creation.nil? || creation.destroyed? ||
              creation.creatorships.approved.count > 1

    errors.add(:base, ts("Sorry, we can't remove all creators of a %{type}.",
                         type: creation.model_name.human.downcase))
    raise ActiveRecord::RecordInvalid, self
  end

  # Record the original creator if the creation is a work.
  # This information is stored temporarily to make it available for
  # Policy and Abuse on orphaned works.
  def save_original_creator
    return unless approved?
    return unless creation.is_a?(Work)
    return if creation.destroyed?

    creation.original_creators.create_or_find_by(user: pseud.user).touch
  end

  def expire_caches
    if creation_type == "Work" && self.pseud.present?
      CacheMaster.record(creation_id, "pseud", self.pseud_id)
      CacheMaster.record(creation_id, "user", self.pseud.user_id)
    end
  end

  ########################################
  # OTHER METHODS
  ########################################

  attr_reader :ambiguous_pseuds

  # We define a virtual "byline" attribute to make it easier to handle
  # ambiguous/missing pseuds. By storing the desired name in the @byline
  # variable, we can generate nicely formatted messages.
  def byline=(byline)
    pseuds = Pseud.parse_byline(byline).to_a

    if pseuds.size == 1
      self.pseud = pseuds.first
      @byline = nil
      @ambiguous_pseuds = nil
    else
      self.pseud = nil
      @byline = byline
      @ambiguous_pseuds = pseuds
    end
  end

  # Retrieve the @byline variable, or, failing that, the pseud's byline.
  def byline
    @byline || pseud&.byline
  end

  # A creatorship counts as "missing" if we couldn't find any pseuds matching
  # the passed-in byline.
  def missing?
    pseud.nil? && @ambiguous_pseuds.blank?
  end

  # A creatorship counts as "ambiguous" if there was more than one pseud
  # matching the passed-in byline.
  def ambiguous?
    pseud.nil? && @ambiguous_pseuds.present?
  end

  # Find or initialize a creatorship matching the options, and then set
  # approved to true and save the results. This is a way of adding a new
  # approved creatorship without potentially running into issues with a
  # pre-existing unapproved creatorship.
  def self.approve_or_create_by(options)
    creatorship = find_or_initialize_by(options)
    creatorship.approved = true
    creatorship.save if creatorship.changed?
  end

  # Change authorship of works or series from a particular pseud to the orphan account
  def self.orphan(pseuds, orphans, default=true)
    for pseud in pseuds
      for new_orphan in orphans
        unless pseud.blank? || new_orphan.blank? || !new_orphan.pseuds.include?(pseud)
          orphan_pseud = default ? User.orphan_account.default_pseud : User.orphan_account.pseuds.find_or_create_by(name: pseud.name)
          pseud.change_ownership(new_orphan, orphan_pseud)
        end
      end
    end
  end

  # Calculate whether this creatorship should count as approved, or whether
  # it's just a creatorship invitation.
  def should_automatically_approve?
    # Approve if we're using an API key, or if the current user has special
    # permissions:
    return true if User.current_user.nil? ||
                   pseud&.user == User.current_user ||
                   pseud&.user == User.orphan_account ||
                   User.current_user.try(:is_archivist?)

    # Approve if the creation is a chapter and the pseud is already listed on
    # the work, or if the creation is a series and the pseud is already listed
    # on one of the works:
    (creation.is_a?(Chapter) && creation.work.pseuds.include?(pseud)) ||
      (creation.is_a?(Series) && creation.work_pseuds.include?(pseud))
  end

  # Accept the creatorship invitation. This consists of setting approved to
  # true, and, if the creation is a work, adding the pseud to all of its
  # chapters as well.
  def accept!
    transaction do
      update(approved: true)

      if creation.is_a?(Work)
        creation.chapters.each do |chapter|
          chapter.creatorships.approve_or_create_by(pseud: pseud)
        end
      end
    end
  end
end
