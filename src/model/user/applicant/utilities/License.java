package model.user.applicant.utilities;

import java.time.LocalDate;

public class License {
  private String licenseName;
  private int licenseNumber;
  private String issuer;
  private LocalDate dateIssued;
  private LocalDate validUntil;
  
  public License(String licenseName, int licenseNumber, String issuer, LocalDate dateIssued, LocalDate validUntil) {
    this.licenseName = licenseName;
    this.licenseNumber = licenseNumber;
    this.issuer = issuer;
    this.dateIssued = dateIssued;
    this.validUntil = validUntil;
  }
  
  public String getLicenseName() {
    return licenseName;
  }
  
  public int getLicenseNumber() {
    return licenseNumber;
  }
  
  public String getIssuer() {
    return issuer;
  }
  
  public LocalDate getDateIssued() {
    return dateIssued;
  }
  
  public LocalDate getValidUntil() {
    return validUntil;
  }
  
  public void setLicenseName(String licenseName) {
    this.licenseName = licenseName;
  }
  
  public void setLicenseNumber(int licenseNumber) {
    this.licenseNumber = licenseNumber;
  }
  
  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }
  
  public void setDateIssued(LocalDate dateIssued) {
    this.dateIssued = dateIssued;
  }
  
  public void setValidUntil(LocalDate validUntil) {
    this.validUntil = validUntil;
  }

  public String toString()
  {
    return "Licence name: " + licenseName + "\nLicence number: " + licenseNumber + "\nIssuer: " + issuer + "\nDate issued: " + dateIssued.toString() + "\nValid until: " + validUntil.toString();
  }
}
