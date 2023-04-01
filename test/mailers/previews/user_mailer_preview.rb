class UserMailerPreview < ApplicationMailerPreview
  # Sends email when an archivist adds someone as a co-creator.
  def creatorship_notification_archivist
    second_creatorship, first_creator = creatorship_notification_data
    UserMailer.creatorship_notification_archivist(second_creatorship.id, first_creator.id)
  end

  # Sends email when a user is added as a co-creator
  def creatorship_notification
    second_creatorship, first_creator = creatorship_notification_data
    UserMailer.creatorship_notification(second_creatorship.id, first_creator.id)
  end

  # Sends email when a user is added as an unapproved/pending co-creator
  def creatorship_request
    second_creatorship, first_creator = creatorship_notification_data
    UserMailer.creatorship_request(second_creatorship.id, first_creator.id)
  end

  private

  def creatorship_notification_data
    first_creator = create(:user, login: "JayceHexmaster")
    second_creator = create(:user, login: "viktor_the_machine")
    work = create(:work, authors: [first_creator.default_pseud, second_creator.default_pseud])
    second_creatorship = Creatorship.find_by(creation: work, pseud: second_creator.default_pseud)
    [second_creatorship, first_creator]
  end
end
