# frozen_string_literal: true

require "zoho_auth_client"
require "zoho_resource_client"

class FeedbackReporter
  include HtmlCleaner
  require "url_formatter"

  attr_accessor :title,
                :description,
                :email,
                :language,
                :category,
                :username,
                :url

  def initialize(attrs = {})
    attrs.each_pair do |key, val|
      self.send("#{key}=", val)
    end
  end

  def title
    strip_html_breaks_simple(@title)
  end

  def description
    add_break_between_paragraphs(@description)
  end

  def send_report!
    zoho_resource_client.create_ticket(ticket_attributes: report_attributes)
  end

  def report_attributes
    {
      "email" => email,
      "contactId" => zoho_contact_id,
      "cf" => {
        "cf_language" => language.presence || Language.default.name,
        "cf_name" => username.presence || "Anonymous user"
      }
    }
  end

  private

  def zoho_contact_id
    zoho_resource_client.retrieve_contact_id
  end

  def access_token
    @access_token ||= ZohoAuthClient.new.access_token
  end

  def zoho_resource_client
    @zoho_resource_client ||= ZohoResourceClient.new(
      access_token: access_token,
      email: email
    )
  end
end
