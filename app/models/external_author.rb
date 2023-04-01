class ExternalAuthor < ApplicationRecord
  # send :include, Activation # eventually we will let users create new identities

  EMAIL_LENGTH_MIN = 3
  EMAIL_LENGTH_MAX = 300

  belongs_to :user

  has_many :external_author_names, dependent: :destroy
  accepts_nested_attributes_for :external_author_names, allow_destroy: true
  validates_associated :external_author_names

  has_many :external_creatorships, through: :external_author_names
  has_many :works, -> { distinct }, through: :external_creatorships, source: :creation, source_type: 'Work'

  has_one :invitation

  validates :email, uniqueness: {
    allow_blank: true,
    message: ts('There is already an external author with that email.')
  }

  validates :email, email_veracity: true

  def self.claimed
    where(is_claimed: true)
  end

  def self.unclaimed
    where(is_claimed: false)
  end

  after_create :create_default_name

  def external_work_creatorships
    external_creatorships.where("external_creatorships.creation_type = 'Work'")
  end

  def create_default_name
    @default_name = self.external_author_names.build
    @default_name.name = self.email.to_s
    self.save
  end

  def default_name
    self.external_author_names.select {|external_name| external_name.name == self.email.to_s }.first
  end

  def names
    self.external_author_names
  end

  def claimed?
    is_claimed
  end

  def claim!(claiming_user)
    raise "There is no user claiming this external author." unless claiming_user
    raise "This external author is already claimed by another user" if claimed? && self.user != claiming_user

    claimed_works = []
    external_author_names.each do |external_author_name|
      external_author_name.external_work_creatorships.each do |external_creatorship|
        work = external_creatorship.creation
        other_external_creators = work.external_creatorships - [external_creatorship]
        other_unclaimed_creators = other_external_creators.reject(&:claimed?)

        # if previously claimed by this user, don't do it again
        unless work.users.include?(claiming_user)
          # Get the pseud to associate with the work
          pseud_to_add = claiming_user.pseuds.select {|pseud| pseud.name == external_author_name.name}.first || claiming_user.default_pseud

          # If there are no other unclaimed authors, or if none of the other unclaimed authors have the same archivist,
          # remove this user's archivist from the work creators, else just add the claiming user
          claiming_user_archivist = external_creatorship.archivist
          if other_unclaimed_creators.map(&:archivist).exclude?(claiming_user_archivist)
            change_ownership(work, claiming_user_archivist, claiming_user, pseud_to_add)
          else
            add_creator(work, claiming_user, pseud_to_add)
          end
          claimed_works << work.id
        end
      end
    end

    self.user = claiming_user
    self.is_claimed = true
    save
    notify_user_of_claim(claimed_works)
  end

  def unclaim!
    return false unless self.is_claimed

    self.external_work_creatorships.each do |external_creatorship|
      # remove user, add archivist back
      archivist = external_creatorship.archivist
      work = external_creatorship.creation
      change_ownership(work, user, archivist)
    end

    self.user = nil
    self.is_claimed = false
    save
  end

  def orphan(remove_pseud)
    external_author_names.each do |external_author_name|
      external_author_name.external_work_creatorships.each do |external_creatorship|
        # remove archivist as owner, convert to the pseud
        archivist = external_creatorship.archivist
        work = external_creatorship.creation
        archivist_pseud = work.pseuds.select {|pseud| archivist.pseuds.include?(pseud)}.first
        orphan_pseud = remove_pseud ? User.orphan_account.default_pseud : User.orphan_account.pseuds.find_or_create_by(name: external_author_name.name)
        change_ownership(work, archivist, User.orphan_account, orphan_pseud)
      end
    end
  end

  def delete_works
    self.external_work_creatorships.each do |external_creatorship|
      work = external_creatorship.creation
      work.destroy
    end
  end

  def block_import
    self.do_not_import = true
    save
  end

  def notify_user_of_claim(claimed_work_ids)
    # send announcement to user of the stories they have been given
    UserMailer.claim_notification(self.id, claimed_work_ids).deliver_later
  end

  def find_or_invite(archivist = nil)
    if self.email
      matching_user = User.find_by(email: self.email) || User.find_by_id(self.user_id)
      if matching_user
        self.claim!(matching_user)
      else
        # invite person at the email address unless they don't want invites
        unless self.do_not_email
          @invitation = Invitation.new(invitee_email: self.email, external_author: self, creator: User.current_user)
          @invitation.save
        end
      end
    end
  end

  private

  # Add a new creator to the work:
  def add_creator(work, creator_to_add, new_pseud = nil)
    new_pseud = creator_to_add.default_pseud if new_pseud.nil?

    work.transaction do
      work.creatorships.find_or_create_by(pseud: new_pseud)

      work.chapters.each do |chapter|
        chapter.creatorships.find_or_create_by(pseud: new_pseud)
      end
    end
  end

  # Transfer ownership of the work from one user to another
  def change_ownership(work, old_user, new_user, new_pseud = nil)
    raise "No new user provided, cannot change ownership" unless new_user

    work.transaction do
      add_creator(work, new_user, new_pseud)
      work.remove_author(old_user)
    end
  end
end
