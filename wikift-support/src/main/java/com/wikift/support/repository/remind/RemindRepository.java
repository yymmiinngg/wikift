package com.wikift.support.repository.remind;

import com.wikift.model.remind.RemindEntity;
import com.wikift.model.user.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RemindRepository extends PagingAndSortingRepository<RemindEntity, Long> {

    /**
     * 根据用户查询该用户所有的提示消息
     *
     * @param users 当前用户
     * @return 提示信息列表
     */
    List<RemindEntity> findAllByUsers(List<UserEntity> users);

    /**
     * 根据用户查询该用户所有的未读提示消息
     *
     * @param users 当前用户
     * @return 未读提示信息
     */
    List<RemindEntity> findAllByUsersAndReadTimeIsNull(List<UserEntity> users);

    /**
     * 根据用户查询该用户所有已读提示信息
     *
     * @param users 当前用户
     * @return 已读提示信息
     */
    List<RemindEntity> findAllByUsersAndReadTimeNotNull(List<UserEntity> users);

    /**
     * 阅读消息
     *
     * @param id 消息id
     * @return 阅读状态
     */
    @Modifying
    @Query(value = "UPDATE remind " +
            "SET r_read = TRUE, r_read_time = CURRENT_TIMESTAMP() " +
            "WHERE r_id = ?1",
            nativeQuery = true)
    Integer read(Long id);

}
