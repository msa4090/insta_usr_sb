package com.sbs.untact.dao;

import com.sbs.untact.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDao {
    Member getMemberByLoginId(@Param("loginId") String loginId);
    Member getMemberById(@Param("id") int id);

    void join(@Param("loginId") String loginId, @Param("loginPw") String loginPw, @Param("name") String name, @Param("nickName") String nickName,
              @Param("cellPhoneNo") String cellPhoneNo, @Param("email") String email);

    int getLastInsertId();
}
