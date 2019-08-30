package enums;

public enum Entity {
  EMPLOYER("1"), STUDENT("2"), SYSTEM_MAINTENANCE_STAFF("3");
  
  private String val;
  
  Entity(String s) {
    this.val = val;
  }
  
  public String getVal() {
    return val;
  }
}
