package rs.xml.ws.agentsoapconsumer.repositories.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.agentsoapconsumer.models.entities.Client;

@Repository
public interface IClientRepository extends JpaRepository< Client, Long >
{

}
