class ModeratedWork < ApplicationRecord
  belongs_to :work
  validates :work_id, uniqueness: true

  delegate :title, :revised_at, to: :work

  def self.register(work)
    find_or_create_by(work_id: work.id)
  end

  def self.mark_reviewed(work)
    register(work).mark_reviewed!
  end

  def self.mark_approved(work)
    register(work).mark_approved!
  end

  def self.bulk_update(params = {})
    ids = processed_bulk_ids(params)
    transaction do
      bulk_review(ids[:spam])
      bulk_approve(ids[:ham])
    end
  end

  def self.processed_bulk_ids(params = {})
    spam_ids = params[:spam] || []
    ham_ids = params[:ham] || []
    # Ensure no overlap
    admin_confusion = spam_ids & ham_ids
    if admin_confusion
      spam_ids -= admin_confusion
      ham_ids -= admin_confusion
    end
    { spam: spam_ids, ham: ham_ids }
  end

  def self.bulk_review(ids)
    return true unless ids.present?
    where(id: ids).update_all(reviewed: true, approved: false)
    # Ensure works are hidden and spam if they weren't already
    Work.joins(:moderated_work).where("moderated_works.id IN (?)", ids).each do |work|
      work.update(hidden_by_admin: true, spam: true)
    end
  end

  def self.bulk_approve(ids)
    return true unless ids.present?
    where(id: ids).update_all("approved = 1")
    Work.joins(:moderated_work).where("moderated_works.id IN (?)", ids).each do |work|
      work.mark_as_ham!
    end    
  end

  def mark_reviewed!
    update_attribute(:reviewed, true)
  end

  def mark_approved!
    update_attribute(:approved, true)
  end

  # Easy access to the creators of spam works
  def admin_user_links
    work.users.map do |u|
      "<a href='/admin/users/#{u.to_param}'>#{u.login}</a>"
    end.join(", ").html_safe
  end
end
