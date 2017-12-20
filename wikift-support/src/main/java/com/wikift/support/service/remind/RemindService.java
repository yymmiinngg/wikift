package com.wikift.support.service.remind;

import com.wikift.model.remind.RemindEntity;
import com.wikift.model.user.UserEntity;

import java.util.List;

public interface RemindService {

    /**
     * 保存提醒信息
     *
     * @return 提醒信息
     */
    RemindEntity save(RemindEntity entity);

    List<RemindEntity> findAll();

    List<RemindEntity> getAllRemindByUsers(List<UserEntity> users);

    List<RemindEntity> getAllUnreadRemindByUsers(List<UserEntity> users);

}
