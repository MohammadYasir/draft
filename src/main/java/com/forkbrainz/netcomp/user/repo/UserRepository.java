/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forkbrainz.netcomp.user.repo;

import com.forkbrainz.netcomp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mohyasir
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public User findByUsername(String username);
    public User findByEmail(String email);
}
