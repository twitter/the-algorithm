namespace :admin do
  desc "Unsuspend suspended users who have been suspended_until up to 12 hours from now"
  task(:unsuspend_users => :environment) do
    User.where(["suspended_until <= ?", 12.hours.from_now]).update_all("suspended_until = NULL, suspended = false")
    puts "Users unsuspended."
  end

  desc "Resend sign-up notification emails after 24 hours"
  task(:resend_signup_emails => :environment) do
    @users = User.where(confirmed_at: nil, created_at: 48.hours.ago..24.hours.ago)
    @users.each do |user|
      UserMailer.signup_notification(user.id).deliver_later
    end
    puts "Sign-up notification emails resent"
  end

  desc "Purge unvalidated accounts created more than 2 weeks ago"
  task(:purge_unvalidated_users => :environment) do
    users = User.where("confirmed_at IS NULL AND created_at < ?", 2.weeks.ago)
    puts users.map(&:login).join(", ")
    users.map(&:destroy)
    puts "Unvalidated accounts created more than two weeks ago have been purged"

    # Purged users are allowed to reuse their invitations:
    invite_ids = users.map(&:invitation_id)
    Invitation.includes(:creator).where(id: invite_ids).each do |invite|
      invite.update(redeemed_at: nil, invitee: nil)
    end
    puts "Invitations for the purged accounts have been reset"
  end

end
