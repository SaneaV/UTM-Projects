package org.example;

import org.springframework.stereotype.Component;

@Component
public class AccessPolicy {

  public boolean isCityAllowed(String city, String role) {
    return "iserlia".equalsIgnoreCase(city) && !"super-admin".equalsIgnoreCase(role);
  }

  public boolean isAuthorized(String city, String role) {
    if ("super-admin".equalsIgnoreCase(role)) return false;
    if (role != null && role.startsWith("mayor_")) {
      return role.substring(6).equalsIgnoreCase(city);
    }
    return false;
  }
}

