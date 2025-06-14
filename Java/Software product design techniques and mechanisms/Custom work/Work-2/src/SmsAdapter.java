class SmsAdapter implements Notification {

  private SmsService smsService;
  private String phone;

  public SmsAdapter(SmsService smsService, String phone) {
    this.smsService = smsService;
    this.phone = phone;
  }

  public void send(String message) {
    smsService.sendSms(phone, message);
  }
}