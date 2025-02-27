package me.zort.acs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AcsApplication {
    // TODO: Roles (packages of scoped target rights that can be granted to a resource)
    // TODO: Node subgrants, tedy možnost nastavit, že některé nody budou automaticky povolovat jiné z úplně jiného scopu. dobré například pro situaci, kdy organizace (scope organization například) mající repozitáře povolí všem uživatelům zásah do repozitářů, pomocí repositories:access bez nutnosti přidávat jim práva do scope repository

    public static void main(String[] args) {
        SpringApplication.run(AcsApplication.class, args);
    }
}
