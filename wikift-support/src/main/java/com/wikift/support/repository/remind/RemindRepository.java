package com.wikift.support.repository.remind;

import com.wikift.model.remind.RemindEntity;
import com.wikift.model.user.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RemindRepository extends PagingAndSortingRepository<RemindEntity, Long> {

    /**
     * 根据用户查询该用户所有的提示消息
     *
     * @return 提示信息列表
     * @param users
     */
    List<RemindEntity> findAllByUsers(List<UserEntity> users);

    /**
     * 根据用户查询该用户所有的未读提示消息
     *
     * @return 提示信息列表
     * @param users
     */
    List<RemindEntity> findAllByUsersAndReadTimeIsNull(List<UserEntity> users);

}
