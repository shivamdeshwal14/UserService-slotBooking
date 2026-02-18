package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrganisationResponse {
private Long orgId;
private String name;
private String location;

public Long getOrgId() {return orgId;}
public String getName() {return name;}
public String getLocation() {return location;}

public void setOrgId(Long orgId) {this.orgId=orgId;}
public void setName(String name) {this.name=name;}
public void setLocation(String location) {this.location=location;}

}
