package model.user.applicant;

import enumerators.PositionType;
import exceptions.InternationalStudentAvailabilityException;
import model.system.ManagementSystem;

public class InternationalStudent extends Applicant {
  
  public InternationalStudent(String firstName, String lastName, String password, ManagementSystem managementSystem) {
    super(firstName, lastName, password, PositionType.PART_TIME, managementSystem);
  }
  
  // throws an exception if an international student tries to add a new availability
  // as International Students can only work part-time ie < 20h per week
  @Override
  public void addAvailability(PositionType availability)
          throws InternationalStudentAvailabilityException {
    throw new InternationalStudentAvailabilityException();
  }
  
  @Override
  public void addAvailability(String availability)
          throws InternationalStudentAvailabilityException {
    throw new InternationalStudentAvailabilityException();
  }
  
  // throws an exception if an international student tries to remove an availability
  // as International Students can only work part-time ie < 20h per week
  @Override
  public boolean removeAvailability(PositionType availability)
          throws InternationalStudentAvailabilityException {
    throw new InternationalStudentAvailabilityException();
  }
  
  @Override
  public boolean removeAvailability(String availability)
          throws InternationalStudentAvailabilityException {
    throw new InternationalStudentAvailabilityException();
  }
}
