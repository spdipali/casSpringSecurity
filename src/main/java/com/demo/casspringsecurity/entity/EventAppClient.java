package com.demo.casspringsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "APP_CLIENT")
public class EventAppClient {

    @Id
    @Column(name = "client_id")
    String clientId;

    @NotBlank
    @Column(name = "client_secret")
    String clientSecret;

    @NotBlank
    @Column(name = "authorized_grants")
    String authorizedGrantTypes;

    @NotBlank
    @Column(name = "client_scope")
    String clientScopeList;

    @NotBlank
    @Column(name = "client_authorities")
    String clientAuthorityList;

    @NotBlank
    @Column(name = "access_token_validity_sec")
    Integer accessTokenValiditySeconds;

    @NotBlank
    @Column(name = "refresh_token_validity_sec")
    Integer refreshTokenValiditySeconds;
}
