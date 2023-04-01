# frozen_string_literal: true

# A module used for classes that can appear as the "creation" in a Creatorship
# (i.e. Work, Series, and Chapter).
module Creatable
  extend ActiveSupport::Concern

  included do
    has_many :creatorships,
             autosave: true,
             as: :creation,
             inverse_of: :creation

    has_many :approved_creatorships,
             -> { Creatorship.approved },
             class_name: "Creatorship",
             as: :creation,
             inverse_of: :creation

    has_many :pseuds,
             through: :approved_creatorships,
             before_add: :disallow_pseud_changes,
             before_remove: :disallow_pseud_changes

    has_many :users,
             -> { distinct },
             through: :pseuds

    attr_reader :current_user_pseuds

    validate :check_no_creators
    validate :check_current_user_pseuds
    after_save :update_current_user_pseuds
    after_destroy :destroy_creatorships
  end

  ########################################
  # CALLBACKS & VALIDATIONS
  ########################################

  # Updating pseuds directly goes through the approved_creatorships relation,
  # so it will automatically approve any pseuds added in this way. So we want
  # to make sure that this is a read-only relation.
  def disallow_pseud_changes(*)
    raise "Cannot add or remove pseuds through the pseuds association!"
  end

  # Make sure that there will be at least one approved creator after saving:
  def check_no_creators
    return if @current_user_pseuds.present? || pseuds_after_saving.any?

    errors.add(:base, ts("%{type} must have at least one creator.",
                         type: model_name.human))
  end

  # Make sure that if @current_user_pseuds is not nil, then the user has
  # selected at least one pseud, and that all of the pseuds they've selected
  # are their own.
  def check_current_user_pseuds
    return unless @current_user_pseuds && User.current_user.is_a?(User)

    if @current_user_pseuds.empty?
      errors.add(:base, ts("You haven't selected any pseuds for this %{type}.",
                           type: model_name.human.downcase))
    end

    if @current_user_pseuds.any? { |p| p.user_id != User.current_user.id }
      errors.add(:base, ts("You're not allowed to use that pseud."))
    end
  end

  # The variable @current_user_pseuds stores which pseuds the current editor
  # wants to use on this work. The pseuds should contain only pseuds owned by
  # User.current_user.
  def update_current_user_pseuds
    return unless @current_user_pseuds

    set_current_user_pseuds(@current_user_pseuds)
    @current_user_pseuds = nil
  end

  # Clean up all creatorships associated with this item.
  def destroy_creatorships
    creatorships.destroy_all
  end

  ########################################
  # VIRTUAL ATTRIBUTES
  ########################################

  # Update all creator-related attributes.
  def author_attributes=(attributes)
    self.new_bylines = attributes[:byline] if attributes[:byline].present?
    self.new_co_creator_ids = attributes[:coauthors] if attributes[:coauthors].present?
    self.current_user_pseud_ids = attributes[:ids] if attributes[:ids].present?
  end

  # Invite new co-creators by passing in their byline.
  def new_bylines=(bylines)
    bylines.split(",").reject(&:blank?).map(&:strip).each do |byline|
      self.creatorships.build(byline: byline, enable_notifications: true)
    end
  end

  # Invite new co-creators by ID.
  def new_co_creator_ids=(ids)
    new_pseuds = Pseud.where(id: ids).to_a

    creatorships.each do |creatorship|
      if new_pseuds.include?(creatorship.pseud)
        new_pseuds.delete(creatorship.pseud)
      end
    end

    new_pseuds.each do |pseud|
      self.creatorships.build(pseud: pseud, enable_notifications: true)
    end
  end

  # Update which of User.current_user's pseuds should be listed on the byline
  # after saving.
  def current_user_pseud_ids=(ids)
    return unless User.current_user.is_a?(User)

    @current_user_pseuds = Pseud.where(id: ids).to_a
  end

  # This behaves very similarly to new_bylines=, but because it's designed to
  # be used for bulk editing works, it doesn't handle ambiguous pseuds well. So
  # we need to manually refine our guess as much as possible.
  def pseuds_to_add=(pseud_names)
    names = pseud_names.split(",").reject(&:blank?).map(&:strip)

    names.each do |name|
      possible_pseuds = Pseud.parse_byline(name)

      if possible_pseuds.size > 1
        possible_pseuds = Pseud.parse_byline(name, assume_matching_login: true)
      end

      pseud = possible_pseuds.first

      if pseud
        creatorship = creatorships.find_or_initialize_by(pseud: pseud)
        creatorship.enable_notifications = true
      end
    end
  end

  ########################################
  # USEFUL FUNCTIONS
  ########################################

  # Update the pseuds on this item so that User.current_user's pseuds are
  # replaced by the passed-in array of pseuds new_pseuds. If it's a Series, we
  # also update the user's byline on any owned works in the series. If it's a
  # Work, we also update the user's byline on any owned chapters in the series.
  def set_current_user_pseuds(new_pseuds)
    return unless User.current_user.is_a?(User)

    user_id = User.current_user.id

    children = if is_a?(Work)
                 chapters.to_a
               elsif is_a?(Series)
                 works.to_a
               else
                 []
               end

    transaction do
      children.each do |child|
        next unless child.users.include?(User.current_user)
        child.set_current_user_pseuds(new_pseuds)
      end

      # Create before destroying, so that we don't run into issues with
      # deleting the very last creator.
      new_pseuds.each do |pseud|
        creatorships.approve_or_create_by(pseud: pseud)
      end

      creatorships.each do |creatorship|
        creatorship.destroy unless new_pseuds.include?(creatorship.pseud) ||
                                   creatorship.pseud&.user_id != user_id
      end
    end
  end

  # Figure out which creatorships will exist after saving.
  #
  # Excludes creatorships with a missing pseud, because those orphaned
  # creatorships can break various bits of code if they're considered valid.
  def creatorships_after_saving
    creatorships.select(&:valid?).reject(&:marked_for_destruction?).
      reject { |creatorship| creatorship.pseud.nil? }
  end

  # Calculate what the pseuds on this work will be after saving, taking into
  # account validity, approval, and @current_user_pseuds.
  def pseuds_after_saving
    pseuds = creatorships_after_saving.select(&:approved?).map(&:pseud)

    if @current_user_pseuds
      pseuds = (pseuds - User.current_user.pseuds) + @current_user_pseuds
    end

    pseuds.uniq
  end

  # Check whether the passed-in user has been invited to become a creator.
  def user_has_creator_invite?(user)
    return false unless user.is_a?(User)
    creatorships.unapproved.for_user(user).exists?
  end

  # Check whether the given user has some kind of creatorship (approved or
  # unapproved) associated with this item.
  def user_is_owner_or_invited?(user)
    return false unless user.is_a?(User)
    creatorships.for_user(user).exists?
  end
end
