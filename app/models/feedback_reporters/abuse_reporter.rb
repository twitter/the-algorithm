class AbuseReporter < FeedbackReporter
  attr_accessor :ip_address

  def report_attributes
    super.deep_merge(
      "departmentId" => department_id,
      "subject" => subject,
      "description" => ticket_description,
      "cf" => custom_zoho_fields
    )
  end

  private

  def custom_zoho_fields
    {
      "cf_ip" => ip_address.presence || "Unknown IP",
      "cf_url" => url.presence || "Unknown URL"
    }
  end

  def department_id
    ArchiveConfig.ABUSE_ZOHO_DEPARTMENT_ID
  end

  def subject
    return "[#{ArchiveConfig.APP_SHORT_NAME}] Abuse - #{title.html_safe}" if title.present?

    "[#{ArchiveConfig.APP_SHORT_NAME}] Abuse - No Subject"
  end

  def ticket_description
    description.present? ? description.html_safe : "No comment submitted."
  end
end
