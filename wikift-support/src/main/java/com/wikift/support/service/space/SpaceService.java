package com.wikift.support.service.space;

import com.wikift.model.space.SpaceEntity;
import com.wikift.model.user.UserEntity;

import java.util.List;

public interface SpaceService {

    /**
     * 获取所有的空间
     *
     * @return 空间列表
     */
    List<SpaceEntity> getAllSpace();

    /**
     * 创建空间
     *
     * @param entity 空间信息
     * @return 空间信息
     */
    SpaceEntity createSpace(SpaceEntity entity);

    /**
     * 根据空间编码获取空间信息
     *
     * @param code 空间编码
     * @return 空间信息
     */
    SpaceEntity getSpaceInfoByCode(String code);

    /**
     * 根据空间id获取空间信息
     *
     * @param id 空间id
     * @return 空间信息
     */
    SpaceEntity getSpaceInfoById(Long id);

    List<SpaceEntity> getAllSpaceByPrivatedFalseOrUser(UserEntity entity);

}
