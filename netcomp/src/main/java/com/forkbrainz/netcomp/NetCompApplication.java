package com.forkbrainz.netcomp;

import com.forkbrainz.netcomp.user.NetCompUser;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NetCompApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetCompApplication.class, args);
    }
    
    @Bean
    public Nitrite database(){
        return Nitrite.builder()
            .filePath("app.db")
            .openOrCreate("yasir", "password");
    }
    
    @Bean(name = "NetCompUserRepo")
    public ObjectRepository<NetCompUser> netCompUserCollection(){
        return database().getRepository(NetCompUser.class);
    }
}
