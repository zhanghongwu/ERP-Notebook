package org.jufe.erp.repository.photo;

import org.jufe.erp.entity.PhotoWall;
import org.jufe.erp.repository.BaseInterface;
import org.jufe.erp.repository.BaseRepository;
import org.jufe.erp.repository.Page;
import org.jufe.erp.utils.MongoUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by raomengnan on 16-8-31.
 */
public interface PhotoWallRepository extends BaseInterface<PhotoWall> {
    public List<PhotoWall> findByGrade(int grade);
    public List<PhotoWall> findByUserNameLike(String userName);

    public boolean updateUserName(String id, String userName);

    public boolean updateUrl(String id, String url);

    /**
     * 按年级从低到高排列 例如 2016---2013
     * @param pno
     * @param pSize
     * @return
     */
    public Page<PhotoWall> findPage(int pno, int pSize);
}
