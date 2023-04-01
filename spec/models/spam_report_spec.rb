require "spec_helper"

describe SpamReport do
  let(:author) { create(:user).default_pseud }
  let!(:first_spam) { create(:work, spam: true, authors: [author]) }
  let!(:second_spam) { create(:work, spam: true, authors: [author]) }
  let!(:third_spam) { create(:work, spam: true, authors: [author]) }

  let(:message_double) { instance_double(ActionMailer::MessageDelivery, deliver_later: true) }

  before do
    allow(ArchiveConfig).to receive(:SPAM_THRESHOLD).and_return(10)
    create(:work, spam: false)
  end

  it "has a recent date after the new date" do
    spam_report = SpamReport.new
    expect(spam_report.new_date).to be > spam_report.recent_date
  end

  it "does not send email if the spam score is lower than the spam threshold" do
    allow(ArchiveConfig).to receive(:SPAM_THRESHOLD).and_return(70)
    expect(AdminMailer).not_to receive(:send_spam_alert)
    SpamReport.run
  end

  it "sends email if the spam score is higher than the spam threshold" do
    expect(AdminMailer).to receive(:send_spam_alert)
      .with({ author.user_id => { score: 13,
                                  work_ids: [first_spam.id, second_spam.id, third_spam.id] } })
      .and_return(message_double)
    SpamReport.run
  end

  it "increases the spam score if the work creator has used more than one IP address" do
    third_spam.ip_address = "192.168.11.1"
    third_spam.save
    expect(AdminMailer).to receive(:send_spam_alert)
      .with({ author.user_id => { score: 14,
                                  work_ids: [first_spam.id, second_spam.id, third_spam.id] } })
      .and_return(message_double)
    SpamReport.run
  end

  it "reduces the spam score if works are posted in the recent past" do
    expect(AdminMailer).to receive(:send_spam_alert)
      .with({ author.user_id => { score: 13,
                                  work_ids: [first_spam.id, second_spam.id, third_spam.id] } })
      .and_return(message_double)
    SpamReport.run
    create(:work, spam: false, authors: [author], created_at: 3.days.ago)
    expect(AdminMailer).to receive(:send_spam_alert)
      .with({ author.user_id => { score: 11,
                                  work_ids: [first_spam.id, second_spam.id, third_spam.id] } })
      .and_return(message_double)
    SpamReport.run
  end
end
