package se.iths.springbootgroupproject.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.iths.springbootgroupproject.dto.MessageAndUsername;
import se.iths.springbootgroupproject.repositories.MessageRepository;

import java.util.List;

@Service
@Transactional
public class MessageService {

    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Cacheable("publicMessages")
    public List<MessageAndUsername> findAllByPrivateMessageIsFalse() {
        return messageRepository.findAllByPrivateMessageIsFalse();
    }
    @Cacheable("publicMessages")
    public List<MessageAndUsername> findAllByPrivateMessageIsFalse(Pageable pageable){
        return messageRepository.findAllByPrivateMessageIsFalse(pageable);
    }
    @Cacheable("messages")
    public List<MessageAndUsername> findAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable).getContent().stream()
                .map(message -> new MessageAndUsername(
                        message.getDate(),
                        message.getTitle(),
                        message.getMessageBody(),
                        message.getUser().getUserName()))
                .toList();
    }
    @Cacheable("messages")
    public List<MessageAndUsername> findAllMessages() {
        return messageRepository.findAll().stream()
                .map(message -> new MessageAndUsername(
                        message.getDate(),
                        message.getTitle(),
                        message.getMessageBody(),
                        message.getUser().getUserName()))
                .toList();
    }

    @CacheEvict(value = {"messages", "publicMessages"}, allEntries = true)
    public void setMessagePrivacy(boolean isPrivate, Long id) {
        messageRepository.setMessagePrivacy(isPrivate, id);
    }

    @CacheEvict(value = {"messages", "publicMessages"}, allEntries = true)
    public void editMessage(String updatedBody, Long id) {
        messageRepository.editMessage(updatedBody, id);
    }

    @CacheEvict(value = {"messages", "publicMessages"}, allEntries = true)
    public void editTitle(String updatedTitle, Long id) {
        messageRepository.editTitle(updatedTitle, id);
    }
}