class Settings {

  private static Settings instance;
  private String language;

  private Settings() {
  } // приватный конструктор

  public static Settings getInstance() {
    if (instance == null) {
      instance = new Settings();
    }
    return instance;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getLanguage() {
    return language;
  }
}