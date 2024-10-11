package com.jykng.thinkon.usermanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
 * Your users should store the following information:
• A username
• A first name
• A last name
• An email
• A phone number
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column
    public String firstName;

    @Column
    public String lastName;

    @Column
    public String email;

    @Column
    public String phoneNumber;
}
