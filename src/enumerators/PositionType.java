package enumerators;

import exceptions.PositionTypeNotFoundException;

public enum PositionType {
  PART_TIME, FULL_TIME, INTERNSHIP;
  
  public static PositionType determinePositionType(String type)
          throws PositionTypeNotFoundException {
    PositionType match = null;
    for (PositionType t : PositionType.values()) {
      if (t.toString().equals(type.toUpperCase())) {
        match = t;
      }
    }
    if (match == null) {
      throw new PositionTypeNotFoundException();
    }
    return match;
  }
}
