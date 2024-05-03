package com.hyperhire.whtsapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hyperhire.whtsapp.model.Message;
@Repository
public interface MessageRepo  extends JpaRepository<Message, Long>{

	Page<Message> findByChatroomId(Long chatroomId,Pageable pageable);
}
