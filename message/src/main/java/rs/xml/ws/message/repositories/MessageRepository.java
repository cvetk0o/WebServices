package rs.xml.ws.message.repositories;

import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import rs.xml.ws.message.models.Message;

@Repository
public interface MessageRepository extends JpaRepository< Message, Long >
{

	Set< Message > findBySender( Long sender );

	Set< Message > findByReceiver( Long receiver );

	Set< Message > findByReceiverAndSender( Long receiver, Long sender );

	Set< Message > findByReceiverAndSenderAndSubject( Long receiver, Long sender, String subject );

	Set< Message > findByCompany( Long company );

}
