package com.team5.secondhand.chat.bubble.service;

import com.team5.secondhand.chat.bubble.domain.ChatBubble;
import com.team5.secondhand.chat.bubble.repository.ChatBubbleCache;
import com.team5.secondhand.chat.bubble.repository.ChatBubbleRepository;
import com.team5.secondhand.chat.bubble.repository.entity.BubbleEntity;
import com.team5.secondhand.chat.bubble.event.ChatBubbleArrivedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatBubbleService {
    @Value("${const.chat.bucket}")
    private String chatBucketPrefix;
    @Value("${const.chat.page-size}")
    private int chatLoadSize;
    private final ChatBubbleRepository bubbleRepository;
    private final ChatBubbleCache bubbleCache;

    @Transactional(readOnly = true)
    public Slice<ChatBubble> getChatBubbles(int page, String roomId) {
        String key = generateChatLogKey(roomId);
        Pageable pageable = PageRequest.of(page, chatLoadSize, Sort.by("createdAt").ascending());
        Slice<BubbleEntity> list = bubbleRepository.findAllByChatroomId(roomId, pageable);
        //TODO service 로직 변경
        return null;
    }

    public ChatBubble saveChatBubble(ChatBubble chatBubble) {
        String key = generateChatLogKey(chatBubble.getChatroomId());
        BubbleEntity bubble= bubbleRepository.save(BubbleEntity.fromDomain(chatBubble));
        return bubble.toDomain();
    }

    @Async
    @EventListener
    public void getChatBubble(ChatBubbleArrivedEvent chatBubbleArrivedEvent) {
        ChatBubble chatBubble = chatBubbleArrivedEvent.getChatBubble();
        saveChatBubble(chatBubble);
    }

    private String generateChatLogKey (String roomId) {
        return String.format("%s%s:logs", chatBucketPrefix, roomId);
    }
}
