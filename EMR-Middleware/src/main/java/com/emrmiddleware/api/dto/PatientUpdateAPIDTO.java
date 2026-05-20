package com.emrmiddleware.api.dto;

import java.util.ArrayList;

/**
 * Partial update for OpenMRS {@code POST patient/{uuid}}. Uses nested {@code {uuid}} refs for
 * {@code person}, {@code identifierType}, and {@code location} (create-style strings cause cast errors).
 */
public class PatientUpdateAPIDTO {
  private OpenMrsUuidRefAPIDTO person;
  private ArrayList<IdentifierUpdateAPIDTO> identifiers;

  public OpenMrsUuidRefAPIDTO getPerson() {
    return person;
  }

  public void setPerson(OpenMrsUuidRefAPIDTO person) {
    this.person = person;
  }

  public ArrayList<IdentifierUpdateAPIDTO> getIdentifiers() {
    return identifiers;
  }

  public void setIdentifiers(ArrayList<IdentifierUpdateAPIDTO> identifiers) {
    this.identifiers = identifiers;
  }
}
